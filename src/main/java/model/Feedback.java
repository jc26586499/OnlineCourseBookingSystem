package model;

import java.io.Serializable;

public class Feedback implements Serializable {
	private int id;
	private int b_id;
	private int rating;
	private String comment;
	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Feedback(int b_id, int rating, String comment) {
		super();
		this.b_id = b_id;
		this.rating = rating;
		this.comment = comment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getB_id() {
		return b_id;
	}
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
