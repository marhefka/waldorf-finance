package hu.waldorf.finance.repository;

import hu.waldorf.finance.model.Csalad;

public interface CsaladRepository {
    void deleteAll();

    void store(Csalad csalad);
}