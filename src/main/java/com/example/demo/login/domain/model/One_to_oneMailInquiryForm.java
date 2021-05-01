package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class One_to_oneMailInquiryForm {

	private int id;

	@Length(max = 20)
	private String user_id;

	private Date registration_date;
	@NotBlank
	@Length(max = 320)
	private String mail;

	private String sender;
}
