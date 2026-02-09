package vo.teacher;

import java.io.Serializable;

public class ViewTeacherRevenue implements Serializable{
	private int teacher_id;          
    private String teacher_name;     
    private String report_month;     
    private long total_lessons;    
    private int total_points_earned;
	public ViewTeacherRevenue() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getReport_month() {
		return report_month;
	}
	public void setReport_month(String report_month) {
		this.report_month = report_month;
	}
	public long getTotal_lessons() {
		return total_lessons;
	}
	public void setTotal_lessons(long total_lessons) {
		this.total_lessons = total_lessons;
	}
	public int getTotal_points_earned() {
		return total_points_earned;
	}
	public void setTotal_points_earned(int total_points_earned) {
		this.total_points_earned = total_points_earned;
	}
    
}