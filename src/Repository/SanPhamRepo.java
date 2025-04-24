/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.SanPhamModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author TONG THI NHUNG
 */
public class SanPhamRepo {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    //hiển thị lên table
    public ArrayList<SanPhamModel> getList() {

        String sql = "SELECT MaSanPham, TenSanPham, SoLuong, MoTa, TrangThai FROM San_Pham";

        ArrayList<SanPhamModel> list = new ArrayList<>();
        try {
            Connection con = DbConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamModel sp = new SanPhamModel();

                sp.setMaSanPham(rs.getString(1));
                sp.setTenSanPham(rs.getString(2));
                sp.setSoLuong(rs.getLong(3));
                sp.setMoTa(rs.getString(4));
                sp.setTrangThai(rs.getBoolean(5));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //thêm sản phẩm

    public int them(SanPhamModel sp) {
        sql = "INSERT INTO San_Pham (MaSanPham, TenSanPham, SoLuong, MoTa, TrangThai) VALUES (?, ?, ?, ?, ?)";

        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sp.getMaSanPham());  // Thêm Mã Sản Phẩm
            ps.setObject(2, sp.getTenSanPham());
            ps.setObject(3, sp.getSoLuong());
            ps.setObject(4, sp.getMoTa());
            ps.setObject(5, sp.isTrangThai());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //sửa sản phẩm
    public int sua(SanPhamModel sp, String maSanPham) {
        sql = "update San_Pham set TenSanPham = ? ,SoLuong=?, MoTa = ? , TrangThai = ? where MaSanPham = ?";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sp.getTenSanPham());
            ps.setObject(2, sp.getSoLuong());
            ps.setObject(3, sp.getMoTa());
            ps.setObject(4, sp.isTrangThai());
            ps.setObject(5, maSanPham);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    public int xoa(String maSanPham) {
//        sql = "update San_Pham set TrangThai = 0 where MaSanPham = ?";
//        try {
//            con = DbConnect.getConnection();
//            ps = con.prepareStatement(sql);
//            ps.setObject(1, maSanPham);
//            return ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
    public int xoa(String maSanPham) {
        sql = "UPDATE San_Pham SET TrangThai = 0 WHERE MaSanPham = ?";
        try {
            con = DbConnect.getConnection();
            con.setAutoCommit(false); // Bắt đầu transaction

            ps = con.prepareStatement(sql);
            ps.setObject(1, maSanPham);
            int result = ps.executeUpdate();

            con.commit(); // Xác nhận thay đổi
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int khoiPhuc(String maSanPham) {
        sql = "UPDATE San_Pham SET TrangThai = 1 WHERE MaSanPham = ?";
        try {
            con = DbConnect.getConnection();
            con.setAutoCommit(false); // Bắt đầu transaction

            ps = con.prepareStatement(sql);
            ps.setObject(1, maSanPham);
            int result = ps.executeUpdate();

            con.commit(); // Xác nhận thay đổi
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean themSanPham(SanPhamModel sanPhamModel) {
        int check = 0;
        String sql = """
                     insert into San_Pham (MaSanPham, TenSanPham,SoLuong,Mota,TrangThai)
                  values(?,?,?,?,?)
                     """;
        try (Connection con = DbConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            SanPhamModel sp = new SanPhamModel();
            ps.setObject(1, sp.getMaSanPham());
            ps.setObject(2, sp.getTenSanPham());
            ps.setObject(3, sp.getSoLuong());
            ps.setObject(4, sp.getMoTa());
            ps.setObject(5, sp.isTrangThai());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<SanPhamModel> hienThiTenSanPham() {
        ArrayList<SanPhamModel> lss = new ArrayList<>();
        String sql = """
                     select MaSanPham,TenSanPham
                     from San_Pham
                     """;
        try (Connection con = DbConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamModel sp = new SanPhamModel();
                sp.setMaSanPham(rs.getString("MaSanPham"));
                sp.setTenSanPham(rs.getString("TenSanPham"));
                lss.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lss;
    }
}
