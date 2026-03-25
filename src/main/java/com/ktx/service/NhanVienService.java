package com.ktx.service;

import com.ktx.model.NhanVien;
import java.util.List;

/**
 * Service quản lý Nhân viên.
 */
public interface NhanVienService {
    List<NhanVien> getAll();
    void addNhanVien(NhanVien nv);
    void updateNhanVien(NhanVien nv);
    void deleteNhanVien(String maNV);
    void resetPassword(String maNV);
}
