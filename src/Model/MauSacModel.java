 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TONG THI NHUNG
 */
public class MauSacModel {
    private String MaMauSac;
    private String TenMauSac;

    public MauSacModel() {
    }
 
    
    public MauSacModel(String MaMauSac, String TenMauSac) {
        this.MaMauSac = MaMauSac;
        this.TenMauSac = TenMauSac;
    }

    public String getMaMauSac() {
        return MaMauSac;
    }

    public void setMaMauSac(String MaMauSac) {
        this.MaMauSac = MaMauSac;
    }

    public String getTenMauSac() {
        return TenMauSac;
    }

    public void setTenMauSac(String TenMauSac) {
        this.TenMauSac = TenMauSac;
    }
    
    public Object[] toDataRow(){
        return new Object[]{
            this.MaMauSac,this.TenMauSac
        };
    }
    
}
