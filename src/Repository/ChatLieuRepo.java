/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.ChatLieuModel;
import Model.MauSacModel;
import Model.XuatXuModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author TONG THI NHUNG
 */
public class ChatLieuRepo {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<ChatLieuModel> getAll() {
        ArrayList<ChatLieuModel> list = new ArrayList<>();
        sql = "select MaChatLieu, TenChatLieu from Chat_Lieu";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maChatLieu = rs.getString(1);
                String tenChatLieu = rs.getString(2);
                ChatLieuModel CL = new ChatLieuModel(maChatLieu, tenChatLieu);
                list.add(CL);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //thêm

    public int themCL(ChatLieuModel m) {
        sql = "insert into Chat_Lieu(MaChatLieu, TenChatLieu) values (?, ?)";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, m.getMaChatLieu());
            ps.setObject(2, m.getTenChatLieu());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean kiemTraTonTai3(String MaChatLieu, String TenChatLieu) {
        String sql = "SELECT COUNT(*) FROM Chat_Lieu WHERE MaChatLieu = ? OR TenChatLieu = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, MaChatLieu);
            ps.setString(2, TenChatLieu);

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
//
    public int suaCL(ChatLieuModel m) {
        String sql = "UPDATE Chat_Lieu SET tenChatLieu = ? WHERE maChatLieu = ?";

        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTenChatLieu()); // Cập nhật tên màu sắc
            ps.setString(2, m.getMaChatLieu()); // Điều kiện theo mã màu sắc

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated; // Trả về số dòng bị ảnh hưởng

        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Nếu lỗi, trả về 0
        }
    }

    public int xoaCL(String maChatLieu) {
        String sql = "DELETE FROM Chat_Lieu WHERE MaChatLieu = ?";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maChatLieu);

            return ps.executeUpdate(); // Trả về số dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
