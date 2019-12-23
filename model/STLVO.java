package model;

public class STLVO {
	private int no;
	private String date;
	private String time;
	private String reservation;
	public STLVO(int no, String date, String time, String reservation) {
		super();
		this.no = no;
		this.date = date;
		this.time = time;
		this.reservation = reservation;
	}
	public STLVO(String date, String time, String reservation) {
		super();
		this.date = date;
		this.time = time;
		this.reservation = reservation;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getReservation() {
		return reservation;
	}
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}
	public int getNo() {
		return no;
	}
	@Override
	public String toString() {
		return "STLVO [no=" + no + ", date=" + date + ", time=" + time + ", reservation=" + reservation + "]";
	}
}
