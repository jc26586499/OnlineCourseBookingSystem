package controller.student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Student;
import service.student.BookingService;
import service.student.impl.BookingServiceImpl;
import vo.student.ViewStudentSchedule;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SScheduleUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Student student;
	private JLabel lblTime;
	private BookingService bookingService = new BookingServiceImpl();

	private JComboBox<String> cmbStatus;
	private JComboBox<String> cmbMonth;
	private JTable tblSchedule;
	private DefaultTableModel scheduleModel;

	private List<ViewStudentSchedule> rawList = new ArrayList<>();//存全部原始資料
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SScheduleUI frame = new SScheduleUI();
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
	public SScheduleUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("我的課表");
		lblTitle.setBounds(319, 28, 117, 18);
		lblTitle.setFont(new Font("PMingLiU", Font.BOLD, 14));
		contentPane.add(lblTitle);
		
		
		
		JLabel lblTeacher = new JLabel("課程狀態:");
		lblTeacher.setBounds(27, 62, 76, 20);
		lblTeacher.setFont(new Font("PMingLiU", Font.BOLD, 12));
		contentPane.add(lblTeacher);
		
		cmbStatus = new JComboBox<String>();
		cmbStatus.setBounds(92, 61, 120, 22);
		contentPane.add(cmbStatus);
		
		JLabel lblTeacher_1 = new JLabel("月份:");
		lblTeacher_1.setFont(new Font("PMingLiU", Font.BOLD, 12));
		lblTeacher_1.setBounds(262, 62, 76, 20);
		contentPane.add(lblTeacher_1);
		
		cmbMonth = new JComboBox<String>();
		cmbMonth.setBounds(299, 60, 137, 22);
		contentPane.add(cmbMonth);
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 100, 670, 149);
		contentPane.add(scrollPane);
		
		tblSchedule = new JTable();
		scrollPane.setViewportView(tblSchedule);

		
		
		/******event*******/
		
		lblTime = new JLabel();
		lblTime.setBounds(439, 10, 258, 18);
		lblTime.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
		contentPane.add(lblTime);
		Timer timer = new Timer(1000, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		                .format(new Date());
		        lblTime.setText("目前時間：" + time);
		    }
		});
		timer.start();
		
		
		JButton btnSearch = new JButton("查詢");
		btnSearch.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        applyFilterAndRender();
		    }
		});
		btnSearch.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnSearch.setBounds(531, 61, 109, 22);
		contentPane.add(btnSearch);
		
		JButton print = new JButton("列印");
		print.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        try {
		            tblSchedule.print();
		        } catch (PrinterException ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		print.setFont(new Font("PMingLiU", Font.BOLD, 12));
		print.setBounds(92, 267, 109, 22);
		contentPane.add(print);
		
		JButton btnClear = new JButton("清除");
		btnClear.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {

		        // JTable
		        scheduleModel.setRowCount(0);

		        // 下拉選單回到預設值
		        cmbStatus.setSelectedIndex(0); // All
		        cmbMonth.setSelectedIndex(0);  // All
		    }
		});btnClear.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnClear.setBounds(311, 267, 109, 22);
		contentPane.add(btnClear);
		
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
		btnHome.setBounds(541, 267, 109, 22);
		contentPane.add(btnHome);
		
		
		
		initStatusCombo();
		initMonthCombo();
		initTable();
		loadAllSchedule();// 抓一次全部
		applyFilterAndRender();// 套用篩選顯示

		
		
		
	}
	
	
	private void initStatusCombo() {
	    cmbStatus.removeAllItems();
	    cmbStatus.addItem("All");
	    cmbStatus.addItem("已預約");
	    cmbStatus.addItem("已完成");
	}
	
	private void initMonthCombo() {
	    cmbMonth.removeAllItems();
	    cmbMonth.addItem("All");

	    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
	    LocalDate now = LocalDate.now();

	    for (int i = 0; i < 6; i++) {
	        cmbMonth.addItem(now.minusMonths(i).format(fmt));
	    }
	}


	private void initTable() {
	    scheduleModel = new DefaultTableModel(
	        new Object[]{"教師", "上課時間", "狀態", "Zoom"}, 0
	    ) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
	    tblSchedule.setModel(scheduleModel);
	}
	
	
	private void loadAllSchedule() {
	    if (student == null) {
	        // 測試
	        rawList = new ArrayList<>();
	        return;
	    }
	    rawList = bookingService.mySchedule(student.getId());
	}


	private void applyFilterAndRender() {
	    scheduleModel.setRowCount(0);

	    String selStatus = (String) cmbStatus.getSelectedItem();
	    String selMonth = (String) cmbMonth.getSelectedItem();

	    for (ViewStudentSchedule row : rawList) {

	        // status 篩選
	        if (selStatus != null && !"All".equals(selStatus)) {
	            if (!selStatus.equals(row.getBooking_status())) {
	                continue;
	            }
	        }

	        // month 篩選（lesson_time 取 yyyy-MM）
	        if (selMonth != null && !"All".equals(selMonth)) {
	            String ym = toYearMonth(row.getLesson_time());
	            if (!selMonth.equals(ym)) {
	                continue;
	            }
	        }

	        scheduleModel.addRow(new Object[]{
	            row.getTeacher_name(),
	            row.getLesson_time(),
	            row.getBooking_status(),
	            row.getZoom_link()
	        });
	    }
	}

	private String toYearMonth(String lessonTime) {
	    if (lessonTime == null) return "";
	    if (lessonTime.length() >= 7) return lessonTime.substring(0, 7);
	    return lessonTime;
	}

	
	
	public SScheduleUI(Student student) {
	    this();              // 先跑原本的 UI 初始化
	    this.student = student;

	    //
	     initStatusCombo();
	     initMonthCombo();
	    initTable();
	    loadAllSchedule();
	    applyFilterAndRender();
	}


}
