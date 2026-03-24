package com.ktx.model.dto;

/**
 * DTO truyền dữ liệu thống kê tổng quan lên Dashboard.
 * Được điền bởi {@code ThongKeRepositoryImpl} thông qua 3 truy vấn JPQL COUNT.
 */
public class DashboardStatsDto {

    private long tongSinhVien;         // COUNT(SinhVien)
    private long tongPhongTrong;       // COUNT(Phong WHERE trangThai = 'Còn trống')
    private long tongHopDongHieuLuc;   // COUNT(HopDong WHERE trangThai = 'Đang hiệu lực')

    // ----------------------------------------------------------------
    // Constructor mặc định
    // ----------------------------------------------------------------
    public DashboardStatsDto() {}

    // ----------------------------------------------------------------
    // Constructor đầy đủ tham số
    // ----------------------------------------------------------------
    public DashboardStatsDto(long tongSinhVien,
                             long tongPhongTrong,
                             long tongHopDongHieuLuc) {
        this.tongSinhVien       = tongSinhVien;
        this.tongPhongTrong     = tongPhongTrong;
        this.tongHopDongHieuLuc = tongHopDongHieuLuc;
    }

    // ----------------------------------------------------------------
    // Getters & Setters
    // ----------------------------------------------------------------
    public long getTongSinhVien()                      { return tongSinhVien; }
    public void setTongSinhVien(long tongSinhVien)     { this.tongSinhVien = tongSinhVien; }

    public long getTongPhongTrong()                       { return tongPhongTrong; }
    public void setTongPhongTrong(long tongPhongTrong)    { this.tongPhongTrong = tongPhongTrong; }

    public long getTongHopDongHieuLuc()                          { return tongHopDongHieuLuc; }
    public void setTongHopDongHieuLuc(long tongHopDongHieuLuc)   { this.tongHopDongHieuLuc = tongHopDongHieuLuc; }

    // ----------------------------------------------------------------
    // toString
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return "DashboardStatsDto{"
                + "tongSinhVien="       + tongSinhVien
                + ", tongPhongTrong="   + tongPhongTrong
                + ", tongHopDongHieuLuc=" + tongHopDongHieuLuc
                + '}';
    }
}
