package com.ktx.repository;

import com.ktx.model.DayNha;

import java.util.List;
import java.util.Optional;

public interface DayNhaRepository {
    List<DayNha> findAll();
    Optional<DayNha> findById(String maDay);
    void save(DayNha dayNha);
    void update(DayNha dayNha);
    void delete(String maDay);
}
