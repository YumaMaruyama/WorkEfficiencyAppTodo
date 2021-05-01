package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkaddtoForm {


	private int id;

	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 1,max = 50,groups = ValidGroup2.class)
	private String item_name;

	//必須入力
	@NotBlank(groups = ValidGroup1.class)
	@Length(min=1,max=25,groups = ValidGroup2.class)
	private String user_name;

	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date expire_date;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date registration_date;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date finished_date;

	@Length(max = 65,groups = ValidGroup2.class)
	private String details;

	@Length(max = 65,groups = ValidGroup2.class)
	private String details2;

	@Length(max = 65,groups = ValidGroup2.class)
	private String details3;



}
