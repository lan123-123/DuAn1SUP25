/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author doann
 */
public class HoaDonChiTietBanHang {
    private String hoaDonChiTiet;
    private String tenSanPham;
    private int soLuong;
    private float donGia;
    private float thanhTien;

    public HoaDonChiTietBanHang() {
    }

    public HoaDonChiTietBanHang(String hoaDonChiTiet, String tenSanPham, int soLuong, float donGia, float thanhTien) {
        this.hoaDonChiTiet = hoaDonChiTiet;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public String getHoaDonChiTiet() {
        return hoaDonChiTiet;
    }

    public void setHoaDonChiTiet(String hoaDonChiTiet) {
        this.hoaDonChiTiet = hoaDonChiTiet;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }
    
}
