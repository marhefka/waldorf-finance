package hu.waldorf.finance.import_;

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

    @Autowired
    public DbDeleteService(BefizetesRepository befizetesRepository,
                           SzerzodesRepository szerzodesRepository,
                           DiakRepository diakRepository,
                           CsaladRepository csaladRepository) {

        this.befizetesRepository = befizetesRepository;
        this.szerzodesRepository = szerzodesRepository;
        this.diakRepository = diakRepository;
        this.csaladRepository = csaladRepository;
    }

    public void deleteDb() {
        befizetesRepository.deleteAll();
        szerzodesRepository.deleteAll();
        diakRepository.deleteAll();
        csaladRepository.deleteAll();
    }
}
