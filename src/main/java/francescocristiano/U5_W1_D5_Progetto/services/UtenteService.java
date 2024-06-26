package francescocristiano.U5_W1_D5_Progetto.services;

import francescocristiano.U5_W1_D5_Progetto.entities.Utente;
import francescocristiano.U5_W1_D5_Progetto.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;


    public void saveUtente(Utente utente) {
        /*utenteRepository.save(utente);*/

       /* if (utenteRepository.existsById(utente.getId())) {
            throw new RuntimeException("Id esistente");
        }*/

        if (utenteRepository.existsByUsername(utente.getUsername())) {
            throw new RuntimeException("Username esistente");
        }

        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email esistente");
        }

        utenteRepository.save(utente);
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Utente con questa email " + email + " non trovato"));
    }


    public void saveAllUtenti(List<Utente> utenti) {
        utenteRepository.saveAll(utenti);
        System.out.println("Utenti salvati con successo");
    }

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente getUtenteById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    public void deleteUtenteById(UUID id) {
        utenteRepository.deleteById(id);
        System.out.println("Utente eliminato con successo");
    }

    public void findAndUpdate(UUID id, Utente utente) {
        Utente utenteToUpdate = this.getUtenteById(id);
        utenteToUpdate.setUsername(utente.getUsername());
        utenteToUpdate.setNomeCompleto(utente.getNomeCompleto());
        utenteToUpdate.setEmail(utente.getEmail());
        utenteRepository.save(utenteToUpdate);
        System.out.println("Utente modificato con successo");
    }

    public long count() {
        return utenteRepository.count();
    }

    public List<Utente> getRandomUtenti(int number) {
        List<Utente> utenti = utenteRepository.findAll();
        List<Utente> randomUtenti = new ArrayList<>();

        Random random = new Random();
        while (randomUtenti.size() < number) {
            int index = random.nextInt(utenti.size());
            if (!randomUtenti.contains(utenti.get(index))) {
                randomUtenti.add(utenti.get(index));
            }
        }
        return randomUtenti;
    }
}
