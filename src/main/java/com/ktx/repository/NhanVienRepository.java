package com.ktx.repository;

import com.ktx.model.NhanVien;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho entity {@link NhanVien}.
 */
public interface NhanVienRepository {
    void save(NhanVien nhanVien);
    void update(NhanVien nhanVien);
    void delete(String maNV);
    Optional<NhanVien> findById(String maNV);
    Optional<NhanVien> findByTaiKhoan(String taiKhoan);
    List<NhanVien> findAll();
}
