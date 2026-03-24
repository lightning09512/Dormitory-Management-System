package com.ktx.controller;

import com.ktx.model.DayNha;
import com.ktx.model.Phong;
import com.ktx.repository.DayNhaRepository;
import com.ktx.service.PhongService;
import com.ktx.view.QuanLyPhongPanel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller quản lý phòng – lọc, thêm, sửa.
 */
public class PhongController {

    private final QuanLyPhongPanel  view;
    private final PhongService       service;
    private final DayNhaRepository   dayNhaRepo;

    public PhongController(QuanLyPhongPanel view,
                           PhongService service,
                           DayNhaRepository dayNhaRepo) {
        this.view       = view;
        this.service    = service;
        this.dayNhaRepo = dayNhaRepo;

        view.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadAll();
            }
        });

        loadAll();
        bindListeners();
    }

    private void bindListeners() {
        view.addLocListener   (e -> handleLoc());
        view.addThemListener  (e -> handleThem());
        view.addSuaListener   (e -> handleSua());
        view.addXoaListener   (e -> handleXoa());
        view.addLamMoiListener(e -> loadAll());
    }

    // ----------------------------------------------------------------
    // Handlers
    // ----------------------------------------------------------------
    private void handleXoa() {
        String maPhong = view.getSelectedMaPhong();
        if (maPhong == null) { 
            showError("Vui lòng chọn phòng cần xóa."); 
            return; 
        }
        
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Xác nhận xóa phòng " + maPhong + "?\nThao tác này không thể hoàn tác.",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.xoaPhong(maPhong);
                showInfo("Xóa phòng thành công!");
                loadAll();
            } catch (Exception ex) {
                String cause = getRootCause(ex).toLowerCase();
                if (cause.contains("foreign key") || cause.contains("constraint") || cause.contains("tham chiếu")) {
                    showError("Không thể xóa phòng này vì đang liên kết với hợp đồng hoặc sinh viên!");
                } else {
                    showError("Lỗi xóa phòng:\n" + getRootCause(ex));
                }
            }
        }
    }
    private void handleLoc() {
        String filter = view.getSelectedFilter();
        try {
            List<Phong> result = "Tất cả".equals(filter)
                    ? service.layTatCa()
                    : service.layTatCa().stream()
                        .filter(p -> filter.equals(p.getTrangThai()))
                        .toList();
            view.setPhongList(result);
        } catch (Exception ex) {
            showError("Lỗi lọc phòng: " + ex.getMessage());
        }
    }

    private void handleThem() {
        List<DayNha> danhSachDay = loadDayNha();
        // Tạm thời bỏ kiểm tra dãy nhà để có thể thêm phòng
        // if (danhSachDay.isEmpty()) {
        //     showError("Chưa có dãy nhà nào trong hệ thống.\nVui lòng thêm dãy nhà trước.");
        //     return;
        // }
        PhongFormDialog dlg = new PhongFormDialog(null, "Thêm phòng mới", null, danhSachDay);
        dlg.setVisible(true);
        if (dlg.isConfirmed()) {
            try {
                service.themPhong(dlg.getPhong());
                showInfo("Thêm phòng thành công!");
                loadAll();
            } catch (Exception ex) {
                showError("Lỗi thêm phòng:\n" + getRootCause(ex));
            }
        }
    }

    private void handleSua() {
        String maPhong = view.getSelectedMaPhong();
        if (maPhong == null) { showError("Vui lòng chọn phòng cần sửa."); return; }
        List<DayNha> danhSachDay = loadDayNha();
        try {
            Phong existing = service.timTheoMa(maPhong);
            PhongFormDialog dlg = new PhongFormDialog(null, "Sửa thông tin phòng",
                                                      existing, danhSachDay);
            dlg.setVisible(true);
            if (dlg.isConfirmed()) {
                service.capNhatPhong(dlg.getPhong());
                showInfo("Cập nhật thành công!");
                loadAll();
            }
        } catch (Exception ex) {
            showError("Lỗi:\n" + getRootCause(ex));
        }
    }

    private void loadAll() {
        try { view.setPhongList(service.layTatCa()); }
        catch (Exception ex) { showError("Lỗi tải dữ liệu phòng:\n" + getRootCause(ex)); }
    }

    private List<DayNha> loadDayNha() {
        try { return dayNhaRepo.findAll(); }
        catch (Exception ex) { return List.of(); }
    }

    /** Lấy thông báo lỗi gốc (bỏ qua các wrapper exception). */
    private static String getRootCause(Throwable t) {
        while (t.getCause() != null) t = t.getCause();
        return t.getMessage() != null ? t.getMessage() : t.getClass().getSimpleName();
    }

    private void showInfo(String msg)  { JOptionPane.showMessageDialog(view, msg, "Thành công", JOptionPane.INFORMATION_MESSAGE); }
    private void showError(String msg) { JOptionPane.showMessageDialog(view, msg, "Lỗi", JOptionPane.ERROR_MESSAGE); }

    // ----------------------------------------------------------------
    // Inner dialog – Form thêm/sửa phòng
    // ----------------------------------------------------------------
    private static class PhongFormDialog extends JDialog {
        private boolean confirmed = false;
        private final JTextField txtMaPhong   = new JTextField(10);
        private final JTextField txtTenPhong  = new JTextField(15);
        private final JTextField txtLoaiPhong = new JTextField(15);
        private final JTextField txtSucChua   = new JTextField(5);
        private final JTextField txtGiaPhong  = new JTextField(15);
        private final JComboBox<String> cbTrangThai = new JComboBox<>(
                new String[]{"Còn trống", "Đang sử dụng", "Bảo trì"});
        private final JComboBox<DayNha> cbDayNha;

        PhongFormDialog(JFrame parent, String title, Phong p, List<DayNha> danhSachDay) {
            super(parent, title, true);
            cbDayNha = new JComboBox<>(danhSachDay.toArray(new DayNha[0]));
            cbDayNha.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof DayNha d)
                        setText(d.getMaDay() + " – " + d.getTenDay());
                    return this;
                }
            });

            if (p != null) prefill(p, danhSachDay);
            buildUI();
            pack();
            setLocationRelativeTo(parent);
        }

        private void prefill(Phong p, List<DayNha> list) {
            txtMaPhong.setText(p.getMaPhong()); txtMaPhong.setEditable(false);
            txtTenPhong.setText(p.getTenPhong()); txtLoaiPhong.setText(p.getLoaiPhong());
            txtSucChua.setText(String.valueOf(p.getSucChua()));
            txtGiaPhong.setText(p.getGiaPhong() != null ? p.getGiaPhong().toPlainString() : "0");
            cbTrangThai.setSelectedItem(p.getTrangThai());
            // Chọn dãy nhà tương ứng
            for (DayNha d : list) {
                if (d.getMaDay().equals(p.getMaDay())) {
                    cbDayNha.setSelectedItem(d);
                    break;
                }
            }
        }

        private void buildUI() {
            JPanel p = new JPanel(new GridBagLayout());
            p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
            GridBagConstraints lc = new GridBagConstraints();
            lc.anchor = GridBagConstraints.WEST; lc.insets = new Insets(4,4,4,8);
            GridBagConstraints fc = new GridBagConstraints();
            fc.gridx = 1; fc.fill = GridBagConstraints.HORIZONTAL;
            fc.weightx = 1; fc.insets = new Insets(4,0,4,4);

            String[] labels = {"Mã phòng:", "Tên phòng:", "Loại phòng:",
                               "Sức chứa:", "Giá phòng (VND):", "Trạng thái:", "Dãy nhà:"};
            JComponent[] fields = {txtMaPhong, txtTenPhong, txtLoaiPhong,
                                   txtSucChua, txtGiaPhong, cbTrangThai, cbDayNha};
            for (int i = 0; i < labels.length; i++) {
                lc.gridy = i; fc.gridy = i;
                p.add(new JLabel(labels[i]), lc);
                p.add(fields[i], fc);
            }

            JButton ok = new JButton("✅ Lưu");
            ok.addActionListener(e -> { confirmed = true; dispose(); });
            JButton cancel = new JButton("Hủy");
            cancel.addActionListener(e -> dispose());
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btns.add(cancel); btns.add(ok);
            setLayout(new BorderLayout());
            add(p, BorderLayout.CENTER); add(btns, BorderLayout.SOUTH);
        }

        boolean isConfirmed() { return confirmed; }

        Phong getPhong() {
            BigDecimal gia;
            try { gia = new BigDecimal(txtGiaPhong.getText().trim()); }
            catch (Exception e) { gia = BigDecimal.ZERO; }
            int sucChua;
            try { sucChua = Integer.parseInt(txtSucChua.getText().trim()); }
            catch (Exception e) { sucChua = 1; }
            DayNha selectedDay = (DayNha) cbDayNha.getSelectedItem();
            String maDay = selectedDay != null ? selectedDay.getMaDay() : "";
            return new Phong(txtMaPhong.getText().trim(), txtTenPhong.getText().trim(),
                    txtLoaiPhong.getText().trim(), sucChua, 0,
                    gia, (String) cbTrangThai.getSelectedItem(), maDay);
        }
    }
}
