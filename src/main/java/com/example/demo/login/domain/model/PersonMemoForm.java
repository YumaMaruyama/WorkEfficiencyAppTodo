package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PersonMemoForm {

	private int id;
	@NotBlank(groups = ValidGroup1.class)
	@Length(max=300,groups = ValidGroup2.class)
	private String memo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finished_date;

}
