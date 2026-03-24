package com.ktx.view;

import com.ktx.model.HopDong;
import com.ktx.model.Phong;
import com.ktx.model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 * Panel Lập Hợp Đồng – form xếp phòng + bảng hợp đồng hiện có.
 */
public class LapHopDongPanel extends JPanel {

    private static final String[] HD_COLS = {
            "Mã HĐ", "Mã SV", "Mã phòng", "Ngày bắt đầu", "Ngày kết thúc", "Tiền cọc", "Trạng thái"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(HD_COLS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    // Form fields
    private final JComboBox<SinhVien> cbSinhVien = new JComboBox<>();
    private final JComboBox<Phong>    cbPhong     = new JComboBox<>();

    private final DatePicker dpNgayBD  = createDatePicker();
    private final DatePicker dpNgayKT  = createDatePicker();

    private final JButton btnLap    = UITheme.button("Lập hợp đồng", UITheme.INDIGO);
    private final JButton btnChamDut = UITheme.button("Chấm dứt HĐ", UITheme.RED);
    private final JButton btnLamMoi = UITheme.button("Làm mới", UITheme.SLATE);

    public LapHopDongPanel() {
        super(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        buildUI();
    }

    private void buildUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        header.add(UITheme.pageTitle("Lập Hợp Đồng – Xếp phòng"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ---- Form card ----
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));

        GridBagConstraints lc = new GridBagConstraints();
        lc.anchor = GridBagConstraints.WEST; lc.insets = new Insets(6, 0, 6, 16);
        GridBagConstraints fc = new GridBagConstraints();
        fc.gridx = 1; fc.fill = GridBagConstraints.HORIZONTAL; fc.weightx = 1;
        fc.insets = new Insets(6, 0, 6, 24);
        GridBagConstraints lc2 = new GridBagConstraints();
        lc2.gridx = 2; lc2.anchor = GridBagConstraints.WEST; lc2.insets = new Insets(6, 0, 6, 16);
        GridBagConstraints fc2 = new GridBagConstraints();
        fc2.gridx = 3; fc2.fill = GridBagConstraints.HORIZONTAL; fc2.weightx = 1;
        fc2.insets = new Insets(6, 0, 6, 0);

        // Row 0: Sinh viên / Phòng
        lc.gridy = fc.gridy = lc2.gridy = fc2.gridy = 0;
        formCard.add(boldLabel("Sinh viên:"), lc);
        styleCombo(cbSinhVien);
        formCard.add(cbSinhVien, fc);
        formCard.add(boldLabel("Phòng trống:"), lc2);
        styleCombo(cbPhong);
        formCard.add(cbPhong, fc2);

        lc.gridy = fc.gridy = lc2.gridy = fc2.gridy = 1;
        formCard.add(boldLabel("Ngày bắt đầu:"), lc);
        formCard.add(dpNgayBD, fc);
        formCard.add(boldLabel("Ngày kết thúc:"), lc2);
        formCard.add(dpNgayKT, fc2);

        // Row 2: Buttons
        lc.gridy = 2; lc.gridwidth = 4; lc.insets = new Insets(14, 0, 0, 0);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btns.setOpaque(false);
        btns.add(btnLap); btns.add(btnChamDut); btns.add(btnLamMoi);
        formCard.add(btns, lc);

        // ---- Table card ----
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        JPanel tHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        tHeader.setBackground(Color.WHITE);
        tHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));
        JLabel tLbl = new JLabel("Danh sách hợp đồng");
        tLbl.setFont(UITheme.FONT_BOLD); tLbl.setForeground(UITheme.TEXT_PRIMARY);
        tHeader.add(tLbl);
        tableCard.add(tHeader, BorderLayout.NORTH);

        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCard.add(UITheme.cleanScrollPane(table), BorderLayout.CENTER);

        // Split layout
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formCard, tableCard);
        split.setBorder(null);
        split.setDividerSize(8);
        split.setResizeWeight(0.28);
        add(split, BorderLayout.CENTER);
    }

    // ---- Helpers ----
    private static JLabel boldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_BOLD);
        l.setForeground(UITheme.TEXT_PRIMARY);
        return l;
    }

    private static void styleCombo(JComboBox<?> cb) {
        cb.setFont(UITheme.FONT_BODY);
        cb.setPreferredSize(new Dimension(0, 34));
    }

    private static DatePicker createDatePicker() {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        settings.setFormatForDatesBeforeCommonEra("dd/MM/yyyy");
        settings.setFontValidDate(UITheme.FONT_BODY);
        settings.setFontInvalidDate(UITheme.FONT_BODY);
        DatePicker dp = new DatePicker(settings);
        dp.setDateToToday();
        return dp;
    }

    // ---- Public API ----
    public void setSinhVienList(List<SinhVien> list) {
        cbSinhVien.removeAllItems();
        for (SinhVien sv : list) cbSinhVien.addItem(sv);
        cbSinhVien.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(
                    JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof SinhVien sv) setText(sv.getMaSV() + " – " + sv.getHoTen());
                return this;
            }
        });
    }

    public void setPhongList(List<Phong> list) {
        cbPhong.removeAllItems();
        for (Phong p : list) cbPhong.addItem(p);
        cbPhong.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(
                    JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof Phong p) setText(p.getMaPhong() + " – " + p.getTenPhong()
                        + " (" + p.getSoNguoiHienTai() + "/" + p.getSucChua() + ")");
                return this;
            }
        });
    }

    public void setHopDongList(List<HopDong> list) {
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (HopDong hd : list) {
            tableModel.addRow(new Object[]{
                    hd.getMaHD(), hd.getMaSV(), hd.getMaPhong(),
                    hd.getNgayBatDau() != null ? hd.getNgayBatDau().format(fmt) : "",
                    hd.getNgayKetThuc() != null ? hd.getNgayKetThuc().format(fmt) : "",
                    hd.getTienCoc() != null ? String.format("%,.0f ₫", hd.getTienCoc()) : "",
                    hd.getTrangThai()
            });
        }
    }

    public SinhVien getSelectedSinhVien() { return (SinhVien) cbSinhVien.getSelectedItem(); }
    public Phong    getSelectedPhong()    { return (Phong)    cbPhong.getSelectedItem(); }
    public LocalDate getNgayBatDau()  { return dpNgayBD.getDate(); }
    public LocalDate getNgayKetThuc() { return dpNgayKT.getDate(); }

    public String getSelectedMaHopDong() {
        int row = table.getSelectedRow();
        return row < 0 ? null : (String) tableModel.getValueAt(row, 0);
    }

    public void addLapHopDongListener (ActionListener l) { btnLap.addActionListener(l); }
    public void addChamDutListener    (ActionListener l) { btnChamDut.addActionListener(l); }
    public void addLamMoiListener     (ActionListener l) { btnLamMoi.addActionListener(l); }
}
