package model;

import java.io.Serializable;

public class Teacher implements Serializable{
	private int id;
	private String account;
	private String password;
	private String teacher_name;
	private int point_per_lesson;
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Teacher(String account, String password, String teacher_name, int point_per_lesson) {
		super();
		this.account = account;
		this.password = password;
		this.teacher_name = teacher_name;
		this.point_per_lesson = point_per_lesson;
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
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public int getPoint_per_lesson() {
		return point_per_lesson;
	}
	public void setPoint_per_lesson(int point_per_lesson) {
		this.point_per_lesson = point_per_lesson;
	}
}
