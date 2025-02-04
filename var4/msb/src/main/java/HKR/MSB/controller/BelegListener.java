package HKR.MSB.controller;

import HKR.MSB.model.Beleg;
import HKR.MSB.repository.BelegRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

@Component
public class BelegListener {

    private final BelegRepository belegRepository;
    private final RabbitTemplate rabbitTemplate;
    String fanoutExchange = "fanoutExchange";
    String leaderExchange = "leaderExchange";
    String leaderRouting = "leaderRouting";

    @Autowired
    private RabbitListenerEndpointRegistry registry;

    private static CountDownLatch latch = new CountDownLatch(1);

    public BelegListener(BelegRepository belegRepository, RabbitTemplate rabbitTemplate) {
        this.belegRepository = belegRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "from_msa", id="from_msa", autoStartup = "false")
    public void receiveMessage(Beleg receivedBeleg) throws UnknownHostException, InterruptedException {
        Beleg newBeleg = new Beleg(receivedBeleg.getId());
        int number = newBeleg.getBelegCounter();

        rabbitTemplate.convertAndSend(fanoutExchange, "", number);

        latch.await();
        latch = new CountDownLatch(1);

        belegRepository.save(newBeleg);
    }

    @RabbitListener(queues = "leaderQueue", id="leaderQueue", autoStartup="false")
    public void receiveMessage2(String text) {
        latch.countDown();
    }
    

    @RabbitListener(queues = "fanoutQueue", id="fanoutQueue", autoStartup="false")
    public void receiveMessage(String text) throws UnknownHostException {

        rabbitTemplate.convertAndSend(leaderExchange, leaderRouting, "OK");
    }



    public void startQueue_from_msa() { registry.getListenerContainer("from_msa").start(); }
    public void startQueue_leaderQueue() { registry.getListenerContainer("leaderQueue").start(); }
    public void startQueue_fanoutQueue() { registry.getListenerContainer("fanoutQueue").start(); }
}