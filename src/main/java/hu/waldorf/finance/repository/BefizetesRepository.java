package hu.waldorf.finance.repository;

import hu.waldorf.finance.model.Befizetes;

import java.util.List;

public interface BefizetesRepository {
    List<Befizetes> findNemFeldolgozottak();

    void deleteAll();

    void save(Befizetes befizetes);

    Befizetes findById(int befizetesId);
}