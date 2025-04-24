/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;

import Model.HoaDonBanHang;
import Model.SanPham;
import Model.SanPhamChiTiet;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author TONG THI NHUNG
 */
public class BanHangRepository {
    public ArrayList<SanPhamChiTiet> getAll(){
        ArrayList<SanPhamChiTiet> ls = new ArrayList<>();
        String sql = """
                     SELECT sp.ID, spct.MaSanPhamChiTiet, sp.TenSanPham, spct.SoLuong, spct.DonGia, ms.TenMauSac, kc.TenKichThuoc, th.TenThuongHieu, cl.TenChatLieu, xx.TenXuatXu,spct.TrangThai 
                     FROM San_Pham_Chi_Tiet spct
                     LEFT JOIN San_Pham sp ON spct.MaSanPham = sp.MaSanPham
                     LEFT JOIN Mau_Sac ms ON spct.MaMauSac = ms.MaMauSac
                     LEFT JOIN Kich_Thuoc kc ON spct.MaKichThuoc = kc.MaKichThuoc
                     LEFT JOIN Xuat_Xu xx ON spct.MaXuatXu = xx.MaXuatXu
                     LEFT JOIN Chat_Lieu cl ON spct.MaChatLieu = cl.MaChatLieu
                     LEFT JOIN Thuong_Hieu th ON spct.MaThuongHieu = th.MaThuongHieu
                     WHERE spct.TrangThai = 1 and spct.SoLuong > 0
                     OR sp.ID IS NULL
                  """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SanPhamChiTiet sp = new SanPhamChiTiet();
                sp.setId(rs.getLong("id"));
                sp.setMaSanPham(rs.getString("MaSanPhamChiTiet"));
                sp.setTenSanPham(rs.getString("TenSanPham"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setGiaBan(rs.getFloat("DonGia"));
                sp.setMauSac(rs.getString("TenMauSac"));
                sp.setKichCo(rs.getString("TenKichThuoc"));
                sp.setThuongHieu(rs.getString("TenThuongHieu"));
                sp.setChatLieu(rs.getString("TenChatLieu"));
                sp.setXuatXu(rs.getString("TenXuatXu"));
                sp.setTrangThai(rs.getByte("TrangThai"));
                ls.add(sp);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    public ArrayList<SanPhamChiTiet> timKiemSanPhamm(String keyword, String thuongHieu) {
    ArrayList<SanPhamChiTiet> ls = new ArrayList<>();
    String sql = """
                 SELECT sp.ID, spct.MaSanPhamChiTiet, sp.TenSanPham, spct.SoLuong, spct.DonGia, ms.TenMauSac, kc.TenKichThuoc, th.TenThuongHieu, cl.TenChatLieu, xx.TenXuatXu,spct.TrangThai 
                                      FROM San_Pham_Chi_Tiet spct
                                      LEFT JOIN San_Pham sp ON spct.MaSanPham = sp.MaSanPham
                                      LEFT JOIN Mau_Sac ms ON spct.MaMauSac = ms.MaMauSac
                                      LEFT JOIN Kich_Thuoc kc ON spct.MaKichThuoc = kc.MaKichThuoc
                                      LEFT JOIN Xuat_Xu xx ON spct.MaXuatXu = xx.MaXuatXu
                                      LEFT JOIN Chat_Lieu cl ON spct.MaChatLieu = cl.MaChatLieu
                                      LEFT JOIN Thuong_Hieu th ON spct.MaThuongHieu = th.MaThuongHieu
                                      WHERE spct.TrangThai = 1 and spct.SoLuong > 0 and
                                      (sp.MaSanPham LIKE ? OR sp.TenSanPham LIKE ? OR spct.DonGia LIKE ? OR ? = '' )
                           AND (th.MaThuongHieu LIKE ? OR ? = '')
                 OR sp.ID IS NULL
                 """;
    try (Connection con = DbConnect.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
         ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ps.setString(3, "%" + keyword + "%");
        ps.setString(4, keyword); 
        ps.setString(5,"%" + thuongHieu + "%");
        ps.setString(6, thuongHieu);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SanPhamChiTiet sp = new SanPhamChiTiet();
            sp.setId(rs.getLong("ID"));
            sp.setMaSanPham(rs.getString("MaSanPhamChiTiet"));
            sp.setTenSanPham(rs.getString("TenSanPham"));
            sp.setSoLuong(rs.getInt("SoLuong"));
            sp.setGiaBan(rs.getFloat("DonGia"));
            sp.setMauSac(rs.getString("TenMauSac"));
            sp.setKichCo(rs.getString("TenKichThuoc"));
            sp.setThuongHieu(rs.getString("TenThuongHieu"));
            sp.setChatLieu(rs.getString("TenChatLieu"));
            sp.setXuatXu(rs.getString("TenXuatXu"));
            ls.add(sp);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return ls;
}
    public ArrayList<HoaDonBanHang> getAllHoaDon(){
        ArrayList<HoaDonBanHang> ls = new ArrayList<>();
        String sql ="""
                    select hd.MaHoaDon, nv.TenNhanVien, kh.TenKhachHang, hd.NgayTao, hd.TrangThai
                    from Hoa_Don hd
                    join Nhan_Vien nv on hd.MaNhanVien = nv.MaNhanVien
                    join Khach_Hang kh on hd.MaKhachHang = kh.MaKhachHang
                    where hd.TrangThai = 0 
                    """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                HoaDonBanHang sc = new HoaDonBanHang();
                sc.setMaHoaDon(rs.getString("MaHoaDon"));
                sc.setMaNhanVien(rs.getString("TenNhanVien"));
                sc.setMaKhachHang(rs.getString("TenKhachHang"));
                sc.setNgayTao(rs.getDate("NgayTao"));
                sc.setTrangThai(rs.getInt("TrangThai"));
                ls.add(sc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }  
}
