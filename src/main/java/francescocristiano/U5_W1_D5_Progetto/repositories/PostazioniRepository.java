package francescocristiano.U5_W1_D5_Progetto.repositories;

import francescocristiano.U5_W1_D5_Progetto.entities.Edificio;
import francescocristiano.U5_W1_D5_Progetto.entities.Postazione;
import francescocristiano.U5_W1_D5_Progetto.enums.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostazioniRepository extends JpaRepository<Postazione, UUID> {

    List<Postazione> findAllByEdificioAndTipoPostazione(Edificio edificio, TipoPostazione tipoPostazione);
}
