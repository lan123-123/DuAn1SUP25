    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Model.NhanVienModel;
import Service.NhanVienService;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TONG THI NHUNG
 */
public class NhanVien extends javax.swing.JPanel {

    NhanVienService service = new NhanVienService();
    DefaultTableModel dtm = new DefaultTableModel();
    List<NhanVienModel> list = new ArrayList<>();
    private List<NhanVienModel> listHienTai = new ArrayList<>();

    int index = 0;

    /**
     * Creates new form NhanVien
     */
    public NhanVien() {
        initComponents();
        setOpaque(false);

        cboTrangThai.setSelectedItem("Tất cả");
        fillTable(service.getAll());

        ButtonGroup groupGioiTinh = new ButtonGroup();
        groupGioiTinh.add(rdoNam);
        groupGioiTinh.add(rdoNu);

        ButtonGroup groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rdoDL);
        groupTrangThai.add(rdoNV);

    }

    private void fillTable(List<NhanVienModel> list) {
        try {
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);

            String locTrangThai = cboTrangThai.getSelectedItem().toString();

            int stt = 1;
            for (NhanVienModel nv : list) {
                String trangThaiStr = nv.getTrangThai() ? "Đang làm" : "Nghỉ việc";

                boolean show = locTrangThai.equals("Tất cả")
                        || (locTrangThai.equals("Đang làm") && nv.getTrangThai())
                        || (locTrangThai.equals("Nghỉ việc") && !nv.getTrangThai());

                if (show) { // <- Chỉ hiển thị nếu phù hợp với trạng thái đã lọc
                    Object[] row = {
                        stt++,
                        nv.getMaNhanVien(),
                        nv.getTenNhanVien(),
                        nv.getSoDienThoai(),
                        nv.getGioiTinh(),
                        nv.getDiaChi(),
                        nv.getEmail(),
                        trangThaiStr,};
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi fill table: " + e.getMessage());
        }
    }

//    private void showData(int index) {
//        NhanVienModel nv = service.getAll().get(index);
//        txtMaNV.setText(nv.getMaNhanVien());
//        txtTenNV.setText(nv.getTenNhanVien());
//        txtSDT.setText(nv.getSoDienThoai());
//        // Chọn radio button theo giới tính
//        if ("Nam".equalsIgnoreCase(nv.getGioiTinh())) {
//            rdoNam.setSelected(true);
//        } else {
//            rdoNu.setSelected(true);
//        }
//        txtDiaChi.setText(nv.getDiaChi());
//        txtEmail.setText(nv.getEmail());
//        if (nv.getTrangThai()) {
//            rdoDL.setSelected(true);
//        } else {
//            rdoNV.setSelected(true);
//        }
//
//    }
    private void showData(int index) {
        NhanVienModel nv = listHienTai.get(index); // <-- Sửa tại đây

        txtMaNV.setText(nv.getMaNhanVien());
        txtTenNV.setText(nv.getTenNhanVien());
        txtSDT.setText(nv.getSoDienThoai());

        if ("Nam".equalsIgnoreCase(nv.getGioiTinh())) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        txtDiaChi.setText(nv.getDiaChi());
        txtEmail.setText(nv.getEmail());

        if (nv.getTrangThai()) {
            rdoDL.setSelected(true);
        } else {
            rdoNV.setSelected(true);
        }
    }

    boolean checkrong() {

        if (txtTenNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            txtTenNV.requestFocus();
            return false;
        }

// Regex họ tên: Viết hoa chữ cái đầu, hỗ trợ tiếng Việt, không chứa số/ký tự đặc biệt
        String ten = txtTenNV.getText().trim();
        String hoTenRegex = "^[A-ZÀÁẠÃẢÂẦẤẬẪẨĂẰẮẶẴẲĐÈÉẸẼẺÊỀẾỆỄỂÌÍỊĨỈ"
                + "ÒÓỌÕỎÔỒỐỘỖỔƠỜỚỢỠỞÙÚỤŨỦƯỪỨỰỮỬỲÝỴỸỶ]"
                + "[a-zàáạãảâầấậẫẩăằắặẵẳđèéẹẽẻêềếệễểìíịĩỉ"
                + "òóọõỏôồốộỗổơờớợỡởùúụũủưừứựữửỳýỵỹỷ]+"
                + "(\\s[A-ZÀÁẠÃẢÂẦẤẬẪẨĂẰẮẶẴẲĐÈÉẸẼẺÊỀẾỆỄỂÌÍỊĨỈ"
                + "ÒÓỌÕỎÔỒỐỘỖỔƠỜỚỢỠỞÙÚỤŨỦƯỪỨỰỮỬỲÝỴỸỶ]"
                + "[a-zàáạãảâầấậẫẩăằắặẵẳđèéẹẽẻêềếệễểìíịĩỉ"
                + "òóọõỏôồốộỗổơờớợỡởùúụũủưừứựữửỳýỵỹỷ]+)*$";

        if (!ten.matches(hoTenRegex)) {
            JOptionPane.showMessageDialog(this,
                    "Họ tên không hợp lệ!\n"
                    + "- Mỗi từ phải viết hoa chữ cái đầu\n"
                    + "- Không chứa số hoặc ký tự đặc biệt",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtTenNV.requestFocus();
            return false;
        }

        if (service.isMaNVExists(txtMaNV.getText())) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Trùng mã", JOptionPane.ERROR_MESSAGE);
            txtMaNV.requestFocus();
            return false;
        }

        if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SDT Không được để trống");
            txtSDT.requestFocus();
            return false;
        }
        String sdt = txtSDT.getText();
// Regex: bắt đầu bằng 03, 05, 07, 08 hoặc 09, theo sau là 8 số
        String checkSDT = "^(03|05|07|08|09)\\d{8}$";

        if (!sdt.matches(checkSDT)) {
            JOptionPane.showMessageDialog(this,
                    "Số điện thoại không hợp lệ!\n"
                    + "- Phải bắt đầu bằng: 03, 05, 07, 08 hoặc 09\n"
                    + "- Gồm 10 chữ số\n"
                    + "- Không chứa ký tự đặc biệt hoặc khoảng trắng",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }
        if (service.isPhoneExists(txtSDT.getText())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong hệ thống!");
            txtSDT.requestFocus();
            return false;
        }

        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            txtEmail.requestFocus();
            return false;
        }
        // Thêm check định dạng email
        String email = txtEmail.getText();
        // Regex: kiểm tra email kết thúc bằng @gmail.com
        String pattern = "^[A-Za-z0-9+_.-]+@gmail\\.com$";

        if (!email.matches(pattern)) {
            JOptionPane.showMessageDialog(this,
                    "Email không hợp lệ!\n"
                    + "Email phải có định dạng: abc@gmail.com",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        if (service.isEmailExists(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Email đã tồn tại trong hệ thống!");
            txtEmail.requestFocus();
            return false;
        }
        if (txtDiaChi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Địa chỉ Không được để trống");
            txtDiaChi.requestFocus();
            return false;
        }
        return true;
    }

    public NhanVienModel readform() {
        NhanVienModel nv = new NhanVienModel();
        nv.setMaNhanVien(txtMaNV.getText());
        nv.setTenNhanVien(txtTenNV.getText());
        nv.setSoDienThoai(txtSDT.getText());

        if (rdoNam.isSelected()) {
            nv.setGioiTinh("Nam");
        } else {
            nv.setGioiTinh("Nữ");
        }
        nv.setDiaChi(txtDiaChi.getText());
        nv.setEmail(txtEmail.getText());

        if (rdoDL.isSelected()) {
            nv.setTrangThai(true);
        }
        if (rdoNV.isSelected()) {
            nv.setTrangThai(false);
        }

        return nv;
    }

    private void locTheoGioiTinh() {
        String gioiTinh = cboLocGioiTinh.getSelectedItem().toString(); // Lấy giá trị từ JComboBox
        NhanVienService nvService = new NhanVienService(); // Tạo đối tượng Service
        List<NhanVienModel> danhSach = nvService.locTheoGioiTinh(gioiTinh); // Gọi phương thức lọc

        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        int stt = 1;
        // Thêm dữ liệu mới vào bảng
        for (NhanVienModel nv : danhSach) {
            Object[] row = {
                stt++,
                nv.getMaNhanVien(),
                nv.getTenNhanVien(),
                nv.getGioiTinh(),
                nv.getSoDienThoai(),
                nv.getDiaChi(),
                nv.getEmail(),
                nv.isTrangThai() ? "Đang làm" : "Nghỉ việc"
            };
            model.addRow(row);
        }
    }

    private void locTheoTrangThai() {
        String trangThai = cboTrangThai.getSelectedItem().toString(); // Lấy giá trị từ JComboBox
        NhanVienService nvService = new NhanVienService(); // Tạo đối tượng Service
        List<NhanVienModel> danhSach = nvService.locTheoTrangThai(trangThai); // Gọi phương thức lọc

        listHienTai = danhSach; // <- Lưu danh sách hiện tại để sử dụng khi click bảng

        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        int stt = 1;
        // Thêm dữ liệu mới vào bảng
        for (NhanVienModel nv : danhSach) {
            Object[] row = {
                stt++,
                nv.getMaNhanVien(),
                nv.getTenNhanVien(),
                nv.getGioiTinh(),
                nv.getSoDienThoai(),
                nv.getDiaChi(),
                nv.getEmail(),
                nv.isTrangThai() ? "Đang làm" : "Nghỉ việc"
            };
            model.addRow(row);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtDiaChi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btn_Them = new javax.swing.JButton();
        btn_Sua = new javax.swing.JButton();
        btn_LamMoi = new javax.swing.JButton();
        btn_Xoa = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        rdoDL = new javax.swing.JRadioButton();
        rdoNV = new javax.swing.JRadioButton();
        txtMaNV = new javax.swing.JTextField();
        txtExcel = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboLocGioiTinh = new javax.swing.JComboBox<>();
        cboLocTrangThai = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        jLabel2.setText("Mã NV");

        jLabel3.setText("Tên NV");

        jLabel9.setText("Trạng thái");

        txtTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNVActionPerformed(evt);
            }
        });

        jLabel4.setText("Số điện thoại");

        jLabel5.setText("Giới Tính");

        jLabel10.setText("Email");

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        rdoNam.setText("Nam");

        rdoNu.setText("Nữ");

        jLabel6.setText("Địa chỉ");

        btn_Them.setText("Thêm");
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        btn_Sua.setText("Sửa");
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
            }
        });

        btn_LamMoi.setText("Làm mới");
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiActionPerformed(evt);
            }
        });

        btn_Xoa.setText("Xóa");
        btn_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaActionPerformed(evt);
            }
        });

        rdoDL.setText("Đang làm");

        rdoNV.setText("Nghỉ việc");

        txtMaNV.setBackground(new java.awt.Color(204, 204, 204));

        txtExcel.setText("Xuất file Excel");
        txtExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_Them)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rdoDL)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoNV))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(btn_Sua)
                                .addGap(45, 45, 45)
                                .addComponent(btn_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(btn_LamMoi)))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoNu)))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(221, 221, 221))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(326, 326, 326)
                .addComponent(txtExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoNu)
                        .addComponent(jLabel3)
                        .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(rdoDL)
                    .addComponent(rdoNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Them)
                    .addComponent(btn_Sua)
                    .addComponent(btn_Xoa)
                    .addComponent(btn_LamMoi))
                .addGap(18, 18, 18)
                .addComponent(txtExcel)
                .addGap(29, 29, 29))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách nhân viên"));

        jLabel8.setText("Tìm kiếm");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Lọc theo giới tính");

        cboLocGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả", "Nam", "Nữ" }));
        cboLocGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocGioiTinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLocGioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cboLocGioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
        );

        cboLocTrangThai.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc"));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Lọc trạng thái");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang làm", "Nghỉ việc" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cboLocTrangThaiLayout = new javax.swing.GroupLayout(cboLocTrangThai);
        cboLocTrangThai.setLayout(cboLocTrangThaiLayout);
        cboLocTrangThaiLayout.setHorizontalGroup(
            cboLocTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cboLocTrangThaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboTrangThai, 0, 154, Short.MAX_VALUE)
                .addContainerGap())
        );
        cboLocTrangThaiLayout.setVerticalGroup(
            cboLocTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cboLocTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cboTrangThai))
        );

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Tên NV", "Điện thoại", "Giới tính", "Địa chỉ", "Email", "Trạng thái"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cboLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 908, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(866, 866, 866))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1001, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 83, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 22, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1013, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        locTheoTrangThai();
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void cboLocGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocGioiTinhActionPerformed
        // TODO add your handling code here:
        locTheoGioiTinh();
    }//GEN-LAST:event_cboLocGioiTinhActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        int index = tblNhanVien.getSelectedRow();
        if (index >= 0 && index < listHienTai.size()) {
            this.index = index; // nếu bạn dùng biến index ở chỗ khác
            showData(index);    // nếu showData nhận chỉ số
        }

    }//GEN-LAST:event_tblNhanVienMouseClicked
    public void clearForm() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        // Xoá chọn radio button giới tính
        rdoNam.setSelected(false);
        rdoNu.setSelected(false);

        // Xoá chọn radio button trạng thái
        rdoDL.setSelected(false);
        rdoNV.setSelected(false);
    }

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemActionPerformed
        // TODO add your handling code here:
        if (checkrong()) {
            NhanVienModel nv = readform();
            if (service.insert(nv) != 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                fillTable(service.getAll());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm không thành công");
            }
        }
    }//GEN-LAST:event_btn_ThemActionPerformed

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn cập nhật thông tin nhân viên này?",
                "Xác nhận cập nhật",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            NhanVienModel nv = readform();

            if (service.update(nv) > 0) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật thông tin nhân viên thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                // Làm mới dữ liệu bảng
                fillTable(service.getAll());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật thông tin nhân viên thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaActionPerformed
//         TODO add your handling code here:
        try {
            // Kiểm tra xem có dòng nào được chọn không
            int selectedRow = tblNhanVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy thông tin được chọn
            String id = tblNhanVien.getValueAt(selectedRow, 1).toString();
            String tenNV = tblNhanVien.getValueAt(selectedRow, 2).toString();

            // Hiển thị dialog xác nhận
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa nhân viên:\n"
                    + "ID: " + id + "\n"
                    + "Tên: " + tenNV + "?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Nếu người dùng chọn Yes
            if (confirm == JOptionPane.YES_OPTION) {
                // Sửa từ remove thành delete
                boolean success = service.delete(id);

                if (success) {
                    JOptionPane.showMessageDialog(this,
                            "Xóa nhân viên thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Refresh lại bảng
                    List<NhanVienModel> list = service.getAll();
                    fillTable(list);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa nhân viên thất bại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btn_XoaActionPerformed
    }
    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btn_LamMoiActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNVActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
        String keyword = txtTimKiem.getText().trim();

        // Tạo đối tượng của Service
        NhanVienService nvService = new NhanVienService();

        // Gọi phương thức tìm kiếm trong Service
        List<NhanVienModel> danhSach = nvService.timKiem(keyword);

        // Lấy model của JTable và cập nhật dữ liệu
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        int stt = 1;
        for (NhanVienModel nv : danhSach) {
            Object[] row = {
                stt++,
                nv.getMaNhanVien(),
                nv.getTenNhanVien(),
                nv.getGioiTinh(),
                nv.getSoDienThoai(),
                nv.getDiaChi(),
                nv.getEmail(),
                nv.isTrangThai() ? "Đang làm" : "Nghỉ việc"
            };
            model.addRow(row);
        }
        txtTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            private void updateTable() {
                String keyword = txtTimKiem.getText().trim();

                // Tạo đối tượng của Service và gọi hàm tìm kiếm
                NhanVienService nvService = new NhanVienService();
                List<NhanVienModel> danhSach = nvService.timKiem(keyword);

                // Lấy model của JTable
                DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ
                int stt = 1;
                // Thêm các dòng mới vào bảng
                for (NhanVienModel nv : danhSach) {
                    Object[] row = {
                        stt++,
                        nv.getMaNhanVien(),
                        nv.getTenNhanVien(),
                        nv.getGioiTinh(),
                        nv.getSoDienThoai(),
                        nv.getDiaChi(),
                        nv.getEmail(),
                        nv.isTrangThai() ? "Đang làm" : "Nghỉ việc"
                    };
                    model.addRow(row);
                }
            }
        });
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExcelActionPerformed
        // TODO add your handling code here:
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn nơi lưu file Excel");
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            NhanVienService service = new NhanVienService();
            if (service.exportToExcel(listHienTai, filePath)) {
                
                JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Xuất Excel thất bại!");
            }
        }
    }//GEN-LAST:event_txtExcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_Sua;
    private javax.swing.JButton btn_Them;
    private javax.swing.JButton btn_Xoa;
    private javax.swing.JComboBox<String> cboLocGioiTinh;
    private javax.swing.JPanel cboLocTrangThai;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoDL;
    private javax.swing.JRadioButton rdoNV;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JButton txtExcel;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
