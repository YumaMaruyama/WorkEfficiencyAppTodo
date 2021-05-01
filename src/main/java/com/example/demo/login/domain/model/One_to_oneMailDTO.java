package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class One_to_oneMailDTO {

	private int id;

	private String user_id;

	private Date registration_date;

	private String mail;

	private String sender;

	private String user_name;
}
