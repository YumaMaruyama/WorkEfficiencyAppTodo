package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class One_to_oneMailForm {

	private int id;

	private String mail;

	private Date registration_date;

	private String user_id;

	private String sender;
}
