package hu.waldorf.finance.repository;

import hu.waldorf.finance.model.Szerzodes;

import java.util.List;

public interface SzerzodesRepository {
    void deleteAll();

    void save(Szerzodes szerzodes);

    List<Szerzodes> findAll();
}