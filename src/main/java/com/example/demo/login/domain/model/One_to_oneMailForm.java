package com.example.demo.login.domain.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class One_to_oneMailForm {

	private int id;

	private String mail;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date registration_date;

	private String user_id;

	private String sender;
}
