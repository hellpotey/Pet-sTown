package model;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class DogVO {
	private int no;
	private String id;
	private String dogname;
	private String breed;
	private String gender;
	private String age;
	private String neuter;
	private String special;
	private String localurl;

	
	public int getNo() {
		return no;
	}





	@Override
	public String toString() {
		return "DogVO [no=" + no + ", id=" + id + ", dogname=" + dogname + ", breed=" + breed + ", gender=" + gender
				+ ", age=" + age + ", neuter=" + neuter + ", special=" + special + ", localurl=" + localurl + "]";
	}





	public DogVO(int no, String id, String dogname, String breed, String gender, String age, String neuter,
			String special, String localurl) {
		super();
		this.no = no;
		this.id = id;
		this.dogname = dogname;
		this.breed = breed;
		this.gender = gender;
		this.age = age;
		this.neuter = neuter;
		this.special = special;
		this.localurl = localurl;
	}





	public DogVO(String id, String dogname, String breed, String gender, String age, String neuter, String special,
			String localurl) {
		super();
		this.id = id;
		this.dogname = dogname;
		this.breed = breed;
		this.gender = gender;
		this.age = age;
		this.neuter = neuter;
		this.special = special;
		this.localurl = localurl;
	}





	public String getLocalurl() {
		return localurl;
	}





	public void setLocalurl(String localurl) {
		this.localurl = localurl;
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


	

	
}
