/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Date;
/**
 *
 * @author doann
 */
public class HoaDonBanHang {
    private String maHoaDon;
    private String maNhanVien;
    private String maKhachHang;
    private Date ngayTao;
    private int trangThai;

    public HoaDonBanHang() {
    }
    public HoaDonBanHang(String maHoaDon, String maNhanVien, String maKhachHang, Date ngayTao, int trangThai) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }
    public String getMaHoaDon() {
        return maHoaDon;
    }
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    public String getMaNhanVien() {
        return maNhanVien;
    }
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    public String getMaKhachHang() {
        return maKhachHang;
    }
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    public Date getNgayTao() {
        return ngayTao;
    }
    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
    public int getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
     public String mapTrangThai(int trangThai) {
        switch (trangThai) {
            case 0:
                return "Chưa thanh toán";
            case 1:
                return "Đã thanh toán";
            case 2:
                return "Bị hủy";
            default:
                return "Không xác định";
        }
    }
}
