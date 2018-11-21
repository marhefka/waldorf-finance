package hu.waldorf.finance.import_;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class JovairasService {
    private final BefizetesRepository befizetesRepository;
    private final EgyenlegTetelRepository egyenlegRepository;

    @Autowired
    public JovairasService(BefizetesRepository befizetesRepository, EgyenlegTetelRepository egyenlegRepository) {
        this.befizetesRepository = befizetesRepository;
        this.egyenlegRepository = egyenlegRepository;
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
        egyenlegRepository.save(jovairas);
    }

}
