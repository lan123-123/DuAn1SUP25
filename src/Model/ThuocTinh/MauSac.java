/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.ThuocTinh;

/**
 *
 * @author doann
 */
public class MauSac {
    private int id;
    private String maMauSac;
    private String tenMauSac;

    public MauSac() {
    }

    public MauSac(int id, String maMauSac, String tenMauSac) {
        this.id = id;
        this.maMauSac = maMauSac;
        this.tenMauSac = tenMauSac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaMauSac() {
        return maMauSac;
    }

    public void setMaMauSac(String maMauSac) {
        this.maMauSac = maMauSac;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }
    
}
