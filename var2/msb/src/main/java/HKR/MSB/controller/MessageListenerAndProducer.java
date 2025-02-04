package HKR.MSB.controller;

import HKR.MSB.model.Beleg;
import HKR.MSB.repository.BelegRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.net.UnknownHostException;

@Component
public class MessageListenerAndProducer {

    private String exchange2 = "exchange2";
    private String routingKey2 = "routingKey2";
    private final RabbitTemplate rabbitTemplate;

    public MessageListenerAndProducer(BelegRepository belegRepository, RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }

    @RabbitListener(queues = "from_msa")
    public void receiveMessage(Beleg beleg) throws UnknownHostException {

        //We make new Beleg, to get the dockerhost, since MSA does not provide a dockerhost
        Beleg newBeleg = new Beleg(beleg.getId());
        rabbitTemplate.convertAndSend(exchange2, routingKey2, newBeleg);
    }
}