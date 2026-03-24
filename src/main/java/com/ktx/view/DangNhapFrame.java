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

    private final JTextField     txtTaiKhoan = new JTextField(30);
    private final JPasswordField txtMatKhau  = new JPasswordField(30);
    private final JButton        btnDangNhap = new JButton("Đăng nhập");
    private final JLabel         lblThongBao = new JLabel(" ");

    public DangNhapFrame() {
        super("Đăng nhập – KTX Manager");
        buildUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
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
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(40, 45, 40, 45)
        ));
        card.setPreferredSize(new Dimension(480, 480));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridwidth = 2; gc.gridy = 0;
        gc.insets = new Insets(0, 0, 24, 0);
        gc.anchor = GridBagConstraints.CENTER;

        // Logo - Load ảnh từ file
        ImageIcon logoIcon = new ImageIcon("images.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaledImage));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(logo, gc);

        gc.gridy = 1; gc.insets = new Insets(16, 0, 8, 0);
        JLabel title = new JLabel("KTX Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(title, gc);

        gc.gridy = 2; gc.insets = new Insets(0, 0, 28, 0);
        JLabel sub = new JLabel("Hệ thống Quản lý Ký túc xá");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(new Color(102, 102, 102));
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(sub, gc);

        // Fields
        gc.gridwidth = 1; gc.anchor = GridBagConstraints.WEST; gc.gridx = 0;
        gc.gridy = 3; gc.insets = new Insets(20, 0, 6, 10);
        card.add(label("Tài khoản"), gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1; gc.insets = new Insets(20, 0, 6, 0);
        styleField(txtTaiKhoan);
        card.add(txtTaiKhoan, gc);

        gc.gridx = 0; gc.gridy = 4; gc.weightx = 0; gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(8, 0, 6, 10);
        card.add(label("Mật khẩu"), gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1; gc.insets = new Insets(8, 0, 6, 0);
        styleField(txtMatKhau);
        card.add(txtMatKhau, gc);

        // Error label
        gc.gridx = 0; gc.gridy = 5; gc.gridwidth = 2; gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE; gc.insets = new Insets(8, 0, 0, 0);
        lblThongBao.setForeground(UITheme.RED);
        lblThongBao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(lblThongBao, gc);

        // Button
        gc.gridy = 6; gc.insets = new Insets(30, 0, 0, 0);
        gc.fill = GridBagConstraints.HORIZONTAL;
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnDangNhap.setBackground(new Color(79, 70, 229));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBorderPainted(false);
        btnDangNhap.setOpaque(true);
        btnDangNhap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDangNhap.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        btnDangNhap.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { 
                btnDangNhap.setBackground(new Color(99, 102, 241));
            }
            @Override public void mouseExited (MouseEvent e) { 
                btnDangNhap.setBackground(new Color(79, 70, 229));
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
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        tf.setBackground(new Color(250, 250, 250));
        tf.setOpaque(true);
        
        // Add hover effect
        tf.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(79, 70, 229), 1),
                    BorderFactory.createEmptyBorder(12, 14, 12, 14)));
                tf.setBackground(Color.WHITE);
            }
            @Override public void mouseExited(MouseEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(12, 14, 12, 14)));
                tf.setBackground(new Color(250, 250, 250));
            }
        });
    }

    // ---- Public API ----
    public String getTaiKhoan() { return txtTaiKhoan.getText().trim(); }
    public String getMatKhau()  { return new String(txtMatKhau.getPassword()); }
    public void setThongBao(String msg) { lblThongBao.setText(msg); }
    public void clearThongBao()         { lblThongBao.setText(" "); }
    public void addDangNhapListener(ActionListener l) { btnDangNhap.addActionListener(l); }
}
