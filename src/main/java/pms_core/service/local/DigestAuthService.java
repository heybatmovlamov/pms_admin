package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DigestAuthService {

    private final RestTemplate restTemplate;

    public boolean postJson(
            String url,
            String username,
            String password,
            String jsonBody
    ) {

        try {
            // 1️⃣ First request (challenge almaq üçün)
            HttpHeaders h1 = new HttpHeaders();
            h1.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> e1 = new HttpEntity<>(jsonBody, h1);

            ResponseEntity<String> r1 = null;
            try {
                r1 = restTemplate.exchange(url, HttpMethod.POST, e1, String.class);
                if (r1.getStatusCode().is2xxSuccessful()) {
                    log.info("Digest accepted without challenge");
                    return true;
                }
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() != HttpStatus.UNAUTHORIZED) {
                    log.warn("Expected 401, got {}", ex.getStatusCode());
                    return false;
                }
            }

            // 2️⃣ WWW-Authenticate parse
            HttpHeaders respHeaders = r1.getHeaders();

            String wwwAuth = respHeaders.getFirst(HttpHeaders.WWW_AUTHENTICATE);
            if (wwwAuth == null || !wwwAuth.toLowerCase().startsWith("digest")) {
                log.error("No Digest header");
                return false;
            }

            Map<String, String> digest = parseDigest(wwwAuth);

            String realm = digest.get("realm");
            String nonce = digest.get("nonce");
            String qop = digest.getOrDefault("qop", "auth");
            String opaque = digest.get("opaque");

            String uri = URI.create(url).getPath();
            String nc = "00000001";
            String cnonce = randomHex(16);

            String ha1 = md5(username + ":" + realm + ":" + password);
            String ha2 = md5("POST:" + uri);
            String response = md5(
                    ha1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":" + ha2
            );

            String authHeader = buildHeader(
                    username, realm, nonce, uri,
                    response, qop, nc, cnonce, opaque
            );

            // 3️⃣ Second request (Authorization ilə)
            HttpHeaders h2 = new HttpHeaders();
            h2.setContentType(MediaType.APPLICATION_JSON);
            h2.set(HttpHeaders.AUTHORIZATION, authHeader);

            HttpEntity<String> e2 = new HttpEntity<>(jsonBody, h2);

            ResponseEntity<String> r2 =
                    restTemplate.exchange(url, HttpMethod.POST, e2, String.class);

            if (r2.getStatusCode().is2xxSuccessful()) {
                log.info("Digest POST OK");
                return true;
            }

            log.warn("Digest POST failed: {}", r2.getStatusCode());
            return false;

        } catch (Exception e) {
            log.error("Digest POST exception", e);
            return false;
        }
    }

    /* =====================
       HELPERS
       ===================== */

    private Map<String, String> parseDigest(String header) {
        Map<String, String> map = new HashMap<>();

        String h = header.substring(header.indexOf("Digest") + 6).trim();
        boolean inQuotes = false;
        StringBuilder cur = new StringBuilder();
        List<String> parts = new ArrayList<>();

        for (char c : h.toCharArray()) {
            if (c == '"') inQuotes = !inQuotes;
            if (c == ',' && !inQuotes) {
                parts.add(cur.toString().trim());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        if (cur.length() > 0) parts.add(cur.toString().trim());

        for (String p : parts) {
            String[] kv = p.split("=", 2);
            if (kv.length != 2) continue;
            map.put(kv[0].trim(), kv[1].replace("\"", "").trim());
        }
        return map;
    }

    private String buildHeader(
            String user, String realm, String nonce, String uri,
            String resp, String qop, String nc,
            String cnonce, String opaque
    ) {
        StringBuilder sb = new StringBuilder("Digest ");
        sb.append("username=\"").append(user).append("\", ");
        sb.append("realm=\"").append(realm).append("\", ");
        sb.append("nonce=\"").append(nonce).append("\", ");
        sb.append("uri=\"").append(uri).append("\", ");
        sb.append("response=\"").append(resp).append("\", ");
        sb.append("qop=").append(qop).append(", ");
        sb.append("nc=").append(nc).append(", ");
        sb.append("cnonce=\"").append(cnonce).append("\"");

        if (opaque != null) sb.append(", opaque=\"").append(opaque).append("\"");
        return sb.toString();
    }

    private String md5(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dig = md.digest(s.getBytes(StandardCharsets.ISO_8859_1));
        StringBuilder sb = new StringBuilder();
        for (byte b : dig) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private String randomHex(int bytes) {
        byte[] b = new byte[bytes];
        new SecureRandom().nextBytes(b);
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }
}

