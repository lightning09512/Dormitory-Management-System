package com.ktx.service;

import com.ktx.model.HoaDon;

import java.math.BigDecimal;
import java.util.List;

public interface HoaDonService {
    List<HoaDon> layTatCaHoaDon();
    List<HoaDon> locHoaDonTheoThangNam(int thang, int nam);
    void taoHoaDon(int thang, int nam, String maPhong, String maNV, 
                   BigDecimal chiSoDienCu, BigDecimal chiSoDienMoi, 
                   BigDecimal chiSoNuocCu, BigDecimal chiSoNuocMoi) throws Exception;
    void thanhToanHoaDon(String maHDon) throws Exception;
    void xoaHoaDon(String maHDon) throws Exception;
}
