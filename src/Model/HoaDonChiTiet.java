package Model;

import java.math.BigDecimal;

    public class HoaDonChiTiet {
    private int id;
    private String maHoaDon;
    private String maSanPhamChiTiet;
    private String maSanPham;  // Mã sản phẩm
    private String tenSanPham; // Tên sản phẩm
    private int soLuong;       // Số lượng sản phẩm
    private BigDecimal donGia; // Giá bán sản phẩm
    private String chatLieu;   // Chất liệu sản phẩm
    private String mauSac;     // Màu sắc sản phẩm
    private String thuongHieu; // Thương hiệu sản phẩm
    private String kichThuoc;  // Kích thước sản phẩm
    private String xuatXu;     // Xuất xứ sản phẩm
    private BigDecimal tongTien;

    // Getter và Setter cho ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho MaHoaDon
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    // Getter và Setter cho MaSanPhamChiTiet
    public String getMaSanPhamChiTiet() {
        return maSanPhamChiTiet;
    }

    public void setMaSanPhamChiTiet(String maSanPhamChiTiet) {
        this.maSanPhamChiTiet = maSanPhamChiTiet;
    }

    // Getter và Setter cho MaSanPham
    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    // Getter và Setter cho TenSanPham
    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    // Getter và Setter cho SoLuong
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    // Getter và Setter cho DonGia
    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    // Getter và Setter cho ChatLieu
    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    // Getter và Setter cho MauSac
    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    // Getter và Setter cho ThuongHieu
    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    // Getter và Setter cho KichThuoc
    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    // Getter và Setter cho XuatXu
    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
    
}