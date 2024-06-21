package francescocristiano.U5_W1_D5_Progetto.services;

import francescocristiano.U5_W1_D5_Progetto.entities.Edificio;
import francescocristiano.U5_W1_D5_Progetto.repositories.EdificioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EdificioService {

    @Autowired
    private EdificioRepository edificioRepository;


    public void saveEdificio(Edificio edificio) {
        edificioRepository.save(edificio);
        System.out.println("Edificio salvato con successo");
    }


    public void saveAllEdifici(List<Edificio> edifici) {
        edificioRepository.saveAll(edifici);
        System.out.println("Edifici salvati con successo");
    }


    public List<Edificio> getAllEdifici() {
        return edificioRepository.findAll();
    }


    public Edificio getEdificioById(UUID id) {
        return edificioRepository.findById(id).orElseThrow(() -> new RuntimeException("Edificio non trovato"));
    }


    public void deleteEdificioById(UUID id) {
        edificioRepository.deleteById(id);
        System.out.println("Edificio eliminato con successo");
    }


    public void findAndUpdate(UUID id, Edificio edificio) {
        Edificio edificioToUpdate = this.getEdificioById(id);
        edificioToUpdate.setNome(edificio.getNome());
        edificioToUpdate.setIndirizzo(edificio.getIndirizzo());
        edificioToUpdate.setCitta(edificio.getCitta());
        edificioRepository.save(edificioToUpdate);
        System.out.println("Edificio modificato con successo");
    }


    public long count() {
        return edificioRepository.count();
    }
}
