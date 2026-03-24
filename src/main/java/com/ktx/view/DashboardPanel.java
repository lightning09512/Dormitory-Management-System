package com.ktx.view;

import com.ktx.model.dto.DashboardStatsDto;

import javax.swing.*;
import java.awt.*;

/**
 * Panel Dashboard – hiển thị thống kê tổng quan.
 */
public class DashboardPanel extends JPanel {

    private final JLabel lblSinhVien   = bigNumber("0");
    private final JLabel lblPhongTrong = bigNumber("0");
    private final JLabel lblHopDong    = bigNumber("0");

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

        // Cards row
        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 0));
        cards.setOpaque(false);
        cards.add(statCard("Tổng sinh viên",    lblSinhVien,   UITheme.BLUE,   "#EFF6FF"));
        cards.add(statCard("Phòng còn trống",   lblPhongTrong, UITheme.GREEN,  "#F0FDF4"));
        cards.add(statCard("Hợp đồng hiệu lực", lblHopDong,    UITheme.AMBER,  "#FFFBEB"));

        JPanel topWrap = new JPanel(new BorderLayout());
        topWrap.setOpaque(false);
        topWrap.add(cards, BorderLayout.NORTH);
        add(topWrap, BorderLayout.NORTH);
        
        // ---- Cover image at bottom ----
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        ImageIcon coverIcon = new ImageIcon("Ktx-2.jpg");
        Image scaledCover = coverIcon.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH);
        JLabel coverLabel = new JLabel(new ImageIcon(scaledCover));
        coverLabel.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));
        
        bottomPanel.add(coverLabel);
        add(bottomPanel, BorderLayout.CENTER);
    }

    private JPanel statCard(String title, JLabel valueLabel, Color accent, String hexBg) {
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR));

        // Top colored stripe
        JPanel stripe = new JPanel();
        stripe.setBackground(accent);
        stripe.setPreferredSize(new Dimension(0, 5));
        card.add(stripe, BorderLayout.NORTH);

        // Content
        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel(title);
        lbl.setFont(UITheme.FONT_BODY);
        lbl.setForeground(UITheme.TEXT_SECONDARY);
        body.add(lbl, gc);

        gc.gridy = 1; gc.insets = new Insets(10, 0, 0, 0);
        valueLabel.setForeground(accent);
        body.add(valueLabel, gc);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    // ---- Public API ----
    public void setStats(DashboardStatsDto s) {
        lblSinhVien.setText(String.valueOf(s.getTongSinhVien()));
        lblPhongTrong.setText(String.valueOf(s.getTongPhongTrong()));
        lblHopDong.setText(String.valueOf(s.getTongHopDongHieuLuc()));
    }

    private static JLabel bigNumber(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 52));
        return l;
    }
}
