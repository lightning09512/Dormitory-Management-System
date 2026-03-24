package com.ktx.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Màn hình đăng nhập – thiết kế card hiện đại trên nền gradient tối.
 */
public class DangNhapFrame extends JFrame {

    private final JTextField     txtTaiKhoan = new JTextField(22);
    private final JPasswordField txtMatKhau  = new JPasswordField(22);
    private final JButton        btnDangNhap = new JButton("Đăng nhập");
    private final JLabel         lblThongBao = new JLabel(" ");

    public DangNhapFrame() {
        super("Đăng nhập – KTX Manager");
        buildUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(460, 430);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        // Nền gradient
        JPanel root = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(
                        0, 0, UITheme.SIDEBAR_BG,
                        0, getHeight(), new Color(30, 41, 90));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        // Card
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(36, 40, 36, 40));
        card.setPreferredSize(new Dimension(360, 350));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridwidth = 2; gc.gridy = 0;
        gc.insets = new Insets(0, 0, 24, 0);
        gc.anchor = GridBagConstraints.CENTER;

        // Logo
        JLabel logo = new JLabel("KTX Manager");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setForeground(UITheme.SIDEBAR_BG);
        card.add(logo, gc);

        gc.gridy = 1; gc.insets = new Insets(0, 0, 4, 0);
        JLabel sub = new JLabel("Hệ thống Quản lý Ký túc xá");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(UITheme.TEXT_SECONDARY);
        card.add(sub, gc);

        // Fields
        gc.gridwidth = 1; gc.anchor = GridBagConstraints.WEST; gc.gridx = 0;
        gc.gridy = 2; gc.insets = new Insets(16, 0, 4, 10);
        card.add(label("Tài khoản"), gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1; gc.insets = new Insets(16, 0, 4, 0);
        styleField(txtTaiKhoan);
        card.add(txtTaiKhoan, gc);

        gc.gridx = 0; gc.gridy = 3; gc.weightx = 0; gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(8, 0, 4, 10);
        card.add(label("Mật khẩu"), gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1; gc.insets = new Insets(8, 0, 4, 0);
        styleField(txtMatKhau);
        card.add(txtMatKhau, gc);

        // Error label
        gc.gridx = 0; gc.gridy = 4; gc.gridwidth = 2; gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE; gc.insets = new Insets(6, 0, 0, 0);
        lblThongBao.setForeground(UITheme.RED);
        lblThongBao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(lblThongBao, gc);

        // Button
        gc.gridy = 5; gc.insets = new Insets(14, 0, 0, 0);
        gc.fill = GridBagConstraints.HORIZONTAL;
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnDangNhap.setBackground(UITheme.INDIGO);
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBorderPainted(false);
        btnDangNhap.setOpaque(true);
        btnDangNhap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDangNhap.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btnDangNhap.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { 
                btnDangNhap.setBackground(new Color(79, 70, 229)); 
            }
            @Override public void mouseExited (MouseEvent e) { 
                btnDangNhap.setBackground(UITheme.INDIGO); 
            }
        });
        card.add(btnDangNhap, gc);

        root.add(card);
        getRootPane().setDefaultButton(btnDangNhap);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(UITheme.TEXT_PRIMARY);
        return l;
    }

    private void styleField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    // ---- Public API ----
    public String getTaiKhoan() { return txtTaiKhoan.getText().trim(); }
    public String getMatKhau()  { return new String(txtMatKhau.getPassword()); }
    public void setThongBao(String msg) { lblThongBao.setText(msg); }
    public void clearThongBao()         { lblThongBao.setText(" "); }
    public void addDangNhapListener(ActionListener l) { btnDangNhap.addActionListener(l); }
}
