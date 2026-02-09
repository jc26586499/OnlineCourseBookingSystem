package model;

import java.io.Serializable;

public class TeacherSlots implements Serializable{
	private int id;
	private int t_id;
	private String slot_datetime;
	private boolean is_available;
	public TeacherSlots() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TeacherSlots(int t_id, String slot_datetime, boolean is_available) {
		super();
		this.t_id = t_id;
		this.slot_datetime = slot_datetime;
		this.is_available = is_available;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public String getSlot_datetime() {
		return slot_datetime;
	}
	public void setSlot_datetime(String slot_datetime) {
		this.slot_datetime = slot_datetime;
	}
	public Boolean getIs_available() {
		return is_available;
	}
	public void setIs_available(Boolean is_available) {
		this.is_available = is_available;
	}
	
}
