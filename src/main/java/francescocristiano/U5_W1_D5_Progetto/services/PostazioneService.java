package francescocristiano.U5_W1_D5_Progetto.services;

import francescocristiano.U5_W1_D5_Progetto.entities.Postazione;
import francescocristiano.U5_W1_D5_Progetto.repositories.PostazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PostazioneService {

    @Autowired
    private PostazioniRepository postazioniRepository;

    @Autowired
    private PrenotazioneService prenotazioneService;


    public void savePostazione(Postazione postazione) {
        postazioniRepository.save(postazione);
        System.out.println("Postazione salvata con successo");
    }


    public void saveAllPostazioni(List<Postazione> postazioni) {
        postazioniRepository.saveAll(postazioni);
        System.out.println("Postazioni salvate con successo");
    }


    public List<Postazione> getAllPostazioni() {
        return postazioniRepository.findAll();
    }


    public Postazione getPostazioneById(UUID id) {
        return postazioniRepository.findById(id).orElseThrow(() -> new RuntimeException("Postazione non trovata"));
    }


    public void deletePostazioneById(UUID id) {
        postazioniRepository.deleteById(id);
        System.out.println("Postazione eliminata con successo");
    }


    public void findAndUpdate(UUID id, Postazione postazione) {
        Postazione postazioneToUpdate = this.getPostazioneById(id);
        postazioneToUpdate.setDescrizione(postazione.getDescrizione());
        postazioneToUpdate.setEdificio(postazione.getEdificio());
        postazioneToUpdate.setTipoPostazione(postazione.getTipoPostazione());
        postazioniRepository.save(postazioneToUpdate);
        System.out.println("Postazione modificata con successo");
    }


    public long count() {
        return postazioniRepository.count();
    }

    public boolean isPostazioneOccupata(Postazione postazione, LocalDate data) {
     /*   List<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniByDataAndPostazione(data, postazione);
        return prenotazioni.isEmpty();*/
        if (prenotazioneService.getPrenotazioniByDataAndPostazione(data, postazione).isEmpty()) {
            return false;
        }
        return true;
    }

    public List<Postazione> getRandomPostazioni(int count) {
        List<Postazione> postazioni = postazioniRepository.findAll();
        List<Postazione> randomPostazioni = new ArrayList<>();

        Random random = new Random();
        while (randomPostazioni.size() < count) {
            int index = random.nextInt(postazioni.size());
            if (!randomPostazioni.contains(postazioni.get(index))) {
                randomPostazioni.add(postazioni.get(index));
            }
        }
        return randomPostazioni;
    }

}
