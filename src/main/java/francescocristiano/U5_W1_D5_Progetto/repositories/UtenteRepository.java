package francescocristiano.U5_W1_D5_Progetto.repositories;

import francescocristiano.U5_W1_D5_Progetto.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
}