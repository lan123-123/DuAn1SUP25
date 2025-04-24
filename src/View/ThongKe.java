/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Jdbc.DbConnect;
import java.io.File;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author TONG THI NHUNG
 */
public final class ThongKe extends javax.swing.JPanel {

    /**
     * Creates new form ThongKe
     */
    public ThongKe() {
        initComponents();
        showPieChartbanChay();
        showBarChartBanChay();
        hienThiDoanhThu();
        hienThiTongKhachHang();
        hienThiDoanhThuTheoNgay();
        hienThiDoanhThuTheoThang();
        hienThiDoanhThuTheoTuan();
        laySoHoaDonDaBanVaDaHuy();
    }

    public void sendFileByEmail(JTextField txtemail, String filePath) {
        String recipientEmail = txtemail.getText().trim();
        if (recipientEmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập email người nhận!");
            return;
        }
        String senderEmail = "quysamset2@gmail.com";
        String senderPassword = "cwcbgrqbimkglhmy";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Gửi tệp đính kèm");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Vui lòng xem tệp đính kèm.");
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            FileDataSource source = new FileDataSource(filePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(new File(filePath).getName());
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi gửi email: " + e.getMessage());
        }
    }

    private void timKiemTheoNgay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayBatDauu = dateFormat.format(ngaybatdau.getDate());
        String ngayKetThucc = dateFormat.format(ngayketthuc.getDate());
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        String query = """
        SELECT TOP 3
            sp.TenSanPham AS TenSanPham,
            SUM(hdct.SoLuong) AS TongSoLuongBanRa
        FROM 
            Hoa_Don_Chi_Tiet hdct
        INNER JOIN 
            San_Pham_Chi_Tiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
        INNER JOIN 
            San_Pham sp ON spct.MaSanPham = sp.MaSanPham
        INNER JOIN 
            Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
        WHERE 
            hd.NgayTao BETWEEN ? AND ? AND hd.TrangThai = 1
        GROUP BY 
            sp.TenSanPham
        ORDER BY 
            TongSoLuongBanRa DESC
    """;

        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ngayBatDauu);
            stmt.setString(2, ngayKetThucc);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String tenSanPham = rs.getString("TenSanPham");
                    int soLuongBanRa = rs.getInt("TongSoLuongBanRa");
                    barDataset.setValue(soLuongBanRa, "Số lượng đã bán", tenSanPham);
                    pieDataset.setValue(tenSanPham, soLuongBanRa);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFreeChart barChart = ChartFactory.createBarChart(
                "SẢN PHẨM BÁN CHẠY",
                "Sản Phẩm",
                "Số Lượng",
                barDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        JFreeChart pieChart = ChartFactory.createPieChart(
                "TỶ LỆ SẢN PHẨM BÁN CHẠY",
                pieDataset,
                true,
                true,
                false
        );
        ChartPanel barChartPanel = new ChartPanel(barChart);
        panelBarChartt.setLayout(new BorderLayout());
        panelBarChartt.removeAll();
        panelBarChartt.add(barChartPanel, BorderLayout.CENTER);
        panelBarChartt.validate();
        panelBarChartt.repaint();
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        panelLineChart.setLayout(new BorderLayout());
        panelLineChart.removeAll();
        panelLineChart.add(pieChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
        panelLineChart.repaint();
        String fileName = "D:/BieuDo_TimKiem_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".pdf";
        exportChartsToPDF(pieChartPanel, barChartPanel, fileName);
    }

    public ChartPanel hienThiBieuDoTongSoLuongBan() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = """
            SELECT YEAR(hd.NgayTao) AS Nam, SUM(hdct.SoLuong) AS TongSoLuongBan
            FROM Hoa_Don_Chi_Tiet hdct
            INNER JOIN Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
            WHERE hd.TrangThai = 1
            GROUP BY YEAR(hd.NgayTao)
            ORDER BY Nam;
        """;
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int nam = rs.getInt("Nam");
                int tongSoLuong = rs.getInt("TongSoLuongBan");
                dataset.addValue(tongSoLuong, "Số lượng đã bán", String.valueOf(nam));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFreeChart barChart = ChartFactory.createBarChart(
                "Tổng số lượng sản phẩm đã bán",
                "Năm",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setOutlinePaint(Color.black);
        ChartPanel chartPanel = new ChartPanel(barChart);
        panelLineChart.setLayout(new BorderLayout());
        panelLineChart.removeAll();
        panelLineChart.add(chartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
        panelLineChart.repaint();
        return chartPanel;
    }

    private void hienThiTongKhachHang() {
        String query = "SELECT COUNT(*) AS TongSoKhachHang FROM Khach_Hang";

        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int tongKhachHang = rs.getInt("TongSoKhachHang");
                txttongsokhachhang.setText(String.valueOf(tongKhachHang));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hienThiDoanhThu() {
        int year = 2025;
        String query = """
                    SELECT 
                        SUM(spct.DonGia * hdct.SoLuong) AS TongDoanhThu
                    FROM 
                        Hoa_Don_Chi_Tiet hdct
                    INNER JOIN 
                        San_Pham_Chi_Tiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
                    INNER JOIN 
                        Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
                    WHERE 
                        YEAR(hd.NgayTao) = ? AND hd.TrangThai = 1
        """;
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double tongDoanhThu = rs.getDouble("TongDoanhThu");

                    // Kiểm tra giảm giá nếu > 10 triệu
                    double giamGia = 0;
                    if (tongDoanhThu > 10_000_000) {
                        giamGia = tongDoanhThu * 0.05;
                        tongDoanhThu -= giamGia;
                    }

                    DecimalFormat df = new DecimalFormat("#,### VND");
                    txtloinhuan.setText(df.format(tongDoanhThu));
                } else {

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void laySoHoaDonDaBanVaDaHuy() {
        int daBan = 0;
        int daHuyy = 0;

        String queryDaBan = "SELECT COUNT(*) AS SoHoaDonDaBan FROM Hoa_Don WHERE TrangThai = 1";            
        String queryDaHuy = "SELECT COUNT(*) AS SoHoaDonDaHuy FROM Hoa_Don WHERE TrangThai = 3";

        try (Connection conn = DbConnect.getConnection()) {
            // Lấy hóa đơn đã bán
            try (PreparedStatement stmt = conn.prepareStatement(queryDaBan); ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    daBan = rs.getInt("SoHoaDonDaBan");
                }
            }

            // Lấy hóa đơn đã hủy
            try (PreparedStatement stmt = conn.prepareStatement(queryDaHuy); ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    daHuyy = rs.getInt("SoHoaDonDaHuy");
                }
            }

            // Gán kết quả lên giao diện
            daBAn.setText(String.valueOf(daBan));
            daHuy.setText(String.valueOf(daHuyy));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hienThiDoanhThuTheoTuan() {
        LocalDate homNay = LocalDate.now();
        LocalDate ngayDauTuan = homNay.with(DayOfWeek.MONDAY);
        LocalDate ngayCuoiTuan = homNay.with(DayOfWeek.SUNDAY);

        String query = """
            SELECT 
                SUM(spct.DonGia * hdct.SoLuong) AS TongDoanhThu
            FROM 
                Hoa_Don_Chi_Tiet hdct
            INNER JOIN 
                San_Pham_Chi_Tiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
            INNER JOIN 
                Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
            WHERE 
                CAST(hd.NgayTao AS DATE) BETWEEN ? AND ? AND hd.TrangThai = 1
    """;

        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(ngayDauTuan));
            stmt.setDate(2, java.sql.Date.valueOf(ngayCuoiTuan));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double tongDoanhThu = rs.getDouble("TongDoanhThu");

                    double giamGia = 0;
                    if (tongDoanhThu > 10_000_000) {
                        giamGia = tongDoanhThu * 0.05;
                        tongDoanhThu -= giamGia;
                    }

                    DecimalFormat df = new DecimalFormat("#,### VND");
                    txtTongDoanhThuTheoTuan.setText(df.format(tongDoanhThu));
                } else {
                    txtTongDoanhThuTheoTuan.setText("0 VND");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hienThiDoanhThuTheoNgay() {
        LocalDate ngayCanXem = LocalDate.now(); // hoặc lấy từ JDateChooser nếu có
        String query = """
            SELECT 
                SUM(spct.DonGia * hdct.SoLuong) AS TongDoanhThu
            FROM 
                Hoa_Don_Chi_Tiet hdct
            INNER JOIN 
                San_Pham_Chi_Tiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
            INNER JOIN 
                Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
            WHERE 
                CAST(hd.NgayTao AS DATE) = ? AND hd.TrangThai = 1
    """;

        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(ngayCanXem));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double tongDoanhThu = rs.getDouble("TongDoanhThu");

                    // Kiểm tra giảm giá nếu > 10 triệu
                    double giamGia = 0;
                    if (tongDoanhThu > 10_000_000) {
                        giamGia = tongDoanhThu * 0.05;
                        tongDoanhThu -= giamGia;
                    }

                    DecimalFormat df = new DecimalFormat("#,### VND");
                    txtTongDoanThuTheoNgay.setText(df.format(tongDoanhThu));
                } else {
                    txtTongDoanThuTheoNgay.setText("0 VND");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hienThiDoanhThuTheoThang() {
        int thangCanXem = LocalDate.now().getMonthValue(); // tháng hiện tại
        int namCanXem = LocalDate.now().getYear();         // năm hiện tại

        String query = """
            SELECT 
                SUM(spct.DonGia * hdct.SoLuong) AS TongDoanhThu
            FROM 
                Hoa_Don_Chi_Tiet hdct
            INNER JOIN 
                San_Pham_Chi_Tiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
            INNER JOIN 
                Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
            WHERE 
                MONTH(hd.NgayTao) = ? AND YEAR(hd.NgayTao) = ? AND hd.TrangThai = 1
    """;

        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, thangCanXem);
            stmt.setInt(2, namCanXem);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double tongDoanhThu = rs.getDouble("TongDoanhThu");

                    // Giảm giá nếu doanh thu > 10 triệu
                    double giamGia = 0;
                    if (tongDoanhThu > 10_000_000) {
                        giamGia = tongDoanhThu * 0.05;
                        tongDoanhThu -= giamGia;
                    }

                    DecimalFormat df = new DecimalFormat("#,### VND");
                    txtTongDoanThuTheoThang.setText(df.format(tongDoanhThu));
                } else {
                    txtTongDoanThuTheoThang.setText("0 VND");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChartPanel showBarChartBanChay() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int year = 2025;
        String query = """
          SELECT TOP 3
                            sp.TenSanPham AS TenSanPham,
                            SUM(hdct.SoLuong) AS TongSoLuongBanRa
                        FROM 
                            Hoa_Don_Chi_Tiet hdct
                        INNER JOIN 
                            San_Pham_Chi_Tiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
                        INNER JOIN 
                            San_Pham sp ON spct.MaSanPham = sp.MaSanPham
                        INNER JOIN 
                            Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
                        WHERE 
                            YEAR(hd.NgayTao) = ? AND hd.TrangThai = 1
                        GROUP BY 
                            sp.TenSanPham
                        ORDER BY 
                            TongSoLuongBanRa desc
        """;
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String tenSanPham = rs.getString("tenSanPham");
                    int soLuongBanRa = rs.getInt("TongSoLuongBanRa");
                    dataset.setValue(soLuongBanRa, "Số lượng đã bán", tenSanPham);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFreeChart barChart = ChartFactory.createBarChart(
                "SẢN PHẨM BÁN CHẠY NHẤT NĂM " + year,
                "Sản Phẩm",
                "Số Lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.GRAY);
        ChartPanel barChartPanel = new ChartPanel(barChart);
        panelBarChartt.setLayout(new BorderLayout());
        panelBarChartt.removeAll();
        panelBarChartt.add(barChartPanel, BorderLayout.CENTER);
        panelBarChartt.validate();
        panelBarChartt.repaint();
        return barChartPanel;
    }

    public ChartPanel showPieChartbanChay() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int year = 2025;
        String query = """
                SELECT TOP 3
                    sp.TenSanPham AS TenSanPham,
                    SUM(hdct.SoLuong) AS TongSoLuongBanRa
                FROM 
                    Hoa_Don_Chi_Tiet hdct
                INNER JOIN 
                    San_Pham_Chi_Tiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
                INNER JOIN 
                    San_Pham sp ON spct.MaSanPham = sp.MaSanPham
                INNER JOIN 
                    Hoa_Don hd ON hdct.MaHoaDon = hd.MaHoaDon
                WHERE 
                    YEAR(hd.NgayTao) = ? AND hd.TrangThai = 1
                GROUP BY 
                    sp.TenSanPham
                ORDER BY 
                    TongSoLuongBanRa desc
    """;

        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year); // Chỉ cần gọi setInt(1, year) vì câu lệnh có tham số ?
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String tenSanPham = rs.getString("TenSanPham");
                    int soLuongDaBan = rs.getInt("TongSoLuongBanRa");
                    dataset.setValue(tenSanPham, soLuongDaBan); // Thêm giá trị vào dataset
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
        JFreeChart pieChart = ChartFactory.createPieChart(
                "SẢN PHẨM BÁN CHẠY NHẤT NĂM " + year, // Tiêu đề biểu đồ
                dataset, // Dữ liệu biểu đồ
                true, // Hiển thị legend
                true, // Hiển thị tooltips
                false // Không hiển thị URLs
        );
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(Color.white);  // Màu nền biểu đồ
        plot.setOutlineVisible(false);          // Tắt viền ngoài
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));  // Cài đặt font chữ cho nhãn

        // Tạo và thêm biểu đồ vào panel
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        panelLineChart.setLayout(new BorderLayout());
        panelLineChart.removeAll();
        panelLineChart.add(pieChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
        panelLineChart.repaint();

        return pieChartPanel;  // Trả về panel chứa biểu đồ
    }

    public void exportChartsToPDF(ChartPanel pieChartPanel, ChartPanel barChartPanel, String pdfFilePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            JFreeChart pieChart = pieChartPanel.getChart();
            BufferedImage pieImage = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = pieImage.createGraphics();
            pieChart.draw(g2d, new Rectangle(0, 0, 500, 400));
            ByteArrayOutputStream pieOut = new ByteArrayOutputStream();
            ChartUtilities.writeBufferedImageAsPNG(pieOut, pieImage);
            Image piePdfImage = Image.getInstance(pieOut.toByteArray());
            piePdfImage.scaleToFit(500, 400);
            document.add(piePdfImage);
            document.add(new com.itextpdf.text.Paragraph(" "));
            JFreeChart barChart = barChartPanel.getChart();
            BufferedImage barImage = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2dBar = barImage.createGraphics();
            barChart.draw(g2dBar, new Rectangle(0, 0, 500, 400));
            ByteArrayOutputStream barOut = new ByteArrayOutputStream();
            ChartUtilities.writeBufferedImageAsPNG(barOut, barImage);
            Image barPdfImage = Image.getInstance(barOut.toByteArray());
            barPdfImage.scaleToFit(500, 400);
            document.add(barPdfImage);
            document.close();
            System.out.println("Biểu đồ đã được xuất ra PDF thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenu11 = new javax.swing.JMenu();
        jMenuBar5 = new javax.swing.JMenuBar();
        jMenu12 = new javax.swing.JMenu();
        jMenu13 = new javax.swing.JMenu();
        jMenuBar6 = new javax.swing.JMenuBar();
        jMenu14 = new javax.swing.JMenu();
        jMenu15 = new javax.swing.JMenu();
        jMenuBar7 = new javax.swing.JMenuBar();
        jMenu16 = new javax.swing.JMenu();
        jMenu17 = new javax.swing.JMenu();
        jMenu18 = new javax.swing.JMenu();
        jMenu19 = new javax.swing.JMenu();
        jMenuBar8 = new javax.swing.JMenuBar();
        jMenu20 = new javax.swing.JMenu();
        jMenu21 = new javax.swing.JMenu();
        jMenuBar9 = new javax.swing.JMenuBar();
        jMenu22 = new javax.swing.JMenu();
        jMenu23 = new javax.swing.JMenu();
        jMenu24 = new javax.swing.JMenu();
        jMenuBar10 = new javax.swing.JMenuBar();
        jMenu25 = new javax.swing.JMenu();
        jMenu26 = new javax.swing.JMenu();
        jMenu27 = new javax.swing.JMenu();
        jMenu28 = new javax.swing.JMenu();
        jPanel3 = new javax.swing.JPanel();
        panelLineChart = new javax.swing.JPanel();
        panelBarChartt = new javax.swing.JPanel();
        ngaybatdau = new com.toedter.calendar.JDateChooser();
        ngayketthuc = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtloinhuan = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txttongsokhachhang = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtGuiEmail = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtTongDoanThuTheoNgay = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtTongDoanThuTheoThang = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtTongDoanhThuTheoTuan = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        daBAn = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        daHuy = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenuItem1.setText("jMenuItem1");

        jMenu5.setText("File");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar3.add(jMenu6);

        jMenu7.setText("jMenu7");

        jMenu8.setText("jMenu8");

        jMenu9.setText("File");
        jMenuBar4.add(jMenu9);

        jMenu10.setText("Edit");
        jMenuBar4.add(jMenu10);

        jMenu11.setText("jMenu11");

        jMenu12.setText("File");
        jMenuBar5.add(jMenu12);

        jMenu13.setText("Edit");
        jMenuBar5.add(jMenu13);

        jMenu14.setText("File");
        jMenuBar6.add(jMenu14);

        jMenu15.setText("Edit");
        jMenuBar6.add(jMenu15);

        jMenu16.setText("File");
        jMenuBar7.add(jMenu16);

        jMenu17.setText("Edit");
        jMenuBar7.add(jMenu17);

        jMenu18.setText("jMenu18");

        jMenu19.setText("jMenu19");

        jMenu20.setText("File");
        jMenuBar8.add(jMenu20);

        jMenu21.setText("Edit");
        jMenuBar8.add(jMenu21);

        jMenu22.setText("File");
        jMenuBar9.add(jMenu22);

        jMenu23.setText("Edit");
        jMenuBar9.add(jMenu23);

        jMenu24.setText("jMenu24");

        jMenu25.setText("File");
        jMenuBar10.add(jMenu25);

        jMenu26.setText("Edit");
        jMenuBar10.add(jMenu26);

        jMenu27.setText("jMenu27");

        jMenu28.setText("jMenu28");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        ngaybatdau.setBackground(new java.awt.Color(255, 255, 255));
        ngaybatdau.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ngaybatdauAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        ngaybatdau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ngaybatdauMouseClicked(evt);
            }
        });

        ngayketthuc.setBackground(new java.awt.Color(255, 255, 255));
        ngayketthuc.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ngayketthucAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        ngayketthuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ngayketthucMouseClicked(evt);
            }
        });

        jButton1.setText("Tìm");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtloinhuan.setBackground(new java.awt.Color(0, 0, 0));
        txtloinhuan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setText("Tổng Doanh Thu");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtloinhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(txtloinhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jButton2.setText("PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txttongsokhachhang.setBackground(new java.awt.Color(0, 0, 0));
        txttongsokhachhang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setText("TỔNG  SỐ KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txttongsokhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txttongsokhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jButton3.setText("Gửi Email");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("NGÀY KẾT THÚC");

        jLabel5.setText("NGÀY BẮT ĐẦU");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtTongDoanThuTheoNgay.setBackground(new java.awt.Color(0, 0, 0));
        txtTongDoanThuTheoNgay.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel3.setText("Doanh Thu Theo Ngày");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addComponent(txtTongDoanThuTheoNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTongDoanThuTheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtTongDoanThuTheoThang.setBackground(new java.awt.Color(0, 0, 0));
        txtTongDoanThuTheoThang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel6.setText("Doanh Thu Theo Tháng");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addComponent(txtTongDoanThuTheoThang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTongDoanThuTheoThang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtTongDoanhThuTheoTuan.setBackground(new java.awt.Color(0, 0, 0));
        txtTongDoanhThuTheoTuan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel7.setText("Tổng Doanh Thu Theo Tuần");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongDoanhThuTheoTuan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(txtTongDoanhThuTheoTuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        daBAn.setBackground(new java.awt.Color(0, 0, 0));
        daBAn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel9.setText("Tổng Số Hóa Đơn");

        daHuy.setBackground(new java.awt.Color(0, 0, 0));
        daHuy.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel8.setText("Đã Hủy");

        jLabel10.setText("Đã Bán");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(daBAn, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(daHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(daBAn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(daHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(panelBarChartt, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelLineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtGuiEmail)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton3)
                                        .addGap(106, 106, 106))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(97, 97, 97)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(ngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(76, 76, 76)
                                        .addComponent(jLabel4)
                                        .addGap(130, 130, 130)))
                                .addGap(35, 35, 35)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(67, 67, 67))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ngaybatdau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ngayketthuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtGuiEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton3))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBarChartt, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(panelLineChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ngaybatdauAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ngaybatdauAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_ngaybatdauAncestorAdded

    private void ngaybatdauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ngaybatdauMouseClicked

    }//GEN-LAST:event_ngaybatdauMouseClicked

    private void ngayketthucAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ngayketthucAncestorAdded

    }//GEN-LAST:event_ngayketthucAncestorAdded

    private void ngayketthucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ngayketthucMouseClicked

    }//GEN-LAST:event_ngayketthucMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

        //        timkiem();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        timKiemTheoNgay();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ChartPanel pieChartPanel = showPieChartbanChay();
        ChartPanel barChartPanel = showBarChartBanChay();
        if (pieChartPanel != null && barChartPanel != null) {
            String pdfFilePath = "D:/duyem.pdf";
            exportChartsToPDF(pieChartPanel, barChartPanel, pdfFilePath);
        } else {
            System.out.println("Biểu đồ không được tạo đúng cách.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SendEmailView send = new SendEmailView();
        send.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel daBAn;
    private javax.swing.JLabel daHuy;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenu jMenu19;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu20;
    private javax.swing.JMenu jMenu21;
    private javax.swing.JMenu jMenu22;
    private javax.swing.JMenu jMenu23;
    private javax.swing.JMenu jMenu24;
    private javax.swing.JMenu jMenu25;
    private javax.swing.JMenu jMenu26;
    private javax.swing.JMenu jMenu27;
    private javax.swing.JMenu jMenu28;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar10;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuBar jMenuBar5;
    private javax.swing.JMenuBar jMenuBar6;
    private javax.swing.JMenuBar jMenuBar7;
    private javax.swing.JMenuBar jMenuBar8;
    private javax.swing.JMenuBar jMenuBar9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private com.toedter.calendar.JDateChooser ngaybatdau;
    private com.toedter.calendar.JDateChooser ngayketthuc;
    private javax.swing.JPanel panelBarChartt;
    private javax.swing.JPanel panelLineChart;
    private javax.swing.JTextField txtGuiEmail;
    private javax.swing.JLabel txtTongDoanThuTheoNgay;
    private javax.swing.JLabel txtTongDoanThuTheoThang;
    private javax.swing.JLabel txtTongDoanhThuTheoTuan;
    private javax.swing.JLabel txtloinhuan;
    private javax.swing.JLabel txttongsokhachhang;
    // End of variables declaration//GEN-END:variables
}
