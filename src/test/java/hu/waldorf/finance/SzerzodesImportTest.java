package hu.waldorf.finance;

import hu.waldorf.finance.import_.Csalad;
import hu.waldorf.finance.import_.CsaladRepository;
import hu.waldorf.finance.import_.Diak;
import hu.waldorf.finance.import_.DiakRepository;
import hu.waldorf.finance.import_.Szerzodes;
import hu.waldorf.finance.import_.SzerzodesRepository;
import org.apache.commons.io.Charsets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SzerzodesImportTest {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private CsaladRepository csaladRepository;

    @Autowired
    private DiakRepository diakRepository;

    @Autowired
    private SzerzodesRepository szerzodesRepository;

    private Set<Integer> csaladok = new HashSet<>();

    @Test
    public void hello() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("tamogatok.csv").getFile());

        Stream<String> lines = Files.lines(file.toPath(), Charsets.UTF_8);
        List<String> lines2 = lines.collect(Collectors.toList());

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        szerzodesRepository.deleteAll();
        diakRepository.deleteAll();
        csaladRepository.deleteAll();

        boolean firstLine = true;
        for (String s : lines2) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            try {
                processLine(s);
            } catch (Exception e) {
                System.out.println("Hibás sor:");
                System.out.println(s);

                e.printStackTrace();

                System.out.println("Hibás sor:");
                System.out.println(s);
                return;
            }
        }

        transactionManager.commit(transactionStatus);
    }

    private void processLine(String s) {
        String[] cols = s.split("\t");
        String tamogatoNeve = cols[0].trim();

        int mukodesiTamogatasNyitoEgyenleg = Integer.parseInt(cols[2]);
        int epitesiHozzajarulasNyitoEgyenleg = Integer.parseInt(cols[3]);
        int csaladId = Integer.parseInt(cols[5]);
        int gyerekekSzama = Integer.parseInt(cols[6]);

        String[] gyerekekNeve = new String[4];
        String[] gyerekekEvfolyama = new String[4];

        gyerekekNeve[0] = cols[7].trim();
        gyerekekEvfolyama[0] = cols[8].trim();
        gyerekekNeve[1] = cols[9].trim();
        gyerekekEvfolyama[1] = cols[10].trim();
        gyerekekNeve[2] = cols[11].trim();
        gyerekekEvfolyama[2] = cols[12].trim();
        gyerekekNeve[3] = cols[13].trim();
        gyerekekEvfolyama[3] = cols[14].trim();

        int mukodesiKoltsegTamogatas = Integer.parseInt(cols[15]);
        int epitesiHozzajarulas = Integer.parseInt(cols[16]);

        int countGyerek = 0;

        for (int i = 0; i < 4; i++) {
            if (!gyerekekEvfolyama[i].isEmpty() && gyerekekNeve[i].isEmpty()) {
                throw new RuntimeException(i + ". gyerek neve hianyzik.");
            }

            if (!gyerekekNeve[i].isEmpty()) {
                countGyerek++;
            } else {
                if (i < 3) {
                    if (!gyerekekNeve[i + 1].isEmpty()) {
                        throw new RuntimeException("A gyerekek listajaban a(z) " + i + ". nev ures.");
                    }
                }
            }

            if (!gyerekekEvfolyama[i].isEmpty()) {
                switch (gyerekekEvfolyama[i]) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                    case "11":
                    case "12":
                        break;
                    default:
                        throw new RuntimeException("Ervenytelen evfolyam: " + gyerekekEvfolyama[i]);
                }
            }
        }

        if (countGyerek != gyerekekSzama) {
            throw new RuntimeException("A gyerekek szama nem egyezik a felvitt gyerekek szamaval.");
        }

        if (!csaladok.contains(csaladId)) {
            csaladok.add(csaladId);

            Csalad csalad = new Csalad();
            csalad.setId(csaladId);
            csaladRepository.save(csalad);

            for (int i = 0; i < gyerekekSzama; i++) {
                Diak diak = new Diak();
                diak.setNev(gyerekekNeve[i]);
                diak.setOsztaly(gyerekekEvfolyama[i]);
                diak.setCsaladId(csalad.getId());
                diakRepository.save(diak);
            }
        } else {
            List<Diak> diakokACsaladban = diakRepository.findByCsaladId(csaladId);

            for (int i = 0; i < gyerekekSzama; i++) {
                if (diakokACsaladban.get(i).getNev().equals(gyerekekNeve[i])) {
                    continue;
                } else {
                    throw new RuntimeException("Egy csaladban levo gyerekeket kulon-kulon tamogato fizet.");
//                    Diak diak = new Diak();
//                    diak.setNev(gyerekekNeve[i]);
//                    diak.setOsztaly(gyerekekEvfolyama[i]);
//                    diak.setCsaladId(csaladId);
//                    diakRepository.save(diak);
                }

            }


            System.out.println();
        }


        Szerzodes szerzodes = new Szerzodes();
        szerzodes.setTamogato(tamogatoNeve);
        szerzodes.setMukodesiKoltsegTamogatas(mukodesiKoltsegTamogatas);
        szerzodes.setEpitesiHozzajarulas(epitesiHozzajarulas);
        szerzodes.setCsaladId(csaladId);
        szerzodes.setMukodesiKoltsegTamogatasInduloEgyenleg(mukodesiTamogatasNyitoEgyenleg);
        szerzodes.setEpitesiHozzajarulasInduloEgyenleg(epitesiHozzajarulasNyitoEgyenleg);
        szerzodesRepository.save(szerzodes);
    }

}
