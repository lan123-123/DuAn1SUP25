    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package Model.ThuocTinh;

    /**
     *
     * @author doann
     */
    public class ThuongHieu {
        private int id;
        private String maThuongHieu;
        private String tenThuongHieu;

        public ThuongHieu() {
        }

        public ThuongHieu(int id, String maThuongHieu, String tenThuongHieu) {
            this.id = id;
            this.maThuongHieu = maThuongHieu;
            this.tenThuongHieu = tenThuongHieu;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMaThuongHieu() {
            return maThuongHieu;
        }

        public void setMaThuongHieu(String maThuongHieu) {
            this.maThuongHieu = maThuongHieu;
        }

        public String getTenThuongHieu() {
            return tenThuongHieu;
        }

        public void setTenThuongHieu(String tenThuongHieu) {
            this.tenThuongHieu = tenThuongHieu;
        }
    @Override
public String toString() {
    return tenThuongHieu; // Hiển thị tên thương hiệu trên JComboBox
}

    }
