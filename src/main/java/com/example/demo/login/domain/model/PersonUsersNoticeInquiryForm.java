package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PersonUsersNoticeInquiryForm {


	private int id;

	private String user_id;
	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 150,groups = ValidGroup2.class)
	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date registration_date;
}
