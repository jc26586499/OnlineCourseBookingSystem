package vo.student;

import java.io.Serializable;

public class ViewAvailableSlots implements Serializable{
	private int slot_id;
    private String teacher_name;
    private int cost;
    private String start_time;
	public ViewAvailableSlots() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSlot_id() {
		return slot_id;
	}
	public void setSlot_id(int slot_id) {
		this.slot_id = slot_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
    
}
