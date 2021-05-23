package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class WorkspaceSearchForm {

	
	@Length(max = 50)
	private String item_name;


	@Length(min=0,max=20)
	private String user_name;

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expire_dateA;

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expire_dateZ;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateA;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateZ;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finished_dateA;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finished_dateZ;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String finished_dateM;
	
}
