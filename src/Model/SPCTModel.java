/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TONG THI NHUNG
 */
public class SPCTModel {
   
    private int id;
    private String maSPCT;
    private String maSP;
    private String tenSanPham;
    private String tenMauSac;
    private String tenChatLieu;
    private String tenKichThuoc;
    private String tenXuatXu;
    private String tenThuongHieu;
    private int soLuong;
    private double donGia;
    private boolean trangThai;
    private int maSanPham;
    private int maChatLieu;
    private int maMauSac;
    private int maKichThuoc;
    private int maXuatXu;

    public SPCTModel() {
    }

    public SPCTModel(String maSPCT, String maSP, String tenSanPham, String tenMauSac, String tenChatLieu, String tenKichThuoc, String tenXuatXu, String tenThuongHieu, int soLuong, double donGia, boolean trangThai) {
        this.maSPCT = maSPCT;
        this.maSP = maSP;
        this.tenSanPham = tenSanPham;
        this.tenMauSac = tenMauSac;
        this.tenChatLieu = tenChatLieu;
        this.tenKichThuoc = tenKichThuoc;
        this.tenXuatXu = tenXuatXu;
        this.tenThuongHieu = tenThuongHieu;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public SPCTModel(int soLuong, double donGia, int maSanPham, int maChatLieu, int maMauSac, int maKichThuoc, int maXuatXu) {
        this.soLuong = soLuong;
        this.donGia = donGia;
//        this.trangThai = trangThai;
        this.maSanPham = maSanPham;
        this.maChatLieu = maChatLieu;
        this.maMauSac = maMauSac;
        this.maKichThuoc = maKichThuoc;
        this.maXuatXu = maXuatXu;
    }

    public SPCTModel(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    public String getTenKichThuoc() {
        return tenKichThuoc;
    }

    public void setTenKichThuoc(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaChatLieu() {
        return maChatLieu;
    }

    public void setMaChatLieu(int maChatLieu) {
        this.maChatLieu = maChatLieu;
    }

    public int getMaMauSac() {
        return maMauSac;
    }

    public void setMaMauSac(int maMauSac) {
        this.maMauSac = maMauSac;
    }

    public int getMaKichThuoc() {
        return maKichThuoc;
    }

    public void setMaKichThuoc(int maKichThuoc) {
        this.maKichThuoc = maKichThuoc;
    }

    public int getMaXuatXu() {
        return maXuatXu;
    }

    public void setMaXuatXu(int maXuatXu) {
        this.maXuatXu = maXuatXu;
    }

    public Object[] toDataRow() {
        return new Object[]{
            this.maSPCT, this.tenSanPham, this.tenMauSac, this.tenChatLieu, this.tenKichThuoc, this.tenXuatXu, this.tenThuongHieu, this.soLuong, this.donGia,
            this.trangThai == true ? "Ngừng Kinh Doanh" : "Còn Kinh Doanh"
        };
    }
    
}
