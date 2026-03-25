package com.ktx.service;

import com.ktx.model.SinhVien;

import java.util.List;
import java.util.Optional;

/**
 * Service interface xử lý nghiệp vụ liên quan đến {@link SinhVien}.
 */
public interface SinhVienService {

    /** Thêm mới sinh viên vào hệ thống. */
    void themSinhVien(SinhVien sinhVien);

    /** Cập nhật thông tin sinh viên. */
    void capNhatSinhVien(SinhVien sinhVien);

    /** Xóa sinh viên theo mã. */
    void xoaSinhVien(String maSV);

    /** Tìm sinh viên theo mã. Ném ngoại lệ nếu không tồn tại. */
    SinhVien timTheoMa(String maSV);

    /** Lấy toàn bộ danh sách sinh viên. */
    List<SinhVien> layTatCa();

    /** Tìm kiếm sinh viên theo tên (tương đối). */
    List<SinhVien> timTheoTen(String keyword);
    
    /** Tìm kiếm sinh viên theo MSSV hoặc tên (tương đối). */
    List<SinhVien> timTheoKeyword(String keyword);

    /** Lấy danh sách sinh viên phân trang. */
    List<SinhVien> layDanhSachPhanTrang(int page, int size);

    /** Đếm tổng số sinh viên. */
    long demTongSo();

    /** Tìm kiếm sinh viên phân trang theo từ khóa. */
    List<SinhVien> timTheoKeywordPhanTrang(String keyword, int page, int size);

    /** Đếm số sinh viên theo từ khóa. */
    long demTheoKeyword(String keyword);
}
