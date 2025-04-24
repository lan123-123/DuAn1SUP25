/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import View.BanHang;
import View.HoaDon;
import View.KhachHang;
import View.KhachHang;
import View.TrangChu;
import View.NhanVien;



import View.Voucher;


import View.SanPham;
import View.ThongKe;
import View.ThuocTinh;
import View.Voucher;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author trinh thanh
 */
public class ChuyenManHinh {

    private JPanel root;
    private String kindSelected = "";
    private List<DanhMucBean> danhMucBeans = null;

    public ChuyenManHinh(JPanel root) {
        this.root = root;
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "TrangChuChinh";
        jpnItem.setBackground(new Color(96, 100, 191));
        jlbItem.setBackground(new Color(96, 100, 191));

        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new BanHang());
        root.validate();
        root.repaint();

    }

    public void setEvent(List<DanhMucBean> danhMucBeans) {
        this.danhMucBeans = danhMucBeans;
        for (DanhMucBean item : danhMucBeans) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;

        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jpbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jpbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "BanHang":
                    node = new BanHang();
                    break;
                case "NhanVien":
                    node = new NhanVien();
                    break;
                case "HoaDon":
                    node = new HoaDon();
                    break;
                case "KhachHang":
                    node = new KhachHang();
                    break;
                case "Voucher":
                    node = new Voucher();
                    break;
                case "SanPham":
                    node = new SanPham();
                    break;
                case "ThuocTinh":
                    node = new ThuocTinh();
                    break;
                case "ThongKe":
                    node = new ThongKe();
                    break;
                default:

            }

            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackgroud(kind);
        }

        @Override

        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(96, 100, 191));
            jlbItem.setBackground(new Color(96, 100, 191));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(96, 100, 191));
            jlbItem.setBackground(new Color(96, 100, 191));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(76, 175, 80));
                jlbItem.setBackground(new Color(76, 175, 80));
            }
        }

    }

    private void setChangeBackgroud(String kind) {
        for (DanhMucBean item : danhMucBeans) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(96, 100, 191));
                item.getJlb().setBackground(new Color(96, 100, 191));
            } else {
                item.getJpn().setBackground(new Color(96, 100, 191));
                item.getJlb().setBackground(new Color(96, 100, 191));
            }
        }
    }
}
