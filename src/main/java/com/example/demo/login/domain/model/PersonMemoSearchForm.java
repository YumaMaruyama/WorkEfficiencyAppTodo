package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PersonMemoSearchForm {

	private int id;
	//@NotBlank(groups = ValidGroup1.class)
	@Length(max=300)
	private String memo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateA;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateZ;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finished_dateA;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finished_dateZ;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String finished_dateT;





}
