/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TONG THI NHUNG
 */
public class KichThuocModel {
    private String maKichThuoc;
    private String tenKichThuoc;

    public KichThuocModel() {
        
    }

    public KichThuocModel(String maKichThuoc, String tenKichThuoc) {
        this.maKichThuoc = maKichThuoc;
        this.tenKichThuoc = tenKichThuoc;
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
    
    public Object[] toDataRow(){
        return new Object[]{
            this.maKichThuoc,this.tenKichThuoc
        };
    }
    
}
