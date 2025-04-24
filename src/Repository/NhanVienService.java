/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Jdbc.DbConnect;
import Model.NhanVienModel;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Acer
 */
public class NhanVienService {

    String INSERT_SQL = "INSERT INTO Nhan_Vien (MaNhanVien, TenNhanVien, GioiTinh, SoDienThoai, DiaChi, Email, CCCD, NgayVaoLam, Password, ChucVu, TrangThai) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Nhan_Vien SET TenNhanVien = ?, GioiTinh = ?, SoDienThoai = ?, DiaChi = ?, Email = ?, CCCD = ?, NgayVaoLam = ?, Password = ?, ChucVu = ?, TrangThai = ? WHERE MaNhanVien = ?";
    String DELETE_SQL = "DELETE FROM Nhan_Vien WHERE MaNhanVien = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Nhan_Vien";
    String SELECT_BY_ID_SQL = "SELECT * FROM Nhan_Vien WHERE MaNhanVien = ?";

//    private final String INSERT_SQL = "INSERT INTO Nhan_Vien (MaNhanVien, TenNhanVien, GioiTinh, SoDienThoai, DiaChi, Email, CCCD, NgayVaoLam, Password, ChucVu, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public String generateMaNhanVien() {
        String sql = "SELECT MAX(MaNhanVien) FROM Nhan_Vien";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maxMa = rs.getString(1);
                if (maxMa != null) {
                    int num = Integer.parseInt(maxMa.substring(2));
                    num++;
                    return String.format("NV%03d", num);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NV001"; // nếu chưa có nhân viên nào
    }

    private String generateRandomCCCD() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 12; i++) {
            sb.append(rand.nextInt(10)); // tạo số từ 0 đến 9
        }
        return sb.toString();
    }

    public int insert(NhanVienModel x) {
        try {
            String maMoi = generateMaNhanVien();
            x.setMaNhanVien(maMoi); // gán mã mới

            try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

                ps.setObject(1, x.getMaNhanVien());
                ps.setObject(2, x.getTenNhanVien());
                ps.setObject(3, x.getGioiTinh());
                ps.setObject(4, x.getSoDienThoai());
                ps.setObject(5, x.getDiaChi());
                ps.setObject(6, x.getEmail());
                String cccd = x.getCccd();
                if (cccd == null || cccd.trim().isEmpty()) {
                    cccd = generateRandomCCCD(); // tự sinh CCCD
                }
                ps.setObject(7, cccd);
                ps.setObject(8, x.getNgayVaoLam());
                ps.setObject(9, (x.getPassWord() == null || x.getPassWord().isEmpty()) ? "Default@123" : x.getPassWord());
                ps.setObject(10, x.getChucVu());
                ps.setObject(11, x.isTrangThai() ? 1 : 0);

                return ps.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(NhanVienModel x) {
        try {
            String UPDATE_SQL = "UPDATE Nhan_Vien SET TenNhanVien = ?, GioiTinh = ?, SoDienThoai = ?, DiaChi = ?, Email = ?, CCCD = ?, NgayVaoLam = ?, Password = ?, ChucVu = ?, TrangThai = ? WHERE MaNhanVien = ?";
            try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {

                ps.setObject(1, x.getTenNhanVien());
                ps.setObject(2, x.getGioiTinh());
                ps.setObject(3, x.getSoDienThoai());
                ps.setObject(4, x.getDiaChi());
                ps.setObject(5, x.getEmail());
                String cccd = x.getCccd();
                if (cccd == null || cccd.trim().isEmpty()) {
                    cccd = generateRandomCCCD(); // tự sinh CCCD
                }
                ps.setObject(6, cccd);
                ps.setObject(7, x.getNgayVaoLam());
                ps.setObject(8, (x.getPassWord() == null || x.getPassWord().isEmpty()) ? "Default@123" : x.getPassWord());
                ps.setObject(9, x.getChucVu());
                ps.setObject(10, x.isTrangThai());
                ps.setObject(11, x.getMaNhanVien());

                return ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean delete(String id) {
        try {
            try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_SQL);) {
                ps.setObject(1, id);
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NhanVienModel> getAll() {
        try {
            try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);) {
                try (ResultSet rs = ps.executeQuery();) {
                    List<NhanVienModel> list = new ArrayList<>();
                    while (rs.next()) {

                        NhanVienModel x = new NhanVienModel();
                        x.setId(rs.getInt("id"));
                        x.setMaNhanVien(rs.getString("MaNhanVien"));
                        x.setTenNhanVien(rs.getString("TenNhanVien"));
                        x.setGioiTinh(rs.getString("GioiTinh"));
                        x.setSoDienThoai(rs.getString("SoDienThoai"));
                        x.setDiaChi(rs.getString("DiaChi"));
                        x.setEmail(rs.getString("Email"));
                        x.setCccd(rs.getString("CCCD"));
                        x.setNgayVaoLam(rs.getString("NgayVaoLam"));
                        x.setPassWord(rs.getString("Password"));
                        x.setChucVu(rs.getString("ChucVu"));
                        x.setTrangThai(rs.getInt("trangThai") == 1);

                        list.add(x);
                    }
                    return list;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NhanVienModel> locTheoGioiTinh(String gioiTinh) {
        List<NhanVienModel> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM Nhan_Vien"; // Truy vấn mặc định lấy toàn bộ dữ liệu

        if (!gioiTinh.equalsIgnoreCase("Tất cả")) {
            sql += " WHERE GioiTinh = ?";
        }

        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!gioiTinh.equalsIgnoreCase("Tất cả")) {
                ps.setString(1, gioiTinh);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienModel nv = new NhanVienModel();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setEmail(rs.getString("Email"));
                nv.setTrangThai(rs.getInt("TrangThai") == 1);
                ketQua.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public List<NhanVienModel> locTheoTrangThai(String trangThai) {
        List<NhanVienModel> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM Nhan_Vien"; // Truy vấn mặc định lấy toàn bộ nhân viên

        if (!trangThai.equalsIgnoreCase("Tất cả")) {
            sql += " WHERE TrangThai = ?";
        }

        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!trangThai.equalsIgnoreCase("Tất cả")) {
                int trangThaiValue = trangThai.equalsIgnoreCase("Đang làm") ? 1 : 0;
                ps.setInt(1, trangThaiValue);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienModel nv = new NhanVienModel();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setEmail(rs.getString("Email"));
                nv.setTrangThai(rs.getInt("TrangThai") == 1);
                ketQua.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public List<NhanVienModel> timKiem(String keyword) {
        List<NhanVienModel> ketQua = new ArrayList<>();
//        String sql = "SELECT * FROM Nhan_Vien WHERE MaNhanVien LIKE ? OR TenNhanVien LIKE ?";
        String sql = "SELECT * FROM Nhan_Vien WHERE "
                + "MaNhanVien LIKE ? OR "
                + "TenNhanVien LIKE ? OR "
                + "SoDienThoai LIKE ? OR "
                + "DiaChi LIKE ? OR "
                + "Email LIKE ?";

        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchKey = "%" + keyword + "%";
//            ps.setString(1, searchKey);
//            ps.setString(2, searchKey);
            // Đặt giá trị cho 5 tham số
            for (int i = 1; i <= 5; i++) {
                ps.setString(i, searchKey);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienModel nv = new NhanVienModel();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setEmail(rs.getString("Email"));
                nv.setTrangThai(rs.getInt("TrangThai") == 1);
                ketQua.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public boolean isMaNVExists(String maNV) {
        String sql = "SELECT 1 FROM Nhan_Vien WHERE MaNhanVien = ?";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Nếu có bản ghi thì mã đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPhoneExists(String phone) {
        String sql = "SELECT 1 FROM Nhan_Vien WHERE SoDienThoai = ?";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Nếu có bản ghi thì đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM Nhan_Vien WHERE Email = ?";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean exportToExcel(List<NhanVienModel> danhSach, String filePath) {
    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("NhanVien");

        // Tạo header
        String[] headers = {"STT", "Mã NV", "Tên NV", "Giới Tính", "SĐT", "Địa chỉ", "Email", "Trạng thái"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Ghi dữ liệu
        int rowIndex = 1;
        for (int i = 0; i < danhSach.size(); i++) {
            NhanVienModel nv = danhSach.get(i);
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(nv.getMaNhanVien());
            row.createCell(2).setCellValue(nv.getTenNhanVien());
            row.createCell(3).setCellValue(nv.getGioiTinh());
            row.createCell(4).setCellValue(nv.getSoDienThoai());
            row.createCell(5).setCellValue(nv.getDiaChi());
            row.createCell(6).setCellValue(nv.getEmail());
            row.createCell(7).setCellValue(nv.isTrangThai() ? "Đang làm" : "Nghỉ việc");
        }

        // Auto-size cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
