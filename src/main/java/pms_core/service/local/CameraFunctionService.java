package pms_core.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pms_core.config.CameraProperties;
import pms_core.config.JSON;
import pms_core.dao.entity.CamerasEntity;
import pms_core.exception.DataNotFoundException;
import pms_core.model.local.request.CameraPayload;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class CameraFunctionService {

    private final DigestAuthService digestAuthService;
    private final CameraProperties properties;
    private final RestTemplate restTemplate;
    private final CameraService cameraService;

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
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return payload;
    }

    @Async("cameraExecutor")
    public void openDoor(String cameraType, String ip) {

        log.info("Opening door for cameraType={} ip={}", cameraType, ip);
        if ("vivotekV3".equals(cameraType)) {
            sendVivotekRequest(ip, true);
        } else {
            sendDidoRequest(ip, true);
        }
    }

    public ResponseEntity<String> sendRequest(String ip, Boolean openOrClose) {
        CamerasEntity camera = cameraService.findCamera(ip).orElseThrow(() -> DataNotFoundException.of("Camera or Door notfound"));
        String type = camera.getType();

        if ("vivotekV3".equals(type)) {
            return sendVivotekRequest(ip, openOrClose);
        } else {
            return sendDidoRequest(ip, openOrClose);
        }
    }

    private ResponseEntity<String> sendDidoRequest(String ip, Boolean openOrClose) {
        int number = Boolean.TRUE.equals(openOrClose) ? 1 : 0;

        String url = String.format(properties.getDidoUri(), ip);
        String auth = properties.getDidoUsername() + ":" + properties.getDidoPassword();
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        ResponseEntity<String> stringResponseEntity = sendBasicRestTemplate(url + "?do0=" + number, encoded);
        log.info("DIDO door triggered");

        return stringResponseEntity;
    }

    private ResponseEntity<String> sendBasicRestTemplate(String url, String auth) {
        ResponseEntity<String> response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + auth);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        return response;
    }

    private ResponseEntity<String> sendVivotekRequest(String ip, Boolean openOrClose) {
        int number = Boolean.TRUE.equals(openOrClose) ? 1 : 0;

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://" + ip + "/cgi-bin/operator/operator.cgi?action=set.event.io&format=json";

        log.info("URL: " + url);
        String body = """
                {
                  "outputIoList":[
                    {
                      "index":0,
                      "outputManual":%d,
                      "outputTime":-2
                    }
                  ]
                }
                """.formatted(number);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(properties.getVivotek().getUsername(), properties.getVivotek().getPassword());

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        System.out.println(response.getBody());
        return response;
    }

}

