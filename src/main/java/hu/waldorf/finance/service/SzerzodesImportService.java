package hu.waldorf.finance.service;

import hu.waldorf.finance.model.Csalad;
import hu.waldorf.finance.model.CsaladRepository;
import hu.waldorf.finance.model.Diak;
import hu.waldorf.finance.model.DiakRepository;
import hu.waldorf.finance.model.Jovairas;
import hu.waldorf.finance.model.JovairasRepository;
import hu.waldorf.finance.model.Szerzodes;
import hu.waldorf.finance.model.SzerzodesRepository;
import hu.waldorf.finance.model.TetelTipus;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class SzerzodesImportService {
    @Autowired
    private CsaladRepository csaladRepository;

    @Autowired
    private DiakRepository diakRepository;

    @Autowired
    private SzerzodesRepository szerzodesRepository;

    @Autowired
    private JovairasRepository jovairasRepository;

    private Set<Integer> csaladok = new HashSet<>();

    public void importSzerzodesek(File file) throws Exception {
        Stream<String> lines = Files.lines(file.toPath(), Charsets.UTF_8);
        List<String> lines2 = lines.collect(Collectors.toList());

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
                }
            }
        }

        Szerzodes szerzodes = new Szerzodes();
        szerzodes.setTamogato(tamogatoNeve);
        szerzodes.setMukodesiKoltsegTamogatas(mukodesiKoltsegTamogatas);
        szerzodes.setEpitesiHozzajarulas(epitesiHozzajarulas);
        szerzodes.setCsaladId(csaladId);
        szerzodes.setMukodesiKoltsegTamogatasInduloEgyenleg(mukodesiTamogatasNyitoEgyenleg);
        szerzodes.setEpitesiHozzajarulasInduloEgyenleg(epitesiHozzajarulasNyitoEgyenleg);
        szerzodesRepository.save(szerzodes);

        LocalDate date2018Sept1 = LocalDate.of(2018, 9, 1);
        Date date = Date.from(date2018Sept1.atStartOfDay().toInstant(ZoneOffset.UTC));

        Jovairas jovairas = new Jovairas();
        jovairas.setSzerzodesId(szerzodes.getId());
        jovairas.setMegnevezes("2018. szeptember 1-jei működési támogatás számla nyitóegyenlege");
        jovairas.setTipus(TetelTipus.MUKODESI);
        jovairas.setOsszeg(mukodesiTamogatasNyitoEgyenleg);
        jovairas.setBefizetesId(null);
        jovairas.setKonyvelesiNap(date);
        jovairasRepository.save(jovairas);

        Jovairas jovairas2 = new Jovairas();
        jovairas2.setSzerzodesId(szerzodes.getId());
        jovairas2.setMegnevezes("2018. szeptember 1-jei építési hozzájárulási számla nyitóegyenlege");
        jovairas2.setTipus(TetelTipus.EPITESI);
        jovairas2.setOsszeg(epitesiHozzajarulasNyitoEgyenleg);
        jovairas2.setBefizetesId(null);
        jovairas2.setKonyvelesiNap(date);
        jovairasRepository.save(jovairas2);
    }

}
