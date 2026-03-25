package com.ktx.model.dto;

/**
 * DTO truyền dữ liệu thống kê tổng quan lên Dashboard.
 * Được điền bởi {@code ThongKeRepositoryImpl} thông qua 3 truy vấn JPQL COUNT.
 */
public class DashboardStatsDto {

    private long tongSinhVien;
    private long tongPhongTrong;
    private long tongHopDongHieuLuc;
    private java.util.Map<String, java.math.BigDecimal> doanhThuTheoThang;
    private java.math.BigDecimal doanhThuDienNuoc;
    private java.math.BigDecimal doanhThuTienPhong;

    public DashboardStatsDto() {}

    public DashboardStatsDto(long tongSinhVien,
                             long tongPhongTrong,
                             long tongHopDongHieuLuc,
                             java.util.Map<String, java.math.BigDecimal> doanhThuTheoThang,
                             java.math.BigDecimal doanhThuDienNuoc,
                             java.math.BigDecimal doanhThuTienPhong) {
        this.tongSinhVien       = tongSinhVien;
        this.tongPhongTrong     = tongPhongTrong;
        this.tongHopDongHieuLuc = tongHopDongHieuLuc;
        this.doanhThuTheoThang  = doanhThuTheoThang;
        this.doanhThuDienNuoc   = doanhThuDienNuoc;
        this.doanhThuTienPhong  = doanhThuTienPhong;
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

    public java.util.Map<String, java.math.BigDecimal> getDoanhThuTheoThang() { return doanhThuTheoThang; }
    public void setDoanhThuTheoThang(java.util.Map<String, java.math.BigDecimal> doanhThuTheoThang) { this.doanhThuTheoThang = doanhThuTheoThang; }

    public java.math.BigDecimal getDoanhThuDienNuoc() { return doanhThuDienNuoc; }
    public void setDoanhThuDienNuoc(java.math.BigDecimal doanhThuDienNuoc) { this.doanhThuDienNuoc = doanhThuDienNuoc; }

    public java.math.BigDecimal getDoanhThuTienPhong() { return doanhThuTienPhong; }
    public void setDoanhThuTienPhong(java.math.BigDecimal doanhThuTienPhong) { this.doanhThuTienPhong = doanhThuTienPhong; }

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
