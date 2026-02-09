	package controller.student;
	
	import java.awt.EventQueue;
	
	import javax.swing.JComboBox;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JTable;
	import javax.swing.JTextField;
	import javax.swing.border.EmptyBorder;
	import javax.swing.table.DefaultTableModel;
	
	
	import dao.student.PointTransactionsDao;
	import dao.student.impl.PointTransactionsDaoImpl;
	import model.PointTransactions;
	import model.Student;
	import service.student.StudentService;
	import service.student.impl.StudentServiceImpl;
	
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;

	import java.awt.Font;
	import javax.swing.JButton;
	import javax.swing.JScrollPane;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.util.List;
	import java.awt.BorderLayout;
	import java.awt.event.ActionListener;
	import java.awt.event.ActionEvent;
	
	public class SPointsUI extends JFrame {
		
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private Student student;
		private StudentService studentService = new StudentServiceImpl();
		private PointTransactionsDao ptDao = new PointTransactionsDaoImpl();
		private JTable tblTx;
		private JTextField txtAmountPaid;
		private JComboBox<String> cmbMonth;
		private JLabel lblPoints;
		private DefaultTableModel txModel;
	
		/**
	     * Launch the application.
	     */
		public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    SPointsUI frame = new SPointsUI();
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
		public SPointsUI() {
			setBounds(100, 100, 730, 350);   
			setLocationRelativeTo(null);     // 視窗在中間
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			getContentPane().setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBounds(10, 10, 696, 291);
			getContentPane().add(panel);
			panel.setLayout(null);
			
			JLabel lblTitle = new JLabel("點數管理");
			lblTitle.setBounds(331, 21, 59, 18);
			lblTitle.setFont(new Font("PMingLiU", Font.BOLD, 14));
			panel.add(lblTitle);
			
			JLabel lblMonth = new JLabel("月份:");
			lblMonth.setFont(new Font("PMingLiU", Font.BOLD, 12));
			lblMonth.setBounds(50, 69, 40, 20);
			panel.add(lblMonth);
			
			cmbMonth = new JComboBox<String>();
			cmbMonth.setBounds(90, 69, 120, 22);
			panel.add(cmbMonth);
			
	
			lblPoints = new JLabel("目前點數：0");
			lblPoints.setFont(new Font("PMingLiU", Font.BOLD, 14));
			lblPoints.setBounds(422, 69, 199, 18);
			panel.add(lblPoints);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(32, 99, 648, 131);
			panel.add(scrollPane);
			
			tblTx = new JTable();
			scrollPane.setViewportView(tblTx);
			
			JLabel lblCurrentPoints_1 = new JLabel("儲值金額：");
			lblCurrentPoints_1.setFont(new Font("PMingLiU", Font.BOLD, 14));
			lblCurrentPoints_1.setBounds(32, 240, 85, 18);
			panel.add(lblCurrentPoints_1);
			
			JLabel lblCurrentPoints_1_1 = new JLabel("（每 10 元 = 1 點）");
			lblCurrentPoints_1_1.setFont(new Font("PMingLiU", Font.BOLD, 14));
			lblCurrentPoints_1_1.setBounds(90, 268, 160, 18);
			panel.add(lblCurrentPoints_1_1);
			
			txtAmountPaid = new JTextField();
			txtAmountPaid.setBounds(109, 240, 96, 20);
			panel.add(txtAmountPaid);
			txtAmountPaid.setColumns(10);
			
		
			
			
			/******event*******/
			
			JButton btnQuery = new JButton("查詢交易明細");
			btnQuery.setFont(new Font("PMingLiU", Font.BOLD, 12));
			btnQuery.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					loadTransactions();
				}
			});
			btnQuery.setBounds(230, 69, 125, 22);
			panel.add(btnQuery);
			
			JButton btnRefresh = new JButton("重新整理");
			btnRefresh.setFont(new Font("PMingLiU", Font.BOLD, 12));
			btnRefresh.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					refreshPoints();
				}
			});
			btnRefresh.setBounds(566, 68, 114, 22);
			panel.add(btnRefresh);
			
			JButton btnClear = new JButton("重新輸入金額");
			btnClear.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					txtAmountPaid.setText("");
				}
			});
			btnClear.setFont(new Font("PMingLiU", Font.BOLD, 12));
			btnClear.setBounds(418, 240, 109, 22);
			panel.add(btnClear);
			
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
			btnHome.setBounds(571, 240, 109, 22);
			panel.add(btnHome);
			
			JButton btnTopUp = new JButton("確認儲值");
			btnTopUp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					doTopUp();
				}
			});
			btnTopUp.setFont(new Font("PMingLiU", Font.BOLD, 12));
			btnTopUp.setBounds(257, 240, 109, 22);
			panel.add(btnTopUp);
			
			
			
		initMonthCombo();
		initTxTable();
		refreshPoints();
			
			
		}

		
		
		private void initTxTable() {
			txModel = new DefaultTableModel(
				    new Object[]{"交易時間", "儲值金額", "點數變動", }, 0
				) {
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				tblTx.setModel(txModel);

		}

		
		private void refreshPoints() {
		    if (student == null) {
		        lblPoints.setText("目前點數：");
		        return;
		    }
		    int points = studentService.getPoints(student.getId());
		    lblPoints.setText("目前點數：" + points);
		}

		private void initMonthCombo() {
		    cmbMonth.removeAllItems();
		    cmbMonth.addItem("All");

		    if (student == null) return;

		    List<String> months = ptDao.findAvailableMonths(student.getId());
		    for (String m : months) {
		        cmbMonth.addItem(m);
		    }
		}
		
		
		private void loadTransactions() {
		    txModel.setRowCount(0);

		    if (student == null) {
		        JOptionPane.showMessageDialog(this, "請先登入");
		        return;
		    }

		    String ym = (String) cmbMonth.getSelectedItem();

		    List<PointTransactions> list;
		    if (ym == null || "All".equals(ym)) {
		        list = ptDao.findByStudentId(student.getId());
		    } else {
		        list = ptDao.findByStudentIdAndMonth(student.getId(), ym);
		    }

		    for (PointTransactions tx : list) {
		        txModel.addRow(new Object[]{
		            tx.getTx_date(),
		            tx.getAmount_paid(),
		            tx.getPoints_added()
		        });
		    }
		}


		//top up
		private void doTopUp() {
		    if (student == null) {
		        JOptionPane.showMessageDialog(this, "請先登入");
		        return;
		    }

		    String s = txtAmountPaid.getText().trim();
		    if (s.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "請輸入儲值金額");
		        return;
		    }

		    int amountPaid;
		    try {
		        amountPaid = Integer.parseInt(s);
		    } catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(this, "金額請輸入整數");
		        return;
		    }

		    if (amountPaid <= 0) {
		        JOptionPane.showMessageDialog(this, "金額必須大於 0");
		        return;
		    }

		    if (amountPaid % 10 != 0) {
		        JOptionPane.showMessageDialog(this, "金額需為 10 的倍數（每 10 元 = 1 點）");
		        return;
		    }

		    boolean ok = studentService.topUp(student.getId(), amountPaid);
		    if (ok) {
		        JOptionPane.showMessageDialog(this, "儲值成功！");
		        txtAmountPaid.setText("");
		        refreshPoints();
		        initMonthCombo();     // 可能新增新月份
		        loadTransactions();
		    } else {
		        JOptionPane.showMessageDialog(this, "儲值失敗");
		    }
		}

		
		
		public SPointsUI(Student student) {
		    this();
		    this.student = student;

		    initTxTable();
		    initMonthCombo();
		    refreshPoints();
		    loadTransactions();
		}

		
		
	
	}
