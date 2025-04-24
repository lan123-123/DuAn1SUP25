/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.ThuocTinh;

import Jdbc.DbConnect;
import Model.ThuocTinh.MauSac;
import Model.ThuocTinh.ThuongHieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author doann
 */
public class ThuongHieuRepository {
     public ArrayList<ThuongHieu> getAll(){
        ArrayList<ThuongHieu> ls = new ArrayList<>();
       String sql = """
                    select * from Thuong_hieu 
                    """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ThuongHieu ms = new ThuongHieu();
                ms.setId(rs.getInt("ID"));
                ms.setMaThuongHieu(rs.getString("MaThuongHieu"));
                ms.setTenThuongHieu(rs.getString("TenThuongHieu"));
                ls.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
