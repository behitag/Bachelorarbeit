package HKR.MSB.controller;

import HKR.MSB.model.Beleg;
import HKR.MSB.repository.BelegRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.net.UnknownHostException;

@Component
public class BelegListener {

    private final BelegRepository belegRepository;

    public BelegListener(BelegRepository belegRepository) { this.belegRepository = belegRepository; }

    @RabbitListener(queues = "from_msa")
    public void receiveMessage(Beleg receivedBeleg) throws UnknownHostException {

        Beleg newBeleg = new Beleg(receivedBeleg.getId());
        belegRepository.save(newBeleg);
    }
}