package com.ktx.repository;

import com.ktx.model.HoaDon;

import java.util.List;
import java.util.Optional;

public interface HoaDonRepository {
    void save(HoaDon hoaDon);
    void update(HoaDon hoaDon);
    void delete(String maHDon);
    List<HoaDon> findAll();
    List<HoaDon> findTheoThangNam(int thang, int nam);
    Optional<HoaDon> findById(String maHDon);
    boolean existsByPhongAndThangNam(String maPhong, int thang, int nam);
    Optional<HoaDon> findLatestByPhong(String maPhong);
}
