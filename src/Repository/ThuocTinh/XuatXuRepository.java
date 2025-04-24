/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository.ThuocTinh;

import Jdbc.DbConnect;
import Model.ThuocTinh.MauSac;
import Model.ThuocTinh.XuatXu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author doann
 */
public class XuatXuRepository {
    public ArrayList<XuatXu> getAll(){
        ArrayList<XuatXu> ls = new ArrayList<>();
       String sql = """
                   select * from Xuat_Xu
                    """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                XuatXu xx = new XuatXu();
                xx.setId(rs.getInt("ID"));
                xx.setMaXuatXu(rs.getString("MaXuatXu"));
                xx.setTenXuatXu(rs.getString("TenXuatXu"));
                ls.add(xx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
}
