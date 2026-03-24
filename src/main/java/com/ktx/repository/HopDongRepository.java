package com.ktx.repository;

import com.ktx.model.HopDong;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho entity {@link HopDong}.
 */
public interface HopDongRepository {

    /** Lưu mới một hợp đồng. */
    void save(HopDong hopDong);

    /** Cập nhật hợp đồng đã tồn tại. */
    void update(HopDong hopDong);

    /** Xóa hợp đồng theo mã. */
    void delete(String maHD);

    /** Tìm hợp đồng theo mã. */
    Optional<HopDong> findById(String maHD);

    /** Lấy toàn bộ danh sách hợp đồng. */
    List<HopDong> findAll();

    /**
     * Lấy danh sách hợp đồng của một sinh viên.
     *
     * @param maSV mã sinh viên
     * @return danh sách {@link HopDong} của sinh viên đó
     */
    List<HopDong> findBySinhVien(String maSV);

    /**
     * Lấy danh sách hợp đồng đang trong trạng thái "Đang hiệu lực".
     *
     * @return danh sách hợp đồng hiệu lực
     */
    List<HopDong> findHopDongHieuLuc();
}
