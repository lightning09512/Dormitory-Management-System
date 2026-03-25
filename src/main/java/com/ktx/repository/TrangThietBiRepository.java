package com.ktx.repository;

import com.ktx.model.entity.TrangThietBi;
import java.util.List;

public interface TrangThietBiRepository {
    void save(TrangThietBi trangThietBi);
    void update(TrangThietBi trangThietBi);
    void delete(String maTB);
    List<TrangThietBi> findAll();
    List<TrangThietBi> findByMaPhong(String maPhong);
}
