/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.ThuocTinh;

import Jdbc.DbConnect;
import Model.ThuocTinh.ChatLieu;
import Model.ThuocTinh.MauSac;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author doann
 */
public class ChatLieuRepository {
    public ArrayList<ChatLieu> getAll(){
        ArrayList<ChatLieu> ls = new ArrayList<>();
       String sql = """
                    select * from chat_lieu 
                    """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ChatLieu ms = new ChatLieu();
                ms.setId(rs.getInt("ID"));
                ms.setMaChatLieu(rs.getString("MaChatLieu"));
                ms.setTenChatLieu(rs.getString("TenChatLieu"));
                ls.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
