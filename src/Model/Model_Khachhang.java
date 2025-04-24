/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.*;
/**
 *
 * @author Asus
 */
public class Model_Khachhang {
    
    private String maKH;
    private String tenKH;
    private String emailKH;
    private String diaChiKH;
    private String sdtKH;
    private String gioiTinhKH;
     private boolean trangThai;

    public Model_Khachhang() {
    }

    public Model_Khachhang(String maKH, String tenKH, String emailKH, String diaChiKH, String sdtKH, String gioiTinhKH, boolean trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.emailKH = emailKH;
        this.diaChiKH = diaChiKH;
        this.sdtKH = sdtKH;
        this.gioiTinhKH = gioiTinhKH;
        this.trangThai = trangThai;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getEmailKH() {
        return emailKH;
    }

    public void setEmailKH(String emailKH) {
        this.emailKH = emailKH;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public String getGioiTinhKH() {
        return gioiTinhKH;
    }

    public void setGioiTinhKH(String gioiTinhKH) {
        this.gioiTinhKH = gioiTinhKH;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

 
    public Object[] toDaTaRow(){
       return new  Object[]{
        this.getMaKH(),this.getTenKH(),this.getEmailKH(),this.getDiaChiKH(),this.getSdtKH(),this.getGioiTinhKH(),this.isTrangThai() ? "Hoạt động" : "Không hoạt động"
    };
    }
     public Object[] toDaTaRow1(){
       return new  Object[]{
           this.getMaKH(),this.getTenKH(),this.getSdtKH()
    };
    }
}
