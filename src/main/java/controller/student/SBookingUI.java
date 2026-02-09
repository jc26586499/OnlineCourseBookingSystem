package controller.student;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Student;
import service.student.BookingService;
import service.student.impl.BookingServiceImpl;
import util.Tool;
import vo.student.ViewAvailableSlots;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SBookingUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private Student student;
    private BookingService bookingService = new BookingServiceImpl();

    private JTable tblSlots;
    private JTable tblCart;
    private JComboBox<String> cmbTeacher;
    private JComboBox<String> cmbMonth;
    private JLabel lblTotal;

    private DefaultTableModel slotsModel;
    private DefaultTableModel cartModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new SLoginUI().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the frame.
     */
    public SBookingUI(Student student) {
    	getContentPane().setFont(new Font("Dialog", Font.BOLD, 12));
        this.student = student;
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        initUI();
        initTableModels();
        initMonthCombo();
        loadTeachers();
        refreshTotal();
    }

    // ========= UI =========
    private void initUI() {

        JLabel lblTitle = new JLabel("預約課程");
        lblTitle.setFont(new Font("PMingLiU", Font.BOLD, 14));
        lblTitle.setBounds(338, 10, 100, 25);
        getContentPane().add(lblTitle);

        JLabel lblTeacher = new JLabel("教師:");
        lblTeacher.setFont(new Font("PMingLiU", Font.BOLD, 12));
        lblTeacher.setBounds(20, 50, 40, 20);
        getContentPane().add(lblTeacher);

        cmbTeacher = new JComboBox<>();
        cmbTeacher.setBounds(60, 50, 120, 22);
        getContentPane().add(cmbTeacher);

        JLabel lblMonth = new JLabel("月份:");
        lblMonth.setFont(new Font("PMingLiU", Font.BOLD, 12));
        lblMonth.setBounds(200, 50, 40, 20);
        getContentPane().add(lblMonth);

        cmbMonth = new JComboBox<>();
        cmbMonth.setBounds(240, 50, 120, 22);
        getContentPane().add(cmbMonth);

      
        JScrollPane spSlots = new JScrollPane();
        spSlots.setBounds(20, 90, 740, 150);
        getContentPane().add(spSlots);

        tblSlots = new JTable();
        spSlots.setViewportView(tblSlots);

       

      

       

        JScrollPane spCart = new JScrollPane();
        spCart.setBounds(20, 290, 740, 150);
        getContentPane().add(spCart);

        tblCart = new JTable();
        spCart.setViewportView(tblCart);

        lblTotal = new JLabel("此次共需扣點：0");
        lblTotal.setFont(new Font("Dialog", Font.BOLD, 12));
        lblTotal.setBounds(310, 460, 200, 25);
        getContentPane().add(lblTotal);

    
      

        /***** event ******/

        JButton btnSearch = new JButton("查詢");
        btnSearch.setFont(new Font("PMingLiU", Font.BOLD, 12));
        btnSearch.setBounds(380, 50, 80, 22);
        getContentPane().add(btnSearch);

        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchSlots();
            }
        });

        JButton btnAdd = new JButton("加入清單");
        btnAdd.setFont(new Font("PMingLiU", Font.BOLD, 12));
        btnAdd.setBounds(20, 250, 100, 25);
        getContentPane().add(btnAdd);
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addToCart();
            }
        });

        
        JButton btnRemove = new JButton("移除");
        btnRemove.setFont(new Font("PMingLiU", Font.BOLD, 12));
        btnRemove.setBounds(130, 250, 80, 25);
        getContentPane().add(btnRemove);
        btnRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblCart.getSelectedRow();
                if (row >= 0) {
                    cartModel.removeRow(row);
                    refreshTotal();
                }
            }
        });

        JButton btnClear = new JButton("清除");
        btnClear.setFont(new Font("PMingLiU", Font.BOLD, 12));
        btnClear.setBounds(220, 250, 80, 25);
        getContentPane().add(btnClear);
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cartModel.setRowCount(0);
                refreshTotal();
            }
        });

        
        JButton btnNext = new JButton("前往明細確認");
        btnNext.setFont(new Font("Dialog", Font.BOLD, 12));
        btnNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (cartModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(SBookingUI.this, "請先加入課程");
                    return;
                }

                // 從 JTable 組 cartList
                List<ViewAvailableSlots> cartList = new ArrayList<>();
                int totalCost = 0;

                for (int i = 0; i < cartModel.getRowCount(); i++) {
                    ViewAvailableSlots v = new ViewAvailableSlots();
                    v.setSlot_id((int) cartModel.getValueAt(i, 0));
                    v.setTeacher_name((String) cartModel.getValueAt(i, 1));
                    v.setStart_time((String) cartModel.getValueAt(i, 2));
                    v.setCost((int) cartModel.getValueAt(i, 3));

                    cartList.add(v);
                    totalCost += v.getCost();
                }

              //
                new SBookingDetailUI(student, cartList, totalCost).setVisible(true);
                dispose();
            }
        });

        btnNext.setBounds(470, 460, 140, 25);
        getContentPane().add(btnNext);
        
        
        JButton btnBack = new JButton("回學生中心");
        btnBack.setFont(new Font("Dialog", Font.BOLD, 12));
        btnBack.setBounds(620, 460, 140, 25);
        getContentPane().add(btnBack);
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SHomeUI home = new SHomeUI();
                home.setStudent(student);
                home.setVisible(true);
                dispose();
            }
        });


    }

    // ========= 初始化 =========
    private void initTableModels() {
        slotsModel = new DefaultTableModel(
                new Object[]{"slot_id", "teacher_name", "start_time", "cost"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblSlots.setModel(slotsModel);

        cartModel = new DefaultTableModel(
                new Object[]{"slot_id", "teacher_name", "start_time", "cost"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCart.setModel(cartModel);
    }

    private void initMonthCombo() {
        cmbMonth.addItem("All");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate now = LocalDate.now();
        for (int i = 0; i < 6; i++) {
            cmbMonth.addItem(now.minusMonths(i).format(fmt));
        }
    }

    private void loadTeachers() {
        cmbTeacher.addItem("All");
        String sql = "SELECT teacher_name FROM teacher ORDER BY teacher_name";
        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cmbTeacher.addItem(rs.getString("teacher_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "教師清單載入失敗");
        }
    }

    // ========= 功能 =========
    private void searchSlots() {
        slotsModel.setRowCount(0);

        String ym = (String) cmbMonth.getSelectedItem();
        String teacher = (String) cmbTeacher.getSelectedItem();

        List<ViewAvailableSlots> list;
        if (ym != null && !"All".equals(ym)) {
            list = bookingService.listAvailableSlotsByMonth(ym);
        } else {
            list = bookingService.listAvailableSlots();
        }

        for (ViewAvailableSlots v : list) {
            if (teacher != null && !"All".equals(teacher) && !teacher.equals(v.getTeacher_name())) {
                continue;
            }
            slotsModel.addRow(new Object[]{
                    v.getSlot_id(),
                    v.getTeacher_name(),
                    v.getStart_time(),
                    v.getCost()
            });
        }
    }

    private void addToCart() {
        int row = tblSlots.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "請先選擇一筆可預約時段");
            return;
        }

        int slotId = (int) slotsModel.getValueAt(row, 0);

        // 防止重複加入（slot_id）
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            if ((int) cartModel.getValueAt(i, 0) == slotId) {
                JOptionPane.showMessageDialog(this, "此時段已在清單中");
                return;
            }
        }

        cartModel.addRow(new Object[]{
                slotsModel.getValueAt(row, 0),
                slotsModel.getValueAt(row, 1),
                slotsModel.getValueAt(row, 2),
                slotsModel.getValueAt(row, 3)
        });

        refreshTotal();
    }

    private void refreshTotal() {
        int total = 0;
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            total += (int) cartModel.getValueAt(i, 3);
        }
        lblTotal.setText("此次共需扣點：" + total);
    }
}
