	package controller.student;
	
	import java.awt.EventQueue;
	
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;
	import javax.swing.JLabel;
	import java.awt.Font;
	import javax.swing.JComboBox;
	import javax.swing.JButton;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.JRadioButton;
	import javax.swing.JTextArea;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import model.Student;
	import javax.swing.JOptionPane;
	import javax.swing.table.DefaultTableModel;

import model.Student;

import java.util.List;
	
	import vo.student.ViewStudentFeedback;
	import vo.student.ViewStudentFeedbackDaoImpl;
	
	
	public class SFeedbackUI extends JFrame {
	
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTable tblCandidates;
		
		private JComboBox<String> cboMonth;
		private JComboBox<String> cboStatus;
		private JComboBox<Integer> cboRating;
		private JTextArea txtComment;
		private JLabel lblSelectedBookingInfo;
		private JButton btnSubmit;
		private JButton btnSearch;
	
		private DefaultTableModel model;
	
		private ViewStudentFeedbackDaoImpl dao = new ViewStudentFeedbackDaoImpl();
	
		// 
		private int studentId = 1;
		
		private Student student;
	
		// 目前選到哪堂課
		private Integer selectedBookingId = null;
		private JButton btnHome;
	
	
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						SFeedbackUI frame = new SFeedbackUI();
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
		public SFeedbackUI() {
			
		



			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 560, 342);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBounds(10, 10, 526, 285);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblTitle = new JLabel("評價課程");
			lblTitle.setBounds(226, 12, 84, 31);
			lblTitle.setFont(new Font("PMingLiU", Font.BOLD, 14));
			panel.add(lblTitle);
			
			JLabel lblMonth = new JLabel("月份:");
			lblMonth.setFont(new Font("PMingLiU", Font.BOLD, 12));
			lblMonth.setBounds(10, 55, 40, 20);
			panel.add(lblMonth);
			
			cboMonth = new JComboBox<String>();
			cboMonth.setBounds(47, 54, 120, 22);
			panel.add(cboMonth);
			
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 86, 506, 97);
			panel.add(scrollPane);
			
			//JTable model
			tblCandidates = new JTable();
			scrollPane.setViewportView(tblCandidates);
			model = new DefaultTableModel(
				    new Object[]{"bookingId", "上課時間", "老師"}, 0
				) {
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				tblCandidates.setModel(model);
	
				// 隱藏 bookingId 欄（第 0 欄）
				tblCandidates.getColumnModel().getColumn(0).setMinWidth(0);
				tblCandidates.getColumnModel().getColumn(0).setMaxWidth(0);
				tblCandidates.getColumnModel().getColumn(0).setWidth(0);
	
			
			
			JLabel lblMonth_1 = new JLabel("評分:");
			lblMonth_1.setFont(new Font("PMingLiU", Font.BOLD, 12));
			lblMonth_1.setBounds(422, 193, 40, 20);
			panel.add(lblMonth_1);
			
			lblSelectedBookingInfo = new JLabel("課程:尚未選擇");
			lblSelectedBookingInfo.setFont(new Font("PMingLiU", Font.BOLD, 12));
			lblSelectedBookingInfo.setBounds(10, 194, 402, 20);
			panel.add(lblSelectedBookingInfo);
			
			txtComment = new JTextArea();
			txtComment.setBounds(46, 225, 264, 21);
			panel.add(txtComment);
			
			JLabel lblMonth_1_1 = new JLabel("心得:");
			lblMonth_1_1.setFont(new Font("PMingLiU", Font.BOLD, 12));
			lblMonth_1_1.setBounds(10, 224, 40, 20);
			panel.add(lblMonth_1_1);
			
			JLabel lblMonth_2 = new JLabel("狀態:");
			lblMonth_2.setFont(new Font("PMingLiU", Font.BOLD, 12));
			lblMonth_2.setBounds(189, 54, 40, 20);
			panel.add(lblMonth_2);
			
			cboStatus = new JComboBox<String>();
			cboStatus.setBounds(228, 53, 120, 22);
			panel.add(cboStatus);
			cboStatus.addItem("待評價");
			cboStatus.addItem("已評價");
			cboStatus.setSelectedIndex(0);
			
			cboRating = new JComboBox<Integer>();
			cboRating.setBounds(458, 193, 58, 22);
			panel.add(cboRating);
			for (int i = 1; i <= 5; i++) cboRating.addItem(i);
			cboRating.setSelectedIndex(4);
	
			
			
			
			/*****event******/
			btnSubmit = new JButton("送出評價");
			btnSubmit.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        // 1. 檢查是否選中課程
			        if (selectedBookingId == null) {
			            JOptionPane.showMessageDialog(SFeedbackUI.this, "請先從列表中選擇一堂課程再進行評價");
			            return;
			        }

			        // 2. 獲取 UI 輸入值
			        int rating = (Integer) cboRating.getSelectedItem();
			        String comment = txtComment.getText().trim();

			        // 3. 字數限制檢查
			        if (comment.length() > 80) {
			            JOptionPane.showMessageDialog(SFeedbackUI.this, "心得內容請限制在 80 字以內");
			            return;
			        }

			        // 4. 正式呼叫後端 Service (建立實例)
			      
			        service.student.FeedbackService feedbackService = new service.student.impl.FeedbackServiceImpl();
			        
			        // 執行提交並取得結果訊息
			        String result = feedbackService.submitFeedback(studentId, selectedBookingId, rating, comment);

			        // 5. 根據結果處理 UI
			        if (result.contains("成功")) {
			            JOptionPane.showMessageDialog(SFeedbackUI.this, result);
			            
			            // 清空輸入欄位
			            txtComment.setText("");
			            selectedBookingId = null;
			            lblSelectedBookingInfo.setText("課程：尚未選擇");
			            
			            // 自動刷新表格：讓已評價的課消失（或移動到已評價清單）
			            refreshTable(); 
			        } else {
			            // 顯示失敗原因 (例如：重複評價、ID錯誤等)
			            JOptionPane.showMessageDialog(SFeedbackUI.this, result, "提交失敗", JOptionPane.ERROR_MESSAGE);
			        }
			    }
			});
	
			btnSubmit.setFont(new Font("PMingLiU", Font.BOLD, 12));
			btnSubmit.setBounds(320, 223, 94, 21);
			panel.add(btnSubmit);
			
			
			btnSearch = new JButton("查詢");
			btnSearch.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
	
			        String yearMonth = (String) cboMonth.getSelectedItem();
			        String mode = (String) cboStatus.getSelectedItem();
			        if (yearMonth == null || mode == null) return;
	
			        model.setRowCount(0);
			        selectedBookingId = null;
			        lblSelectedBookingInfo.setText("課程：尚未選擇");
			        txtComment.setText("");
	
			        List<ViewStudentFeedback> list;
	
			        if ("待評價".equals(mode)) {
			            list = dao.findCandidatesByStudentAndMonth(studentId, yearMonth);
			            cboRating.setEnabled(true);
			            txtComment.setEditable(true);
			            btnSubmit.setEnabled(false);
			        } else {
			            list = dao.findRatedByStudentAndMonth(studentId, yearMonth);
			            cboRating.setEnabled(false);
			            txtComment.setEditable(false);
			            btnSubmit.setEnabled(false);
			        }
	
			        for (ViewStudentFeedback r : list) {
			            model.addRow(new Object[]{
			                r.getBooking_id(),
			                r.getLesson_time(),
			                r.getTeacher_name()
			            });
			        }
			    }
			});
	
			btnSearch.setFont(new Font("PMingLiU", Font.BOLD, 12));
			btnSearch.setBounds(422, 55, 94, 21);
			panel.add(btnSearch);
			
			
			btnHome = new JButton("回學生中心");
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
			btnHome.setBounds(422, 223, 94, 22);
			panel.add(btnHome);
			
			
			
			//表格點選某列：顯示課程資訊＋記bookingId
			tblCandidates.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        int row = tblCandidates.getSelectedRow();
			        if (row < 0) return;
	
			        selectedBookingId = Integer.parseInt(model.getValueAt(row, 0).toString());
			        String time = model.getValueAt(row, 1).toString();
			        String teacher = model.getValueAt(row, 2).toString();
	
			        lblSelectedBookingInfo.setText("課程：" + time + " / " + teacher);
	
			        if ("待評價".equals(cboStatus.getSelectedItem())) {
			            btnSubmit.setEnabled(true);
			        }
			    }
			});
	
			
			
			
		}
		
		
		
		private void refreshTable() {
		    String yearMonth = (String) cboMonth.getSelectedItem();
		    String mode = (String) cboStatus.getSelectedItem();
	
		    if (yearMonth == null || mode == null) return;
	
		    model.setRowCount(0);
		    selectedBookingId = null;
		    lblSelectedBookingInfo.setText("課程：尚未選擇");
		    txtComment.setText("");
	
		    List<ViewStudentFeedback> list;
	
		    if ("待評價".equals(mode)) {
		        list = dao.findCandidatesByStudentAndMonth(studentId, yearMonth);
		        cboRating.setEnabled(true);
		        txtComment.setEditable(true);
		        btnSubmit.setEnabled(false); // 等選到課才 enable
		    } else {
		        list = dao.findRatedByStudentAndMonth(studentId, yearMonth);
		        cboRating.setEnabled(false);
		        txtComment.setEditable(false);
		        btnSubmit.setEnabled(false);
		    }
	
		    for (ViewStudentFeedback r : list) {
		        model.addRow(new Object[]{
		            r.getBooking_id(),
		            r.getLesson_time(),
		            r.getTeacher_name()
		        });
		    }
		
		 // ===== 載入月份下拉選單 =====
		    List<String> months = dao.findAvailableMonthsByStudent(studentId);
		    cboMonth.removeAllItems();
		    for (String m : months) {
		        cboMonth.addItem(m);
		    }

		    if (cboMonth.getItemCount() == 0) {
		        JOptionPane.showMessageDialog(this, "目前沒有可查詢的月份（可能沒有已完成/評價資料）。");
		    }

	
		    
		    
		}
		
	
		public SFeedbackUI(Student student) {
		    this(); 
		    this.student = student;
		    this.studentId = student.getId();
		    
		    loadMonths(); // 月份進下拉
		}

		
		
		private void loadMonths() {
		    List<String> months = dao.findAvailableMonthsByStudent(studentId);
		    cboMonth.removeAllItems();
		    for (String m : months) {
		        cboMonth.addItem(m);
		    }

		    if (cboMonth.getItemCount() == 0) {
		        JOptionPane.showMessageDialog(this, "目前沒有可查詢月份（請確認此學生是否有已完成課程）。");
		    }
		}

		
		

	}
