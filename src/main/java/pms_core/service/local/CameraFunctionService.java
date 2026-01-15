package pms_core.service.local;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pms_core.config.CameraProperties;
import pms_core.config.JSON;
import pms_core.model.local.request.CameraPayload;

import javax.net.ssl.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class CameraFunctionService {

    private final DigestAuthService  digestAuthService;
    private final CameraProperties properties;

    public CameraPayload parsePayload(String body, String cameraType) {
        CameraPayload payload = new CameraPayload();
        try {
            JSON.Object json = new JSON(body).parseObject();
            String plate;
            String date;
            switch (cameraType) {
                case "vivotekV2":
                    json = json.getObject("infoplate");
                    plate = json.getString("Plate");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    date = LocalDateTime.parse(json.getString("DateHour"), formatter2).toString();
                    break;
                case "vivotekV3":
                    plate = json.getString("license_plate");
                    DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    date = LocalDateTime.parse(json.getString("time"), formatter3).toString();
                    break;
                default:
                    plate = json.getString("plate");
                    OffsetDateTime offsetDateTime = OffsetDateTime.parse(json.getString("date"));
                    date = offsetDateTime.toLocalDateTime().toString();
            }
            payload.setPlate(plate);
            payload.setDateTime(date);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return payload;
    }

    @Async("cameraExecutor")
    public void openDoor(String cameraType, String ip) {

        log.info("Opening door for cameraType={} ip={}", cameraType, ip);

        try {
            if ("vivotekV3".equals(cameraType)) {
                openVivotek(ip);
            } else {
                openDido(ip);
            }
        } catch (Exception e) {
            log.error("Open door error", e);
        }
    }

    private void openVivotek(String ip) throws Exception {

        String url = "http://" + ip +
                "/cgi-bin/operator/operator.cgi?action=set.event.io&format=json";

        String jsonOpen =
                "{\"outputIoList\":[{\"index\":0,\"outputManual\":1,\"outputTime\":-2}]}";
        String jsonClose =
                "{\"outputIoList\":[{\"index\":0,\"outputManual\":0,\"outputTime\":-2}]}";

        for (int i = 0; i < 10; i++) {
            if (sendDigestPost(url,properties.getVivotek().getUsername(),properties.getVivotek().getPassword(), jsonOpen)) break;
        }

        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            if (sendDigestPost(url,properties.getDidoUsername(),properties.getDidoPassword(), jsonClose)) break;
        }

        log.info("Vivotek door triggered");
    }

    private void openDido(String ip) throws Exception {

        String url = String.format(properties.getDidoUri(), ip);

        String auth = properties.getDidoUsername() + ":" + properties.getDidoPassword();
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());

        for (int i = 0; i < 10; i++) {
            if (sendBasic(url + "?do0=1", encoded)) break;
        }

        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            if (sendBasic(url + "?do0=0", encoded)) break;
        }

        log.info("DIDO door triggered");
    }

    private boolean sendBasic(String url, String auth) {
        try {
            HttpURLConnection con =
                    (HttpURLConnection) new URL(url).openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Basic " + auth);
            con.setConnectTimeout(10_000);

            if (con instanceof HttpsURLConnection) {
                try {
                    TrustManager[] tmp = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return null;
                                }

                                @Override
                                public void checkClientTrusted(
                                        X509Certificate[] chain,
                                        String type) {
                                }

                                @Override
                                public void checkServerTrusted(
                                        X509Certificate[] chain,
                                        String type
                                ) {
                                }
                            }
                    };
                    SSLContext context
                            = SSLContext.getInstance("SSL");
                    context.init(
                            null,
                            tmp,
                            new java.security.SecureRandom()
                    );
                    SSLSocketFactory factory = context.getSocketFactory();
                    ((HttpsURLConnection) con).setSSLSocketFactory(factory);
                } catch (
                        NoSuchAlgorithmException
                        | KeyManagementException e
                ) {
                    log.info("HTTPS URL Connection Error: {}", e.getMessage());
                }
            }

            return con.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean sendDigestPost(String url, String username , String password , String json) {
       return digestAuthService.postJson(url,username,password,json);
    }
}

