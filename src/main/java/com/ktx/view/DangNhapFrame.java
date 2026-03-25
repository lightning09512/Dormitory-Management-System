package com.ktx.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Màn hình đăng nhập – thiết kế card hiện đại trên nền Slate-100 sạch sẽ.
 */
public class DangNhapFrame extends JFrame {

    private final JTextField     txtTaiKhoan = new JTextField(30);
    private final JPasswordField txtMatKhau  = new JPasswordField(30);
    private final JButton        btnDangNhap = new JButton("Đăng nhập");
    private final JLabel         lblThongBao = new JLabel(" ");

    public DangNhapFrame() {
        super("Đăng nhập – KTX Manager");
        buildUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 750);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        // Main container with clean background
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(241, 245, 249)); // slate-100
        setContentPane(root);

        // Modern Card using FlatLaf style
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.style", "arc: 24; [light]background: #ffffff; [dark]background: #1e293b");
        card.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        card.setPreferredSize(new Dimension(540, 620));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridwidth = 2; gc.gridy = 0;
        gc.insets = new Insets(0, 0, 32, 0);
        gc.anchor = GridBagConstraints.CENTER;

        // Logo
        try {
            ImageIcon logoIcon = new ImageIcon("images.png");
            Image scaledImage = logoIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            JLabel logo = new JLabel(new ImageIcon(scaledImage));
            card.add(logo, gc);
        } catch (Exception e) {}

        gc.gridy = 1; gc.insets = new Insets(0, 0, 8, 0);
        JLabel title = new JLabel("KTX Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(UITheme.TEXT_PRIMARY);
        card.add(title, gc);

        gc.gridy = 2; gc.insets = new Insets(0, 0, 48, 0);
        JLabel sub = new JLabel("Hệ thống Quản lý Ký túc xá");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sub.setForeground(UITheme.TEXT_SECONDARY);
        card.add(sub, gc);

        // Input Fields
        gc.gridwidth = 2; gc.fill = GridBagConstraints.HORIZONTAL; gc.weightx = 1;

        // Username
        gc.gridy = 3; gc.insets = new Insets(0, 0, 12, 0);
        JLabel lblUser = new JLabel("Tài khoản");
        lblUser.setFont(UITheme.FONT_BOLD);
        card.add(lblUser, gc);
        
        gc.gridy = 4; gc.insets = new Insets(0, 0, 24, 0);
        styleField(txtTaiKhoan);
        txtTaiKhoan.setPreferredSize(new Dimension(0, 52));
        card.add(txtTaiKhoan, gc);

        // Password
        gc.gridy = 5; gc.insets = new Insets(0, 0, 12, 0);
        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setFont(UITheme.FONT_BOLD);
        card.add(lblPass, gc);
        
        gc.gridy = 6; gc.insets = new Insets(0, 0, 16, 0);
        styleField(txtMatKhau);
        txtMatKhau.setPreferredSize(new Dimension(0, 52));
        card.add(txtMatKhau, gc);

        // Error message
        gc.gridy = 7; gc.insets = new Insets(0, 0, 24, 0);
        lblThongBao.setForeground(UITheme.RED);
        lblThongBao.setFont(UITheme.FONT_BODY);
        lblThongBao.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblThongBao, gc);

        // Login Button
        gc.gridy = 8; gc.insets = new Insets(10, 0, 0, 0);
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnDangNhap.setBackground(UITheme.INDIGO);
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDangNhap.setPreferredSize(new Dimension(0, 56));
        btnDangNhap.putClientProperty("JButton.buttonType", "roundRect");
        card.add(btnDangNhap, gc);

        root.add(card);
        getRootPane().setDefaultButton(btnDangNhap);
    }

    private void styleField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tf.putClientProperty("JTextField.showClearButton", true);
        tf.putClientProperty("FlatLaf.style", "margin: 8,16,8,16; arc: 16");
        if (tf instanceof JPasswordField) {
            tf.putClientProperty("JTextField.showRevealButton", true);
        }
    }

    // ---- Public API ----
    public String getTaiKhoan() { return txtTaiKhoan.getText().trim(); }
    public String getMatKhau()  { return new String(txtMatKhau.getPassword()); }
    public void setThongBao(String msg) { lblThongBao.setText(msg); }
    public void clearThongBao()         { lblThongBao.setText(" "); }
    public void addDangNhapListener(ActionListener l) { btnDangNhap.addActionListener(l); }
}
