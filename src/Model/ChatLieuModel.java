/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TONG THI NHUNG
 */
public class ChatLieuModel {
    private String MaChatLieu;
    private String TenChatLieu;

    public ChatLieuModel() {
    }

    public ChatLieuModel(String MaChatLieu, String TenChatLieu) {
        this.MaChatLieu = MaChatLieu;
        this.TenChatLieu = TenChatLieu;
    }

    public String getMaChatLieu() {
        return MaChatLieu;
    }

    public void setMaChatLieu(String MaChatLieu) {
        this.MaChatLieu = MaChatLieu;
    }

    public String getTenChatLieu() {
        return TenChatLieu;
    }

    public void setTenChatLieu(String TenChatLieu) {
        this.TenChatLieu = TenChatLieu;
    }
    
     public Object[] toDataRow(){
        return new Object[]{
            this.MaChatLieu,this.TenChatLieu
        };
    }
    
   
}
