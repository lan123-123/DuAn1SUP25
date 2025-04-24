/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.MauSacModel;
import Model.XuatXuModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author TONG THI NHUNG
 */
public class XuatXuRepo {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<XuatXuModel> getAll() {
        ArrayList<XuatXuModel> list = new ArrayList<>();
        sql = "select maXuatXu,TenXuatXu from Xuat_Xu";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maXuatXu = rs.getString(1);
                String TenXuatXu = rs.getString(2);
                XuatXuModel XX = new XuatXuModel(TenXuatXu, maXuatXu);
                list.add(XX);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //thêm

    public int themXX(XuatXuModel m) {
        sql = "insert into Xuat_Xu(MaXuatXu, TenXuatXu) values (?, ?)";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, m.getMaXuatXu());
            ps.setObject(2, m.getTenXuatXu());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean kiemTraTonTai2(String maXuatXu, String tenXuatXu) {
        String sql = "SELECT COUNT(*) FROM Xuat_Xu WHERE maXuatXu = ? OR tenXuatXu = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maXuatXu);
            ps.setString(2, tenXuatXu);

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

    public int suaXX(XuatXuModel m) {
        String sql = "UPDATE Xuat_Xu SET TenXuatXu = ? WHERE MaXuatXu = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTenXuatXu().trim()); // Cập nhật tên xuất xứ
            ps.setString(2, m.getMaXuatXu().trim()); // Điều kiện theo mã xuất xứ

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated; // Trả về số dòng bị ảnh hưởng

        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Nếu lỗi, trả về 0
        }
    }

    public int xoaXX(String maXuatxu) {
        String sql = "DELETE FROM Xuat_Xu WHERE MaXuatXu = ?";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maXuatxu);

            return ps.executeUpdate(); // Trả về số dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
