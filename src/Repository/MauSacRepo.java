/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.MauSacModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author TONG THI NHUNG
 */
public class MauSacRepo {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<MauSacModel> getAll() {
        ArrayList<MauSacModel> list = new ArrayList<>();
        sql = "select MaMauSac,TenMauSac from Mau_Sac";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maMauSac = rs.getString(1);
                String tenMauSac = rs.getString(2);
                MauSacModel MS = new MauSacModel(maMauSac, tenMauSac);
                list.add(MS);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//thêm

    public int themMS(MauSacModel m) {
        sql = "insert into Mau_Sac(MaMauSac, TenMauSac) values (?, ?)";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, m.getMaMauSac());
            ps.setObject(2, m.getTenMauSac());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean kiemTraTonTai(String maMauSac, String tenMauSac) {
        String sql = "SELECT COUNT(*) FROM Mau_Sac WHERE MaMauSac = ? OR TenMauSac = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMauSac);
            ps.setString(2, tenMauSac);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu số lượng > 0 thì đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//sửa
    public int suaMS(MauSacModel m) {
        String sql = "UPDATE Mau_Sac SET TenMauSac = ? WHERE MaMauSac = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTenMauSac()); // Cập nhật tên màu sắc
            ps.setString(2, m.getMaMauSac()); // Điều kiện theo mã màu sắc

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated; // Trả về số dòng bị ảnh hưởng

        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Nếu lỗi, trả về 0
        }
    }

    public int xoaMS(String maMauSac) {
        String sql = "DELETE FROM Mau_Sac WHERE MaMauSac = ?";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maMauSac);

            return ps.executeUpdate(); // Trả về số dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
