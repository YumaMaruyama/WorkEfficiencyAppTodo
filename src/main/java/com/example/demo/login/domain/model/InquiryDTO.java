package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class InquiryDTO {

	private int id;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private Date registration_date;
	private Date finished_date;
	@Email
	private String mail;
	private String user_id;
}
