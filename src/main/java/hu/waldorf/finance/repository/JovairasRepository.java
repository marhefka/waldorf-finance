package hu.waldorf.finance.repository;

import hu.waldorf.finance.model.Jovairas;

public interface JovairasRepository {
    void deleteAll();

    void insert(Jovairas jovairas);
}