package HKR.MSB.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="msb2")
public class Beleg {

    private String dockerHost;

    @Id
    @Column(name="id")
    private UUID id;

    public Beleg(){}

    public Beleg(UUID id) throws UnknownHostException {
        this.id = id;
        dockerHost = InetAddress.getLocalHost().getHostName();
    }

    public UUID getId() { return id; }

    public String getDockerHost() { return dockerHost; }

    @Override
    public String toString() { return "\t" + id + "\t" + LocalDateTime.now() + "\t" + dockerHost; }
}
