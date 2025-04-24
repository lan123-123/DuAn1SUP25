package Model;

import java.math.BigDecimal;
import java.util.Date;

public class HoaDon {
    private int id;
    private String maHoaDon;
    private String maNhanVien;
    private String maKhachHang;
    private String tenKhachHang; // Tên khách hàng
    private String diaChi;       // Địa chỉ khách hàng
    private String soDienThoai;  // Số điện thoại khách hàng
    private String maVoucher;    // Mã voucher
    private Date ngayTao;        // Ngày tạo hóa đơn
    private BigDecimal tongTien; // Tổng tiền
    private byte trangThai;      // Trạng thái hóa đơn

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

    // Getter và Setter cho MaNhanVien
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    // Getter và Setter cho MaKhachHang
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    // Getter và Setter cho TenKhachHang
    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    // Getter và Setter cho DiaChi
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // Getter và Setter cho SoDienThoai
    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    // Getter và Setter cho MaVoucher
    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    // Getter và Setter cho NgayTao
    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    // Getter và Setter cho TongTien
    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    // Getter và Setter cho TrangThai
    public byte getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(byte trangThai) {
        this.trangThai = trangThai;
    }
}