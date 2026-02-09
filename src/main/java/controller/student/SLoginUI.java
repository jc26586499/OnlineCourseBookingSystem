package controller.student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.UserChooseUI;
import model.Student;
import service.student.StudentService;
import service.student.impl.StudentServiceImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class SLoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAccount;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SLoginUI frame = new SLoginUI();
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
	public SLoginUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(72, 10, 278, 243);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblv = new JLabel("學生登入頁");
		lblv.setBounds(110, 25, 60, 15);
		panel.add(lblv);
		
		JLabel lblAccount = new JLabel("帳號:");
		lblAccount.setBounds(61, 75, 38, 15);
		panel.add(lblAccount);
		
		JLabel lblAccount_1 = new JLabel("密碼:");
		lblAccount_1.setBounds(61, 117, 38, 15);
		panel.add(lblAccount_1);
		
		
		
		txtAccount = new JTextField();
		txtAccount.setBounds(97, 72, 124, 20);
		panel.add(txtAccount);
		txtAccount.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(97, 114, 124, 20);
		panel.add(txtPassword);
		
		
		
		
		
		/******event******/
	
		JButton btnLogin = new JButton("登入");
		btnLogin.setBounds(61, 165, 68, 23);
		panel.add(btnLogin);
		btnLogin.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {

		        String account = txtAccount.getText().trim();
		        String password = new String(txtPassword.getPassword()).trim();

		        if (account.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(
		                SLoginUI.this,
		                "請輸入帳號與密碼"
		            );
		            return;
		        }

		        StudentService service = new StudentServiceImpl();
		        Student student = service.login(account, password);

		        if (student != null) {
		            JOptionPane.showMessageDialog(SLoginUI.this, "登入成功");
		            SHomeUI home = new SHomeUI();
					home.setStudent(student);
					home.setVisible(true);
					dispose();
		        } else {
		            JOptionPane.showMessageDialog(SLoginUI.this, "帳號或密碼錯誤");
		        }


		        System.out.println("Login clicked: " + account);
		    }
		});
		
		
	
		JButton btnRegister = new JButton("註冊");
		btnRegister.setBounds(153, 165, 68, 23);
		panel.add(btnRegister);
		btnRegister.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        
		        new SRegisterUI().setVisible(true);
		        System.out.println("Register clicked");
		    }
		});
	
		
		JButton btnBack = new JButton("回上頁");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBack.setBounds(92, 210, 103, 23);
		panel.add(btnBack);
		btnBack.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        new UserChooseUI().setVisible(true);
		        dispose();
		    }
		});

		
	
	
	
	
	
	}
	
	
	
	
}
