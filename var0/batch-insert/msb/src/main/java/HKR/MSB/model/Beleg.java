package HKR.MSB.model;

import jakarta.persistence.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name="msb")
public class Beleg {

    private static int minuteOfThisBeleg=0;
    private static int minuteOfLastBeleg=0;
    private static int belegCounter=1;

    @Id
    @Column(name="id")
    private UUID id;

    @Column(name="belegID")
    private String belegID;

    public Beleg(){}

    public Beleg(UUID id) throws UnknownHostException {
        this.id = id;
        belegCounter++;

        if(minutesChanged())
            belegCounter=1;

        belegID = assignBelegID();
    }

    public UUID getId() { return id; }

    public int getBelegCounter() { return belegCounter; }

    public String getBelegID() { return belegID; }

    public String assignBelegID() throws UnknownHostException {
        String dockerHostId = InetAddress.getLocalHost().getHostName();
        return getFormattedDateTimeInMin(LocalDateTime.now()) + "-" + dockerHostId + "-" + belegCounter;
    }

    public String getFormattedDateTimeInMin(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
        return localDateTime.format(formatter);
    }

    public boolean minutesChanged() {
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

}
