/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author doann
 */
public class AddSanPham {
// Các thuộc tính đại diện cho các cột trong bảng San_Pham_Chi_Tiet
    private String maSanPhamChiTiet;
    private String maSanPham;
    private String maChatLieu;
    private String maMauSac;
    private String maThuongHieu;
    private String maKichThuoc;
    private String maXuatXu;
    private int soLuong;
    private double donGia;   
    private int trangThai;
    private String tenSanPham;

    // Constructor không tham số

    public AddSanPham() {
    }

    public AddSanPham(String maSanPhamChiTiet, String maSanPham, String maChatLieu, String maMauSac, String maThuongHieu, String maKichThuoc, String maXuatXu, int soLuong, double donGia, int trangThai, String tenSanPham) {
        this.maSanPhamChiTiet = maSanPhamChiTiet;
        this.maSanPham = maSanPham;
        this.maChatLieu = maChatLieu;
        this.maMauSac = maMauSac;
        this.maThuongHieu = maThuongHieu;
        this.maKichThuoc = maKichThuoc;
        this.maXuatXu = maXuatXu;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
        this.tenSanPham = tenSanPham;
    }

    public String getMaSanPhamChiTiet() {
        return maSanPhamChiTiet;
    }

    public void setMaSanPhamChiTiet(String maSanPhamChiTiet) {
        this.maSanPhamChiTiet = maSanPhamChiTiet;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaChatLieu() {
        return maChatLieu;
    }

    public void setMaChatLieu(String maChatLieu) {
        this.maChatLieu = maChatLieu;
    }

    public String getMaMauSac() {
        return maMauSac;
    }

    public void setMaMauSac(String maMauSac) {
        this.maMauSac = maMauSac;
    }

    public String getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(String maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public String getMaKichThuoc() {
        return maKichThuoc;
    }

    public void setMaKichThuoc(String maKichThuoc) {
        this.maKichThuoc = maKichThuoc;
    }

    public String getMaXuatXu() {
        return maXuatXu;
    }

    public void setMaXuatXu(String maXuatXu) {
        this.maXuatXu = maXuatXu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    @Override
    public String toString() {
        return "AddSanPham{" + "maSanPhamChiTiet=" + maSanPhamChiTiet + ", maSanPham=" + maSanPham + ", maChatLieu=" + maChatLieu + ", maMauSac=" + maMauSac + ", maThuongHieu=" + maThuongHieu + ", maKichThuoc=" + maKichThuoc + ", maXuatXu=" + maXuatXu + ", soLuong=" + soLuong + ", donGia=" + donGia + ", trangThai=" + trangThai + ", tenSanPham=" + tenSanPham + '}';
    }
    

}
