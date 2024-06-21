package francescocristiano.U5_W1_D5_Progetto.runner;

import francescocristiano.U5_W1_D5_Progetto.entities.Edificio;
import francescocristiano.U5_W1_D5_Progetto.entities.Postazione;
import francescocristiano.U5_W1_D5_Progetto.entities.Prenotazione;
import francescocristiano.U5_W1_D5_Progetto.entities.Utente;
import francescocristiano.U5_W1_D5_Progetto.enums.TipoPostazione;
import francescocristiano.U5_W1_D5_Progetto.services.EdificioService;
import francescocristiano.U5_W1_D5_Progetto.services.PostazioneService;
import francescocristiano.U5_W1_D5_Progetto.services.PrenotazioneService;
import francescocristiano.U5_W1_D5_Progetto.services.UtenteService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
@Transactional
public class AppRunner implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private PrenotazioneService prenotazioneService;
    @Autowired
    private PostazioneService postazioneService;
    @Autowired
    private EdificioService edificioService;
    @Autowired
    private EntityManager entityManager;

    @Override
    public void run(String... args) throws Exception {
        startApp();
    }

    public void startApp() {
        System.out.println();
        System.out.println("Benvenuto nella Gestione Prenotazioni - EpiJava Edition!");
        System.out.println();

        while (true) {
            System.out.println("Scegli un'opzione:");
            System.out.println("1. Nuovo Utente");
            System.out.println("2. Utente registrato");
            System.out.println("3. Esci");
            int scelta = Integer.parseInt(scanner.nextLine());

            try {
                switch (scelta) {
                    case 1:
                        utenteNuovo();
                        break;
                    case 2:
                        utenteRegistrato();
                        break;
                    case 3:
                        System.out.println("Arrivederci!");
                        System.out.println();
                        resetDataBase();
                        return;
                    default:
                        System.out.println("Scelta non valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Scelta non valida");
            }
        }
    }

    public void resetDataBase() {
        entityManager.createNativeQuery("DROP TABLE prenotazione, postazione, utente, edificio").executeUpdate();
    }

    public void utenteNuovo() {
        System.out.println("Registrazione nuovo utente:");

        System.out.println("Inserisci username:");
        String username = scanner.nextLine();
        System.out.println("Inserisci nome completo:");
        String nomeCompleto = scanner.nextLine();
        System.out.println("Inserisci email:");
        String email = scanner.nextLine();

        Utente nuovoUtente = new Utente(username, nomeCompleto, email);
        try {
            utenteService.saveUtente(nuovoUtente);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        }

        System.out.println();
        System.out.println("Utente registrato con successo!");
        System.out.println();

        menuUtenteNuovo(utenteService.getUtenteById(nuovoUtente.getId()));

    }

    public void menuUtenteNuovo(Utente utente) {
        System.out.println("Scegli un'opzione:");
        System.out.println();
        System.out.println("1. Prenota");
        System.out.println("2. Esci");
        int scelta = Integer.parseInt(scanner.nextLine());

        try {
            switch (scelta) {
                case 1:
                    prenota(utente);
                    break;
                case 2:
                    return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Scelta non valida");
        }
    }

    public void prenota(Utente utente) {
        System.out.println("Prenotazione:");
        System.out.println();

        System.out.println("In quale città vuoi prenotare una postazione?");
        List<Edificio> cittaEdifici = edificioService.getAllEdifici();
        for (int i = 0; i < cittaEdifici.size(); i++) {
            System.out.println((i + 1) + ". " + cittaEdifici.get(i).getCitta() + " - " + cittaEdifici.get(i).getNome());
        }
        int scelta = Integer.parseInt(scanner.nextLine());
        Edificio edificio = cittaEdifici.get(scelta - 1);

        System.out.println();
        System.out.println("Che tipo di postazione vuoi prenotare?");
        System.out.println("1. Postazione singola");
        System.out.println("2. OpenSpace");
        System.out.println("3. Sala Riunioni");

        int sceltaTipo = Integer.parseInt(scanner.nextLine());
        TipoPostazione tipoPostazione = null;
        switch (sceltaTipo) {
            case 1:
                tipoPostazione = TipoPostazione.PRIVATO;
                break;
            case 2:
                tipoPostazione = TipoPostazione.OPENSPACE;
                break;
            case 3:
                tipoPostazione = TipoPostazione.SALA_RIUNIONI;
                break;
            default:
                System.out.println("Scelta non valida");
        }

        System.out.println();

        System.out.println("Quale postazione vuoi prenotare?");
        List<Postazione> postazioni = postazioneService.getPostazioniByEdificioAndTipoPostazione(edificio, tipoPostazione);
        for (int i = 0; i < postazioni.size(); i++) {
            System.out.println((i + 1) + ". " + postazioni.get(i).getDescrizione());
        }
        int sceltaPostazione = Integer.parseInt(scanner.nextLine());
        Postazione postazione = postazioni.get(sceltaPostazione - 1);


        try {
            System.out.println("Scegli la data (formato dd-MM-yyyy):");
            LocalDate data = LocalDate.parse(scanner.nextLine(), formatter);
            if (data.isBefore(LocalDate.now())) {
                throw new Exception("Data non valida");
            }

            if (postazioneService.isPostazioneOccupata(postazione, data)) {
                throw new Exception("Postazione occupata");
            }

            prenotazioneService.savePrenotazione(new Prenotazione(data, utente, postazione));
        } catch (Exception e) {
            System.out.println("Data non valida");
            prenota(utente);
        }


        System.out.println("Prenotazione effettuata con successo!");
        System.out.println();

        return;
    }

    public void utenteRegistrato() {
        System.out.println("Inserisci il tuo username:");
        String username = scanner.nextLine();
        /*Utente utente = utenteService.findByUsername(username);*/
        if (utenteService.findByUsername(username) == null) {
            System.out.println("Utente non trovato");
            return;
        }
        Utente utente = utenteService.findByUsername(username);

        System.out.println();
        System.out.println("Benvenuto " + utente.getNomeCompleto() + "!");
        System.out.println();
        menuUtenteRegistrato(utente);
    }


    public void menuUtenteRegistrato(Utente utente) {
        System.out.println("Scegli un'opzione:");
        System.out.println();
        System.out.println("1. Prenota");
        System.out.println("2. Le tue prenotazioni");
        System.out.println("2. Esci");
        int scelta = Integer.parseInt(scanner.nextLine());

        try {
            switch (scelta) {
                case 1:
                    prenota(utente);
                    break;
                case 2:
                    visualizzaPrenotazioni(utente);
                    break;
                case 3:
                    return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Scelta non valida");
        }

    }

    public void visualizzaPrenotazioni(Utente utente) {
        List<Prenotazione> prenotazioni = prenotazioneService.findByUtenteId(utente.getId());
        for (Prenotazione prenotazione : prenotazioni) {
            System.out.println(prenotazione.toString());
        }


    }

}







