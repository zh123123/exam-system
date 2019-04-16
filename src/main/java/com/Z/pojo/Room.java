package com.Z.pojo;

public class Room {
	private String room;
	private int count;
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Room [room=" + room + ", count=" + count + "]";
	}
	
	
}
