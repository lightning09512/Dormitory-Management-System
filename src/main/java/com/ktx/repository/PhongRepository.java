package com.ktx.repository;

import com.ktx.model.Phong;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho entity {@link Phong}.
 */
public interface PhongRepository {

    /** Lưu mới một phòng. */
    void save(Phong phong);

    /** Cập nhật thông tin phòng đã tồn tại. */
    void update(Phong phong);

    /** Xóa phòng theo mã. */
    void delete(String maPhong);

    /** Tìm phòng theo mã. */
    Optional<Phong> findById(String maPhong);

    /** Lấy toàn bộ danh sách phòng. */
    List<Phong> findAll();

    /**
     * Lấy danh sách phòng có trạng thái "Còn trống".
     *
     * @return danh sách {@link Phong} còn trống
     */
    List<Phong> findPhongTrong();
}
