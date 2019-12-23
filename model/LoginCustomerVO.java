package model;

public class LoginCustomerVO {
	private int no;
	private String id;
	private String password;
	private String name;
	private String phone;
	private String email;
	public LoginCustomerVO(int no, String id, String password, String name, String phone, String email) {
		super();
		this.no = no;
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	public LoginCustomerVO(String id, String password, String name, String phone, String email) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "LoginCustomerVO [no=" + no + ", id=" + id + ", password=" + password + ", name=" + name + ", phone="
				+ phone + ", email=" + email + "]";
	}
	
}
