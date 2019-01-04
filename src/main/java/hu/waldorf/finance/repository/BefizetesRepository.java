package hu.waldorf.finance.repository;

import hu.waldorf.finance.model.Befizetes;

import java.util.List;

public interface BefizetesRepository {
//    @Query("SELECT b FROM Befizetes b WHERE b.statusz = 'BEIMPORTALVA' ORDER BY b.id")
    List<Befizetes> findNemFeldolgozottak();

    void deleteAll();

    void save(Befizetes befizetes);
}