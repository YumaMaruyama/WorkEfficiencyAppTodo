package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class InquiryForm {

	private int id;

@NotBlank(groups = ValidGroup1.class)
@Length(min=1,max=80,groups = ValidGroup2.class)

	private String title;
@NotBlank(groups = ValidGroup1.class)
@Length(min=1,max=100,groups = ValidGroup2.class)
	private String content;
@DateTimeFormat(pattern = "yyyy-MM-dd")//ユーザーが入力したデータと同じformatにする。type="date"ではhh:mmなどは追加できない
	private Date registration_date;

@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finished_date;
@Email
@Length(max=35,groups = ValidGroup2.class)
	private String mail;

	private String user_id;

	private String user_idT;

}
