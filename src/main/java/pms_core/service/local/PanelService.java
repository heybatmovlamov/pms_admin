package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class PanelService {

    private static final Logger log = LoggerFactory.getLogger(PanelService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, ExecutorService> executors = new ConcurrentHashMap<>();

    @Value("${panel.uri}")
    private String panelUri;
    @Value("${panel.speed:5}")
    private String panelSpeed;

    public void print(String panelIp, String text) {
        ExecutorService ex = executors.computeIfAbsent(panelIp, k -> Executors.newSingleThreadExecutor());
        ex.submit(() -> {
            try {
                String url = String.format(panelUri, panelIp);
                MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
                body.add("text", text);
                body.add("hiz", panelSpeed);

                restTemplate.postForEntity(url, new HttpEntity<>(body, formHeaders()), String.class);
                Thread.sleep(2000);
            } catch (Exception ignored) {
                log.info("Panel dont showing : " + panelIp + " : " + text);
                throw  new RuntimeException("Panel dont showing : " + panelIp + " : " + text);
            }
        });
    }

    private HttpHeaders formHeaders() {
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return h;
    }
}
