package com.chinatvpay.bean;

public class User {

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", city=" + city
				+ ", age=" + age + "]";
	}
	// ID，唯一性
	private String id;
	// 登录ID
	private String name;
	// 用户名
	private String city;
	// 角色
	private String age;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

}
