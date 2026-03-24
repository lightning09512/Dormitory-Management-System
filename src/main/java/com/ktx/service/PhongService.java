package com.ktx.service;

import com.ktx.model.Phong;

import java.util.List;

/**
 * Service interface xử lý nghiệp vụ liên quan đến {@link Phong}.
 */
public interface PhongService {

    /** Thêm mới phòng. */
    void themPhong(Phong phong);

    /** Cập nhật thông tin phòng. */
    void capNhatPhong(Phong phong);

    /** Xóa phòng theo mã. */
    void xoaPhong(String maPhong);

    /** Tìm phòng theo mã. Ném ngoại lệ nếu không tồn tại. */
    Phong timTheoMa(String maPhong);

    /** Lấy toàn bộ danh sách phòng. */
    List<Phong> layTatCa();

    /** Lấy danh sách các phòng còn trống. */
    List<Phong> layPhongConTrong();
}
