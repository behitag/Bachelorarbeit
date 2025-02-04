package HKR.MSA.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name="msa")
public class Beleg {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public Beleg() {}

    public Beleg(UUID id) { this.id = id; }

    public UUID getId() { return id; }
}
