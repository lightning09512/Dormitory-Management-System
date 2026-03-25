package com.ktx.repository;

import com.ktx.model.HoaDonTienPhong;
import java.util.List;
import java.util.Optional;

public interface HoaDonTienPhongRepository {
    void save(HoaDonTienPhong hoaDon);
    void update(HoaDonTienPhong hoaDon);
    void delete(String maHDTP);
    Optional<HoaDonTienPhong> findById(String maHDTP);
    List<HoaDonTienPhong> findAll();
    List<HoaDonTienPhong> findByHopDong(String maHD);
    List<HoaDonTienPhong> findBySinhVien(String maSV);
    List<HoaDonTienPhong> findByPhong(String maPhong);
}
