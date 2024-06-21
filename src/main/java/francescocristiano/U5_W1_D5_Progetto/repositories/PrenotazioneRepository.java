package francescocristiano.U5_W1_D5_Progetto.repositories;

import francescocristiano.U5_W1_D5_Progetto.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
}
