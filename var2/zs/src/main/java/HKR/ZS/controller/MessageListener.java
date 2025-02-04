package HKR.ZS.controller;

import HKR.ZS.model.Beleg;
import HKR.ZS.repository.BelegRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private final RabbitTemplate rabbitTemplate;
    private final BelegRepository belegRepository;

    @Autowired
    public MessageListener(RabbitTemplate rabbitTemplate, BelegRepository belegRepository) { this.rabbitTemplate = rabbitTemplate; this.belegRepository = belegRepository; }

    @RabbitListener(queues = "from_msb")
    public void receiveMessage(Beleg beleg) {

        Beleg newBeleg = new Beleg(beleg.getId());
        newBeleg.setDockerHost(beleg.getDockerHost());
        belegRepository.save(newBeleg);
    }
}
