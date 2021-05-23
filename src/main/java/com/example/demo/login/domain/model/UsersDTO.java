package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class UsersDTO {

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
