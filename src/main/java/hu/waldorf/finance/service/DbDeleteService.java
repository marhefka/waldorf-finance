package hu.waldorf.finance.service;

import com.google.inject.Inject;
import hu.waldorf.finance.repository.BefizetesRepository;
import hu.waldorf.finance.repository.CsaladRepository;
import hu.waldorf.finance.repository.DiakRepository;
import hu.waldorf.finance.repository.JovairasRepository;
import hu.waldorf.finance.repository.SzerzodesRepository;

public class DbDeleteService {
    private final BefizetesRepository befizetesRepository;
    private final SzerzodesRepository szerzodesRepository;
    private final DiakRepository diakRepository;
    private final CsaladRepository csaladRepository;
    private final JovairasRepository jovairasRepository;

    @Inject
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
