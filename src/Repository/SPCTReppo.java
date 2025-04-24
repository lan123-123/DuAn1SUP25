/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Jdbc.DbConnect;
import Model.SPCTModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author TONG THI NHUNG
 */
public class SPCTReppo {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<SPCTModel> getAll() {
        ArrayList<SPCTModel> list = new ArrayList<>();
        String sql = "SELECT spct.ID, spct.MaSanPhamChiTiet, sp.MaSanPham, sp.TenSanPham, \n"
                + "       ms.TenMauSac, cl.TenChatLieu, kt.TenKichThuoc, xx.TenXuatXu, \n"
                + "       th.TenThuongHieu, spct.SoLuong, spct.DonGia, spct.TrangThai\n"
                + "FROM dbo.San_Pham_Chi_Tiet spct\n"
                + "LEFT JOIN dbo.San_Pham sp ON sp.MaSanPham = spct.MaSanPham\n"
                + "LEFT JOIN dbo.Mau_Sac ms ON ms.MaMauSac = spct.MaMauSac\n"
                + "LEFT JOIN dbo.Chat_Lieu cl ON cl.MaChatLieu = spct.MaChatLieu\n"
                + "LEFT JOIN dbo.Kich_Thuoc kt ON kt.MaKichThuoc = spct.MaKichThuoc\n"
                + "LEFT JOIN dbo.Xuat_Xu xx ON xx.MaXuatXu = spct.MaXuatXu\n"
                + "LEFT JOIN dbo.Thuong_Hieu th ON th.MaThuongHieu = sp.MaThuongHieu;";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maSPCT = rs.getString(2);
                String maSP = rs.getString(3);
                String tenSanPham = rs.getString(4);
                String tenMauSac = rs.getString(5);
                String tenChatLieu = rs.getString(6);
                String tenKichThuoc = rs.getString(7);
                String tenXuatSu = rs.getString(8);
                String tenThuongHieu = rs.getString(9);
                Integer soLuong = rs.getInt(10);
                double donGia = rs.getDouble(11);
                boolean trangThai = rs.getBoolean(12);

                SPCTModel SPCT = new SPCTModel(maSPCT, maSP, tenSanPham, tenMauSac, tenChatLieu,
                        tenKichThuoc, tenXuatSu, tenThuongHieu, soLuong, donGia, trangThai);
                list.add(SPCT);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //cbb 
    public ArrayList<SPCTModel> getCBBTH() {
        ArrayList<SPCTModel> list = new ArrayList<>();
        sql = "SELECT dbo.Thuong_Hieu.TenThuongHieu\n"
                + "FROM     dbo.San_Pham_Chi_Tiet INNER JOIN\n"
                + "                  dbo.Thuong_Hieu ON dbo.San_Pham_Chi_Tiet.ID = dbo.Thuong_Hieu.ID";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String ThuongHieu;
                SPCTModel modelSPCT = new SPCTModel();
                modelSPCT.setTenThuongHieu(rs.getString(1));
                list.add(modelSPCT);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SPCTModel> getCBBTSP() {
        ArrayList<SPCTModel> list = new ArrayList<>();
        sql = "select TenSanPham from San_Pham where TrangThai = 1";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String TenSanPham;
                TenSanPham = rs.getString(1);
                SPCTModel cT = new SPCTModel(TenSanPham);
                list.add(cT);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SPCTModel> getCBBMS() {
        ArrayList<SPCTModel> list = new ArrayList<>();
        String sql = "SELECT TenMauSac FROM Mau_Sac";
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String TenSanPham;
                TenSanPham = rs.getString(1);
                SPCTModel cT = new SPCTModel(TenSanPham);
                list.add(cT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public ArrayList<SPCTModel> getCBBCL() {
        ArrayList<SPCTModel> list = new ArrayList<>();
        sql = "select TenChatLieu from Chat_Lieu";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String TenSanPham;
                TenSanPham = rs.getString(1);
                SPCTModel cT = new SPCTModel(TenSanPham);
                list.add(cT);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SPCTModel> getCBBKT() {
        ArrayList<SPCTModel> list = new ArrayList<>();
        sql = "select TenKichThuoc from Kich_Thuoc";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String TenSanPham;
                TenSanPham = rs.getString(1);
                SPCTModel cT = new SPCTModel(TenSanPham);
                list.add(cT);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SPCTModel> getCBBXX() {
        ArrayList<SPCTModel> list = new ArrayList<>();
        sql = "	select TenXuatXu from Xuat_Xu";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String TenSanPham;
                TenSanPham = rs.getString(1);
                SPCTModel cT = new SPCTModel(TenSanPham);
                list.add(cT);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    //thêm sản phẩm chi tiết
     public int them(SPCTModel spct) {
        sql = "INSERT INTO San_Pham_Chi_Tiet(MaSanPham, MaChatLieu, MaMauSac, MaKichThuoc, MaXuatXu, SoLuong, DonGia, TrangThai) \n"
                + "	VALUES(?,?,?,?,?,?,?,?)";
        try {
            con = DbConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, spct.getMaSanPham());
            ps.setObject(2, spct.getMaChatLieu());
            ps.setObject(3, spct.getMaMauSac());
            ps.setObject(4, spct.getMaKichThuoc());
            ps.setObject(5, spct.getMaXuatXu());
            ps.setObject(6, spct.getSoLuong());
            ps.setObject(7, spct.getDonGia());
            ps.setObject(8, 1);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
     //get id
     public int getIDSP(String tenSP) {
        sql = "SELECT ID FROM San_Pham WHERE TenSanPham = ?";
        int IDSP = 0;
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    IDSP = rs.getInt("ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return IDSP;
    }

    public int getIDMS(String tenMS) {
        sql = "	SELECT ID FROM Mau_Sac WHERE TenMauSac = ?";
        int IDMS = 0;
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenMS);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    IDMS = rs.getInt("ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return IDMS;
    }

    public int getIDCL(String tenCL) {
        sql = "	SELECT ID FROM Chat_Lieu WHERE TenChatLieu = ?";
        int IDCL = 0;
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenCL);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    IDCL = rs.getInt("ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return IDCL;
    }

    public int getIDKT(String tenKT) {
        sql = "	SELECT ID FROM Kich_Thuoc WHERE TenKichThuoc = ?";
        int IDKT = 0;
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenKT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    IDKT = rs.getInt("ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return IDKT;
    }

    public int getIDXX(String tenXX) {
        sql = "	SELECT ID FROM Xuat_Xu WHERE TenXuatXu = ?";
        int IDXX = 0;
        try (Connection con = DbConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenXX);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    IDXX = rs.getInt("ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return IDXX;
    }
}
