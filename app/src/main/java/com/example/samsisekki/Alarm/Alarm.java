package com.example.samsisekki.Alarm;

public class Alarm {

	private int id;
	private int gc;
	private String time;
	private int weekday;

	public Alarm(int id, int gc, String time, int weekday) {
		super();
		this.id = id;
		this.gc = gc;
		this.time = time;
		this.weekday = weekday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGc() {
		return gc;
	}

	public void setGc(int gc) {
		this.gc = gc;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}
}


