package com.ktx.repository;

import com.ktx.model.CauHinhGia;
import java.util.List;
import java.util.Optional;

public interface CauHinhGiaRepository {
    List<CauHinhGia> findAll();
    void update(CauHinhGia gia);
    Optional<CauHinhGia> findByLoai(String loaiDichVu);
}
