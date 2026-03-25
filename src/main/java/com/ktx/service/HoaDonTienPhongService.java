package com.ktx.service;

import com.ktx.model.HoaDonTienPhong;
import java.util.List;

public interface HoaDonTienPhongService {
    /**
     * Lập hóa đơn tiền phòng mới.
     * 
     * @param maHD Mã hợp đồng
     * @param goiThanhToan Số tháng (6, 9, 12)
     * @param maNV Mã nhân viên lập
     * @return Hóa đơn đã tạo
     */
    HoaDonTienPhong lapHoaDonTienPhong(String maHD, int goiThanhToan, String maNV);

    /**
     * Xác nhận đã đóng tiền cho hóa đơn.
     * 
     * @param maHDTP Mã hóa đơn tiền phòng
     */
    void xacNhanThanhToan(String maHDTP);

    List<HoaDonTienPhong> layTatCa();
    List<HoaDonTienPhong> timTheoSinhVien(String maSV);
    List<HoaDonTienPhong> timTheoPhong(String maPhong);
}
