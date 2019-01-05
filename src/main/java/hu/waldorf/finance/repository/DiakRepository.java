package hu.waldorf.finance.repository;

import hu.waldorf.finance.model.Diak;

import java.util.List;

public interface DiakRepository {
    List<Diak> findByCsaladId(int csaladId);

    void deleteAll();

    void store(Diak diak);
}