package com.example.simourapp;

public class Notif {
	
	protected int id;
	protected String message, item;
	
	public Notif(int id, String message, String item) {
		super();
		this.id = id;
		this.message = message;
		this.item = item;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	

}
