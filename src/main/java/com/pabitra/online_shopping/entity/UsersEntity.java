package com.pabitra.online_shopping.entity;

public class UsersEntity {
	private int userId = -1;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;
	private int role_id = 2;
	private int active = 2;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "UsersEntity [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone="
				+ phone + ", password=" + password + ", role_id=" + role_id + ", active=" + active + "]";
	}
}
