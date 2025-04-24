/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.AddSanPham;
import Model.SanPham;
import Model.SanPhamChiTiet;
import Model.SanPhamModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author TONG THI NHUNG
 */
public class SanPhamRepository {

    public ArrayList<SanPham> getAllSanPham() {
        ArrayList<SanPham> sanPhamList = new ArrayList<>();
        String query = """
                      select * from San_Pham
                      """;
        try (Connection connection = DbConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                SanPham sanPham = new SanPham();
                sanPham.setMaSanPham(resultSet.getString("MaSanPham"));
                sanPham.setTenSanPham(resultSet.getString("TenSanPham"));
                sanPham.setSoLuong(resultSet.getInt("SoLuong"));
                sanPham.setMoTa(resultSet.getString("MoTa"));
                sanPham.setTrangThai(resultSet.getByte("TrangThai"));
                sanPhamList.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPhamList;
    }

    public ArrayList<SanPham> timKiemTenSanPham(String keyword) {
        ArrayList<SanPham> sanPhamList = new ArrayList<>();
        String query = """
        SELECT * FROM San_Pham
        WHERE TenSanPham LIKE ?
           OR MaSanPham LIKE ?
           OR MoTa LIKE ?
           OR CAST(SoLuong AS VARCHAR) LIKE ?
    """;
        try (Connection connection = DbConnect.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            ps.setString(3, likeKeyword);
            ps.setString(4, likeKeyword); // SoLuong được ép kiểu sang chuỗi để so sánh

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                SanPham sanPham = new SanPham();
                sanPham.setMaSanPham(resultSet.getString("MaSanPham"));
                sanPham.setTenSanPham(resultSet.getString("TenSanPham"));
                sanPham.setSoLuong(resultSet.getInt("SoLuong"));
                sanPham.setMoTa(resultSet.getString("MoTa"));
                sanPham.setTrangThai(resultSet.getByte("TrangThai"));
                sanPhamList.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPhamList;
    }

    public SanPham findSanPhamByMa(String maSanPham) {
        String query = "SELECT sp.MaSanPham, sp.TenSanPham, spct.DonGia, spct.SoLuong, spct.TrangThai "
                + "FROM San_Pham sp "
                + "INNER JOIN San_Pham_Chi_Tiet spct ON sp.MaSanPham = spct.MaSanPham "
                + "WHERE sp.MaSanPham = ?";
        Model.SanPham sanPham = null;
        try (Connection connection = DbConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, maSanPham);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sanPham = new Model.SanPham();
                sanPham.setMaSanPham(resultSet.getString("MaSanPham"));
                sanPham.setTenSanPham(resultSet.getString("TenSanPham"));
                sanPham.setDonGia(resultSet.getBigDecimal("DonGia"));
                sanPham.setSoLuong(resultSet.getInt("SoLuong"));
                sanPham.setTrangThai(resultSet.getByte("TrangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPham;
    }

    public String generateMaSanPham() {
        String sql = "SELECT TOP 1 MaSanPham FROM San_Pham ORDER BY MaSanPham DESC";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastMaSP = rs.getString("MaSanPham"); // Lấy mã sản phẩm lớn nhất
                int num = Integer.parseInt(lastMaSP.substring(2)); // Bỏ "SP" lấy số
                return String.format("SP%03d", num + 1); // Tăng giá trị và format "SPxxx"
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SP001"; // Nếu chưa có sản phẩm nào thì bắt đầu từ SP001
    }

    public String addSanPham(SanPhamModel sanPham) {
        String maSanPhamMoi = generateMaSanPham();
//        String mota = "Tuyệt Vời";// Tạo mã sản phẩm mới
        String sql = "INSERT INTO San_Pham (MaSanPham, TenSanPham, SoLuong, MoTa, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSanPhamMoi);
            ps.setString(2, sanPham.getTenSanPham());
            ps.setLong(3, sanPham.getSoLuong());
            ps.setString(4, sanPham.getMoTa());
            ps.setBoolean(5, sanPham.isTrangThai());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return maSanPhamMoi;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateSanPham(SanPhamModel sanPham) {
        String sql = "UPDATE San_Pham SET TenSanPham = ?, SoLuong = ?, MoTa = ?, TrangThai = ? WHERE MaSanPham = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sanPham.getTenSanPham());
            ps.setLong(2, sanPham.getSoLuong());
            ps.setString(3, sanPham.getMoTa());
            ps.setBoolean(4, sanPham.isTrangThai());
            ps.setString(5, sanPham.getMaSanPham());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SanPham> getAllSanPhamWithFullDetails() {
        List<SanPham> sanPhamList = new ArrayList<>();
        String query = """
                       SELECT spct.MaSanPhamChiTiet, sp.TenSanPham, spct.DonGia, spct.SoLuong, spct.TrangThai, 
                                              th.MaThuongHieu, th.TenThuongHieu, cl.MaChatLieu, cl.TenChatLieu,
                                              ms.MaMauSac, ms.TenMauSac, kt.MaKichThuoc, kt.TenKichThuoc, xx.MaXuatXu, xx.TenXuatXu
                                              FROM San_Pham_Chi_Tiet spct
                                              JOIN  San_Pham sp ON sp.MaSanPham = spct.MaSanPham 
                                              JOIN Thuong_Hieu th ON spct.MaThuongHieu = th.MaThuongHieu 
                                              JOIN Chat_Lieu cl ON spct.MaChatLieu = cl.MaChatLieu 
                                              JOIN Mau_Sac ms ON spct.MaMauSac = ms.MaMauSac 
                                              JOIN Kich_Thuoc kt ON spct.MaKichThuoc = kt.MaKichThuoc 
                                              JOIN Xuat_Xu xx ON spct.MaXuatXu = xx.MaXuatXu
                       """;
        try (Connection connection = DbConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                SanPham sanPham = new SanPham();
                sanPham.setMaSanPhamCT(resultSet.getString("MaSanPhamChiTiet"));
                sanPham.setTenSanPham(resultSet.getString("TenSanPham"));
                sanPham.setDonGia(resultSet.getBigDecimal("DonGia"));
                sanPham.setSoLuong(resultSet.getInt("SoLuong"));
                sanPham.setTrangThai(resultSet.getByte("TrangThai"));
                sanPham.setMaThuongHieu(resultSet.getString("MaThuongHieu"));
                sanPham.setTenThuongHieu(resultSet.getString("TenThuongHieu"));
                sanPham.setMaChatLieu(resultSet.getString("MaChatLieu"));
                sanPham.setTenChatLieu(resultSet.getString("TenChatLieu"));
                sanPham.setMaMauSac(resultSet.getString("MaMauSac"));
                sanPham.setTenMauSac(resultSet.getString("TenMauSac"));
                sanPham.setMaKichThuoc(resultSet.getString("MaKichThuoc"));
                sanPham.setTenKichThuoc(resultSet.getString("TenKichThuoc"));
                sanPham.setMaXuatXu(resultSet.getString("MaXuatXu"));
                sanPham.setTenXuatXu(resultSet.getString("TenXuatXu"));
                sanPhamList.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPhamList;
    }

    public boolean add(AddSanPham addSanPham) {
        int check = 0;

        String sql = """
        INSERT INTO San_Pham_Chi_Tiet
        (MaSanPhamChiTiet, MaSanPham, MaChatLieu, MaMauSac, MaThuongHieu, MaKichThuoc, MaXuatXu, SoLuong, DonGia, TrangThai)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, addSanPham.getMaSanPhamChiTiet());
            ps.setObject(2, addSanPham.getMaSanPham());
            ps.setObject(3, addSanPham.getMaChatLieu());
            ps.setObject(4, addSanPham.getMaMauSac());
            ps.setObject(5, addSanPham.getMaThuongHieu());
            ps.setObject(6, addSanPham.getMaKichThuoc());
            ps.setObject(7, addSanPham.getMaXuatXu());
            ps.setObject(8, addSanPham.getSoLuong());
            ps.setObject(9, addSanPham.getDonGia());
            ps.setObject(10, addSanPham.getTrangThai());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updateProductDetail(AddSanPham addSanPham, String maSanPham) {
        int check2 = 0;

        String sqlSanPhamChiTiet = """
        UPDATE San_Pham_Chi_Tiet
        SET MaChatLieu =?, MaMauSac =?, MaThuongHieu =?, MaKichThuoc =?, MaXuatXu =?, 
            SoLuong =?, DonGia =?, TrangThai =?
        WHERE MaSanPham = ?
    """;
        try (Connection con = DbConnect.getConnection()) {

            try (PreparedStatement ps2 = con.prepareStatement(sqlSanPhamChiTiet)) {
                ps2.setObject(1, addSanPham.getMaChatLieu());
                ps2.setObject(2, addSanPham.getMaMauSac());
                ps2.setObject(3, addSanPham.getMaThuongHieu());
                ps2.setObject(4, addSanPham.getMaKichThuoc());
                ps2.setObject(5, addSanPham.getMaXuatXu());
                ps2.setObject(6, addSanPham.getSoLuong());
                ps2.setObject(7, addSanPham.getDonGia());
                ps2.setObject(8, addSanPham.getTrangThai());
                ps2.setObject(9, maSanPham);
                check2 = ps2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check2 > 0;
    }

    public boolean deleteProduct(String maSanPham) {
        int check1 = 0, check2 = 0, check3 = 0;

        String sqlDeleteHoaDonChiTiet = """
        DELETE FROM Hoa_Don_Chi_Tiet
        WHERE MaSanPhamChiTiet IN (SELECT MaSanPhamChiTiet FROM San_Pham_Chi_Tiet WHERE MaSanPham = ?)
    """;

        String sqlDeleteSanPhamChiTiet = """
        DELETE FROM San_Pham_Chi_Tiet WHERE MaSanPham = ?
    """;

        String sqlDeleteSanPham = """
        DELETE FROM San_Pham WHERE MaSanPham = ?
    """;

        try (Connection con = DbConnect.getConnection()) {
            try (PreparedStatement ps1 = con.prepareStatement(sqlDeleteHoaDonChiTiet)) {
                ps1.setString(1, maSanPham);
                check1 = ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = con.prepareStatement(sqlDeleteSanPhamChiTiet)) {
                ps2.setString(1, maSanPham);
                check2 = ps2.executeUpdate();
            }
            try (PreparedStatement ps3 = con.prepareStatement(sqlDeleteSanPham)) {
                ps3.setString(1, maSanPham);
                check3 = ps3.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check1 >= 0 && check2 >= 0 && check3 > 0;
    }

//    public ArrayList<SanPham> timKiemSanPham(String keyword) {
//        ArrayList<SanPham> sanPhamList = new ArrayList<>();
//        String sql = """
//                 SELECT 
//                     spct.MaSanPhamChiTiet, 
//                     sp.TenSanPham, 
//                     spct.DonGia, 
//                     spct.SoLuong, 
//                     spct.TrangThai,
//                     th.MaThuongHieu, th.TenThuongHieu, 
//                     cl.MaChatLieu, cl.TenChatLieu, 
//                     ms.MaMauSac, ms.TenMauSac, 
//                     kt.MaKichThuoc, kt.TenKichThuoc, 
//                     xx.MaXuatXu, xx.TenXuatXu
//                 FROM San_Pham_Chi_Tiet spct
//                 JOIN San_Pham sp ON sp.MaSanPham = spct.MaSanPham  
//                 JOIN Thuong_Hieu th ON spct.MaThuongHieu = th.MaThuongHieu  
//                 JOIN Chat_Lieu cl ON spct.MaChatLieu = cl.MaChatLieu  
//                 JOIN Mau_Sac ms ON spct.MaMauSac = ms.MaMauSac  
//                 JOIN Kich_Thuoc kt ON spct.MaKichThuoc = kt.MaKichThuoc  
//                 JOIN Xuat_Xu xx ON spct.MaXuatXu = xx.MaXuatXu
//                 WHERE (spct.MaSanPham LIKE ? OR sp.TenSanPham LIKE ? OR spct.DonGia LIKE ? OR ? = '')
//       """;
//        try (Connection connection = DbConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, "%" + keyword + "%");
//            ps.setString(2, "%" + keyword + "%");
//            ps.setString(3, "%" + keyword + "%");
//            ps.setString(4, keyword);
//
//            try (ResultSet resultSet = ps.executeQuery()) {
//                while (resultSet.next()) {
//                    SanPham sanPham = new SanPham();
//                    sanPham.setMaSanPhamCT(resultSet.getString("MaSanPhamChiTiet"));
//                    sanPham.setTenSanPham(resultSet.getString("TenSanPham"));
//                    sanPham.setDonGia(resultSet.getBigDecimal("DonGia"));
//                    sanPham.setSoLuong(resultSet.getInt("SoLuong"));
//                    sanPham.setTrangThai(resultSet.getByte("TrangThai"));
//                    sanPham.setMaThuongHieu(resultSet.getString("MaThuongHieu"));
//                    sanPham.setTenThuongHieu(resultSet.getString("TenThuongHieu"));
//                    sanPham.setMaChatLieu(resultSet.getString("MaChatLieu"));
//                    sanPham.setTenChatLieu(resultSet.getString("TenChatLieu"));
//                    sanPham.setMaMauSac(resultSet.getString("MaMauSac"));
//                    sanPham.setTenMauSac(resultSet.getString("TenMauSac"));
//                    sanPham.setMaKichThuoc(resultSet.getString("MaKichThuoc"));
//                    sanPham.setTenKichThuoc(resultSet.getString("TenKichThuoc"));
//                    sanPham.setMaXuatXu(resultSet.getString("MaXuatXu"));
//                    sanPham.setTenXuatXu(resultSet.getString("TenXuatXu"));
//                    sanPhamList.add(sanPham);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return sanPhamList;
//    }
    public ArrayList<SanPham> timKiemSanPham(String keyword) {
        ArrayList<SanPham> sanPhamList = new ArrayList<>();
        String sql = """
                 SELECT 
                     spct.MaSanPhamChiTiet, 
                     sp.TenSanPham, 
                     spct.DonGia, 
                     spct.SoLuong, 
                     spct.TrangThai,
                     th.MaThuongHieu, th.TenThuongHieu, 
                     cl.MaChatLieu, cl.TenChatLieu, 
                     ms.MaMauSac, ms.TenMauSac, 
                     kt.MaKichThuoc, kt.TenKichThuoc, 
                     xx.MaXuatXu, xx.TenXuatXu
                 FROM San_Pham_Chi_Tiet spct
                 JOIN San_Pham sp ON sp.MaSanPham = spct.MaSanPham  
                 JOIN Thuong_Hieu th ON spct.MaThuongHieu = th.MaThuongHieu  
                 JOIN Chat_Lieu cl ON spct.MaChatLieu = cl.MaChatLieu  
                 JOIN Mau_Sac ms ON spct.MaMauSac = ms.MaMauSac  
                 JOIN Kich_Thuoc kt ON spct.MaKichThuoc = kt.MaKichThuoc  
                 JOIN Xuat_Xu xx ON spct.MaXuatXu = xx.MaXuatXu
                 WHERE spct.MaSanPham LIKE ? 
                    OR sp.TenSanPham LIKE ? 
                    OR spct.DonGia LIKE ? 
                    OR spct.SoLuong LIKE ?
                    OR th.TenThuongHieu LIKE ?
                    OR cl.TenChatLieu LIKE ?
                    OR ms.TenMauSac LIKE ?
                    OR kt.TenKichThuoc LIKE ?
                    OR xx.TenXuatXu LIKE ?
       """;
        try (Connection connection = DbConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, "%" + keyword + "%");
            ps.setString(5, "%" + keyword + "%");
            ps.setString(6, "%" + keyword + "%");
            ps.setString(7, "%" + keyword + "%");
            ps.setString(8, "%" + keyword + "%");
            ps.setString(9, "%" + keyword + "%");

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    SanPham sanPham = new SanPham();
                    sanPham.setMaSanPhamCT(resultSet.getString("MaSanPhamChiTiet"));
                    sanPham.setTenSanPham(resultSet.getString("TenSanPham"));
                    sanPham.setDonGia(resultSet.getBigDecimal("DonGia"));
                    sanPham.setSoLuong(resultSet.getInt("SoLuong"));
                    sanPham.setTrangThai(resultSet.getByte("TrangThai"));
                    sanPham.setMaThuongHieu(resultSet.getString("MaThuongHieu"));
                    sanPham.setTenThuongHieu(resultSet.getString("TenThuongHieu"));
                    sanPham.setMaChatLieu(resultSet.getString("MaChatLieu"));
                    sanPham.setTenChatLieu(resultSet.getString("TenChatLieu"));
                    sanPham.setMaMauSac(resultSet.getString("MaMauSac"));
                    sanPham.setTenMauSac(resultSet.getString("TenMauSac"));
                    sanPham.setMaKichThuoc(resultSet.getString("MaKichThuoc"));
                    sanPham.setTenKichThuoc(resultSet.getString("TenKichThuoc"));
                    sanPham.setMaXuatXu(resultSet.getString("MaXuatXu"));
                    sanPham.setTenXuatXu(resultSet.getString("TenXuatXu"));
                    sanPhamList.add(sanPham);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPhamList;
    }

}
