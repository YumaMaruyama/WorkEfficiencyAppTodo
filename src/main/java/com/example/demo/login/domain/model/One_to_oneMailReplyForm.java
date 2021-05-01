package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class One_to_oneMailReplyForm {

	private int id;

	private String user_id;

	private String user_name;
	@NotBlank
	@Length(max=320)
	private String mail;

	private Date registration_date;

	private String sender;
}
