package vo.student;

import java.io.Serializable;

public class ViewStudentSchedule implements Serializable{
	private int booking_id;
    private int student_id;     
    private String student_name;  
    private int teacher_id;       
    private String teacher_name;
    private String lesson_time;  
    private String booking_status; 
    private String zoom_link;
	public ViewStudentSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getLesson_time() {
		return lesson_time;
	}
	public void setLesson_time(String lesson_time) {
		this.lesson_time = lesson_time;
	}
	public String getBooking_status() {
		return booking_status;
	}
	public void setBooking_status(String booking_status) {
		this.booking_status = booking_status;
	}
	public String getZoom_link() {
		return zoom_link;
	}
	public void setZoom_link(String zoom_link) {
		this.zoom_link = zoom_link;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public int getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	
	

}
