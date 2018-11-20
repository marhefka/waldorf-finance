package hu.waldorf.finance.import_;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DiakRepository extends JpaRepository<Diak, Long> {
    @Query("SELECT d FROM Diak d WHERE d.csaladId = :csaladId")
    List<Diak> findByCsaladId(@Param("csaladId") long csaladId);
}