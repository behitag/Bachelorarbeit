package HKR.MSB.service;

import HKR.MSB.model.Beleg;
import HKR.MSB.repository.BelegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BelegService {

    private final BelegRepository belegRepository;

    @Autowired
    public BelegService(BelegRepository belegRepository) { this.belegRepository = belegRepository; }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity<Object> createBeleg(Beleg beleg) {
        belegRepository.save(beleg);
        return new ResponseEntity<>(beleg, HttpStatus.CREATED);
    }
}
