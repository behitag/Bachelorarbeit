package HKR.MSB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class RMQController {

    @Autowired
    private BelegListener belegListener;

    private String dockerHost = InetAddress.getLocalHost().getHostName();

    public RMQController() throws UnknownHostException {
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if(dockerHost.charAt(0) == 'A') {
            belegListener.startQueue_from_msa();
            belegListener.startQueue_leaderQueue();
        }

        if(dockerHost.charAt(0) == 'B') { belegListener.startQueue_fanoutQueue(); }
        if(dockerHost.charAt(0) == 'C') { belegListener.startQueue_fanoutQueue(); }
        if(dockerHost.charAt(0) == 'D') { belegListener.startQueue_fanoutQueue(); }
    }

}