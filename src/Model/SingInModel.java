/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TONG THI NHUNG
 */
public class SingInModel {
     private int maUser;
     private String TaiKhoan;
     private String MatKhau;
     private String trangThai;
     private String chucVu;

    public SingInModel() {
    }

    public SingInModel(int maUser, String TaiKhoan, String MatKhau, String trangThai, String chucVu) {
        this.maUser = maUser;
        this.TaiKhoan = TaiKhoan;
        this.MatKhau = MatKhau;
        this.trangThai = trangThai;
        this.chucVu = chucVu;
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
     

     
  }
