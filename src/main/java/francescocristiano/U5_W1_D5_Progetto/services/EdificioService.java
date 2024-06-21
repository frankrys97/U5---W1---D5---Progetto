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

    }


    public void saveAllEdifici(List<Edificio> edifici) {
        edificioRepository.saveAll(edifici);

    }


    public List<Edificio> getAllEdifici() {
        return edificioRepository.findAll();
    }


    public Edificio getEdificioById(UUID id) {
        return edificioRepository.findById(id).orElseThrow(() -> new RuntimeException("Edificio non trovato"));
    }


    public void deleteEdificioById(UUID id) {
        edificioRepository.deleteById(id);
    }


    public void findAndUpdate(UUID id, Edificio edificio) {
        Edificio edificioToUpdate = this.getEdificioById(id);
        edificioToUpdate.setNome(edificio.getNome());
        edificioToUpdate.setIndirizzo(edificio.getIndirizzo());
        edificioToUpdate.setCitta(edificio.getCitta());
        edificioRepository.save(edificioToUpdate);

    }


    public long count() {
        return edificioRepository.count();
    }
}
