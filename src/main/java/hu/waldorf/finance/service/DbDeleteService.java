package hu.waldorf.finance.service;

import hu.waldorf.finance.model.BefizetesRepository;
import hu.waldorf.finance.model.CsaladRepository;
import hu.waldorf.finance.model.DiakRepository;
import hu.waldorf.finance.model.JovairasRepository;
import hu.waldorf.finance.model.SzerzodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DbDeleteService {
    private final BefizetesRepository befizetesRepository;
    private final SzerzodesRepository szerzodesRepository;
    private final DiakRepository diakRepository;
    private final CsaladRepository csaladRepository;
    private final JovairasRepository jovairasRepository;

    @Autowired
    public DbDeleteService(BefizetesRepository befizetesRepository,
                           SzerzodesRepository szerzodesRepository,
                           DiakRepository diakRepository,
                           CsaladRepository csaladRepository,
                           JovairasRepository jovairasRepository) {

        this.befizetesRepository = befizetesRepository;
        this.szerzodesRepository = szerzodesRepository;
        this.diakRepository = diakRepository;
        this.csaladRepository = csaladRepository;
        this.jovairasRepository = jovairasRepository;
    }

    public void deleteDb() {
        jovairasRepository.deleteAll();
        befizetesRepository.deleteAll();
        szerzodesRepository.deleteAll();
        diakRepository.deleteAll();
        csaladRepository.deleteAll();
    }
}
