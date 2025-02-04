package HKR.MSB.controller;

import HKR.MSB.model.Beleg;
import HKR.MSB.repository.BelegRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BelegListener {
	
	int groupSize = 1;
	    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final BelegRepository belegRepository;
    private ArrayList<Beleg> belegList = new ArrayList<>();

    public BelegListener(BelegRepository belegRepository) { this.belegRepository = belegRepository; }

    @RabbitListener(queues = "from_msa")
    public void receiveMessage(Beleg receivedBeleg) throws UnknownHostException {
        Beleg newBeleg = new Beleg(receivedBeleg.getId());
		
		thisHour   = LocalDateTime.now().getHour();
		thisMinute = LocalDateTime.now().getMinute();
		
        synchronized (belegList) {
            belegList.add(newBeleg);
            
            if (belegList.size() >= groupSize) {

                // Use batch insert with placeholders
                String sql = "INSERT INTO msb (id, belegid) VALUES (?, ?)";
                List<Object[]> batchArgs = new ArrayList<>();

                for (Beleg beleg : belegList) { batchArgs.add(new Object[]{beleg.getId(), beleg.getBelegID()}); }

                jdbcTemplate.batchUpdate(sql, batchArgs);
                belegList.clear();
            }
        }
    }
}