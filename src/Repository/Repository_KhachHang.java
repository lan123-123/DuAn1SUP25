/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Jdbc.DbConnect;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class Repository_KhachHang {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public Repository_KhachHang() {
        con = DbConnect.getConnection();
    }

    public ArrayList<Model_Khachhang> getAll() {
        ArrayList<Model_Khachhang> List = new ArrayList<>();
        sql = "select MaKhachHang,TenKhachHang,Email,DiaChi,SoDienThoai,GioiTinh,TrangThai from Khach_Hang";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                String maKH = rs.getString(1);
                String tenKH = rs.getString(2);
                String emailKH = rs.getString(3);
                String diaChiKH = rs.getString(4);
                String sdtKH = rs.getString(5);
                String goiTinhKH = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                List.add(new Model_Khachhang(maKH, tenKH, emailKH, diaChiKH, sdtKH, goiTinhKH, trangThai));
            }
            return List;
        } catch (Exception e) {
            return null;
        }
    }

    public int addKH(Model_Khachhang u) {
        sql = "insert into Khach_Hang (MaKhachHang,TenKhachHang,Email,DiaChi,SoDienThoai,GioiTinh,TrangThai)\n"
                + "values (?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getMaKH());
            ps.setString(2, u.getTenKH());
            ps.setString(3, u.getEmailKH());
            ps.setString(4, u.getDiaChiKH());
            ps.setString(5, u.getSdtKH());
            ps.setString(6, u.getGioiTinhKH());
            ps.setBoolean(7, u.isTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            return 0;
        }

    }

    public int remove(Model_Khachhang u) {
        sql = "delete Khach_Hang\n"
                + "where MaKhachHang = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getMaKH());

            return ps.executeUpdate();
        } catch (Exception e) {
            return 0;
        }

    }

    public int upDaTe(Model_Khachhang ul) {
        sql = "update Khach_Hang set TenKhachHang = ?, Email = ?, DiaChi = ?, SoDienThoai = ?,GioiTinh = ?, TrangThai = ?\n"
                + "where MaKhachHang = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, ul.getTenKH());
            ps.setString(2, ul.getEmailKH());
            ps.setString(3, ul.getDiaChiKH());
            ps.setString(4, ul.getSdtKH());
            ps.setString(5, ul.getGioiTinhKH());
            ps.setBoolean(6, ul.isTrangThai());
            ps.setString(7, ul.getMaKH());
            return ps.executeUpdate();
        } catch (Exception e) {
            return 0;
        }

    }

    public Model_Khachhang getByMaKhachHang(String key) {
        Model_Khachhang khachHang = null;
        sql = "select MaKhachHang,TenKhachHang,Email,DiaChi,SoDienThoai,GioiTinh,TrangThai from Khach_Hang where MaKhachHang LIKE ? OR TenKhachHang LIKE ? OR SoDienThoai LIKE ?";
        try {
            ps = con.prepareStatement(sql);
            String searchPattern = "%" + key + "%";
            ps.setString(1, searchPattern); // Tìm theo mã khách hàng
            ps.setString(2, searchPattern); // Tìm theo tên
            ps.setString(3, searchPattern);
            // ps.setString(1, maKhachHang); // Truyền giá trị MaKhachHang vào câu truy vấn
            rs = ps.executeQuery();
            if (rs.next()) { // Chỉ lấy một bản ghi (vì MaKhachHang là UNIQUE)
                String maKH = rs.getString(1);
                String tenKH = rs.getString(2);
                String emailKH = rs.getString(3);
                String diaChiKH = rs.getString(4);
                String sdtKH = rs.getString(5);
                String gioiTinhKH = rs.getString(6);
                boolean trangThai = rs.getBoolean(7);
                khachHang = new Model_Khachhang(maKH, tenKH, emailKH, diaChiKH, sdtKH, gioiTinhKH, trangThai);
            }
            return khachHang;
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi để debug
            return null;
        }

    }

    public boolean checkEmail(String email, java.awt.Component parentComponent) {

        // Kết nối cơ sở dữ liệu
        sql = "select COUNT(*) from Khach_Hang where Email = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(parentComponent, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return true;
                } else {
                    return false;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean checkSDT(String soDT, java.awt.Component parentComponent) {

        // Kết nối cơ sở dữ liệu
        sql = "select COUNT(*) from Khach_Hang Where SoDienThoai = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, soDT);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(parentComponent, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return true;
                } else {
                    return false;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean checkMaKH(String ma, java.awt.Component parentComponent) {

        // Kết nối cơ sở dữ liệu
        sql = "select COUNT(*) from Khach_Hang where MaKhachHang = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, ma);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(parentComponent, "Mã khách hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return true;
                } else {
                    return false;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public int getMaxMaKhachHang() {
        int maxId = 0;
        sql = "select MAX(CAST(SUBSTRING(MaKhachHang, 3, LEN(MaKhachHang)) AS int)) from Khach_Hang";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }
}
