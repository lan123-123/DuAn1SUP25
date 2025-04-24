/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.KhachHang;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author TONG THI NHUNG
 */
public class KhachHangRepository {
    public ArrayList<KhachHang> getAll(){
        ArrayList<KhachHang> kh = new ArrayList<>();
        String sql ="""
                    select * from Khach_hang
                    """;
        try (Connection con = DbConnect.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                KhachHang spp = new KhachHang();
                 spp.setMaKhachHang(rs.getString("MaKhachHang"));
                 spp.setTenKhachHang(rs.getString("TenKhachHang"));
                 spp.setSoDienThoai(rs.getString("SoDienThoai"));
                 kh.add(spp);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
    public static void main(String[] args) {
        KhachHangRepository kh = new KhachHangRepository();
        System.out.println(kh.getAll());
    }
}
