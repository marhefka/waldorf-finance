package hu.waldorf.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class FamilyService {
    private final FamilyRepository familyRepository;

    @Autowired
    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    public void addFamily() {
        Family family = new Family();
        family.setSignee("signee" + new Random().nextInt());
        familyRepository.save(family);
    }
}
