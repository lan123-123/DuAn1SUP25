/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.ThuocTinh;

/**
 *
 * @author doann
 */
public class KichThuoc {
    private int id;
    private String maKichThuoc;
    private String tenKichThuoc;

    public KichThuoc() {
    }

    public KichThuoc(int id, String maKichThuoc, String tenKichThuoc) {
        this.id = id;
        this.maKichThuoc = maKichThuoc;
        this.tenKichThuoc = tenKichThuoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKichThuoc() {
        return maKichThuoc;
    }

    public void setMaKichThuoc(String maKichThuoc) {
        this.maKichThuoc = maKichThuoc;
    }

    public String getTenKichThuoc() {
        return tenKichThuoc;
    }

    public void setTenKichThuoc(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }
}
