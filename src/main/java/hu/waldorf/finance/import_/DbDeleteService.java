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
    private final EgyenlegTetelRepository egyenlegTetelRepository;

    @Autowired
    public DbDeleteService(BefizetesRepository befizetesRepository,
                           SzerzodesRepository szerzodesRepository,
                           DiakRepository diakRepository,
                           CsaladRepository csaladRepository,
                           EgyenlegTetelRepository egyenlegTetelRepository) {

        this.befizetesRepository = befizetesRepository;
        this.szerzodesRepository = szerzodesRepository;
        this.diakRepository = diakRepository;
        this.csaladRepository = csaladRepository;
        this.egyenlegTetelRepository = egyenlegTetelRepository;
    }

    public void deleteDb() {
        egyenlegTetelRepository.deleteAll();
        befizetesRepository.deleteAll();
        szerzodesRepository.deleteAll();
        diakRepository.deleteAll();
        csaladRepository.deleteAll();
    }
}
