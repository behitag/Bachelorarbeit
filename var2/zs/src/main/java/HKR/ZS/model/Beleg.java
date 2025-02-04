package HKR.ZS.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name="zs2")
public class Beleg {

    private static int minuteOfThisBeleg=0;
    private static int minuteOfLastBeleg=0;
    private static int belegCounter=0;
    private String dockerhost;
    private String belegID;

    @Id
    @Column(name="id")
    private UUID id;

    @CreatedDate
    @Column(name="time", updatable=false)
    private LocalDateTime time;

    public Beleg() {}

    public Beleg(UUID id) {
        this.id = id;
        time = LocalDateTime.now();
        belegCounter++;

        if(minuteChanged())
            belegCounter=0;

        belegID = assignBelegID();
    }

    public LocalDateTime getTime () { return time; }

    public UUID getId() { return id; }

    public String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
        return localDateTime.format(formatter);
    }

    public String assignBelegID() { return formatDateTime(time) + "-" + belegCounter; }

    public String getBelegID() { return belegID; }

    public String getDockerHost() { return dockerhost; }

    public void setDockerHost(String dockerHost) { this.dockerhost = dockerHost; }

    public boolean minuteChanged() {
        if(belegCounter == 0) {
            minuteOfLastBeleg = LocalDateTime.now().getMinute();
            minuteOfThisBeleg = LocalDateTime.now().getMinute();
        }
        if(belegCounter > 0) {
            minuteOfLastBeleg = minuteOfThisBeleg;
            minuteOfThisBeleg = LocalDateTime.now().getMinute();
        }

        if(minuteOfThisBeleg == minuteOfLastBeleg)
            return false;
        else
            return true;
    }

    @Override
    public String toString() { return id + "\t" + dockerhost + "\t\t" + time + "\t\t" + belegID; }
}
