/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Jdbc.DbConnect;
import Model.ChatLieuModel;
import Model.KichThuocModel;
import Model.MauSacModel;
import Model.SanPhamModel;
import Model.XuatXuModel;
import Repository.ChatLieuRepo;
import Repository.KichThuocRepo;
import Repository.MauSacRepo;
import Repository.XuatXuRepo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TONG THI NHUNG
 */
public class ThuocTinh extends javax.swing.JPanel {

    private DefaultTableModel molms = new DefaultTableModel();
    private MauSacRepo ms = new MauSacRepo();
    private ChatLieuRepo cl = new ChatLieuRepo();
    private KichThuocRepo kt = new KichThuocRepo();
    private XuatXuRepo xx = new XuatXuRepo();

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    /**
     * Creates new form ThuocTinh
     */
    public ThuocTinh() {
        initComponents();
        this.filltableMauSac(ms.getAll());
        this.filltableChatLieu(cl.getAll());
        this.filltableKichThuoc(kt.getAll());
        this.filltableXuatXu(xx.getAll());
    }
    //check màu sắc

    private int layIDMoiNhat() {
        int idMoiNhat = 0;
        String sql = "SELECT COUNT(*) FROM Mau_Sac"; // Đếm số lượng màu sắc hiện có

        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                idMoiNhat = rs.getInt(1); // Lấy tổng số lượng màu sắc hiện có
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idMoiNhat;
    }

    MauSacModel readForm() {
        int idMoiNhat = layIDMoiNhat(); // Lấy ID mới nhất từ DB
        String maMauSac = String.format("MS%03d", idMoiNhat + 1); // Format mã: MS001, MS002,...

        String tenMauSac = txttenmausac.getText().trim();
        if (tenMauSac.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên màu sắc!");
            return null;
        }

        // Kiểm tra xem mã hoặc tên màu đã tồn tại chưa
        if (ms.kiemTraTonTai(maMauSac, tenMauSac)) {
            JOptionPane.showMessageDialog(this, "Mã màu đã tồn tại!");
            return null;
        }

        return new MauSacModel(maMauSac, tenMauSac); // Trả về đối tượng MauSacModel
    }

    private int layIDMoiNhat2() {
        int idMoiNhat = 0;
        String sql = "SELECT COUNT(*) FROM Xuat_Xu"; // Đếm số lượng màu sắc hiện có

        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                idMoiNhat = rs.getInt(1); // Lấy tổng số lượng màu sắc hiện có
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idMoiNhat;
    }

    XuatXuModel readForm2() {
        int idMoiNhat = layIDMoiNhat2(); // Lấy ID mới nhất từ DB
        String maXuatXu = String.format("XX%03d", idMoiNhat + 1); // Format mã: MS001, MS002,...

        String tenXuatXu = txtTenXuatXu.getText().trim();
        if (tenXuatXu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên xuất xứ!");
            return null;
        }

        // Kiểm tra xem mã hoặc tên màu đã tồn tại chưa
        if (xx.kiemTraTonTai2(maXuatXu, tenXuatXu)) {
            JOptionPane.showMessageDialog(this, "Mã xuất xứ đã tồn tại!");
            return null;
        }

        return new XuatXuModel(tenXuatXu, maXuatXu);
    }

    private int layIDMoiNhat3() {
        int idMoiNhat = 0;
        String sql = "SELECT COUNT(*) FROM Chat_Lieu"; // Đếm số lượng màu sắc hiện có

        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                idMoiNhat = rs.getInt(1); // Lấy tổng số lượng màu sắc hiện có
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idMoiNhat;
    }

    ChatLieuModel readForm3() {
        int idMoiNhat = layIDMoiNhat3(); // Lấy ID mới nhất từ DB
        String maChatLieu = String.format("CL%03d", idMoiNhat + 1); // Format mã: MS001, MS002,...

        String tenChatLieu = txtTenChatLieu.getText().trim();
        if (tenChatLieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên chất liệu!");
            return null;
        }

        // Kiểm tra xem mã hoặc tên màu đã tồn tại chưa
        if (cl.kiemTraTonTai3(maChatLieu, tenChatLieu)) {
            JOptionPane.showMessageDialog(this, "Mã chất liệu đã tồn tại!");
            return null;
        }

        return new ChatLieuModel(maChatLieu, tenChatLieu);
    }

    private int layIDMoiNhat4() {
        int idMoiNhat = 0;
        String sql = "SELECT COUNT(*) FROM Kich_Thuoc";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                idMoiNhat = rs.getInt(1); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idMoiNhat;
    }

    KichThuocModel readForm4() {
        int idMoiNhat = layIDMoiNhat4(); // Lấy ID mới nhất từ DB
        String maKichThuoc = String.format("KT%03d", idMoiNhat + 1); // Format mã: MS001, MS002,...

        String tenKichThuoc = txtTenKichThuoc.getText().trim();
        if (tenKichThuoc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên kích thước!");
            return null;
        }
        if (kt.kiemTraTonTai4(maKichThuoc, tenKichThuoc)) {
            JOptionPane.showMessageDialog(this, "Mã kích thước đã tồn tại!");
            return null;
        }

        return new KichThuocModel(maKichThuoc, tenKichThuoc);
    }

    ///////////////////////////////////////////////////////////////////////
    void filltableXuatXu(ArrayList<XuatXuModel> list) {
        molms = (DefaultTableModel) tblxuatxu.getModel();
        molms.setRowCount(0);
        int i = 1;
        for (XuatXuModel x : list) {
            if (x != null) {
                molms.addRow(new Object[]{
                    i++,
                    x.getMaXuatXu(),
                    x.getTenXuatXu()
                });
            }
        }
    }

    void filltableMauSac(ArrayList<MauSacModel> list) {
        molms = (DefaultTableModel) tblmausac.getModel();
        molms.setRowCount(0);
        int i = 1;
        for (MauSacModel x : list) {
            if (x != null) {
                molms.addRow(new Object[]{
                    i++,
                    x.getMaMauSac(),
                    x.getTenMauSac()
                });
            }
        }
    }

    void filltableChatLieu(ArrayList<ChatLieuModel> list) {
        molms = (DefaultTableModel) tblchatlieu.getModel();
        molms.setRowCount(0);
        int i = 1;
        for (ChatLieuModel x : list) {
            if (x != null) {
                molms.addRow(new Object[]{
                    i++,
                    x.getMaChatLieu(),
                    x.getTenChatLieu()
                });
            }
        }
    }

    void filltableKichThuoc(ArrayList<KichThuocModel> list) {
        molms = (DefaultTableModel) tblKichThuoc.getModel();
        molms.setRowCount(0);
        int i = 1;
        for (KichThuocModel x : list) {
            if (x != null) {
                molms.addRow(new Object[]{
                    i++,
                    x.getMaKichThuoc(),
                    x.getTenKichThuoc()
                });
            }
        }
    }

    public void showDataMS(int i) {
        txt_MaMauSac.setText(tblmausac.getValueAt(i, 1).toString());
        txttenmausac.setText(tblmausac.getValueAt(i, 2).toString());
    }

    public void showDataCL(int i) {
        txtMaChatLieu.setText(tblchatlieu.getValueAt(i, 1).toString());
        txtTenChatLieu.setText(tblchatlieu.getValueAt(i, 2).toString());
    }

    public void showDataKT(int i) {
        txtMaKichThuoc.setText(tblKichThuoc.getValueAt(i, 1).toString());
        txtTenKichThuoc.setText(tblKichThuoc.getValueAt(i, 2).toString());
    }

    public void showDataXX(int i) {
        txtMaXuatXu.setText(tblxuatxu.getValueAt(i, 1).toString());
        txtTenXuatXu.setText(tblxuatxu.getValueAt(i, 2).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txttenkichthuoc = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblmausac = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txttenmausac = new javax.swing.JTextField();
        txt_MaMauSac = new javax.swing.JTextField();
        btnThemMS = new javax.swing.JButton();
        btnSuaMS = new javax.swing.JButton();
        btnXoaMS = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblxuatxu = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        txtMaXuatXu = new javax.swing.JTextField();
        txtTenXuatXu = new javax.swing.JTextField();
        btnXuatXuThem = new javax.swing.JButton();
        btnXuatXuSua = new javax.swing.JButton();
        btnXuatXuXoa = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblchatlieu = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        txtMaChatLieu = new javax.swing.JTextField();
        txtTenChatLieu = new javax.swing.JTextField();
        btnChatLieuThem = new javax.swing.JButton();
        btnChatLieuSua = new javax.swing.JButton();
        btnCLXoa = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKichThuoc = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaKichThuoc = new javax.swing.JTextField();
        txtTenKichThuoc = new javax.swing.JTextField();
        btnKTThem = new javax.swing.JButton();
        btnKSua = new javax.swing.JButton();
        btnKTXoa = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jPanel1.setToolTipText("");
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tblmausac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Tên màu sắc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblmausac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblmausacMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblmausac);

        jLabel6.setText("Mã:");

        jLabel7.setText("Tên màu sắc:");

        txt_MaMauSac.setEditable(false);
        txt_MaMauSac.setEnabled(false);

        btnThemMS.setText("Thêm");
        btnThemMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMSActionPerformed(evt);
            }
        });

        btnSuaMS.setText("Sửa");
        btnSuaMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMSActionPerformed(evt);
            }
        });

        btnXoaMS.setText("Xóa");
        btnXoaMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMSActionPerformed(evt);
            }
        });

        jButton1.setText("Làm mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_MaMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttenmausac, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(btnThemMS)
                .addGap(58, 58, 58)
                .addComponent(btnSuaMS)
                .addGap(59, 59, 59)
                .addComponent(btnXoaMS)
                .addGap(47, 47, 47)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_MaMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txttenmausac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemMS)
                    .addComponent(btnSuaMS)
                    .addComponent(btnXoaMS)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txttenkichthuoc.addTab("Màu sắc", jPanel5);

        jLabel14.setText("Tên Xuất Xứ");

        tblxuatxu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Tên quốc gia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblxuatxu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblxuatxuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblxuatxu);

        jLabel18.setText("Mã:");

        txtMaXuatXu.setEditable(false);
        txtMaXuatXu.setEnabled(false);

        btnXuatXuThem.setText("Thêm");
        btnXuatXuThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatXuThemActionPerformed(evt);
            }
        });

        btnXuatXuSua.setText("Sửa");
        btnXuatXuSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatXuSuaActionPerformed(evt);
            }
        });

        btnXuatXuXoa.setText("Xóa");
        btnXuatXuXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatXuXoaActionPerformed(evt);
            }
        });

        jButton2.setText("Làm mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel14))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenXuatXu, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(txtMaXuatXu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addComponent(btnXuatXuThem)
                .addGap(57, 57, 57)
                .addComponent(btnXuatXuSua)
                .addGap(52, 52, 52)
                .addComponent(btnXuatXuXoa)
                .addGap(41, 41, 41)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtMaXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTenXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatXuThem)
                    .addComponent(btnXuatXuSua)
                    .addComponent(btnXuatXuXoa)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txttenkichthuoc.addTab("Xuất xứ", jPanel7);

        jLabel19.setText("Tên chất liệu:");

        tblchatlieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Tên chất liệu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblchatlieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblchatlieuMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblchatlieu);

        jLabel23.setText("Mã: ");

        txtMaChatLieu.setEditable(false);
        txtMaChatLieu.setEnabled(false);

        btnChatLieuThem.setText("Thêm");
        btnChatLieuThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatLieuThemActionPerformed(evt);
            }
        });

        btnChatLieuSua.setText("Sửa");
        btnChatLieuSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatLieuSuaActionPerformed(evt);
            }
        });

        btnCLXoa.setText("Xóa");
        btnCLXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCLXoaActionPerformed(evt);
            }
        });

        jButton3.setText("Làm mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel19))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaChatLieu)
                            .addComponent(txtTenChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(btnChatLieuThem)
                .addGap(53, 53, 53)
                .addComponent(btnChatLieuSua, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnCLXoa)
                .addGap(49, 49, 49)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtMaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTenChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChatLieuThem)
                    .addComponent(btnChatLieuSua)
                    .addComponent(btnCLXoa)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txttenkichthuoc.addTab("Chất liệu", jPanel8);

        tblKichThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Kích thước"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKichThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKichThuocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKichThuoc);

        jLabel9.setText("Mã:");

        jLabel10.setText("Kích thước:");

        txtMaKichThuoc.setEditable(false);
        txtMaKichThuoc.setEnabled(false);

        btnKTThem.setText("Thêm");
        btnKTThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKTThemActionPerformed(evt);
            }
        });

        btnKSua.setText("Sửa");
        btnKSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKSuaActionPerformed(evt);
            }
        });

        btnKTXoa.setText("Xóa");
        btnKTXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKTXoaActionPerformed(evt);
            }
        });

        jButton4.setText("Làm mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(61, 61, 61)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaKichThuoc)
                                    .addComponent(txtTenKichThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(244, 244, 244)
                                .addComponent(btnKTThem)
                                .addGap(42, 42, 42)
                                .addComponent(btnKSua)
                                .addGap(42, 42, 42)
                                .addComponent(btnKTXoa)
                                .addGap(44, 44, 44)
                                .addComponent(jButton4)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMaKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTenKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKTThem)
                    .addComponent(btnKSua)
                    .addComponent(btnKTXoa)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txttenkichthuoc.addTab("Kích Thước", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txttenkichthuoc)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txttenkichthuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblmausacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblmausacMouseClicked
        // TODO add your handling code here:
        int i = tblmausac.getSelectedRow();
        this.showDataMS(i);
    }//GEN-LAST:event_tblmausacMouseClicked

    private void tblchatlieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblchatlieuMouseClicked
        // TODO add your handling code here:
        int i = tblchatlieu.getSelectedRow();
        this.showDataCL(i);

    }//GEN-LAST:event_tblchatlieuMouseClicked

    private void tblKichThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKichThuocMouseClicked
        // TODO add your handling code here:
        int i = tblKichThuoc.getSelectedRow();
        this.showDataKT(i);
    }//GEN-LAST:event_tblKichThuocMouseClicked

    private void tblxuatxuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblxuatxuMouseClicked
        // TODO add your handling code here:
        int i = tblxuatxu.getSelectedRow();
        this.showDataXX(i);
    }//GEN-LAST:event_tblxuatxuMouseClicked


    private void btnThemMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMSActionPerformed
        // TODO add your handling code here:
        if (this.readForm() != null) {
            if (ms.themMS(this.readForm()) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                this.filltableMauSac(ms.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnThemMSActionPerformed

    private void btnSuaMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMSActionPerformed
        // TODO add your handling code here:
        String maMauSac = txt_MaMauSac.getText().trim();
        String tenMauSac = txttenmausac.getText().trim();

        // Kiểm tra nhập đầy đủ thông tin
        if (maMauSac.isEmpty() || tenMauSac.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Tạo đối tượng màu sắc mới
        MauSacModel m = new MauSacModel(maMauSac, tenMauSac);

        // Gọi phương thức sửa màu sắc trong Repository
        int result = ms.suaMS(m);

        // Kiểm tra kết quả cập nhật
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật màu sắc thành công!");
            this.filltableMauSac(ms.getAll()); // Cập nhật lại bảng
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã màu sắc hoặc lỗi cập nhật!");
        }
    }//GEN-LAST:event_btnSuaMSActionPerformed

    private void btnXoaMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMSActionPerformed
        // TODO add your handling code here:
        // Lấy mã màu sắc từ ô nhập
        String maMauSac = txt_MaMauSac.getText().trim();

        // Kiểm tra xem người dùng đã chọn màu sắc để xóa chưa
        if (maMauSac.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập hoặc chọn mã màu sắc cần xóa!");
            return;
        }

        // Hỏi xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa màu sắc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Gọi phương thức xóa trong Repository
        int result = ms.xoaMS(maMauSac);

        // Kiểm tra kết quả
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Xóa màu sắc thành công!");
            filltableMauSac(ms.getAll()); // Cập nhật lại bảng sau khi xóa
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã màu sắc hoặc lỗi xóa!");
        }
    }//GEN-LAST:event_btnXoaMSActionPerformed

    private void btnXuatXuThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatXuThemActionPerformed
        // TODO add your handling code here:
        if (this.readForm2() != null) {
            if (xx.themXX(this.readForm2()) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                this.filltableXuatXu(xx.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnXuatXuThemActionPerformed

    private void btnXuatXuSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatXuSuaActionPerformed
        // TODO add your handling code here:
        String maXuatXu = txtMaXuatXu.getText().trim();
        String tenXuatXu = txtTenXuatXu.getText().trim();

        // Tạo đối tượng màu sắc mới
        XuatXuModel m = new XuatXuModel(tenXuatXu, maXuatXu);

        // Gọi phương thức sửa màu sắc trong Repository
        int result = xx.suaXX(m);

        // Kiểm tra kết quả cập nhật
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật xuất xứ thành công!");
            this.filltableXuatXu(xx.getAll()); // Cập nhật lại bảng
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã xuất xứ hoặc lỗi cập nhật!");
        }
    }//GEN-LAST:event_btnXuatXuSuaActionPerformed

    private void btnXuatXuXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatXuXoaActionPerformed
        // TODO add your handling code here:
        // Lấy mã màu sắc từ ô nhập
        String maXuatXu = txtMaXuatXu.getText().trim();

        // Hỏi xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa xuất xứ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Gọi phương thức xóa trong Repository
        int result = xx.xoaXX(maXuatXu);

        // Kiểm tra kết quả
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Xóa xuất xứ thành công!");
            this.filltableXuatXu(xx.getAll()); // Cập nhật lại bảng sau khi xóa
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã xuất xứ hoặc lỗi xóa!");
        }
    }//GEN-LAST:event_btnXuatXuXoaActionPerformed

    private void btnChatLieuThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatLieuThemActionPerformed
        // TODO add your handling code here:
        if (this.readForm3() != null) {
            if (cl.themCL(this.readForm3()) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                filltableChatLieu(cl.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }
    }//GEN-LAST:event_btnChatLieuThemActionPerformed

    private void btnChatLieuSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatLieuSuaActionPerformed
        // TODO add your handling code here:
        String maChatLieu = txtMaChatLieu.getText().trim();
        String tenChatLieu = txtTenChatLieu.getText().trim();
        ChatLieuModel m = new ChatLieuModel(maChatLieu, tenChatLieu);
        int result = cl.suaCL(m);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật chất liệu thành công!");
            filltableChatLieu(cl.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã chất liệu hoặc lỗi cập nhật!");
        }
    }//GEN-LAST:event_btnChatLieuSuaActionPerformed

    private void btnCLXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCLXoaActionPerformed
        // TODO add your handling code here:
        // Lấy mã màu sắc từ ô nhập
        String maChatLieu = txtMaChatLieu.getText().trim();

        // Hỏi xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa chất liệu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Gọi phương thức xóa trong Repository
        int result = cl.xoaCL(maChatLieu);

        // Kiểm tra kết quả
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Xóa chất liệu thành công!");
            filltableChatLieu(cl.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã chất liệu hoặc lỗi xóa!");
        }
    }//GEN-LAST:event_btnCLXoaActionPerformed

    private void btnKTThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKTThemActionPerformed
        // TODO add your handling code here:
        if (this.readForm4() != null) {
            if (kt.themKT(this.readForm4()) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                filltableKichThuoc(kt.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        }

    }//GEN-LAST:event_btnKTThemActionPerformed

    private void btnKSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKSuaActionPerformed
        // TODO add your handling code here:
        String maKichThuoc = txtMaKichThuoc.getText().trim();
        String tenKichThuoc = txtTenKichThuoc.getText().trim();
        KichThuocModel m = new KichThuocModel(maKichThuoc, tenKichThuoc);
        int result = kt.suaKT(m);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật kích thước thành công!");
            this.filltableKichThuoc(kt.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã kích thước hoặc lỗi cập nhật!");
        }
    }//GEN-LAST:event_btnKSuaActionPerformed

    private void btnKTXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKTXoaActionPerformed
        // TODO add your handling code here:
        String maKichThuoc = txtMaKichThuoc.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa kích thước này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Gọi phương thức xóa trong Repository
        int result = kt.xoaKT(maKichThuoc);

        // Kiểm tra kết quả
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Xóa kích thước thành công!");
            filltableKichThuoc(kt.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã kích thước hoặc lỗi xóa!");
        }
    }//GEN-LAST:event_btnKTXoaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txt_MaMauSac.setText("");
        txttenmausac.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        txtMaXuatXu.setText("");
        txtTenXuatXu.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        txtMaChatLieu.setText("");
        txtTenChatLieu.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        txtMaKichThuoc.setText("");
        txtTenKichThuoc.setText("");
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCLXoa;
    private javax.swing.JButton btnChatLieuSua;
    private javax.swing.JButton btnChatLieuThem;
    private javax.swing.JButton btnKSua;
    private javax.swing.JButton btnKTThem;
    private javax.swing.JButton btnKTXoa;
    private javax.swing.JButton btnSuaMS;
    private javax.swing.JButton btnThemMS;
    private javax.swing.JButton btnXoaMS;
    private javax.swing.JButton btnXuatXuSua;
    private javax.swing.JButton btnXuatXuThem;
    private javax.swing.JButton btnXuatXuXoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblKichThuoc;
    private javax.swing.JTable tblchatlieu;
    private javax.swing.JTable tblmausac;
    private javax.swing.JTable tblxuatxu;
    private javax.swing.JTextField txtMaChatLieu;
    private javax.swing.JTextField txtMaKichThuoc;
    private javax.swing.JTextField txtMaXuatXu;
    private javax.swing.JTextField txtTenChatLieu;
    private javax.swing.JTextField txtTenKichThuoc;
    private javax.swing.JTextField txtTenXuatXu;
    private javax.swing.JTextField txt_MaMauSac;
    private javax.swing.JTabbedPane txttenkichthuoc;
    private javax.swing.JTextField txttenmausac;
    // End of variables declaration//GEN-END:variables
}
