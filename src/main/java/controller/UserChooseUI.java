package controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.student.SLoginUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;



public class UserChooseUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	private JButton btnStudent;
    private JButton btnTeacher;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserChooseUI frame = new UserChooseUI();
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
	public UserChooseUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 351, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(23, 60, 282, 135);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		JLabel lblv = new JLabel("線上會話課程_個人班");
		lblv.setFont(new Font("PMingLiU", Font.BOLD, 14));
		lblv.setBounds(67, 10, 176, 51);
		panel.add(lblv);

		
		/********event*********/
		JButton btnStudent = new JButton("學生");
        btnStudent.setBounds(36, 71, 85, 23);
        btnStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SLoginUI().setVisible(true);
                dispose();
                System.out.println("Student clicked");
            }
        });
        panel.add(btnStudent);

        //  教師
        JButton btnTeacher = new JButton("教師");
        btnTeacher.setBounds(158, 71, 85, 23);
        btnTeacher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	JOptionPane.showMessageDialog(
            	        UserChooseUI.this,
            	        "教師端功能尚未開放（Coming Soon）",
            	        "",
            	        JOptionPane.INFORMATION_MESSAGE
            	    );
            }
        });
        panel.add(btnTeacher);
    }
	
	
}
