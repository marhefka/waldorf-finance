package hu.waldorf.finance.service;

import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.BefizetesRepository;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import hu.waldorf.finance.model.Jovairas;
import hu.waldorf.finance.model.JovairasRepository;
import hu.waldorf.finance.model.Szerzodes;
import hu.waldorf.finance.model.SzerzodesRepository;
import hu.waldorf.finance.model.TetelTipus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Transactional
public class JovairasService {
    private static final String[] honapok = new String[]{"január", "február", "március", "április", "május", "június", "július", "augusztus", "szeptember", "október", "november", "december"};

    private final BefizetesRepository befizetesRepository;
    private final JovairasRepository jovairasRepository;
    private final SzerzodesRepository szerzodesRepository;

    @Autowired
    public JovairasService(BefizetesRepository befizetesRepository,
                           JovairasRepository jovairasRepository,
                           SzerzodesRepository szerzodesRepository) {

        this.befizetesRepository = befizetesRepository;
        this.jovairasRepository = jovairasRepository;
        this.szerzodesRepository = szerzodesRepository;
    }

    public void ignoreBefizetes(long befizetesId) {
        Befizetes befizetes = befizetesRepository.findById(befizetesId).get();
        befizetes.setStatusz(FeldolgozasStatusza.FIGYELMEN_KIVUL_HAGYVA);
        befizetesRepository.save(befizetes);
    }

    public void postponeBefizetes(long befizetesId) {
        Befizetes befizetes = befizetesRepository.findById(befizetesId).get();
        befizetes.setStatusz(FeldolgozasStatusza.KESOBBRE_HALASZTVA);
        befizetesRepository.save(befizetes);
    }

    public void jovairBefizetest(int szerzodesId, TetelTipus tipus, long befizetesId) {
        Befizetes befizetes = befizetesRepository.findById(befizetesId).get();
        befizetes.setStatusz(FeldolgozasStatusza.KESZ);
        befizetesRepository.save(befizetes);

        Jovairas jovairas = new Jovairas();
        jovairas.setSzerzodesId(szerzodesId);
        jovairas.setMegnevezes("Befizetés jóváírása");
        jovairas.setTipus(tipus);
        jovairas.setOsszeg(befizetes.getOsszeg());
        jovairas.setBefizetesId(befizetesId);
        jovairas.setKonyvelesiNap(befizetes.getKonyvelesiNap());
        jovairasRepository.save(jovairas);
    }

    public void terhel(int ev, int honap) {
        LocalDate day = LocalDate.of(ev, honap, 1).plusMonths(1).minusDays(1);
        java.util.Date dateDay = Date.from(day.atStartOfDay().toInstant(ZoneOffset.UTC));

        List<Szerzodes> szerzodesek = szerzodesRepository.findAll();

        for (Szerzodes szerzodes : szerzodesek) {
            Jovairas jovairas = new Jovairas();
            jovairas.setSzerzodesId(szerzodes.getId());
            jovairas.setMegnevezes(ev + " " + honapok[honap - 1] + " működési támogatás terhelés");
            jovairas.setTipus(TetelTipus.MUKODESI);
            jovairas.setOsszeg(-szerzodes.getMukodesiKoltsegTamogatas());
            jovairas.setBefizetesId(null);
            jovairas.setKonyvelesiNap(dateDay);
            jovairasRepository.save(jovairas);
        }

        for (Szerzodes szerzodes : szerzodesek) {
            Jovairas jovairas = new Jovairas();
            jovairas.setSzerzodesId(szerzodes.getId());
            jovairas.setMegnevezes(ev + " " + honapok[honap - 1] + " építési hozzájárulás terhelés");
            jovairas.setTipus(TetelTipus.EPITESI);
            jovairas.setOsszeg(-szerzodes.getEpitesiHozzajarulas());
            jovairas.setBefizetesId(null);
            jovairas.setKonyvelesiNap(dateDay);
            jovairasRepository.save(jovairas);
        }
    }
}
