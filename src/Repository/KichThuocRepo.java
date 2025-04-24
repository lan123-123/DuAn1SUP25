/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.ChatLieuModel;
import Model.KichThuocModel;
import Model.MauSacModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author TONG THI NHUNG
 */
public class KichThuocRepo {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<KichThuocModel> getAll() {
        ArrayList<KichThuocModel> list = new ArrayList<>();
        sql = "select MaKichThuoc, TenKichThuoc from Kich_Thuoc";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maKichThuoc = rs.getString(1);
                String tenkichThuoc = rs.getString(2);
                KichThuocModel KT = new KichThuocModel(maKichThuoc, tenkichThuoc);
                list.add(KT);
            }
          
        } catch (Exception e) {
            e.printStackTrace();
      
        } 
        return list;
    }
    public int themKT(KichThuocModel m) {
        sql = "insert into Kich_Thuoc(MaKichThuoc,TenKichThuoc) values (?,?)";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, m.getMaKichThuoc());
            ps.setObject(2, m.getTenKichThuoc());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean kiemTraTonTai4(String maKichThuoc, String tenKichThuoc) {
        String sql = "SELECT COUNT(*) FROM Kich_Thuoc WHERE MaKichThuoc = ? OR TenKichThuoc = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maKichThuoc);
            ps.setString(2, tenKichThuoc);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int suaKT(KichThuocModel m) {
        String sql = "UPDATE Kich_Thuoc SET TenKichThuoc = ? WHERE MaKichThuoc = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTenKichThuoc()); 
            ps.setString(2, m.getMaKichThuoc()); 

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated; 

        } catch (SQLException e) {
            e.printStackTrace();
            return 0; 
        }
    }

    public int xoaKT(String MaKichThuoc) {
        String sql = "DELETE FROM Kich_Thuoc WHERE MaKichThuoc = ?";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, MaKichThuoc);

            return ps.executeUpdate(); // Trả về số dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
