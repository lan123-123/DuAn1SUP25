/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.ThuocTinh;

/**
 *
 * @author doann
 */
public class XuatXu {
    private int id;
    private String maXuatXu;
    private String tenXuatXu;

    public XuatXu() {
    }

    public XuatXu(int id, String maXuatXu, String tenXuatXu) {
        this.id = id;
        this.maXuatXu = maXuatXu;
        this.tenXuatXu = tenXuatXu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaXuatXu() {
        return maXuatXu;
    }

    public void setMaXuatXu(String maXuatXu) {
        this.maXuatXu = maXuatXu;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
    }
}
