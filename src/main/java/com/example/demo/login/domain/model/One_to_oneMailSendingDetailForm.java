package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class One_to_oneMailSendingDetailForm {

	private int id;

	private String mail;

	private String registration_date;

	private String sender;

	private String user_name;

}
