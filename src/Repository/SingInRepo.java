/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.SingInModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

/**
 *
 * @author TONG THI NHUNG
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SingInRepo {

    private Connection conn;

    public SingInRepo() {
        try {
            this.conn = DbConnect.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public boolean signIn(String taiKhoan, String matKhau) {
//        if (conn == null) {
//            System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
//            return false;
//        }
//
//        String sql = "SELECT * FROM Users WHERE taiKhoan = ? AND matKhau = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, taiKhoan);
//            ps.setString(2, matKhau);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next(); // Nếu có bản ghi khớp => đăng nhập thành công
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
    public SingInModel signIn(String taiKhoan, String matKhau) {
        if (conn == null) {
            System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
            return null;
        }

        String sql = "SELECT * FROM Users WHERE taiKhoan = ? AND matKhau = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, taiKhoan);
            ps.setString(2, matKhau);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SingInModel user = new SingInModel();
                    user.setMaUser(rs.getInt("maUser"));
                    user.setTaiKhoan(rs.getString("taiKhoan"));
                    user.setMatKhau(rs.getString("matKhau"));
                    user.setTrangThai(rs.getString("trangThai"));
                    user.setChucVu(rs.getString("chucVu")); // cột vaiTro cần có trong CSDL
                    return user;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
