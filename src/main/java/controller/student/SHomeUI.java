package controller.student;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Student;



import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

public class SHomeUI extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Student student;
	private JLabel lblWelcome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 測試
                    Student s = new Student();
                    s.setStudent_name("測試學生");
                    SHomeUI frame = new SHomeUI();
                    frame.setStudent(s);
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
	


	public SHomeUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 511, 353);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblv = new JLabel("學生中心");
		lblv.setFont(new Font("PMingLiU", Font.BOLD, 12));
		lblv.setBounds(209, 10, 88, 41);
		panel.add(lblv);
		
		lblWelcome = new JLabel("歡迎:");
		lblWelcome.setFont(new Font("PMingLiU", Font.BOLD, 12));
		lblWelcome.setBounds(161, 43, 209, 41);
		panel.add(lblWelcome);
		
		

		/******event******/
		
		JButton btnBooking = new JButton("預約課程");
		btnBooking.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnBooking.setBounds(161, 94, 150, 41);
		btnBooking.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        new SBookingUI(student).setVisible(true);
		        dispose();
		        System.out.println("Go SBookingUI");
		    }
		});
		panel.add(btnBooking);
		
		
		JButton btnSchedule = new JButton("我的課表");
		btnSchedule.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnSchedule.setBounds(161, 145, 150, 41);
		btnSchedule.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        new SScheduleUI(student).setVisible(true);
		        dispose();
		        System.out.println("Go SScheduleUI");
		    }
		});
		panel.add(btnSchedule);
		

		JButton btnPoints = new JButton("點數管理");
		btnPoints.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnPoints.setBounds(161, 196, 150, 41);
		btnPoints.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        new SPointsUI(student).setVisible(true);
		        dispose();
		        System.out.println("Go SPointsUI");
		    }
		});
		panel.add(btnPoints);
		
		
		JButton btnFeedback = new JButton("評價課程");
		btnFeedback.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnFeedback.setBounds(161, 247, 150, 41);
		btnFeedback.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        new SFeedbackUI(student).setVisible(true);
		        dispose();
		        System.out.println("Go SFeedbackUI");
		    }
		});
		panel.add(btnFeedback);
		
		JButton btnLogout = new JButton("登出");
		btnLogout.setFont(new Font("PMingLiU", Font.BOLD, 12));
		btnLogout.setBounds(161, 298, 150, 41);
		btnLogout.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        new SLoginUI().setVisible(true);
		        dispose();
		    }
		});
		panel.add(btnLogout);
		
	}	
		
	public void setStudent(Student student) {
		    this.student = student;
		    lblWelcome.setText("歡迎：" + student.getStudent_name());
		}

		
	

}
