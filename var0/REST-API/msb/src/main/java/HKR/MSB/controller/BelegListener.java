package HKR.MSB.controller;

import HKR.MSB.model.Beleg;
import HKR.MSB.repository.BelegRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
public class BelegListener {

    private final BelegRepository belegRepository;

    public BelegListener(BelegRepository belegRepository) { this.belegRepository = belegRepository; }


    @PostMapping("/beleg")
    public void receiveObject(@RequestBody Beleg beleg) throws UnknownHostException {
        Beleg newBeleg = new Beleg(beleg.getId());
        belegRepository.save(newBeleg);
    }
}