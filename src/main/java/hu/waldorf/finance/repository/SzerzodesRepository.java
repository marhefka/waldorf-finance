package hu.waldorf.finance.repository;

import hu.waldorf.finance.model.Szerzodes;

public interface SzerzodesRepository {
    void deleteAll();

    void save(Szerzodes szerzodes);
}