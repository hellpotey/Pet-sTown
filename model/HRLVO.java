package model;

public class HRLVO {
	private int no;
	private String checkin;
	private String checkout;
	private String room;
	private String reservation;
	public HRLVO(int no, String checkin, String checkout, String room, String reservation) {
		super();
		this.no = no;
		this.checkin = checkin;
		this.checkout = checkout;
		this.room = room;
		this.reservation = reservation;
	}
	public HRLVO(String checkin, String checkout, String room, String reservation) {
		super();
		this.checkin = checkin;
		this.checkout = checkout;
		this.room = room;
		this.reservation = reservation;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
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
		return "HRLVO [no=" + no + ", checkin=" + checkin + ", checkout=" + checkout + ", room=" + room
				+ ", reservation=" + reservation + "]";
	}
}
