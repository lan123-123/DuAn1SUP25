/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Acer
 */
public class NhanVienModel {
    private int id;
    private String maNhanVien;
    private String tenNhanVien;
    private String gioiTinh;
    private String soDienThoai;
    private String diaChi;
    private String email;
    private String cccd;
    private String ngayVaoLam;
    private String passWord;
    private String chucVu;
    private boolean trangThai;

    public NhanVienModel() {
    }

    public NhanVienModel(int id, String maNhanVien, String tenNhanVien, String gioiTinh, String soDienThoai, String diaChi, String email, String cccd, String ngayVaoLam, String passWord, String chucVu, boolean trangThai) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.email = email;
        this.cccd = cccd;
        this.ngayVaoLam = ngayVaoLam;
        this.passWord = passWord;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }
//    public boolean isVaitro(){
//        if(trangThai == 0){
//            return false;
//        }
//        return true;
//    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
    public boolean getTrangThai(){
        return trangThai;
    }

    @Override
    public String toString() {
        return "NhanVienModel{" + "id=" + id + ", maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", gioiTinh=" + gioiTinh + ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + ", email=" + email + ", cccd=" + cccd + ", ngayVaoLam=" + ngayVaoLam + ", passWord=" + passWord + ", chucVu=" + chucVu + ", trangThai=" + trangThai + '}';
    }

    
    
    
           
}
