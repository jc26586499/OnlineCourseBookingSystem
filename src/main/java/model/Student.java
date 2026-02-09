package model;

import java.io.Serializable;

public class Student implements Serializable{
	private int id;
	private String account;
	private String password;
	private String student_name;
	private int student_points;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String account, String password, String student_name, int student_points) {
		super();
		this.account = account;
		this.password = password;
		this.student_name = student_name;
		this.student_points = student_points;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public int getStudent_points() {
		return student_points;
	}
	public void setStudent_points(int student_points) {
		this.student_points = student_points;
	}
}
