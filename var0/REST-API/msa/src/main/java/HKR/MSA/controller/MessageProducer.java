package HKR.MSA.controller;

import HKR.MSA.model.Beleg;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class MessageProducer {

    private int i = 0;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void send() {
        while(true) {            
            Beleg beleg = new Beleg(UUID.randomUUID());
			
			i++;
            if(i%4 == 0)
                sendBeleg1(beleg);
            if(i%4 == 1)
                sendBeleg2(beleg);
            if(i%4 == 2)
                sendBeleg3(beleg);
            if(i%4 == 3)
                sendBeleg4(beleg);
        }
    }

    public void sendBeleg1(Beleg beleg) {
        String url = "http://localhost:8081/beleg";
        restTemplate.postForObject(url, beleg, String.class);
    }

    public void sendBeleg2(Beleg beleg) {
        String url = "http://localhost:8082/beleg";
        restTemplate.postForObject(url, beleg, String.class);
    }

    public void sendBeleg3(Beleg beleg) {
        String url = "http://localhost:8083/beleg";
        restTemplate.postForObject(url, beleg, String.class);
    }

    public void sendBeleg4(Beleg beleg) {
        String url = "http://localhost:8084/beleg";
        restTemplate.postForObject(url, beleg, String.class);
    }
}
