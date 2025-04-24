/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.ThuocTinh;

import Jdbc.DbConnect;
import Model.ThuocTinh.KichThuoc;
import Model.ThuocTinh.MauSac;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author doann
 */
public class KichThuocrepository {
    public ArrayList<KichThuoc> getAll(){
        ArrayList<KichThuoc> ls = new ArrayList<>();
       String sql = """
                    select * from kich_thuoc 
                    """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                KichThuoc ms = new KichThuoc();
                ms.setId(rs.getInt("ID"));
                ms.setMaKichThuoc(rs.getString("MaKichThuoc"));
                ms.setTenKichThuoc( rs.getString("TenKichThuoc"));
                ls.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
