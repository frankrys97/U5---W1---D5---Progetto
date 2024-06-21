package francescocristiano.U5_W1_D5_Progetto.configuration;

import com.github.javafaker.Faker;
import francescocristiano.U5_W1_D5_Progetto.entities.Edificio;
import francescocristiano.U5_W1_D5_Progetto.entities.Postazione;
import francescocristiano.U5_W1_D5_Progetto.entities.Prenotazione;
import francescocristiano.U5_W1_D5_Progetto.entities.Utente;
import francescocristiano.U5_W1_D5_Progetto.enums.TipoPostazione;
import francescocristiano.U5_W1_D5_Progetto.services.EdificioService;
import francescocristiano.U5_W1_D5_Progetto.services.PostazioneService;
import francescocristiano.U5_W1_D5_Progetto.services.PrenotazioneService;
import francescocristiano.U5_W1_D5_Progetto.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class BeanConfiguration {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PostazioneService postazioneService;

    @Autowired
    private EdificioService edificioService;

    @Autowired
    private PrenotazioneService prenotazioneService;

    private Faker faker = new Faker();

    @Bean
    public Faker faker() {
        return faker;
    }


    @Bean
    public List<Edificio> edifici() {
        List<Edificio> edifici = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Edificio edificio = new Edificio(faker.company().name(), faker.address().streetAddress(), faker.address().city());
            edificioService.saveEdificio(edificio);
            edifici.add(edificio);
        }
        return edifici;
    }


    @Bean
    public List<Utente> utenti() {
        List<Utente> utenti = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Utente utente = new Utente(faker.name().username(), faker.harryPotter().character(), faker.internet().emailAddress());
            utenteService.saveUtente(utente);
            utenti.add(utente);
        }
        return utenti;
    }

    @Bean
    public List<Postazione> postazioni() {
        List<Postazione> postazioni = new ArrayList<>();
        List<Edificio> edifici = edificioService.getAllEdifici();
        for (Edificio edificio : edifici) {
            for (int i = 0; i < 10; i++) {
                Postazione postazionePrivata = new Postazione(TipoPostazione.PRIVATO, edificio, nomePostazione(TipoPostazione.PRIVATO, i));
                Postazione postazioneOpenSpace = new Postazione(TipoPostazione.OPENSPACE, edificio, nomePostazione(TipoPostazione.OPENSPACE, i));
                Postazione postazioneSalaRiunioni = new Postazione(TipoPostazione.SALA_RIUNIONI, edificio, nomePostazione(TipoPostazione.SALA_RIUNIONI, i));
                postazioneService.savePostazione(postazionePrivata);
                postazioneService.savePostazione(postazioneOpenSpace);
                postazioneService.savePostazione(postazioneSalaRiunioni);
                postazioni.add(postazionePrivata);
                postazioni.add(postazioneOpenSpace);
                postazioni.add(postazioneSalaRiunioni);
            }
        }
        return postazioni;
    }

    private String nomePostazione(TipoPostazione tipoPostazione, int index) {
        switch (tipoPostazione) {
            case PRIVATO:
                return "Postazione singola " + (index + 1);
            case OPENSPACE:
                return "Openspace " + (index + 1);
            case SALA_RIUNIONI:
                return "Sala Riunioni " + (index + 1);
            default:
                return "Postazione generica";
        }
    }

    @Bean
    public List<Prenotazione> prenotazioni() {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        List<Utente> utenti = utenteService.getRandomUtenti(10);
        List<Postazione> postazioni = postazioneService.getAllPostazioni();
        for (Utente utente : utenti) {
            for (int i = 0; i < 3; i++) {
                Random random = new Random();
                LocalDate data = LocalDate.now().plusDays(i + 1);
                Postazione postazioneRandomica = postazioni.get(random.nextInt(postazioni.size()));
                if (!postazioneService.isPostazioneOccupata(postazioneRandomica, data)) {
                    Prenotazione prenotazione = new Prenotazione(data, utente, postazioneRandomica);
                    prenotazioneService.savePrenotazione(prenotazione);
                    prenotazioni.add(prenotazione);
                }

            }

        }
        return prenotazioni;
    }
}
