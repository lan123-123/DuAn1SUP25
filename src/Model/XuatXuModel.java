/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TONG THI NHUNG
 */
public class XuatXuModel {
    private String tenXuatXu;
    private String maXuatXu;

    public XuatXuModel() {
    }

    public XuatXuModel(String tenXuatXu, String maXuatXu) {
        this.tenXuatXu = tenXuatXu;
        this.maXuatXu = maXuatXu;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
    }

    public String getMaXuatXu() {
        return maXuatXu;
    }

    public void setMaXuatXu(String maXuatXu) {
        this.maXuatXu = maXuatXu;
    }
    
    public Object[] toDataRow(){
        return new Object[]{
            this.maXuatXu,this.tenXuatXu
        };
    }
    
}
