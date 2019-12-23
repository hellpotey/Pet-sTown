package model;

public class SRVVO {
	private int no;
	private String date;
	private String time;
	private String id;
	private String dogname;
	private String breed;
	private String gender;
	private String age;
	private String neuter;
	private String special;
	private String cutstyle;

	public SRVVO(int no, String date, String time, String id, String dogname, String breed, String gender,
			String age, String neuter, String special, String cutstyle) {
		super();
		this.no = no;
		this.date = date;
		this.time = time;
		this.id = id;
		this.dogname = dogname;
		this.breed = breed;
		this.gender = gender;
		this.age = age;
		this.neuter = neuter;
		this.special = special;
		this.cutstyle = cutstyle;
	}

	public SRVVO(String date, String time, String id, String dogname, String breed, String gender, String age,
			String neuter, String special, String cutstyle) {
		super();
		this.date = date;
		this.time = time;
		this.id = id;
		this.dogname = dogname;
		this.breed = breed;
		this.gender = gender;
		this.age = age;
		this.neuter = neuter;
		this.special = special;
		this.cutstyle = cutstyle;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public String getCutstyle() {
		return cutstyle;
	}

	public void setCutstyle(String cutstyle) {
		this.cutstyle = cutstyle;
	}

	@Override
	public String toString() {
		return "STVVO [no=" + no + ", date=" + date + ", time=" + time + ", id=" + id + ", dogname=" + dogname
				+ ", breed=" + breed + ", gender=" + gender + ", age=" + age + ", neuter=" + neuter + ", special="
				+ special + ", cutstyle=" + cutstyle + "]";
	}
}