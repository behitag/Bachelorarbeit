package HKR.MSB.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="msb3")
public class Beleg {

    private static int belegCounter=0;

    @Id
    @Column(name="id")
    private UUID id;

    @Column(name="belegid")
    private String belegid;

    @Column(name="dockerhost")
    private String dockerHost;

    public Beleg(){}

    public Beleg(UUID id) throws UnknownHostException {
        this.id = id;
        belegCounter++;

        dockerHost = InetAddress.getLocalHost().getHostName();
    }

    public UUID getId() { return id; }

    public int getBelegCounter() { return belegCounter; }

    public String getFormattedDateTimeInMin(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
        return localDateTime.format(formatter);
    }

    public String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss.SSS");
        return localDateTime.format(formatter);
    }

    @Override
    public String toString() { return "\t" + id + "\t\t" + dockerHost; }
}
