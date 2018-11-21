package hu.waldorf.finance;

import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.BefizetesRepository;
import hu.waldorf.finance.model.Diak;
import hu.waldorf.finance.model.DiakRepository;
import hu.waldorf.finance.model.Szerzodes;
import hu.waldorf.finance.model.SzerzodesRepository;
import hu.waldorf.finance.model.TetelTipus;
import hu.waldorf.finance.service.JovairasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan("hu.waldorf.finance")
public class FinanceApplication implements CommandLineRunner {
    private static final Locale locale = Locale.forLanguageTag("hu-HU");

    @Autowired
    private BefizetesRepository befizetesRepository;
    @Autowired
    private SzerzodesRepository szerzodesRepository;
    @Autowired
    private DiakRepository diakRepository;
    @Autowired
    private JovairasService jovairasService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public static void main(String[] args) {
        SpringApplication.run(FinanceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        feldolgozo();
    }

    public void feldolgozo() {
        while (true) {
            List<Befizetes> befizetesek = befizetesRepository.findNemFeldolgozottak();
            System.out.println("Feldolgozandó befizetések: " + befizetesek.size());
            System.out.println();

            if (befizetesek.isEmpty()) {
                System.out.println("KÉSZ!");
                return;
            }

            Befizetes befizetes = befizetesek.get(0);
            print(befizetes);
            printHints(befizetes);

            String utasitas = readCommand();

            char lastCharacter = utasitas.toLowerCase(locale).charAt(utasitas.length() - 1);

            if (lastCharacter == 'é' || lastCharacter == 'm') {
                String sSzerzodesId = utasitas.substring(0, utasitas.length() - 1);
                try {
                    int szerzodesId = Integer.parseInt(sSzerzodesId);
                    jovairBefizetest(szerzodesId, lastCharacter, befizetes.getId());
                    continue;
                } catch (NumberFormatException e) {
                }
            }

            if (utasitas.toLowerCase().startsWith("t") && utasitas.length() == 7) {
                String sEv = utasitas.substring(1, 5);
                String sHonap = utasitas.substring(5, 7);

                try {
                    int ev = Integer.parseInt(sEv);
                    int honap = Integer.parseInt(sHonap);

                    if (ev < 2018 && ev > 2019) {
                        throw new NumberFormatException();
                    }

                    if (honap < 1 && honap > 12) {
                        throw new NumberFormatException();
                    }

                    jovairasService.terhel(ev, honap);
                    continue;
                } catch (NumberFormatException ex) {
                    System.out.println("Ervenytelen t parancs. Pelda: t201809");
                }
            }

            switch (utasitas.toLowerCase()) {
                case "h":
                    jovairasService.postponeBefizetes(befizetes.getId());
                    break;
                case "f":
                    jovairasService.ignoreBefizetes(befizetes.getId());
                    break;
                case "?":
                    printHelp();
                    break;
                default:
                    System.out.println("Ismeretlen parancs. Használd a ?-t az utasítások megtekintéséhez.");
            }
        }
    }

    private void printHints(Befizetes befizetes) {
        List<Hint> hints = new ArrayList<>();

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        List<Szerzodes> szerzodesek = szerzodesRepository.findAll();

        for (Szerzodes szerzodes : szerzodesek) {
            long csaladId = szerzodes.getCsaladId();
            List<Diak> diakok = diakRepository.findByCsaladId(csaladId);
            Hint hint = new Hint(diakok, szerzodes);
            hint.calculateConfidencePoint(befizetes);
            if (hint.getConfidencePoint() > 0) {
                hints.add(hint);
            }
        }

        transactionManager.rollback(transactionStatus);

        hints.sort((o1, o2) -> o2.getConfidencePoint() - o1.getConfidencePoint());

        int i = 0;
        while (i < hints.size() && i < 10) {
            System.out.println(hints.get(i).format());
            i++;
        }
    }

    private String readCommand() {
        System.out.println("> ");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void jovairBefizetest(int szerzodesId, char tipus, long befizetesId) {
        TetelTipus tetelTipus;
        switch (tipus) {
            case 'é':
                tetelTipus = TetelTipus.EPITESI;
                break;
            case 'm':
                tetelTipus = TetelTipus.MUKODESI;
                break;
            default:
                throw new IllegalStateException("" + tipus);
        }

        jovairasService.jovairBefizetest(szerzodesId, tetelTipus, befizetesId);
    }

    private void printHelp() {
        System.out.println("Segitseg");
        System.out.println("34é: jovairas a 34-es tamogatoi szerzodeshez, epitesi hozzajarulashoz");
        System.out.println("34m: jovairas a 34-es tamogatoi szerzodeshez, mukodesi tamogatashoz");
        System.out.println("t201809: 2018. 09. honap terhelesek");
        System.out.println("h: tetel jovairasanak kesobbre halasztasa");
        System.out.println("f: befizetest figyelmen kivul hagy");
        System.out.println("?: segitseg megjelenitese");
        System.out.println();
    }

    private void print(Befizetes befizetes) {
        System.out.println("Forrás: " + befizetes.getImportForras());
        System.out.println("Beeérkezes időpontja: " + befizetes.getKonyvelesiNap());
        System.out.println("Befizető: " + befizetes.getBefizetoNev() + " (" + befizetes.getBefizetoSzamlaszam() + ")");
        System.out.println("Összeg: " + befizetes.getOsszeg());
        System.out.println("Közlemény: " + befizetes.getKozlemeny());
        System.out.println();
    }
}
