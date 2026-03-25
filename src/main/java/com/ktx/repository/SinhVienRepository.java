package com.ktx.repository;

import com.ktx.model.SinhVien;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho entity {@link SinhVien}.
 */
public interface SinhVienRepository {

    /** Lưu mới một sinh viên. */
    void save(SinhVien sinhVien);

    /** Cập nhật thông tin sinh viên. */
    void update(SinhVien sinhVien);

    /** Xóa sinh viên theo mã. */
    void delete(String maSV);

    /** Tìm sinh viên theo mã. */
    Optional<SinhVien> findById(String maSV);

    /** Lấy toàn bộ danh sách sinh viên. */
    List<SinhVien> findAll();

    /** Lấy danh sách sinh viên phân trang. */
    List<SinhVien> findAll(int page, int size);

    /** Đếm tổng số sinh viên. */
    long count();

    /**
     * Tìm sinh viên theo tên (tìm kiếm tương đối, không phân biệt hoa thường).
     *
     * @param keyword từ khóa tìm kiếm
     * @return danh sách {@link SinhVien} khớp
     */
    List<SinhVien> findByHoTen(String keyword);
    
    /**
     * Tìm sinh viên theo MSSV và tên (tìm kiếm tương đối, không phân biệt hoa thường).
     *
     * @param keyword từ khóa tìm kiếm (có thể là MSSV hoặc tên)
     * @return danh sách {@link SinhVien} khớp
     */
    List<SinhVien> findByKeyword(String keyword);

    /** Tìm sinh viên phân trang theo từ khóa. */
    List<SinhVien> findByKeyword(String keyword, int page, int size);

    /** Đếm số sinh viên theo từ khóa. */
    long countByKeyword(String keyword);
}
