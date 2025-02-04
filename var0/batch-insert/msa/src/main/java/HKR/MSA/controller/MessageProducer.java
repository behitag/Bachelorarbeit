package HKR.MSA.controller;

import HKR.MSA.model.Beleg;
import HKR.MSA.repository.BelegRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final BelegRepository belegRepository;
    String exchange = "exchange";
    String routingKey = "routingKey";

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate, BelegRepository belegRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.belegRepository = belegRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void send() {
        for(int i=1; i<=70000000; i++) {
            Beleg beleg = new Beleg(UUID.randomUUID());
            rabbitTemplate.convertAndSend(exchange, routingKey, beleg);
        }
    }
}
