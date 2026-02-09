package model;

import java.io.Serializable;

public class PointTransactions implements Serializable{
	private int id;
	private int s_id;
	private int amount_paid;
	private int points_added;
	private String tx_date;
	public PointTransactions() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PointTransactions(int s_id, int amount_paid, int points_added) {
		super();
		this.s_id = s_id;
		this.amount_paid = amount_paid;
		this.points_added = points_added;
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
	public int getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(int amount_paid) {
		this.amount_paid = amount_paid;
	}
	public int getPoints_added() {
		return points_added;
	}
	public void setPoints_added(int points_added) {
		this.points_added = points_added;
	}
	public String getTx_date() {
		return tx_date;
	}
	public void setTx_date(String tx_date) {
		this.tx_date = tx_date;
	}
	
}
