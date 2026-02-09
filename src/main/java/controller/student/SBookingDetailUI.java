package controller.student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Student;
import service.student.StudentService;
import service.student.impl.StudentServiceImpl;
import vo.student.ViewAvailableSlots;
import service.student.BookingService;
import service.student.impl.BookingServiceImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SBookingDetailUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblDetail;
	private JLabel lblCurrentPoints;
	private JLabel lblTotalCost;
	private JLabel lblRemaining;
	private Student student;
	
	private List<ViewAvailableSlots> cartList;
	private int totalCost;

	private BookingService bookingService = new BookingServiceImpl();
	private StudentService studentService = new StudentServiceImpl();


	private DefaultTableModel detailModel;

	//JTable  TableModel
	private void initTable() {
	    detailModel = new DefaultTableModel(
	        new Object[]{"教師", "上課時間", "扣點"}, 0
	    ) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
	    tblDetail.setModel(detailModel);
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SBookingDetailUI frame = new SBookingDetailUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SBookingDetailUI() {
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 331);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 754, 274);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitle = new JLabel("本次已選課程明細");
		lblTitle.setBounds(305, 10, 181, 35);
		lblTitle.setFont(new Font("PMingLiU", Font.BOLD, 14));
		panel.add(lblTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 734, 130);
		panel.add(scrollPane);
		
		tblDetail = new JTable();
		scrollPane.setViewportView(tblDetail);
		
		lblCurrentPoints = new JLabel("目前點數：0");
		lblCurrentPoints.setFont(new Font("PMingLiU", Font.BOLD, 14));
		lblCurrentPoints.setBounds(43, 195, 199, 18);
		panel.add(lblCurrentPoints);
		
		lblTotalCost = new JLabel("本次扣點：0");
		lblTotalCost.setFont(new Font("PMingLiU", Font.BOLD, 14));
		lblTotalCost.setBounds(323, 195, 199, 18);
		panel.add(lblTotalCost);
		
		lblRemaining = new JLabel("預估剩餘：0");
		lblRemaining.setFont(new Font("PMingLiU", Font.BOLD, 14));
		lblRemaining.setBounds(604, 195, 150, 18);
		panel.add(lblRemaining);
		
		
		
		/*******event*******/

		JButton btnConfirm = new JButton("確認扣點並預約");
		btnConfirm.setFont(new Font("PMingLiU", Font.BOLD, 12));
			
		btnConfirm.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {

		        if (student == null) {
		            JOptionPane.showMessageDialog(SBookingDetailUI.this, "請先登入學生帳號");
		            return;
		        }
		        if (cartList == null || cartList.isEmpty()) {
		            JOptionPane.showMessageDialog(SBookingDetailUI.this, "清單是空的，無法預約");
		            return;
		        }

		        // 先做一次總點數檢查
		        int currentPoints = studentService.getPoints(student.getId());
		        if (currentPoints < totalCost) {
		            JOptionPane.showMessageDialog(
		                SBookingDetailUI.this,
		                "點數不足\n目前點數：" + currentPoints + "\n需要點數：" + totalCost
		            );
		            return;
		        }

		        StringBuilder sb = new StringBuilder();
		        int successCount = 0;

		        for (ViewAvailableSlots v : cartList) {
		            String msg = bookingService.bookLesson(student.getId(), v.getSlot_id(), v.getCost(), "");
		            sb.append(msg).append("\n");

		            if (msg != null && msg.startsWith("預約成功")) {
		                successCount++;
		            }
		        }

		        JOptionPane.showMessageDialog(
		            SBookingDetailUI.this,
		            "完成：" + successCount + " / " + cartList.size() + "\n\n" + sb.toString()
		        );

		        // 更新 student 點數
		        int pointsAfter = studentService.getPoints(student.getId());
		        student.setStudent_points(pointsAfter);

		        // 回學生中心
		        SHomeUI home = new SHomeUI();
		        home.setStudent(student);
		        home.setVisible(true);
		        dispose();
		    }
		});

		btnConfirm.setBounds(10, 223, 150, 41);
		panel.add(btnConfirm);
		
		
		
		JButton btnBack = new JButton("回上一頁");
		btnBack.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        SBookingUI bookingUI = new SBookingUI(student);
		        bookingUI.setVisible(true);
		        dispose();
		    }
		});
		btnBack.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnBack.setBounds(290, 223, 150, 41);
		panel.add(btnBack);
		
		
		
		JButton btnHome = new JButton("回學生中心");
		btnHome.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        SHomeUI home = new SHomeUI();
		        home.setStudent(student);
		        home.setVisible(true);
		        dispose();
		    }
		});
		btnHome.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnHome.setBounds(583, 223, 150, 41);
		panel.add(btnHome);

		
		
		
	}
	
	
	public SBookingDetailUI(Student student, List<ViewAvailableSlots> cartList, int totalCost) {
	    this(); // 畫面

	    // 存進欄位
	    this.student = student;
	    this.cartList = cartList;
	    this.totalCost = totalCost;

	    // 1. 初始化 table
	    initTable();

	    // 2. cart 資料進 JTable
	    detailModel.setRowCount(0);
	    for (ViewAvailableSlots v : cartList) {
	        detailModel.addRow(new Object[]{
	            v.getTeacher_name(),
	            v.getStart_time(),
	            v.getCost()
	        });
	    }

	    // 3. 查學生目前點數
	    int currentPoints = studentService.getPoints(student.getId());

	    // 4. 更新 label
	    lblCurrentPoints.setText("目前點數：" + currentPoints);
	    lblTotalCost.setText("本次扣點：" + totalCost);
	    lblRemaining.setText("預估剩餘：" + (currentPoints - totalCost));
	}

	
	
}
