package model;

import java.io.Serializable;

public class Bookings implements Serializable {
	private int id;
	private int s_id;
	private int t_id;
	private int slot_id;
	private String status;
	private String zoom_link;
	private String created_at;
	public Bookings() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bookings(int s_id, int t_id, int slot_id, String status, String zoom_link, String created_at) {
		super();
		this.s_id = s_id;
		this.t_id = t_id;
		this.slot_id = slot_id;
		this.status = status;
		this.zoom_link = zoom_link;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public int getSlot_id() {
		return slot_id;
	}
	public void setSlot_id(int slot_id) {
		this.slot_id = slot_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getZoom_link() {
		return zoom_link;
	}
	public void setZoom_link(String zoom_link) {
		this.zoom_link = zoom_link;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
