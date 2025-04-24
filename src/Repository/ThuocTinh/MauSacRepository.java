/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.ThuocTinh;

import Jdbc.DbConnect;
import Model.ThuocTinh.MauSac;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author doann
 */
public class MauSacRepository {
    public ArrayList<MauSac> getAll(){
        ArrayList<MauSac> ls = new ArrayList<>();
       String sql = """
                    select * from mau_sac 
                    """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                MauSac ms = new MauSac();
                ms.setId(rs.getInt("ID"));
                ms.setMaMauSac(rs.getString("MaMauSac"));
                ms.setTenMauSac(rs.getString("TenMauSac"));
                ls.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
