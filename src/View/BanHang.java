/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Jdbc.DbConnect;
import Model.HoaDonBanHang;
import Model.SanPhamChiTiet;
import Model.ThuocTinh.ThuongHieu;
import Model.KhachHang;
import Repository.BanHangRepository;
import Repository.KhachHangRepository;
import Repository.SanPhamRepository;
import Repository.ThuocTinh.ThuongHieuRepository;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TONG THI NHUNG
 */
public class BanHang extends javax.swing.JPanel {
    
    private DefaultTableModel md = new DefaultTableModel();
    private BanHangRepository repo = new BanHangRepository();
    double tongTienSauGiam = 0;
    private ThuongHieuRepository th = new ThuongHieuRepository();
    private KhachHangRepository khachHangg = new KhachHangRepository();
    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("us", "VN"));
    private String selectMaKH;
    private String selectedTenKH;
    private String selectedsdt;
    
    public BanHang() {
        initComponents();
        getAllSanPham(repo.getAll());
        getAllHoaDon(repo.getAllHoaDon());
        String maHoaDon = txtMaHD.getText();
        ThuongHieu();
        hienThiKhachhang();
    }
    
    public String getMaKhachhang(String tenKichCo) {
        String maThuongHieu = (String) cbxKH.getSelectedItem();
        String sql = "SELECT MaKhachHang FROM Khach_Hang WHERE TenKhachHang = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenKichCo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maThuongHieu = rs.getString("MaKhachHang");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maThuongHieu;
    }
    
    public void hienThiKhachhang() {
        cbxKH.removeAllItems();
        cbxKH.addItem("");
        ArrayList<KhachHang> ls = khachHangg.getAll();
        for (KhachHang l : ls) {
            cbxKH.addItem(l.getTenKhachHang());
            System.out.println("Hiển thị thông tin khách hàng thành công");
        }
    }
    
    public String getSoDienThoaiByTenKhachHang(String tenKhachHang) {
        String sdt = "";
        String sql = "SELECT SoDienThoai FROM Khach_Hang WHERE TenKhachHang = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenKhachHang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sdt = rs.getString("SoDienThoai");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sdt;
    }
    
    public void taoHoaDon() {
        String maNhanVien = "NV001";
        String tenKhachHang = cbxKH.getSelectedItem() != null ? cbxKH.getSelectedItem().toString() : "";
        String maKhachhang;
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            maKhachhang = "KH001";
        } else {
            maKhachhang = getMaKhachhang(tenKhachHang);
            System.out.println("Hiển thị thông tin khách hàng");
        }
        
        int trangThai = 0;
        String layMaxID = "SELECT ISNULL(MAX(ID), 0) + 1 FROM Hoa_Don";
        String insertSql = """
        INSERT INTO Hoa_Don (MaHoaDon, MaNhanVien, MaKhachHang, MaVoucher, NgayTao, TongTien, TongTienSauGiamGia, TienKhachTra, TrangThai)
        VALUES (?, ?, ?, NULL, GETDATE(), NULL, NULL, NULL, ?);
    """;
        
        try (Connection con = DbConnect.getConnection()) {
            // Lấy mã hóa đơn mới
            PreparedStatement psMaxID = con.prepareStatement(layMaxID);
            ResultSet rs = psMaxID.executeQuery();
            int idMoi = 1;
            if (rs.next()) {
                idMoi = rs.getInt(1);
            }
            String maHoaDon = String.format("HD%03d", idMoi);

            // Hộp thoại xác nhận trước khi tạo hóa đơn
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có muốn tạo hóa đơn không?",
                    "Xác nhận tạo " + maHoaDon,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Người dùng chọn "Có" => tiến hành tạo hóa đơn
                PreparedStatement ps = con.prepareStatement(insertSql);
                ps.setString(1, maHoaDon);
                ps.setString(2, maNhanVien);
                ps.setString(3, maKhachhang);
                ps.setInt(4, trangThai);
                
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Hóa đơn được tạo thành công! Mã: " + maHoaDon);
                    getAllHoaDon(repo.getAllHoaDon());
                } else {
                    JOptionPane.showMessageDialog(null, "Có lỗi khi tạo hóa đơn.");
                }
            } else {
                // Người dùng chọn "Không" => không làm gì cả
                JOptionPane.showMessageDialog(null, "Đã hủy tạo hóa đơn.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu.");
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTimkiem = new javax.swing.JTextField();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_sanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTienKH = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cbxKH = new javax.swing.JComboBox<>();
        txtSDT = new javax.swing.JLabel();
        txtTongtien = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JLabel();
        txtNV = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnTaoHoaDon = new javax.swing.JButton();
        txtThanhTien = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnAddKH = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_hoadonchitiet = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblhoadon = new javax.swing.JTable();

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        jLabel13.setText("Tìm kiếm sản phẩm:");

        txtTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimkiemActionPerformed(evt);
            }
        });
        txtTimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimkiemKeyReleased(evt);
            }
        });

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Đang còn hàng", "Hết Hàng", " " }));
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        tbl_sanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Sản Phẩm", "Mã Sản Phẩm", "Tên Sản Phẩm", "Giá Sản Phẩm", "Số Lượng", "Kích Thước", "Máu Sắc", "Thương Hiệu", "Chất Liệu", "Xuất Xứ", "Trang Thái"
            }
        ));
        tbl_sanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_sanPham);

        jLabel1.setText("Trạng thái");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tạo hóa đơn"));

        jLabel5.setText("Mã NV:");

        jLabel6.setText("Tên KH:");

        jLabel7.setText("SĐT:");

        jLabel8.setText("Tổng tiền:");

        jLabel11.setText("Tiền khách trả:");

        txtTienKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKHActionPerformed(evt);
            }
        });

        jLabel12.setText("Tiền thừa:");

        txtTienThua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienThuaActionPerformed(evt);
            }
        });

        jLabel14.setText("Mã HD:");

        cbxKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxKHItemStateChanged(evt);
            }
        });
        cbxKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxKHActionPerformed(evt);
            }
        });

        txtSDT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(51, 51, 255));

        txtTongtien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTongtien.setForeground(new java.awt.Color(255, 0, 51));

        txtMaHD.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        txtNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jButton1.setText("Thanh Toán");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnTaoHoaDon.setText("Tạo Hóa Đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        txtThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtThanhTien.setForeground(new java.awt.Color(255, 0, 51));

        jLabel15.setText("Thành tiền:");

        jButton7.setText("Hủy Hóa Đơn");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        btnAddKH.setBackground(new java.awt.Color(0, 0, 0));
        btnAddKH.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnAddKH.setForeground(new java.awt.Color(255, 255, 255));
        btnAddKH.setText("+");
        btnAddKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddKHMouseClicked(evt);
            }
        });
        btnAddKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel7))
                            .addComponent(jLabel14)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(179, 179, 179))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtTongtien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbxKH, javax.swing.GroupLayout.Alignment.LEADING, 0, 225, Short.MAX_VALUE)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(txtNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAddKH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtTienKH))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(42, 42, 42)
                                .addComponent(txtTienThua)))
                        .addGap(226, 226, 226))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNV, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(cbxKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(txtTongtien, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtTienKH, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoHoaDon)
                    .addComponent(jButton1)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        jButton3.setText("Xóa Tất Cả");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Xóa Khỏi Giỏ Hàng");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tbl_hoadonchitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ));
        tbl_hoadonchitiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_hoadonchitietMouseClicked(evt);
            }
        });
        tbl_hoadonchitiet.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                tbl_hoadonchitietComponentMoved(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_hoadonchitiet);

        jButton8.setText("Cập nhập số lượng");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton8))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách hóa đơn chờ"));

        tblhoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Id Hóa Đơn", "Nhân Viên", "Khách Hàng", "Ngày Tạo", "Trang Thái"
            }
        ));
        tblhoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblhoadonMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblhoadon);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimkiemActionPerformed

    }//GEN-LAST:event_txtTimkiemActionPerformed

    private void txtTienKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKHActionPerformed

    private void txtTienThuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienThuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienThuaActionPerformed

    private void cbxKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxKHItemStateChanged
        //    String u = service.getSDTTheoTen((String) cbxKH.getSelectedItem());
        // txtSDT.setText(u);
    }//GEN-LAST:event_cbxKHItemStateChanged

    private void btnAddKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddKHMouseClicked

    }//GEN-LAST:event_btnAddKHMouseClicked

    private void btnAddKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKHActionPerformed
        
        KhachHang1 khachHang1 = new KhachHang1(this); // Mở form thêm khách hàng
        khachHang1.setVisible(true);

    }//GEN-LAST:event_btnAddKHActionPerformed
    public void setSelectKhachHang(String maKH, String tenKH, String sdt) {
        this.selectMaKH = maKH;
        this.selectedTenKH = tenKH;
        this.selectedsdt = sdt;
        for (int i = 0; i < cbxKH.getItemCount(); i++) {
            if (cbxKH.getItemAt(i).toString().contains(maKH)
                    || cbxKH.getItemAt(i).toString().contains(tenKH)) {
                cbxKH.setSelectedIndex(i);
                return;
            }
        }
        cbxKH.addItem(maKH + " - " + tenKH);
        cbxKH.setSelectedItem(maKH + " - " + tenKH);
        txtSDT.setText(sdt);
    }
    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        taoHoaDon();
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void tblhoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblhoadonMouseClicked
        // TODO add your handling code here
        int selectedRow = tblhoadon.getSelectedRow();
        if (selectedRow != -1) {
            String maHoaDon = tblhoadon.getValueAt(selectedRow, 1).toString();
            String tongTien = tblhoadon.getValueAt(selectedRow, 4).toString();
            String tenKhachHang = tblhoadon.getValueAt(selectedRow, 3).toString(); // tên KH từ bảng

            txtMaHD.setText(maHoaDon);
            txtTongtien.setText(tongTien + " VNĐ");
            for (int i = 0; i < cbxKH.getItemCount(); i++) {
                String item = cbxKH.getItemAt(i);
                if (item.contains(tenKhachHang)) {
                    cbxKH.setSelectedIndex(i);
                    break;
                }
            }
            
            loadHoaDonChiTiet(maHoaDon);
        }
    }//GEN-LAST:event_tblhoadonMouseClicked

    private void tbl_hoadonchitietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_hoadonchitietMouseClicked
        

    }//GEN-LAST:event_tbl_hoadonchitietMouseClicked

    private void tbl_hoadonchitietComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tbl_hoadonchitietComponentMoved

    }//GEN-LAST:event_tbl_hoadonchitietComponentMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int trangThai = 1;
        String maHoaDon = txtMaHD.getText().trim();
        
        String tongTienText = txtTongtien.getText().replaceAll("[^\\d.]", "");
        double tongTien = 0;
        try {
            tongTien = Double.parseDouble(tongTienText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            tongTien = 0;
        }
        
        String thanhTienText = txtThanhTien.getText().replaceAll("[^\\d.]", "");
        double tongTienSauGiamGia = 0;
        try {
            tongTienSauGiamGia = Double.parseDouble(thanhTienText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            tongTienSauGiamGia = 0;
        }
        
        String tienKhachTraText = txtTienKH.getText().replaceAll("[^\\d.]", "");
        double tienKhachTra = 0;
        try {
            tienKhachTra = Double.parseDouble(tienKhachTraText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            tienKhachTra = 0;
        }
        
        double tienThua = tienKhachTra - tongTienSauGiamGia;
        
        if (tienKhachTra <= 0) {
            JOptionPane.showMessageDialog(null, "Khách chưa đưa tiền, vui lòng xem lại!");
            return;
        }
        
        if (tienThua < 0) {
            JOptionPane.showMessageDialog(null, "Tiền khách đưa không đủ để thanh toán!");
            return;
        }
        
        String updateSql = """
    UPDATE Hoa_Don 
    SET TongTien = ?, TongTienSauGiamGia = ?, TienKhachTra = ?, TrangThai = ?
    WHERE MaHoaDon = ?
""";
        
        try (Connection con = DbConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(updateSql);
            ps.setDouble(1, tongTien);
            ps.setDouble(2, tongTienSauGiamGia);
            ps.setDouble(3, tienKhachTra);
            ps.setInt(4, trangThai);
            ps.setString(5, maHoaDon);
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                getAllHoaDon(repo.getAllHoaDon());
//                txtGiamGia.setText("0");
                txtMaHD.setText("Vui lòng tạo !");
                txtThanhTien.setText("0");
                txtTongtien.setText("0");
                loadHoaDonChiTiet("Vui lòng tạo !");
                txtTienThua.setText("0");
                txtTienKH.setText("0");
                cbxKH.setSelectedIndex(-1);
                txtSDT.setText("");
                JOptionPane.showMessageDialog(null, "Thanh toán thành công! Tiền thừa: " + tienThua + " VNĐ");
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn để cập nhật.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu.");
        }
        

    }//GEN-LAST:event_jButton1ActionPerformed
    private void xoaSanPhamKhoiHoaDon() {
        int selectedRow = tbl_hoadonchitiet.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
            return;
        }
        String maSanPhamChiTiet = tbl_hoadonchitiet.getValueAt(selectedRow, 1).toString();
        int soLuongXoa = Integer.parseInt(tbl_hoadonchitiet.getValueAt(selectedRow, 3).toString());
        try (Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false);
            String sqlXoa = "DELETE FROM Hoa_Don_Chi_Tiet WHERE maSanPhamChiTiet = ?";
            try (PreparedStatement psXoa = conn.prepareStatement(sqlXoa)) {
                psXoa.setString(1, maSanPhamChiTiet);
                psXoa.executeUpdate();
            }
            String sqlCapNhat = "UPDATE San_Pham_Chi_Tiet SET SoLuong = SoLuong + ? WHERE maSanPhamChiTiet = ?";
            try (PreparedStatement psCapNhat = conn.prepareStatement(sqlCapNhat)) {
                psCapNhat.setInt(1, soLuongXoa);
                psCapNhat.setString(2, maSanPhamChiTiet);
                psCapNhat.executeUpdate();
            }
            conn.commit();
            JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm và cập nhật lại số lượng!");
            getAllSanPham(repo.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm!");
        }
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        xoaSanPhamKhoiHoaDon();
        String maHoaDon = txtMaHD.getText();
        loadHoaDonChiTiet(maHoaDon);
        capNhatTienThua();
    }//GEN-LAST:event_jButton4ActionPerformed
    private void xoaTatCaSanPhamKhoiHoaDon() {
        int rowCount = tbl_hoadonchitiet.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tất cả sản phẩm?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        try (Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false);
            String sqlCapNhat = "UPDATE San_Pham_Chi_Tiet "
                    + "SET SoLuong = SoLuong + ( "
                    + "    SELECT COALESCE(SUM(SoLuong), 0) FROM Hoa_Don_Chi_Tiet "
                    + "    WHERE Hoa_Don_Chi_Tiet.maSanPhamChiTiet = San_Pham_Chi_Tiet.maSanPhamChiTiet "
                    + ")";
            try (PreparedStatement psCapNhat = conn.prepareStatement(sqlCapNhat)) {
                psCapNhat.executeUpdate();
            }
            String sqlXoaTatCa = "DELETE FROM Hoa_Don_Chi_Tiet";
            try (PreparedStatement psXoaTatCa = conn.prepareStatement(sqlXoaTatCa)) {
                psXoaTatCa.executeUpdate();
            }
            conn.commit();
            JOptionPane.showMessageDialog(this, "Đã xóa tất cả sản phẩm và cập nhật lại số lượng!");
            getAllSanPham(repo.getAll());
            String maHoaDon = txtMaHD.getText();
            loadHoaDonChiTiet(maHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa tất cả sản phẩm!");
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        xoaTatCaSanPhamKhoiHoaDon();
        capNhatTienThua();
        txtTienKH.setText("0");
        txtTienThua.setText("0");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        String maHoaDon = txtMaHD.getText().trim();
        if (maHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần hủy!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn hủy hóa đơn " + maHoaDon + " không?",
                "Xác nhận hủy hóa đơn",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Đã hủy thao tác hủy hóa đơn.");
            return;
        }
        
        String selectChiTietHD = "SELECT MaSanPhamChiTiet, SoLuong FROM Hoa_Don_Chi_Tiet WHERE MaHoaDon = ?";
        String updateSanPhamSQL = "UPDATE San_Pham_Chi_Tiet SET SoLuong = SoLuong + ? WHERE MaSanPhamChiTiet = ?";
        String updateTrangThaiHoaDon = "UPDATE Hoa_Don SET TrangThai = 3 WHERE MaHoaDon = ?";
        
        try (Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmtSelect = conn.prepareStatement(selectChiTietHD)) {
                pstmtSelect.setString(1, maHoaDon);
                ResultSet rs = pstmtSelect.executeQuery();
                
                ArrayList<String> maSanPhamList = new ArrayList<>();
                ArrayList<Integer> soLuongList = new ArrayList<>();
                while (rs.next()) {
                    maSanPhamList.add(rs.getString("MaSanPhamChiTiet"));
                    soLuongList.add(rs.getInt("SoLuong"));
                }
                
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSanPhamSQL)) {
                    for (int i = 0; i < maSanPhamList.size(); i++) {
                        pstmtUpdate.setInt(1, soLuongList.get(i));
                        pstmtUpdate.setString(2, maSanPhamList.get(i));
                        pstmtUpdate.executeUpdate();
                    }
                }
                
                try (PreparedStatement pstmtUpdateTrangThai = conn.prepareStatement(updateTrangThaiHoaDon)) {
                    pstmtUpdateTrangThai.setString(1, maHoaDon);
                    pstmtUpdateTrangThai.executeUpdate();
                }
                
                conn.commit();
                JOptionPane.showMessageDialog(null, "Hủy hóa đơn thành công!");
                getAllHoaDon(repo.getAllHoaDon());
                getAllSanPham(repo.getAll());
                loadHoaDonChiTiet(maHoaDon);
//                txtGiamGia.setText("0");
                txtMaHD.setText("Vui lòng tạo !");
                txtThanhTien.setText("0");
                txtTongtien.setText("0");
                loadHoaDonChiTiet("Vui lòng tạo !");
                txtTienThua.setText("0");
                txtTienKH.setText("0");
                cbxKH.setSelectedIndex(-1);
                txtSDT.setText("");
                
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi hủy hóa đơn!");
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtTimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimkiemKeyReleased
        // TODO add your handling code here:
        timKiemSanPham();
    }//GEN-LAST:event_txtTimkiemKeyReleased

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        // TODO add your handling code here:
        timKiemSanPham();

    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void tbl_sanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMouseClicked
        int selectedRow = tbl_sanPham.getSelectedRow();
        if (selectedRow != -1) {
            String maHoaDon = txtMaHD.getText();
            if (maHoaDon.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước!");
                return;
            }
            String maSanPham = tbl_sanPham.getValueAt(selectedRow, 1).toString();
            String tenSanPham = tbl_sanPham.getValueAt(selectedRow, 2).toString();
            String donGiaStr = tbl_sanPham.getValueAt(selectedRow, 3).toString();
            donGiaStr = donGiaStr.replace("₫", "").trim(); // Xóa ký hiệu tiền Việt
            donGiaStr = donGiaStr.replace("$", "").trim(); // Xóa ký hiệu tiền USD
            donGiaStr = donGiaStr.replaceAll("[^\\d.]", ""); // Chỉ giữ số và dấu chấm // Chỉ giữ số và dấu chấm
            String soLuonggg = tbl_sanPham.getValueAt(selectedRow, 4).toString();
            double donGia;
            try {
                donGia = Double.parseDouble(donGiaStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lỗi chuyển đổi giá tiền! Vui lòng kiểm tra lại.");
                e.printStackTrace();
                return;
            }
            int soLuong;
            int soLuongKho = Integer.parseInt(soLuonggg);
            while (true) {
                String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng: ");
                if (soLuongStr == null) {
                    return;
                }
                try {
                    soLuong = Integer.parseInt(soLuongStr);
                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                    } else if (soLuong > soLuongKho) {
                        JOptionPane.showMessageDialog(this, "Số lượng không được lớn hơn số lượng trong kho!");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ!");
                }
            }
            int response = JOptionPane.showConfirmDialog(this, "Thêm sản phẩm vào hóa đơn thành công!\nBạn có muốn tiếp tục không?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                double thanhTien = soLuong * donGia;
                DefaultTableModel model = (DefaultTableModel) tbl_hoadonchitiet.getModel();
                model.addRow(new Object[]{maSanPham, tenSanPham, soLuong, formatter.format(donGia), formatter.format(thanhTien)});
                themVaoDatabase(maHoaDon, maSanPham, soLuong, donGia);
                loadHoaDonChiTiet(maHoaDon);
                capNhatTienThua();
            } else {
                JOptionPane.showMessageDialog(this, "Quá trình thêm sản phẩm đã dừng.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước!");
        }
    }//GEN-LAST:event_tbl_sanPhamMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        UpdateSanPhamKhoiHoaDon();
        String maHoaDon = txtMaHD.getText();
        loadHoaDonChiTiet(maHoaDon);
        capNhatTienThua();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void cbxKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxKHActionPerformed
        // TODO add your handling code here:
        cbxKH.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String tenKhachHang = cbxKH.getSelectedItem().toString();
                    if (!tenKhachHang.isEmpty()) {
                        String sdt = getSoDienThoaiByTenKhachHang(tenKhachHang);
                        txtSDT.setText(sdt);
                    } else {
                        txtSDT.setText("");
                    }
                }
            }
        });
    }//GEN-LAST:event_cbxKHActionPerformed
    private void UpdateSanPhamKhoiHoaDon() {
        int selectedRow = tbl_hoadonchitiet.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để cập nhật!");
            return;
        }
        String maSanPhamChiTiet = tbl_hoadonchitiet.getValueAt(selectedRow, 1).toString();
        String soLuong = tbl_hoadonchitiet.getValueAt(selectedRow, 3).toString();
        int soLuongCu = Integer.parseInt(soLuong);
        String input = JOptionPane.showInputDialog(this, "Nhập số lượng mới:", soLuongCu);
        if (input == null || input.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
            return;
        }
        int soLuongMoi;
        try {
            soLuongMoi = Integer.parseInt(input);
            if (soLuongMoi <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một số hợp lệ!");
            return;
        }
        int chenhLech = soLuongMoi - soLuongCu;
        try (Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false);
            String sqlCheckKho = "SELECT SoLuong FROM San_Pham_Chi_Tiet WHERE MaSanPhamChiTiet = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheckKho)) {
                psCheck.setString(1, maSanPhamChiTiet);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    int soLuongTonKho = rs.getInt("SoLuong");
                    if (chenhLech > 0 && chenhLech > soLuongTonKho) {
                        JOptionPane.showMessageDialog(this, "Không đủ hàng trong kho để cập nhật!");
                        return;
                    }
                }
            }
            String sqlUpdateHoaDon = "UPDATE Hoa_Don_Chi_Tiet SET SoLuong = ? WHERE MaSanPhamChiTiet = ?";
            try (PreparedStatement psHoaDon = conn.prepareStatement(sqlUpdateHoaDon)) {
                psHoaDon.setInt(1, soLuongMoi);
                psHoaDon.setString(2, maSanPhamChiTiet);
                psHoaDon.executeUpdate();
            }
            String sqlUpdateKho = "UPDATE San_Pham_Chi_Tiet SET SoLuong = SoLuong - ? WHERE MaSanPhamChiTiet = ?";
            try (PreparedStatement psKho = conn.prepareStatement(sqlUpdateKho)) {
                psKho.setInt(1, chenhLech);
                psKho.setString(2, maSanPhamChiTiet);
                psKho.executeUpdate();
            }
            conn.commit();
            tbl_hoadonchitiet.setValueAt(soLuongMoi, selectedRow, 3);
            JOptionPane.showMessageDialog(this, "Cập nhật số lượng sản phẩm thành công!");
            getAllSanPham(repo.getAll());
            capNhatTienThua();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }
    
    private void themVaoDatabase(String maHoaDon, String maSanPham, int soLuong, double donGia) {
        maHoaDon = txtMaHD.getText();
        String checkSQL = "SELECT SoLuong, DonGia FROM Hoa_Don_Chi_Tiet WHERE MaHoaDon = ? AND MaSanPhamChiTiet = ?";
        String updateHoaDonSQL = "UPDATE Hoa_Don_Chi_Tiet SET SoLuong = SoLuong + ?, DonGia = DonGia + ? WHERE MaHoaDon = ? AND MaSanPhamChiTiet = ?";
        String insertSQL = "INSERT INTO Hoa_Don_Chi_Tiet (MaHoaDon, MaSanPhamChiTiet, SoLuong, DonGia, TrangThai) VALUES (?, ?, ?, ?, 1)";
        String updateSanPhamSQL = "UPDATE San_Pham_Chi_Tiet SET SoLuong = SoLuong - ? WHERE MaSanPhamChiTiet = ? AND SoLuong >= ?";
        try (Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmtCheck = conn.prepareStatement(checkSQL); PreparedStatement pstmtUpdateHoaDon = conn.prepareStatement(updateHoaDonSQL); PreparedStatement pstmtInsert = conn.prepareStatement(insertSQL); PreparedStatement pstmtUpdateSanPham = conn.prepareStatement(updateSanPhamSQL)) {
                pstmtCheck.setString(1, maHoaDon);
                pstmtCheck.setString(2, maSanPham);
                ResultSet rs = pstmtCheck.executeQuery();
                boolean sanPhamTonTai = false;
                int soLuongHienTai = 0;
                double donGiaHienTai = 0;
                if (rs.next()) {
                    sanPhamTonTai = true;
                    soLuongHienTai = rs.getInt("SoLuong");
                    donGiaHienTai = rs.getDouble("DonGia");
                }
                if (sanPhamTonTai) {
                    pstmtUpdateHoaDon.setInt(1, soLuong);
                    pstmtUpdateHoaDon.setDouble(2, donGia);
                    pstmtUpdateHoaDon.setString(3, maHoaDon);
                    pstmtUpdateHoaDon.setString(4, maSanPham);
                    pstmtUpdateHoaDon.executeUpdate();
                } else {
                    pstmtInsert.setString(1, maHoaDon);
                    pstmtInsert.setString(2, maSanPham);
                    pstmtInsert.setInt(3, soLuong);
                    pstmtInsert.setDouble(4, donGia);
                    pstmtInsert.executeUpdate();
                }
                pstmtUpdateSanPham.setInt(1, soLuong);
                pstmtUpdateSanPham.setString(2, maSanPham);
                pstmtUpdateSanPham.setInt(3, soLuong);
                int rowsAffected = pstmtUpdateSanPham.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    System.out.println("Không đủ số lượng tồn kho để trừ.");
                    return;
                }
                conn.commit();
                System.out.println("Thêm sản phẩm và cập nhật tồn kho thành công.");
                loadHoaDonChiTiet(maHoaDon);
                getAllSanPham(repo.getAll());
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void getAllSanPham(ArrayList<SanPhamChiTiet> ls) {
        md = (DefaultTableModel) tbl_sanPham.getModel();
        md.setRowCount(0);
        for (SanPhamChiTiet l : ls) {
            md.addRow(new Object[]{
                l.getId(), l.getMaSanPham(), l.getTenSanPham(), formatter.format(l.getGiaBan()), l.getSoLuong(), l.getMauSac(), l.getKichCo(), l.getChatLieu(), l.getThuongHieu(), l.getXuatXu(), l.getTrangThai() != 1 ? "Đang Còn Hàng" : "Hết Hàng"
            });
        }
    }
    
    public void timKiemSanPham() {
        String thuongHieu = (String) cbbTrangThai.getSelectedItem();
        String tuKhoa = txtTimkiem.getText().trim();
        ArrayList<Model.SanPhamChiTiet> danhSachSP = repo.timKiemSanPhamm(tuKhoa, thuongHieu);
        getAllSanPham(danhSachSP);
    }
    
    public void ThuongHieu() {
        cbbTrangThai.removeAllItems();
        cbbTrangThai.addItem(" ");
        ArrayList<ThuongHieu> list = th.getAll();
        for (ThuongHieu thuongHieu : list) {
            cbbTrangThai.addItem(thuongHieu.getTenThuongHieu());
        }
    }
    
    private void capNhatTable(ArrayList<Model.SanPham> danhSachSP) {
        DefaultTableModel model = (DefaultTableModel) tbl_sanPham.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        for (Model.SanPham sp : danhSachSP) {
            model.addRow(new Object[]{
                sp.getMaSanPham(),
                sp.getTenSanPham(),
                formatter.format(sp.getDonGia()),
                sp.getSoLuong(),
                sp.getTrangThai(),
                sp.getTenThuongHieu(),
                sp.getTenChatLieu(),
                sp.getTenMauSac(),
                sp.getTenKichThuoc(),
                sp.getTenXuatXu()
            });
        }
    }
    
    public void capNhatTienThua() {
        String inputAmountStr = txtTienKH.getText().trim();
        
        if (!inputAmountStr.isEmpty()) {
            try {
                double inputAmount = Double.parseDouble(inputAmountStr.replace(",", ""));
                double moneyLeft = inputAmount - tongTienSauGiam;
                if (moneyLeft < 0) {
                    txtTienThua.setText("-" + formatter.format(Math.abs(moneyLeft)));
                } else {
                    txtTienThua.setText(formatter.format(moneyLeft));
                }
            } catch (NumberFormatException ex) {
                txtTienThua.setText("Nhập số hợp lệ.");
            }
        } else {
            txtTienThua.setText("0");
        }
    }
    
    private void loadHoaDonChiTiet(String maHoaDon) {
        md = (DefaultTableModel) tbl_hoadonchitiet.getModel();
        md.setRowCount(0);
        double tongTien = 0;
        double giamGia = 0;
        int index = 1;
        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "SELECT San_Pham_Chi_Tiet.MaSanPhamChiTiet, tenSanPham, Hoa_Don_Chi_Tiet.soLuong, Hoa_Don_Chi_Tiet.donGia, "
                + "Hoa_Don_Chi_Tiet.soLuong * Hoa_Don_Chi_Tiet.donGia AS thanhTien "
                + "FROM Hoa_don_chi_tiet "
                + "JOIN San_Pham_Chi_Tiet ON Hoa_Don_Chi_Tiet.MaSanPhamChiTiet = San_Pham_Chi_Tiet.MaSanPhamChiTiet "
                + "JOIN San_Pham ON San_Pham_Chi_Tiet.MaSanPham = San_Pham.MaSanPham "
                + "WHERE maHoaDon = ?")) {
            ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("MaSanPhamChiTiet");
                String tenSanPham = rs.getString("tenSanPham");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                double thanhTien = rs.getDouble("thanhTien");
                tongTien += thanhTien;
                md.addRow(new Object[]{index++, maSanPham, tenSanPham, soLuong, formatter.format(donGia), formatter.format(thanhTien)});
            }
            txtTongtien.setText(formatter.format(tongTien));
            if (tongTien > 10000000) {
                giamGia = tongTien * 0.05;
            }
            tongTienSauGiam = tongTien - giamGia;
            txtThanhTien.setText(formatter.format(giamGia));
            txtThanhTien.setText(formatter.format(tongTienSauGiam));
            txtTienKH.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    capNhatTienThua();
                }
                
                @Override
                public void removeUpdate(DocumentEvent e) {
                    capNhatTienThua();
                }
                
                @Override
                public void changedUpdate(DocumentEvent e) {
                    capNhatTienThua();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getAllHoaDon(ArrayList<HoaDonBanHang> ls) {
        md = (DefaultTableModel) tblhoadon.getModel();
        md.setRowCount(0);
        int index = 1;
        for (HoaDonBanHang l : ls) {
            md.addRow(new Object[]{
                index++,
                l.getMaHoaDon(), l.getMaNhanVien(), l.getMaKhachHang(), l.getNgayTao(), l.mapTrangThai(l.getTrangThai())
            });
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddKH;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JComboBox<String> cbxKH;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tbl_hoadonchitiet;
    private javax.swing.JTable tbl_sanPham;
    private javax.swing.JTable tblhoadon;
    private javax.swing.JLabel txtMaHD;
    private javax.swing.JLabel txtNV;
    private javax.swing.JLabel txtSDT;
    private javax.swing.JLabel txtThanhTien;
    private javax.swing.JTextField txtTienKH;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTimkiem;
    private javax.swing.JLabel txtTongtien;
    // End of variables declaration//GEN-END:variables
}
