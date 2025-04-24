/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Jdbc.DbConnect;
import Model.AddSanPham;
import Model.SPCTModel;
import Model.SanPhamModel;
import Model.ThuocTinh.ChatLieu;
import Model.ThuocTinh.KichThuoc;
import Model.ThuocTinh.MauSac;
import Model.ThuocTinh.ThuongHieu;
import Model.ThuocTinh.XuatXu;
import Repository.SPCTReppo;
import Repository.SanPhamRepo;
import Repository.SanPhamRepository;
import Repository.ThuocTinh.ChatLieuRepository;
import Repository.ThuocTinh.KichThuocrepository;
import Repository.ThuocTinh.MauSacRepository;
import Repository.ThuocTinh.ThuongHieuRepository;
import Repository.ThuocTinh.XuatXuRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author TONG THI NHUNG
 */
public class SanPham extends javax.swing.JPanel {

    private MauSacRepository ms = new MauSacRepository();
    private ChatLieuRepository cl = new ChatLieuRepository();
    private XuatXuRepository xx = new XuatXuRepository();
    private KichThuocrepository kc = new KichThuocrepository();
    private ThuongHieuRepository th = new ThuongHieuRepository();
    private SanPhamRepository repo = new SanPhamRepository();
    private SanPhamRepo rp = new SanPhamRepo();
    private DefaultTableModel mol = new DefaultTableModel();
    private int i = -1;
    private SPCTReppo spct = new SPCTReppo();

    /**
     * Creates new form SanPham
     */
    public SanPham() {
        initComponents();
        showSanPhamTable();
        showSanPhamTableWithFullDetails();
        MauSac();
        KichThuoc();
        ChatLieu();
        XuatXu();
        ThuongHieu();
        hienThiTenSanPham();
        trangThai.removeAllItems();
        trangThai.addItem("Tất cả");
        trangThai.addItem("Đang bán");
        trangThai.addItem("Ngừng bán");
        trangThai.setSelectedIndex(0);
        addTrangThaiListener();

    }

    private void addTrangThaiListener() {
        trangThai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = trangThai.getSelectedItem(); // Lấy giá trị được chọn
                if (selectedItem == null) {
                    return; // Nếu không có giá trị nào được chọn, thoát khỏi sự kiện
                }

                String selectedStatus = selectedItem.toString().trim(); // Chuyển giá trị thành chuỗi
                DefaultTableModel model = (DefaultTableModel) tblSPCT.getModel();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                tblSPCT.setRowSorter(sorter);

                // Lọc theo trạng thái
                if (selectedStatus.equalsIgnoreCase("Tất cả")) {
                    sorter.setRowFilter(null); // Hiển thị toàn bộ dữ liệu
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + selectedStatus, 9)); // Cột 8: Trạng Thái
                }
            }
        });
    }

    public void hienThiTenSanPham() {
        SanPhamRepo sanPham = new SanPhamRepo();
        txtTenSanPham.removeAllItems();
        ArrayList<SanPhamModel> list = sanPham.hienThiTenSanPham();
        for (SanPhamModel sanPhamModel : list) {
            txtTenSanPham.addItem(sanPhamModel.getTenSanPham());
        }
    }

    public void MauSac() {
        cbbTenMauSac.removeAllItems();
        ArrayList<MauSac> list = ms.getAll();
        for (MauSac mauSac : list) {
            cbbTenMauSac.addItem(mauSac.getTenMauSac());
        }
    }

    public void XuatXu() {
        cbbTenXuatXu.removeAllItems();
        ArrayList<XuatXu> list = xx.getAll();
        for (XuatXu mauSac : list) {
            cbbTenXuatXu.addItem(mauSac.getTenXuatXu());
        }
    }

    public void KichThuoc() {
        cbbTenKichThuoc.removeAllItems();
        ArrayList<KichThuoc> list = kc.getAll();
        for (KichThuoc mauSac : list) {
            cbbTenKichThuoc.addItem(mauSac.getTenKichThuoc());
        }
    }

    public void ChatLieu() {
        cbbTenChatLieu.removeAllItems();
        ArrayList<ChatLieu> list = cl.getAll();
        for (ChatLieu mauSac : list) {
            cbbTenChatLieu.addItem(mauSac.getTenChatLieu());
        }
    }

    public void ThuongHieu() {
        cbbTenThuongHieu.removeAllItems();
        ArrayList<ThuongHieu> list = th.getAll();
        for (ThuongHieu thuongHieu : list) {
            cbbTenThuongHieu.addItem(thuongHieu.getTenThuongHieu());
        }
    }

    public String getMaThuongHieuByNameTenSanPham(String tenSanPham) {
        String maThuongHieu = (String) cbbTenKichThuoc.getSelectedItem();
        String sql = "SELECT MaSanPham FROM San_Pham WHERE TenSanPham = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenSanPham);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maThuongHieu = rs.getString("MaSanPham");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maThuongHieu;
    }

    public String getMaThuongHieuByNameKichCo(String tenKichCo) {
        String maThuongHieu = (String) cbbTenKichThuoc.getSelectedItem();
        String sql = "SELECT MaKichThuoc FROM Kich_Thuoc WHERE TenKichThuoc = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenKichCo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maThuongHieu = rs.getString("MaKichThuoc");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maThuongHieu;
    }

    public void timKiem() {
        SanPhamRepository repo = new SanPhamRepository();
        String tuKhoa = txtTimkiem.getText().trim();
        ArrayList<Model.SanPham> danhSachSP = repo.timKiemSanPham(tuKhoa);
        capNhatTable(danhSachSP);
    }

    private void capNhatTable(ArrayList<Model.SanPham> danhSachSP) {
        DefaultTableModel model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);
        int index = 1;
        for (Model.SanPham sp : danhSachSP) {
            model.addRow(new Object[]{
                index++,
                sp.getMaSanPhamCT(),
                sp.getTenSanPham(),
                sp.getDonGia(),
                sp.getSoLuong(),
                sp.getTenKichThuoc(),
                sp.getTenMauSac(),
                sp.getTenThuongHieu(),
                sp.getTenChatLieu(),
                sp.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"
            });
        }
    }

    public String getMaThuongHieuByNameXuatXu(String tenXuatXu) {
        String maThuongHieu = (String) cbbTenXuatXu.getSelectedItem();
        String sql = "SELECT MaXuatXu FROM Xuat_Xu WHERE TenXuatXu = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenXuatXu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maThuongHieu = rs.getString("MaXuatXu");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maThuongHieu;
    }

    public String getMaThuongHieuByNameChatLieu(String tenChatLieu) {
        String maThuongHieu = (String) cbbTenChatLieu.getSelectedItem();
        String sql = "SELECT MaChatLieu FROM Chat_Lieu WHERE TenChatLieu = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenChatLieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maThuongHieu = rs.getString("MaChatLieu");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maThuongHieu;
    }

    public String getMaThuongHieuByNameMauSac(String tenMauSac) {
        String maThuongHieu = (String) cbbTenMauSac.getSelectedItem();
        String sql = "SELECT MaMauSac FROM Mau_Sac WHERE TenMauSac = ?";

        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenMauSac);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maThuongHieu = rs.getString("MaMauSac");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maThuongHieu;
    }

    public void timKiemTheoTen() {
        SanPhamRepository reposs = new SanPhamRepository();
        String tenSanPham = txtTimKiemm.getText().trim();
        ArrayList<Model.SanPham> ls = reposs.timKiemTenSanPham(tenSanPham);
        HienThiDuLieu(ls);
    }

    public void HienThiDuLieu(ArrayList<Model.SanPham> lsss) {
        DefaultTableModel mdd = (DefaultTableModel) tblsanpham.getModel();
        int index = 1;
        mdd.setRowCount(0);
        for (Model.SanPham sp : lsss) {
            mdd.addRow(new Object[]{
                index++,
                sp.getMaSanPham(),
                sp.getTenSanPham(),
                sp.getMoTa(),
                sp.getSoLuong(),
                sp.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"
            });
        }
    }

    public String getMaThuongHieuByName(String tenThuongHieu) {
        String maThuongHieu = (String) cbbTenThuongHieu.getSelectedItem();
        String sql = "SELECT MaThuongHieu FROM Thuong_Hieu WHERE TenThuongHieu = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenThuongHieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maThuongHieu = rs.getString("MaThuongHieu");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maThuongHieu;
    }

    public void hinThiSanPhamChiTiet(int index) {
        SanPhamRepository sanPham = new SanPhamRepository();
        Model.SanPham sc = sanPham.getAllSanPhamWithFullDetails().get(index);
        txtSoLuong1.setText(String.valueOf(sc.getSoLuong()));
        txtGiaBan.setText(String.valueOf(sc.getDonGia()));
        cbbTenXuatXu.setSelectedItem(sc.getTenXuatXu());
        cbbTenMauSac.setSelectedItem(sc.getTenMauSac());
        cbbTenKichThuoc.setSelectedItem(sc.getTenKichThuoc());
        cbbTenThuongHieu.setSelectedItem(sc.getTenThuongHieu());
        cbbTenChatLieu.setSelectedItem(sc.getTenChatLieu());
        txtMaSPCT.setText(sc.getMaSanPham());
        txtTenSanPham.setSelectedItem(sc.getTenSanPham());
    }

    public String findExistingProductDetail(AddSanPham sp) {
        String sql = """
        SELECT MaSanPhamChiTiet 
        FROM San_Pham_Chi_Tiet 
        WHERE MaSanPham = ? AND MaChatLieu = ? AND MaMauSac = ? AND MaThuongHieu = ? 
        AND MaKichThuoc = ? AND MaXuatXu = ? AND DonGia = ?
    """;
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSanPham());
            ps.setString(2, sp.getMaChatLieu());
            ps.setString(3, sp.getMaMauSac());
            ps.setString(4, sp.getMaThuongHieu());
            ps.setString(5, sp.getMaKichThuoc());
            ps.setString(6, sp.getMaXuatXu());
            ps.setDouble(7, sp.getDonGia());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("MaSanPhamChiTiet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("HGieenr thị danh sách khách hàng");
        }
        return null;
    }

    public String generateMaSanPhamChiTiet() {
        String newCode = "";
        String sql = "SELECT TOP 1 MaSanPhamChiTiet FROM San_Pham_Chi_Tiet ORDER BY MaSanPhamChiTiet DESC";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String lastCode = rs.getString("MaSanPhamChiTiet");
                if (lastCode != null && lastCode.startsWith("SPCT") && lastCode.length() >= 8) {
                    int number = Integer.parseInt(lastCode.substring(4));
                    newCode = "SPCT" + String.format("%04d", number + 1);
                } else {
                    newCode = "SPCT0001";
                }
            } else {
                newCode = "SPCT0001";
            }
            String checkSql = "SELECT COUNT(*) FROM San_Pham_Chi_Tiet WHERE MaSanPhamChiTiet = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, newCode);
                ResultSet checkRs = checkStmt.executeQuery();
                while (checkRs.next() && checkRs.getInt(1) > 0) {
                    int number = Integer.parseInt(newCode.substring(4));
                    newCode = "SPCT" + String.format("%04d", number + 1);
                    checkStmt.setString(1, newCode);
                    checkRs = checkStmt.executeQuery();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            newCode = "SPCT0001";
        }
        return newCode;
    }

    public boolean updateSoLuong(String maSanPhamChiTiet, int soLuongMoi) {
        String sql = "UPDATE San_Pham_Chi_Tiet SET SoLuong = SoLuong + ? WHERE MaSanPhamChiTiet = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongMoi);
            ps.setString(2, maSanPhamChiTiet);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addProductDetail() {
        try {
            String tenSanPham = txtTenSanPham.getSelectedItem() != null ? txtTenSanPham.getSelectedItem().toString() : "";
            String tenChatLieu = cbbTenChatLieu.getSelectedItem() != null ? cbbTenChatLieu.getSelectedItem().toString() : "";
            String tenMauSac = cbbTenMauSac.getSelectedItem() != null ? cbbTenMauSac.getSelectedItem().toString() : "";
            String tenKichThuoc = cbbTenKichThuoc.getSelectedItem() != null ? cbbTenKichThuoc.getSelectedItem().toString() : "";
            String tenThuongHieu = cbbTenThuongHieu.getSelectedItem() != null ? cbbTenThuongHieu.getSelectedItem().toString() : "";
            String tenXuatXu = cbbTenXuatXu.getSelectedItem() != null ? cbbTenXuatXu.getSelectedItem().toString() : "";
            String soLuongStr = txtSoLuong1.getText().trim();
            int soLuong = 0;
            if (!soLuongStr.isEmpty()) {
                try {
                    soLuong = Integer.parseInt(soLuongStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ. Vui lòng nhập một số nguyên hợp lệ.");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Số lượng không được để trống.");
                return;
            }
            double donGia = Double.parseDouble(txtGiaBan.getText());
            String maChatLieu = getMaThuongHieuByNameChatLieu(tenChatLieu);
            String maMauSac = getMaThuongHieuByNameMauSac(tenMauSac);
            String maKichCo = getMaThuongHieuByNameKichCo(tenKichThuoc);
            String maThuongHieu = getMaThuongHieuByName(tenThuongHieu);
            String maXuatXu = getMaThuongHieuByNameXuatXu(tenXuatXu);
            String maSanPham = getMaThuongHieuByNameTenSanPham(tenSanPham);
            int trangThai = 1;
            AddSanPham addSanPham = new AddSanPham();
            addSanPham.setMaSanPham(maSanPham);
            addSanPham.setMaChatLieu(maChatLieu);
            addSanPham.setMaMauSac(maMauSac);
            addSanPham.setMaThuongHieu(maThuongHieu);
            addSanPham.setMaKichThuoc(maKichCo);
            addSanPham.setMaXuatXu(maXuatXu);
            addSanPham.setSoLuong(soLuong);
            addSanPham.setDonGia(donGia);
            addSanPham.setTrangThai(trangThai);
            String existingMaSPCT = findExistingProductDetail(addSanPham);
            if (existingMaSPCT != null) {
                boolean updateSuccess = updateSoLuong(existingMaSPCT, soLuong);
                if (updateSuccess) {
                    JOptionPane.showMessageDialog(null, "Sản phẩm chi tiết đã tồn tại. Đã cộng dồn số lượng!");
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể cập nhật số lượng sản phẩm chi tiết.");
                }
            } else {
                String maSanPhamChiTiet = generateMaSanPhamChiTiet();
                addSanPham.setMaSanPhamChiTiet(maSanPhamChiTiet);
                addSanPham.setSoLuong(soLuong);
                boolean success = repo.add(addSanPham);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Thêm sản phẩm chi tiết thành công!");
//                    String qrCodePath = createQRCode(addSanPham);
//                    if (qrCodePath != null) {
//                        JOptionPane.showMessageDialog(null, "Mã QR đã được tạo tại: " + qrCodePath);
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Không thể tạo mã QR. Vui lòng thử lại.");
//                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm sản phẩm chi tiết thất bại!");
                }
            }
            showSanPhamTable();
            showSanPhamTableWithFullDetails();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "lỗi cơ sổ dữ liệu: " + ex.getMessage());
        }
    }

//    private String createQRCode(AddSanPham addSanPham) {
//        try {
//            String maSanPham = addSanPham.getMaSanPham();
//            String filePath = "D:\\" + maSanPham + ".png";
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(maSanPham, BarcodeFormat.QR_CODE, 200, 200);
//            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", new File(filePath).toPath());
//            return filePath;
//        } catch (WriterException | IOException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi tạo mã QR: " + e.getMessage());
//            return null;
//        }
//    }
    public void updateProductDetail() {
        String tenSanPham = txtTenSanPham.getSelectedItem() != null ? txtTenSanPham.getSelectedItem().toString() : "";
        String maSanPham = getMaThuongHieuByNameTenSanPham(tenSanPham);

//        String maSanPham = txtMaSPCT.getText().trim();
        if (!maSanPham.isEmpty()) {
            AddSanPham addSanPham = new AddSanPham();
            addSanPham.setSoLuong(Integer.parseInt(txtSoLuong1.getText().trim()));
            addSanPham.setTrangThai((byte) 1);
            addSanPham.setMaChatLieu(getMaThuongHieuByNameChatLieu(cbbTenChatLieu.getSelectedItem().toString()));
            addSanPham.setMaMauSac(getMaThuongHieuByNameMauSac(cbbTenMauSac.getSelectedItem().toString()));
            addSanPham.setMaThuongHieu(getMaThuongHieuByName(cbbTenThuongHieu.getSelectedItem().toString()));
            addSanPham.setMaKichThuoc(getMaThuongHieuByNameKichCo(cbbTenKichThuoc.getSelectedItem().toString()));
            addSanPham.setMaXuatXu(getMaThuongHieuByNameXuatXu(cbbTenXuatXu.getSelectedItem().toString()));
            addSanPham.setDonGia(Double.parseDouble(txtGiaBan.getText().trim()));

            boolean updated = repo.updateProductDetail(addSanPham, maSanPham);
            if (updated) {
                JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
                showSanPhamTable(); // Cập nhật lại bảng dữ liệu
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thất bại!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để cập nhật!");
        }
    }

    public void delete() {
        SanPhamRepository sp = new SanPhamRepository();
        String tenSanPham = txtTenSanPham.getSelectedItem() != null ? txtTenSanPham.getSelectedItem().toString() : "";
        String maSanPham = getMaThuongHieuByNameTenSanPham(tenSanPham);
        Boolean ketQua = sp.deleteProduct(maSanPham);
        if (ketQua) {
            JOptionPane.showMessageDialog(null, "Xóa Thành Công");
            showSanPhamTableWithFullDetails();
            showSanPhamTable();
        } else {
            JOptionPane.showMessageDialog(null, "Xóa Thất Bại");
        }
    }

    public void clear() {
        txtMaSPCT.setText("");
        txtTenSanPham.setSelectedIndex(0);
        cbbTenKichThuoc.setSelectedIndex(0);
        cbbTenChatLieu.setSelectedIndex(0);
        cbbTenMauSac.setSelectedIndex(0);
        cbbTenXuatXu.setSelectedIndex(0);
        txtGiaBan.setText("");
        txtSoLuong1.setText("");
    }

    public void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser("D:\\anh");
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Sản Phẩm");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Mã Sản Phẩm");
                headerRow.createCell(1).setCellValue("Tên Sản Phẩm");
                headerRow.createCell(2).setCellValue("Giá Bán");
                headerRow.createCell(3).setCellValue("Số Lượng Tồn");
                headerRow.createCell(4).setCellValue("Kích Cỡ Sản Phẩm");
                headerRow.createCell(5).setCellValue("Màu Sắc Sản Phẩm");
                headerRow.createCell(6).setCellValue("Tên Thương Hiệu");
                headerRow.createCell(7).setCellValue("Chất Liệu");
                headerRow.createCell(8).setCellValue("Trạng Thái");
                DefaultTableModel model = (DefaultTableModel) tblSPCT.getModel();
                System.out.println("Số dòng: " + model.getRowCount());
                System.out.println("Số cột: " + model.getColumnCount());
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object cellValue = model.getValueAt(i, j);
                        System.out.println("Dòng " + (i + 1) + ", Cột " + (j + 1) + ": " + cellValue);
                        row.createCell(j).setCellValue(cellValue != null ? cellValue.toString() : "");
                    }
                }
                try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                    workbook.write(outputStream);
                    JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void HienThiChiTiet(int index) {
        SanPhamRepository sanPham = new SanPhamRepository();
        Model.SanPham sp = sanPham.getAllSanPham().get(index);
        txtMaSanPham.setText(sp.getMaSanPham());
        txtTenSanPham11.setText(sp.getTenSanPham());
        taMoTa.setText(sp.getMoTa());
        txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
        if (sp.getTrangThai() == 1) {
            rdoConKinhDoanh.setSelected(true);
        } else {
            rdoNgungKinhDoan.setSelected(true);
        }
    }

    public void showSanPhamTable() {
        DefaultTableModel spModel = (DefaultTableModel) tblsanpham.getModel();
        spModel.setRowCount(0);
        SanPhamRepository sanPhamRepository = new SanPhamRepository();
        ArrayList<Model.SanPham> sanPhamList = sanPhamRepository.getAllSanPham();
        int index = 1;
        for (Model.SanPham sp : sanPhamList) {
            Object[] rowData = {
                index++,
                sp.getMaSanPham(),
                sp.getTenSanPham(),
                sp.getMoTa(),
                sp.getSoLuong(),
                sp.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"
            };
            spModel.addRow(rowData);
        }
    }

    public void showSanPhamTableWithFullDetails() {
        DefaultTableModel spctModel = (DefaultTableModel) tblSPCT.getModel();
        spctModel.setRowCount(0);
        SanPhamRepository sanPhamRepository = new SanPhamRepository();
        List<Model.SanPham> sanPhamList = sanPhamRepository.getAllSanPhamWithFullDetails();
        int index = 0;
        for (Model.SanPham sp : sanPhamList) {
            Object[] rowData = {
                index++,
                sp.getMaSanPhamCT(),
                sp.getTenSanPham(),
                sp.getDonGia(),
                sp.getSoLuong(),
                sp.getTenKichThuoc(),
                sp.getTenMauSac(),
                sp.getTenThuongHieu(),
                sp.getTenChatLieu(),
                sp.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"
            };
            spctModel.addRow(rowData);
        }
    }

    public void themSanPham() {
        String tenSanPham = txtTenSanPham11.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();
        String moTa = taMoTa.getText().trim();

        // Kiểm tra bỏ trống
        if (tenSanPham.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sản phẩm!");
            return;
        }

        if (soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!");
            return;
        }

        if (moTa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mô tả sản phẩm!");
            return;
        }

        if (!rdoConKinhDoanh.isSelected() && !rdoNgungKinhDoan.isSelected()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái sản phẩm!");
            return;
        }

        long soLuong;

        try {
            soLuong = Long.parseLong(soLuongStr);
            if (soLuong < 0) {
                JOptionPane.showMessageDialog(null, "Số lượng không được âm!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là số hợp lệ!");
            return;
        }

        boolean trangThai = rdoConKinhDoanh.isSelected();

        SanPhamModel save = new SanPhamModel();
        save.setTenSanPham(tenSanPham);
        save.setSoLuong(soLuong);
        save.setMoTa(moTa);
        save.setTrangThai(trangThai);

        String maSanPham = repo.addSanPham(save); // Thêm vào CSDL

        if (maSanPham != null) {
            JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công! Mã: " + maSanPham);
            System.out.println("Thêm sản phẩm thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm sản phẩm!");
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        rdoKinhDoanh = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiemm = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham11 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        taMoTa = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblsanpham = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        rdoConKinhDoanh = new javax.swing.JRadioButton();
        rdoNgungKinhDoan = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnThemSPCT = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnSuaSPCT = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnXoaSPCT = new javax.swing.JButton();
        txtMaSPCT = new javax.swing.JTextField();
        btnReSet = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSPCT = new javax.swing.JTable();
        cbbTenMauSac = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbbTenChatLieu = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSoLuong1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbbTenKichThuoc = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cbbTenXuatXu = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cbbTenThuongHieu = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTimkiem = new javax.swing.JTextField();
        trangThai = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        txtTenSanPham = new javax.swing.JComboBox<>();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jLabel1.setText("Tìm kiếm");

        txtTimKiemm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemmKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemmKeyReleased(evt);
            }
        });

        jLabel2.setText("Mã sản phẩm");

        jLabel3.setText("Tên sản phẩm");

        jLabel4.setText("Mô tả");

        txtMaSanPham.setEditable(false);
        txtMaSanPham.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txtMaSanPham.setEnabled(false);

        taMoTa.setColumns(20);
        taMoTa.setRows(5);
        jScrollPane1.setViewportView(taMoTa);

        tblsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Mô Tả", "Số Lượng", "Trạng Thái"
            }
        ));
        tblsanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblsanphamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblsanpham);

        jLabel5.setText("Số Lượng");

        jLabel6.setText("Trạng Thái");

        buttonGroup1.add(rdoConKinhDoanh);
        rdoConKinhDoanh.setText("Đang bán");

        buttonGroup1.add(rdoNgungKinhDoan);
        rdoNgungKinhDoan.setText("Ngừng bán");

        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rdoKinhDoanhLayout = new javax.swing.GroupLayout(rdoKinhDoanh);
        rdoKinhDoanh.setLayout(rdoKinhDoanhLayout);
        rdoKinhDoanhLayout.setHorizontalGroup(
            rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                        .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addComponent(jLabel1))
                        .addGap(41, 41, 41)
                        .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaSanPham)
                                    .addComponent(txtTenSanPham11)
                                    .addComponent(jScrollPane1))
                                .addGap(141, 141, 141)
                                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(81, 81, 81)
                                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                                        .addComponent(rdoConKinhDoanh)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoNgungKinhDoan))))
                            .addComponent(txtTimKiemm, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(54, 54, 54)
                                .addComponent(btnSua)
                                .addGap(64, 64, 64)
                                .addComponent(btnLamMoi)))))
                .addGap(38, 38, 38))
        );
        rdoKinhDoanhLayout.setVerticalGroup(
            rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txtTimKiemm, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenSanPham11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(rdoConKinhDoanh)
                    .addComponent(rdoNgungKinhDoan))
                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel4))
                    .addGroup(rdoKinhDoanhLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(rdoKinhDoanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSua)
                        .addComponent(btnLamMoi)))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rdoKinhDoanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rdoKinhDoanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm", jPanel2);

        btnThemSPCT.setText("Thêm");
        btnThemSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPCTActionPerformed(evt);
            }
        });

        jLabel7.setText("Kích Thước");

        btnSuaSPCT.setText("Sửa");
        btnSuaSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPCTActionPerformed(evt);
            }
        });

        jLabel8.setText("Xuất Xứ");

        btnXoaSPCT.setText("Xóa");
        btnXoaSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPCTActionPerformed(evt);
            }
        });

        txtMaSPCT.setEditable(false);
        txtMaSPCT.setEnabled(false);

        btnReSet.setText("Làm Mới");
        btnReSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReSetActionPerformed(evt);
            }
        });

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Giá Bán", "Số Lượng", "Kích Thước", "Màu Sắc", "Thương Hiệu", "Chất Liệu", "Trạng Thái"
            }
        ));
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSPCT);

        cbbTenMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Mã");

        cbbTenChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Tên");

        jLabel11.setText("Màu Sắc");

        jLabel12.setText("Giá Bán");

        cbbTenKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setText("Chất Liệu");

        cbbTenXuatXu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("Số Lượng");

        cbbTenThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Thương Hiệu");

        jLabel16.setText("Tìm kiếm sản phẩm:");

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

        trangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "     ", "Đang bán", "Ngừng bán" }));
        trangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trangThaiActionPerformed(evt);
            }
        });

        jButton1.setText("Xuất file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtTenSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbTenMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbTenChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtGiaBan)
                                    .addComponent(txtTenSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(156, 156, 156)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(154, 154, 154)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbTenKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbTenXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbbTenThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThemSPCT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaSPCT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoaSPCT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReSet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(101, 101, 101)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14)
                    .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(cbbTenKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(cbbTenMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTenXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbbTenChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(cbbTenThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSPCT)
                    .addComponent(btnSuaSPCT)
                    .addComponent(btnXoaSPCT)
                    .addComponent(btnReSet)
                    .addComponent(jLabel16)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm Chi Tiết", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblsanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblsanphamMouseClicked
        // TODO add your handling code here:
        int index = tblsanpham.getSelectedRow();
        HienThiChiTiet(index);
    }//GEN-LAST:event_tblsanphamMouseClicked

    private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMouseClicked
        // TODO add your handling code here;
        int index = tblSPCT.getSelectedRow();
        hinThiSanPhamChiTiett(index);
    }//GEN-LAST:event_tblSPCTMouseClicked
    public void hinThiSanPhamChiTiett(int index) {
        SanPhamRepository sanPham = new SanPhamRepository();
        Model.SanPham sc = sanPham.getAllSanPhamWithFullDetails().get(index);
        txtSoLuong1.setText(String.valueOf(sc.getSoLuong()));
        txtGiaBan.setText(String.valueOf(sc.getDonGia()));
        cbbTenXuatXu.setSelectedItem(sc.getTenXuatXu());
        cbbTenMauSac.setSelectedItem(sc.getTenMauSac());
        cbbTenKichThuoc.setSelectedItem(sc.getTenKichThuoc());
        cbbTenThuongHieu.setSelectedItem(sc.getTenThuongHieu());
        cbbTenChatLieu.setSelectedItem(sc.getTenChatLieu());
        txtMaSPCT.setText(sc.getMaSanPhamCT());
        txtTenSanPham.setSelectedItem(sc.getTenSanPham());
    }
    private void btnThemSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPCTActionPerformed
        // TODO add your handling code here:
        addProductDetail();
    }//GEN-LAST:event_btnThemSPCTActionPerformed

    private void btnReSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReSetActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btnReSetActionPerformed

    private void txtTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimkiemActionPerformed

    }//GEN-LAST:event_txtTimkiemActionPerformed

    private void txtTimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimkiemKeyReleased
        // TODO add your handling code here:
        timKiem();
    }//GEN-LAST:event_txtTimkiemKeyReleased

    private void trangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trangThaiActionPerformed
        // TODO add your handling code here:
        timKiem();
    }//GEN-LAST:event_trangThaiActionPerformed

    private void btnSuaSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPCTActionPerformed
        // TODO add your handling code here:
        updateProductDetail();
        showSanPhamTableWithFullDetails();
        showSanPhamTable();
    }//GEN-LAST:event_btnSuaSPCTActionPerformed

    private void btnXoaSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPCTActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaSPCTActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        exportToExcel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTimKiemmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemmKeyReleased
        // TODO add your handling code here:
        timKiemTheoTen();
    }//GEN-LAST:event_txtTimKiemmKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        themSanPham();
        showSanPhamTable();
        hienThiTenSanPham();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtTimKiemmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemmKeyPressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        String ma = txtMaSanPham.getText().trim();
        String ten = txtTenSanPham11.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();
        String moTa = taMoTa.getText().trim();
        if (ma.isEmpty() || ten.isEmpty() || soLuongStr.isEmpty() || moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
            return;
        }
        long soLuong;
        try {
            soLuong = Long.parseLong(soLuongStr);
            if (soLuong < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn hoặc bằng 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số!");
            return;
        }

        boolean trangThai;
        if (!rdoConKinhDoanh.isSelected() && !rdoNgungKinhDoan.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái!");
            return;
        } else {
            trangThai = rdoConKinhDoanh.isSelected();
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn sửa sản phẩm \"" + ten + "\" không?",
                "Xác nhận sửa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        SanPhamModel sp = new SanPhamModel();
        sp.setMaSanPham(ma);
        sp.setTenSanPham(ten);
        sp.setSoLuong(soLuong);
        sp.setMoTa(moTa);
        sp.setTrangThai(trangThai);

        if (repo.updateSanPham(sp)) {
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
            // Load lại bảng sản phẩm nếu cần
        } else {
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại!");
        }
        System.out.println("Update: " + sp.getTenSanPham() + ", " + sp.getMaSanPham());

        showSanPhamTable();

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txtMaSanPham.setText("");
        txtTenSanPham11.setText("");
        taMoTa.setText("");
        txtSoLuong.setText("");
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_btnLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnReSet;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaSPCT;
    private javax.swing.JButton btnThemSPCT;
    private javax.swing.JButton btnXoaSPCT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbTenChatLieu;
    private javax.swing.JComboBox<String> cbbTenKichThuoc;
    private javax.swing.JComboBox<String> cbbTenMauSac;
    private javax.swing.JComboBox<String> cbbTenThuongHieu;
    private javax.swing.JComboBox<String> cbbTenXuatXu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton rdoConKinhDoanh;
    private javax.swing.JPanel rdoKinhDoanh;
    private javax.swing.JRadioButton rdoNgungKinhDoan;
    private javax.swing.JTextArea taMoTa;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTable tblsanpham;
    private javax.swing.JComboBox<String> trangThai;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuong1;
    private javax.swing.JComboBox<String> txtTenSanPham;
    private javax.swing.JTextField txtTenSanPham11;
    private javax.swing.JTextField txtTimKiemm;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
