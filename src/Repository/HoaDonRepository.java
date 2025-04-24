package Repository;

import Model.HoaDon;
import Jdbc.DbConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonRepository {

    // Phương thức lấy toàn bộ thông tin hóa đơn cùng chi tiết khách hàng (JOIN với Khach_Hang)
    public List<HoaDon> getAllWithDetails() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String query = "SELECT hd.ID, hd.MaHoaDon, hd.MaNhanVien, hd.MaKhachHang, kh.TenKhachHang, kh.DiaChi, kh.SoDienThoai, " +
                       "hd.MaVoucher, hd.NgayTao, hd.TongTien, hd.TrangThai " +
                       "FROM Hoa_Don hd " +
                       "LEFT JOIN Khach_Hang kh ON hd.MaKhachHang = kh.MaKhachHang";

        try (Connection connection = DbConnect.getConnection(); // Kết nối database
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getInt("ID"));
                hoaDon.setMaHoaDon(rs.getString("MaHoaDon"));
                hoaDon.setMaNhanVien(rs.getString("MaNhanVien"));
                hoaDon.setMaKhachHang(rs.getString("MaKhachHang"));
                hoaDon.setTenKhachHang(rs.getString("TenKhachHang"));
                hoaDon.setDiaChi(rs.getString("DiaChi"));
                hoaDon.setSoDienThoai(rs.getString("SoDienThoai"));
                hoaDon.setMaVoucher(rs.getString("MaVoucher"));
                hoaDon.setNgayTao(rs.getDate("NgayTao"));
                hoaDon.setTongTien(rs.getBigDecimal("TongTien"));
                hoaDon.setTrangThai(rs.getByte("TrangThai"));

                hoaDonList.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console nếu có vấn đề
        }
        return hoaDonList; // Trả về danh sách hóa đơn
    }
}