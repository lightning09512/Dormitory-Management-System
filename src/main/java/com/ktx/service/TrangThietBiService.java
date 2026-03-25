package com.ktx.service;

import com.ktx.model.entity.TrangThietBi;
import java.util.List;

public interface TrangThietBiService {
    void themThietBi(TrangThietBi trangThietBi) throws Exception;
    void capNhatThietBi(TrangThietBi trangThietBi) throws Exception;
    void xoaThietBi(String maTB) throws Exception;
    List<TrangThietBi> layTatCaThietBi();
    List<TrangThietBi> timKiemTheoPhong(String maPhong);
}
