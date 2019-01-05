package hu.waldorf.finance.service;

import com.google.inject.Inject;
import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.model.FeldolgozasStatusza;
import hu.waldorf.finance.model.Jovairas;
import hu.waldorf.finance.model.Szerzodes;
import hu.waldorf.finance.model.TetelTipus;
import hu.waldorf.finance.repository.BefizetesRepository;
import hu.waldorf.finance.repository.JovairasRepository;
import hu.waldorf.finance.repository.SzerzodesRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

public class JovairasService {
    private static final String[] honapok = new String[]{"január", "február", "március", "április", "május", "június", "július", "augusztus", "szeptember", "október", "november", "december"};

    private final BefizetesRepository befizetesRepository;
    private final JovairasRepository jovairasRepository;
    private final SzerzodesRepository szerzodesRepository;

    @Inject
    public JovairasService(BefizetesRepository befizetesRepository,
                           JovairasRepository jovairasRepository,
                           SzerzodesRepository szerzodesRepository) {

        this.befizetesRepository = befizetesRepository;
        this.jovairasRepository = jovairasRepository;
        this.szerzodesRepository = szerzodesRepository;
    }

    public void ignoreBefizetes(int befizetesId) {
        Befizetes befizetes = befizetesRepository.findById(befizetesId);
        befizetes.setStatusz(FeldolgozasStatusza.FIGYELMEN_KIVUL_HAGYVA);
        befizetesRepository.store(befizetes);
    }

    public void postponeBefizetes(int befizetesId) {
        Befizetes befizetes = befizetesRepository.findById(befizetesId);
        befizetes.setStatusz(FeldolgozasStatusza.KESOBBRE_HALASZTVA);
        befizetesRepository.store(befizetes);
    }

    public void jovairBefizetest(int szerzodesId, TetelTipus tipus, int befizetesId) {
        Befizetes befizetes = befizetesRepository.findById(befizetesId);
        befizetes.setStatusz(FeldolgozasStatusza.KESZ);
        befizetesRepository.store(befizetes);

        Jovairas jovairas = new Jovairas();
        jovairas.setSzerzodesId(szerzodesId);
        jovairas.setMegnevezes("Befizetés jóváírása");
        jovairas.setTipus(tipus);
        jovairas.setOsszeg(befizetes.getOsszeg());
        jovairas.setBefizetesId(befizetesId);
        jovairas.setKonyvelesiNap(befizetes.getKonyvelesiNap());
        jovairasRepository.store(jovairas);
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
            jovairasRepository.store(jovairas);
        }

        for (Szerzodes szerzodes : szerzodesek) {
            Jovairas jovairas = new Jovairas();
            jovairas.setSzerzodesId(szerzodes.getId());
            jovairas.setMegnevezes(ev + " " + honapok[honap - 1] + " építési hozzájárulás terhelés");
            jovairas.setTipus(TetelTipus.EPITESI);
            jovairas.setOsszeg(-szerzodes.getEpitesiHozzajarulas());
            jovairas.setBefizetesId(null);
            jovairas.setKonyvelesiNap(dateDay);
            jovairasRepository.store(jovairas);
        }
    }
}
