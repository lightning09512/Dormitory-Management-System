package com.ktx.view;

import com.ktx.model.dto.DashboardStatsDto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Panel Dashboard – hiển thị thống kê tổng quan.
 */
public class DashboardPanel extends JPanel {

    private final JLabel lblSinhVien   = bigNumber("0");
    private final JLabel lblPhongTrong = bigNumber("0");
    private final JLabel lblHopDong    = bigNumber("0");
    private final JLabel lblDienNuoc   = bigNumber("0 ₫");
    private final JLabel lblTienPhong  = bigNumber("0 ₫");
    private final JLabel lblTongDT     = bigNumber("0 ₫");

    public DashboardPanel() {
        super(new BorderLayout(0, 24));
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buildUI();
    }

    private void buildUI() {
        // Header row
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = UITheme.pageTitle("Dashboard – Tổng quan");
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Cards row (2 rows, 3 cols)
        JPanel cards = new JPanel(new GridLayout(2, 3, 22, 22));
        cards.setOpaque(false);
        cards.add(statCard("Tổng sinh viên",    lblSinhVien,   UITheme.BLUE,   "#EFF6FF"));
        cards.add(statCard("Phòng còn trống",   lblPhongTrong, UITheme.GREEN,  "#F0FDF4"));
        cards.add(statCard("Hợp đồng hiệu lực", lblHopDong,    UITheme.AMBER,  "#FFFBEB"));
        
        cards.add(statCard("Doanh thu Điện Nước", lblDienNuoc,  UITheme.BLUE,   "#EFF6FF"));
        cards.add(statCard("Doanh thu Tiền Phòng",lblTienPhong, UITheme.INDIGO, "#EEF2FF"));
        cards.add(statCard("Tổng Doanh Thu",     lblTongDT,     UITheme.GREEN,  "#F0FDF4"));

        // Wrapper để bảng không bị kéo dãn toàn màn hình
        JPanel mainWrap = new JPanel(new BorderLayout(0, 30));
        mainWrap.setOpaque(false);
        mainWrap.add(cards, BorderLayout.NORTH); // Cards chỉ bám ở đỉnh

        // Logo Panel hiển thị ảnh thu phóng động
        JPanel imagePanel = new JPanel() {
            private final Image img = new ImageIcon("d:/hoc/OOSE/KTX/ktxlogo.png").getImage();
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (img != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    
                    int w = getWidth();
                    int h = getHeight();
                    int iW = img.getWidth(this);
                    int iH = img.getHeight(this);
                    if (iW > 0 && iH > 0) {
                        double scale = (double)w / iW; // Khớp chiều rộng tỉ lệ
                        int tW = w;
                        int tH = (int)(iH * scale);
                        g2.drawImage(img, 0, (h-tH)/2, tW, tH, this);
                    }
                }
            }
        };
        imagePanel.setOpaque(false);
        mainWrap.add(imagePanel, BorderLayout.CENTER);

        add(mainWrap, BorderLayout.CENTER);
    }

    private JPanel statCard(String title, JLabel valueLabel, Color accent, String hexBg) {
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.style", "arc: 16");
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        // Content
        JPanel body = new JPanel(new GridBagLayout());
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel(title);
        lbl.setFont(UITheme.FONT_BODY);
        lbl.setForeground(UITheme.TEXT_SECONDARY);
        body.add(lbl, gc);

        gc.gridy = 1; gc.insets = new Insets(8, 0, 0, 0);
        valueLabel.setForeground(accent);
        body.add(valueLabel, gc);

        // Subtle accent bar at the bottom
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setPreferredSize(new Dimension(0, 4));
        card.add(accentBar, BorderLayout.SOUTH);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    // ---- Public API ----
    public void setStats(DashboardStatsDto s) {
        lblSinhVien.setText(String.valueOf(s.getTongSinhVien()));
        lblPhongTrong.setText(String.valueOf(s.getTongPhongTrong()));
        lblHopDong.setText(String.valueOf(s.getTongHopDongHieuLuc()));

        java.text.DecimalFormat df = new java.text.DecimalFormat("#,### ₫");
        
        java.math.BigDecimal dtDienNuoc = s.getDoanhThuDienNuoc() != null ? s.getDoanhThuDienNuoc() : java.math.BigDecimal.ZERO;
        java.math.BigDecimal dtTienPhong = s.getDoanhThuTienPhong() != null ? s.getDoanhThuTienPhong() : java.math.BigDecimal.ZERO;
        java.math.BigDecimal dtTong = dtDienNuoc.add(dtTienPhong);

        lblDienNuoc.setText(df.format(dtDienNuoc));
        lblTienPhong.setText(df.format(dtTienPhong));
        lblTongDT.setText(df.format(dtTong));
    }

    private static JLabel bigNumber(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 52));
        return l;
    }
}
