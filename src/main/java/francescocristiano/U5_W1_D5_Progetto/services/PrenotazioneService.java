package francescocristiano.U5_W1_D5_Progetto.services;

import francescocristiano.U5_W1_D5_Progetto.entities.Postazione;
import francescocristiano.U5_W1_D5_Progetto.entities.Prenotazione;
import francescocristiano.U5_W1_D5_Progetto.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;


    public void savePrenotazione(Prenotazione prenotazione) {
        prenotazioneRepository.save(prenotazione);
    }


    public void saveAllPrenotazioni(List<Prenotazione> prenotazioni) {
        prenotazioneRepository.saveAll(prenotazioni);
    }


    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();
    }

    public Prenotazione getPrenotazioneById(UUID id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
    }


    public void deletePrenotazioneById(UUID id) {
        prenotazioneRepository.deleteById(id);
    }


    public void findAndUpdate(UUID id, Prenotazione prenotazione) {
        Prenotazione prenotazioneToUpdate = this.getPrenotazioneById(id);
        prenotazioneToUpdate.setData(prenotazione.getData());
        prenotazioneToUpdate.setUtente(prenotazione.getUtente());
        prenotazioneToUpdate.setPostazione(prenotazione.getPostazione());
        prenotazioneRepository.save(prenotazioneToUpdate);
    }

    public long count() {
        return prenotazioneRepository.count();
    }

    public List<Prenotazione> getPrenotazioniByDataAndPostazione(LocalDate data, Postazione postazione) {
        return prenotazioneRepository.findAll().stream().filter(prenotazione -> prenotazione.getData().equals(data) && prenotazione.getPostazione().equals(postazione)).toList();
    }

    public List<Prenotazione> findByUtenteId(UUID utenteId) {
        return prenotazioneRepository.findAllByUtenteId(utenteId);
    }
}



