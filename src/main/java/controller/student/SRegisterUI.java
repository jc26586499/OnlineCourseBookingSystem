package controller.student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.student.StudentDao;
import dao.student.impl.StudentDaoImpl;
import model.Student;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class SRegisterUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAccount;
	private JPasswordField txtPassword;
	private JTextField txtName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SRegisterUI frame = new SRegisterUI();
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
	public SRegisterUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(43, 10, 363, 230);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblAccount = new JLabel("帳號:");
		lblAccount.setBounds(104, 73, 27, 15);
		panel.add(lblAccount);
		
		txtAccount = new JTextField();
		txtAccount.setBounds(141, 70, 96, 21);
		txtAccount.setColumns(10);
		panel.add(txtAccount);
		
		JLabel lblAccount_1 = new JLabel("密碼:");
		lblAccount_1.setBounds(104, 116, 27, 15);
		panel.add(lblAccount_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(141, 113, 96, 21);
		panel.add(txtPassword);
		
		JLabel lblAccount_2 = new JLabel("姓名:");
		lblAccount_2.setBounds(104, 156, 27, 15);
		panel.add(lblAccount_2);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(141, 153, 96, 21);
		panel.add(txtName);
		
		JLabel lblv = new JLabel("學生註冊頁");
		lblv.setBounds(150, 25, 60, 15);
		panel.add(lblv);
		
		

		
		/******event******/
		
		
		JButton btnBack = new JButton("回上頁");
		btnBack.setBounds(211, 197, 103, 23);
		btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SLoginUI().setVisible(true);
                dispose();
            }
        });
		panel.add(btnBack);
		
		JButton btnConfirm = new JButton("確認");
		btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String account = txtAccount.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                String name = txtName.getText().trim();

                if (account.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(SRegisterUI.this, "請輸入帳號、密碼、姓名");
                    return;
                }

                StudentDao studentDao = new StudentDaoImpl();

                // 1) 檢查帳號是否重複
                if (studentDao.existsAccount(account)) {
                    JOptionPane.showMessageDialog(SRegisterUI.this, "帳號重複，請換一個");
                    return;
                }

                // 2) 註冊（點數預設 0）
                Student s = new Student();
                s.setAccount(account);
                s.setPassword(password);
                s.setStudent_name(name);
                s.setStudent_points(0);

                int rows = studentDao.register(s);

                if (rows > 0) {
                    JOptionPane.showMessageDialog(SRegisterUI.this, "註冊成功，請重新登入");
                    new SLoginUI().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SRegisterUI.this, "註冊失敗，請稍後再試");
                }

            }
        });
       
		btnConfirm.setBounds(46, 197, 68, 23);
		panel.add(btnConfirm);
		
		
		
		JButton btnClear = new JButton("清除");
		btnClear.setBounds(129, 197, 68, 23);
		btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtAccount.setText("");
                txtPassword.setText("");
                txtName.setText("");
                txtAccount.requestFocus();
            }
        });
		panel.add(btnClear);
		
		
	}

}
