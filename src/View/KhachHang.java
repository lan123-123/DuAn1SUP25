/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Model.Model_Khachhang;
import Model.Repository_KhachHang;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TONG THI NHUNG
 */
public class KhachHang extends javax.swing.JPanel {
private Repository_KhachHang repo = new Repository_KhachHang();
private DefaultTableModel mol = new DefaultTableModel();
private int index = -1;
private int maKHDem = 0;
    /**
     * Creates new form KhachHang
     */
    public KhachHang() {
        initComponents();
        this.fillTable(repo.getAll());
        txt_timKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleTextChange();
            }

            private void handleTextChange() {
                String timKiem = txt_timKiem.getText().trim();
                if (timKiem.isEmpty()) {
                    // Nếu ô tìm kiếm trống, hiển thị toàn bộ dữ liệu
                    fillTable(repo.getAll());
                }
                // Không tìm kiếm tự động khi nhập, chờ người dùng nhấn nút "Tìm kiếm"
            }
        });
        
        rdo_nam.setSelected(true);
        rdo_hd.setSelected(true);
        maKHDem = repo.getMaxMaKhachHang();
        updateMaKhachHang();
        //txt_maKH.setEnabled(false);
        
    }

    void fillTable(ArrayList<Model_Khachhang> List){
      mol =(DefaultTableModel)tbl_KH.getModel();
      mol.setRowCount(0);
      int index =1;
        for (Model_Khachhang u : List) {
            Object [] row={
                index++,
                u.getMaKH(),
                u.getTenKH(),
                u.getEmailKH(),
                u.getDiaChiKH(),
                u.getSdtKH(),
                u.getGioiTinhKH(),
                u.isTrangThai()?"Hoạt động":"Không hoạt động"
            };
            mol.addRow(row);
        }
    }
    
    void showDaTa(int index){
         
    txt_maKH.setText(tbl_KH.getValueAt(index, 1).toString());
    txt_tenKH.setText(tbl_KH.getValueAt(index, 2).toString());
    txt_emailKH.setText(tbl_KH.getValueAt(index, 3).toString());
    txt_diaChiKH.setText(tbl_KH.getValueAt(index, 4).toString());
    txt_sdtKH.setText(tbl_KH.getValueAt(index, 5).toString());
    String re2 =tbl_KH.getValueAt(index, 6).toString(); 
    if(re2.equalsIgnoreCase("Hoạt động")) rdo_hd.setSelected(true);
    else if(re2.equalsIgnoreCase("Không hoạt động")) rdo_khd.setSelected(true);
    String re = tbl_KH.getValueAt(index, 5).toString();
    if(re.equalsIgnoreCase("nam")) rdo_nam.setSelected(true);
    else rdo_nu.setSelected(true);
    }
    
    Model_Khachhang readform (){
        
    String ma = txt_maKH.getText();
     String ten = txt_tenKH.getText();
     String email = txt_emailKH.getText();
     String diaChi = txt_diaChiKH.getText();
      String sdt = txt_sdtKH.getText();
      String gioiTính1;
      if(rdo_nam.isSelected()) gioiTính1 = "nam";
      else gioiTính1 ="nữ";
      boolean trangThai1;
      if(rdo_hd.isSelected())  trangThai1 = true;
      else trangThai1 = false;
      return new Model_Khachhang(ma, ten, email, diaChi, sdt, gioiTính1, trangThai1);
    }
    
    
    
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Loại bỏ ký tự không phải số
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        // Kiểm tra độ dài (10 số)
        if (phoneNumber.length() != 10) {
            return false;
        }

        // Lấy 3 chữ số đầu
        String prefix = phoneNumber.substring(0, 3);

        // Kiểm tra đầu số
        for (String validPrefix : VALID_PREFIXES) {
            if (prefix.equals(validPrefix)) {
                return true;
            }
        }
        return false;
    }

    
    private static final String[] VALID_PREFIXES = {
        "032", "033", "034", "035", "036", "037", "038", "039", "096", "097", "098", "086", // Viettel
        "070", "076", "077", "078", "079", "089", "090", "093", // Mobifone
        "081", "082", "083", "084", "085", "088", "091", "094", // Vinaphone
        "056", "057", "058", "059", "092", // Vietnamobile
        "099" // Gmobile
    };
    
    
    private void updateMaKhachHang() {
        maKHDem++; // Tăng số thứ tự
        txt_maKH.setText("KH" + String.format("%04d", maKHDem)); // Định dạng mã: KH001, KH002, ...
    }
  
   
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rdo_nam = new javax.swing.JRadioButton();
        rdo_nu = new javax.swing.JRadioButton();
        btn_them = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        txt_tenKH = new javax.swing.JTextField();
        txt_maKH = new javax.swing.JTextField();
        txt_emailKH = new javax.swing.JTextField();
        txt_diaChiKH = new javax.swing.JTextField();
        txt_sdtKH = new javax.swing.JTextField();
        btn_timKiem = new javax.swing.JButton();
        txt_timKiem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rdo_hd = new javax.swing.JRadioButton();
        rdo_khd = new javax.swing.JRadioButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_KH = new javax.swing.JTable();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenuItem1.setText("jMenuItem1");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã Khách Hàng:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tên Khách Hàng:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Email:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Địa chỉ:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("SĐT:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Giới Tính");

        buttonGroup1.add(rdo_nam);
        rdo_nam.setText("Nam");

        buttonGroup1.add(rdo_nu);
        rdo_nu.setText("Nữ");

        btn_them.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_sua.setText("Sửa");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_xoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xoa.setText("Xóa");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        txt_maKH.setEditable(false);
        txt_maKH.setEnabled(false);
        txt_maKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maKHActionPerformed(evt);
            }
        });

        btn_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_timKiem.setText("Tìm kiếm");
        btn_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timKiemActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Trạng Thái");

        buttonGroup2.add(rdo_hd);
        rdo_hd.setText("Hoạt động");

        buttonGroup2.add(rdo_khd);
        rdo_khd.setText("Không hoạt động");

        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(21, 21, 21)
                                .addComponent(txt_maKH))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_emailKH, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btn_timKiem)
                        .addGap(18, 18, 18)
                        .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(150, 150, 150)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_hd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_khd))
                    .addComponent(btn_them)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_sua)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdo_nam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdo_nu)))
                                .addGap(18, 18, 18)
                                .addComponent(btn_xoa)
                                .addGap(18, 18, 18)
                                .addComponent(btnLamMoi))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_sdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_diaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(txt_diaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_maKH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txt_sdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(txt_tenKH)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(rdo_nam)
                            .addComponent(rdo_nu)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txt_emailKH, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rdo_hd)
                    .addComponent(rdo_khd))
                .addGap(18, 20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_them)
                            .addComponent(btn_sua)
                            .addComponent(btn_xoa)
                            .addComponent(btnLamMoi))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_timKiem)
                            .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Danh sách khách hàng");

        tbl_KH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Tên KH", "Email", "Địa chỉ", "SĐT", "Giới tính", "Trạng thái"
            }
        ));
        tbl_KH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_KH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 943, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_KHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KHMouseClicked
        index = tbl_KH.getSelectedRow();
        this.showDaTa(index);
    }//GEN-LAST:event_tbl_KHMouseClicked

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        String checkemail = txt_emailKH.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String phoneNumber = txt_sdtKH.getText().trim();
        String checkSo = txt_sdtKH.getText().trim();
        String checkMa = txt_maKH.getText().trim();
       
        
        try {
            if(txt_diaChiKH.getText().trim().isEmpty() || txt_emailKH.getText().trim().isEmpty() || phoneNumber.isEmpty() || txt_tenKH.getText().trim().isEmpty()){
             JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
            }
            if(!Pattern.matches(emailRegex, checkemail)){
            JOptionPane.showMessageDialog(this, "Email hông hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
            }
            if(repo.checkEmail(checkemail, this)){
              return;
            }
            if(repo.checkSDT(checkSo, this)){
              return;
            }
            if(!isValidPhoneNumber(phoneNumber)){
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            else{
             repo.addKH(this.readform());
             this.updateMaKhachHang();
             this.fillTable(repo.getAll());
             txt_diaChiKH.setText("");
            txt_tenKH.setText("");
            txt_emailKH.setText("");
            txt_sdtKH.setText("");
            rdo_nam.setSelected(true);
            rdo_hd.setSelected(true);
             JOptionPane.showMessageDialog(this, "Thành công!", "Thông báo", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
        }
       
        
        
        
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        try {
            if(txt_diaChiKH.getText().trim().isEmpty() || txt_emailKH.getText().trim().isEmpty() || txt_maKH.getText().trim().isEmpty() ||txt_sdtKH.getText().trim().isEmpty() || txt_tenKH.getText().trim().isEmpty()){
             JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }else{
            repo.remove(this.readform());
            this.fillTable(repo.getAll());
            txt_diaChiKH.setText("");
            txt_tenKH.setText("");
            txt_emailKH.setText("");
            txt_sdtKH.setText("");
            rdo_nam.setSelected(true);
            rdo_hd.setSelected(true);
             txt_maKH.setText("KH" + String.format("%04d", maKHDem));
             JOptionPane.showMessageDialog(this, "Thành công!", "Thông báo", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        String checkemail = txt_emailKH.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String phoneNumber = txt_sdtKH.getText().trim();
        String checkTen = txt_tenKH.getText().trim();
        try {
            if(txt_diaChiKH.getText().trim().isEmpty() || txt_emailKH.getText().trim().isEmpty() || txt_maKH.getText().trim().isEmpty() || txt_sdtKH.getText().trim().isEmpty() ||txt_tenKH.getText().trim().isEmpty()){
             JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
            }
             if(!Pattern.matches(emailRegex, checkemail)){
            JOptionPane.showMessageDialog(this, "Email hông hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
            }
            if(!isValidPhoneNumber(phoneNumber)){
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            else{
             repo.upDaTe(this.readform());
         this.fillTable(repo.getAll());
         txt_diaChiKH.setText("");
            txt_tenKH.setText("");
            txt_emailKH.setText("");
            txt_sdtKH.setText("");
            rdo_nam.setSelected(true);
            rdo_hd.setSelected(true);
             txt_maKH.setText("KH" + String.format("%04d", maKHDem));
          JOptionPane.showMessageDialog(this, "Thành công!", "Thông báo", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timKiemActionPerformed
         String timKiem = txt_timKiem.getText().trim(); // .trim() để loại bỏ khoảng trắng thừa

    // Kiểm tra giá trị nhập
    if (timKiem.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng để tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Gọi phương thức tìm kiếm
    Model_Khachhang khachHang = repo.getByMaKhachHang(timKiem);

    // Kiểm tra kết quả
    if (khachHang != null) {
        // Tạo danh sách để hiển thị trên JTable (vì JTable thường hiển thị danh sách)
        ArrayList<Model_Khachhang> list = new ArrayList<>();
        list.add(khachHang);

        
        fillTable(list);
    } else {
        // Hiển thị thông báo nếu không tìm thấy
        JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với mã: " + timKiem, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        
        // Xóa dữ liệu trong JTable (nếu cần)
        DefaultTableModel model = (DefaultTableModel) tbl_KH.getModel();
        model.setRowCount(0);
    }
        
        
    }//GEN-LAST:event_btn_timKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txt_diaChiKH.setText("");
        txt_emailKH.setText("");
       txt_maKH.setText("KH" + String.format("%04d", maKHDem));
        txt_sdtKH.setText("");
        txt_tenKH.setText("");
       buttonGroup1.clearSelection();
       buttonGroup2.clearSelection();
       
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txt_maKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maKHActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_txt_maKHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_timKiem;
    private javax.swing.JButton btn_xoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_hd;
    private javax.swing.JRadioButton rdo_khd;
    private javax.swing.JRadioButton rdo_nam;
    private javax.swing.JRadioButton rdo_nu;
    private javax.swing.JTable tbl_KH;
    private javax.swing.JTextField txt_diaChiKH;
    private javax.swing.JTextField txt_emailKH;
    private javax.swing.JTextField txt_maKH;
    private javax.swing.JTextField txt_sdtKH;
    private javax.swing.JTextField txt_tenKH;
    private javax.swing.JTextField txt_timKiem;
    // End of variables declaration//GEN-END:variables
}
