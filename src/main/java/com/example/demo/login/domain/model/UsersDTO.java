package com.example.demo.login.domain.model;




import java.util.Date;

import lombok.Data;

@Data
public class UsersDTO {
//getとsetで呼び出せるクラス

	private int id;
	private String user_id;
	private String password;
	private String user_name;
	private Date birthday;
	private int age;
	private Date hireDate;
	private boolean MaleFemale;
	private String role;

	}
//	public Users(int id, String user_id, String password, String user_name, Date birthday,int age,boolean MaleFemale) {
//		this.id = id;
//		this.user_id = user_id;
//		this.password = password;
//		this.user_name = user_name;
//		this.birthday = birthday;
//		this.age = age;
//		this.MaleFemale = MaleFemale;
//	}

//	public  java.sql.Date setBirthday(java.util.Date birthday) {
//		java.sql.Date birthday1 = new java.sql.Date(birthday.getTime());
//		return birthday1;
//	}
//	public int getId() {
//		return id;
//	}
//
//	public String getUser_id() {
//		return user_id;
//	}
//	public String getPassword() {
//		return password;
//	}
//
//	public  String getUser_name() {
//		return user_name;
//	}
//
//	public Date getBirthday() {
//		return birthday;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public boolean getMaleFemale() {
//		return MaleFemale;
//	}



