package antoniogiovanni.marchese.U5W2L5.model;

import antoniogiovanni.marchese.U5W2L5.enums.StatoDispositivo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter
@Setter
public class Dispositivo {
    @Id
    @GeneratedValue
    private UUID id;
    private String tipologia;
    @Enumerated(EnumType.STRING)
    private StatoDispositivo statoDispositivo;
    @ManyToOne
    @JoinColumn(name = "id_utente")
    @JsonIgnore
    private Utente utente;
}
