package com.ktx.service;

import com.ktx.model.HopDong;
import com.ktx.model.NhanVien;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface xử lý nghiệp vụ liên quan đến {@link HopDong}.
 */
public interface HopDongService {

    /**
     * Tạo hợp đồng lưu trú mới.
     *
     * <p>Đây là hàm nghiệp vụ cốt lõi: kiểm tra sinh viên, kiểm tra
     * sức chứa phòng, sinh mã hợp đồng, tăng sĩ số phòng, cập nhật
     * trạng thái phòng, rồi lưu xuống DB.</p>
     *
     * @param maSV          Mã sinh viên cần ký hợp đồng
     * @param maPhong       Mã phòng được xếp
     * @param ngayBatDau    Ngày bắt đầu hợp đồng
     * @param ngayKetThuc   Ngày kết thúc hợp đồng
     * @param staff         Nhân viên lập hợp đồng
     * @return              {@link HopDong} vừa được tạo và lưu
     * @throws IllegalArgumentException nếu sinh viên không tồn tại
     * @throws IllegalStateException    nếu phòng đã hết chỗ
     */
    HopDong taoHopDongLuuTru(String maSV, String maPhong,
                              LocalDate ngayBatDau, LocalDate ngayKetThuc,
                              NhanVien staff);

    /**
     * Thanh lý hợp đồng: giảm sĩ số phòng, cập nhật trạng thái phòng
     * về 'Còn trống' nếu phòng chưa đầy, đổi trạng thái hợp đồng
     * thành 'Đã thanh lý'.
     *
     * @param maHD Mã hợp đồng cần thanh lý
     * @throws IllegalArgumentException nếu hợp đồng không tồn tại
     * @throws IllegalStateException    nếu hợp đồng không ở trạng thái 'Đang hiệu lực'
     */
    void thanhLyHopDong(String maHD);

    /** Lấy toàn bộ danh sách hợp đồng. */
    List<HopDong> layTatCa();

    /** Lấy danh sách hợp đồng đang hiệu lực. */
    List<HopDong> layHopDongHieuLuc();

    /** Lấy danh sách hợp đồng của một sinh viên. */
    List<HopDong> layTheoDongSinhVien(String maSV);
}
