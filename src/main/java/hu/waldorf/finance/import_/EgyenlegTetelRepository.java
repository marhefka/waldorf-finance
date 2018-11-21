package hu.waldorf.finance.import_;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EgyenlegTetelRepository extends JpaRepository<Jovairas, Long> {
}