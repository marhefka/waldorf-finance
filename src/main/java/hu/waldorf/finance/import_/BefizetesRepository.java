package hu.waldorf.finance.import_;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BefizetesRepository extends JpaRepository<Befizetes, Long> {
    @Query("SELECT b FROM Befizetes b WHERE b.statusz = 'BEIMPORTALVA' ORDER BY b.id")
    List<Befizetes> findNemFeldolgozottak();
}