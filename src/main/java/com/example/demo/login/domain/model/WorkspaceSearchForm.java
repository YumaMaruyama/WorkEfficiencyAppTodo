package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class WorkspaceSearchForm {

	//@NotBlank(groups = ValidGroup1.class)
	@Length(max = 50)
	private String item_name;

	//必須入力
	//@NotBlank(groups = ValidGroup1.class)
	@Length(min=0,max=20)
	private String user_name;

	//@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expire_dateA;

	//@NotNull(groups = ValidGroup1.class)
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
	//private boolean finished_dateM;
}
