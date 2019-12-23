package model;

public class HRVVO {
	private int no;
	private String checkin;
	private String checkout;
	private String room;
	private String id;
	private String dogname;
	private String breed;
	private String gender;
	private String age;
	private String neuter;
	private String special;
	
	public HRVVO(int no, String checkin, String checkout, String room, String id, String dogname, String breed,
			String gender, String age, String neuter, String special) {
		super();
		this.no = no;
		this.checkin = checkin;
		this.checkout = checkout;
		this.room = room;
		this.id = id;
		this.dogname = dogname;
		this.breed = breed;
		this.gender = gender;
		this.age = age;
		this.neuter = neuter;
		this.special = special;
	}
	public HRVVO(String checkin, String checkout, String room, String id, String dogname, String breed, String gender,
			String age, String neuter, String special) {
		super();
		this.checkin = checkin;
		this.checkout = checkout;
		this.room = room;
		this.id = id;
		this.dogname = dogname;
		this.breed = breed;
		this.gender = gender;
		this.age = age;
		this.neuter = neuter;
		this.special = special;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDogname() {
		return dogname;
	}
	public void setDogname(String dogname) {
		this.dogname = dogname;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getNeuter() {
		return neuter;
	}
	public void setNeuter(String neuter) {
		this.neuter = neuter;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public int getNo() {
		return no;
	}
	@Override
	public String toString() {
		return "HRVVO [no=" + no + ", checkin=" + checkin + ", checkout=" + checkout + ", room=" + room + ", id=" + id
				+ ", dogname=" + dogname + ", breed=" + breed + ", gender=" + gender + ", age=" + age + ", neuter="
				+ neuter + ", special=" + special + "]";
	}
	
}
