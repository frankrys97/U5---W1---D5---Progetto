package francescocristiano.U5_W1_D5_Progetto.entities;

import francescocristiano.U5_W1_D5_Progetto.enums.TipoPostazione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Postazione {
    @Id
    @GeneratedValue
    private UUID id;
    @Setter
    private String descrizione;
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoPostazione tipoPostazione;
    private int capienzaMax;
    @Setter
    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;
    @Setter
    @OneToMany(mappedBy = "postazione")
    private List<Prenotazione> prenotazioni;

    public Postazione(TipoPostazione tipoPostazione, Edificio edificio, String descrizione) {
        this.tipoPostazione = tipoPostazione;
        this.descrizione = descrizione;
        this.capienzaMax = 0;
        setCapienzaMax();
        this.edificio = edificio;

    }

    public void setCapienzaMax() {
        switch (tipoPostazione) {
            case PRIVATO:
                this.capienzaMax = 1;
                break;
            case OPENSPACE:
                this.capienzaMax = 10;
                break;
            case SALA_RIUNIONI:
                this.capienzaMax = 50;
                break;
        }
    }

    @Override
    public String toString() {
        return "Postazione{" +
                "id=" + id +
                ", descrizione='" + descrizione + '\'' +
                ", tipoPostazione=" + tipoPostazione +
                ", capienzaMax=" + capienzaMax +
                ", edificio=" + edificio +
                '}';
    }
}
