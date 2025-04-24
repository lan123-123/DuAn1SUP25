package Repository;

import Model.HoaDonChiTiet;
import Jdbc.DbConnect;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietRepository {

    public List<HoaDonChiTiet> getByMaHoaDon(String maHoaDon) {
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        String query = """
                       SELECT hdc.ID, hdc.MaHoaDon, hdc.MaSanPhamChiTiet, sp.MaSanPham, hdc.SoLuong, hdc.DonGia, (hdc.SoLuong * hdc.DonGia) AS TongTien,
                                       sp.TenSanPham, spct.MaThuongHieu AS MaThuongHieu, cl.TenChatLieu AS ChatLieu, ms.TenMauSac AS MauSac,
                                       th.TenThuongHieu AS ThuongHieu, kt.TenKichThuoc AS KichThuoc, xx.TenXuatXu AS XuatXu
                                       FROM Hoa_Don_Chi_Tiet hdc
                                       JOIN San_Pham_Chi_Tiet spct ON hdc.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
                                       JOIN San_Pham sp ON spct.MaSanPham = sp.MaSanPham
                                       LEFT JOIN Chat_Lieu cl ON spct.MaChatLieu = cl.MaChatLieu
                                       LEFT JOIN Mau_Sac ms ON spct.MaMauSac = ms.MaMauSac
                                       LEFT JOIN Thuong_Hieu th ON spct.MaThuongHieu = th.MaThuongHieu
                                       LEFT JOIN Kich_Thuoc kt ON spct.MaKichThuoc = kt.MaKichThuoc
                                       LEFT JOIN Xuat_Xu xx ON spct.MaXuatXu = xx.MaXuatXu
                                      WHERE hdc.MaHoaDon = ?;
                       """;

        try (Connection connection = DbConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, maHoaDon); // Truyền mã hóa đơn vào câu truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setId(rs.getInt("ID"));
                    hoaDonChiTiet.setMaHoaDon(rs.getString("MaHoaDon"));
                    hoaDonChiTiet.setMaSanPhamChiTiet(rs.getString("MaSanPhamChiTiet"));
                    hoaDonChiTiet.setMaSanPham(rs.getString("MaSanPham")); // Lấy Mã Sản Phẩm
                    hoaDonChiTiet.setTenSanPham(rs.getString("TenSanPham"));
                    hoaDonChiTiet.setChatLieu(rs.getString("ChatLieu"));
                    hoaDonChiTiet.setMauSac(rs.getString("MauSac"));
                    hoaDonChiTiet.setThuongHieu(rs.getString("MaThuongHieu")); // Gán mã thương hiệu từ sản phẩm chi tiết
                    hoaDonChiTiet.setThuongHieu(rs.getString("ThuongHieu"));
                    hoaDonChiTiet.setKichThuoc(rs.getString("KichThuoc"));
                    hoaDonChiTiet.setXuatXu(rs.getString("XuatXu"));
                    hoaDonChiTiet.setSoLuong(rs.getInt("SoLuong"));
                    hoaDonChiTiet.setDonGia(rs.getBigDecimal("DonGia"));
                    hoaDonChiTiet.setTongTien(rs.getBigDecimal("TongTien"));
                   
                  

                    hoaDonChiTietList.add(hoaDonChiTiet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console nếu có vấn đề
        }
        return hoaDonChiTietList; // Trả về danh sách hóa đơn chi tiết
    }
}
