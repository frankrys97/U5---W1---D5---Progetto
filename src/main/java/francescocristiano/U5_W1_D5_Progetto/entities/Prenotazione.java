package francescocristiano.U5_W1_D5_Progetto.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue
    private UUID id;
    @Setter
    private LocalDate data;
    @Setter
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @Setter
    @ManyToOne
    @JoinColumn(name = "postazione_id")
    private Postazione postazione;

    public Prenotazione(LocalDate data, Utente utente, Postazione postazione) {
        this.data = data;
        this.utente = utente;
        this.postazione = postazione;
    }
}
