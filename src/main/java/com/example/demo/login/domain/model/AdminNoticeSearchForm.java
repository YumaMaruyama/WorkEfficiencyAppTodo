package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class AdminNoticeSearchForm {

	private int id;
	//@NotBlank(groups = ValidGroup1.class)
	@Length(max=190)
	private String contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd")//:ss
	private Date registration_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")//:ss
	private Date registration_dateA;
	@DateTimeFormat(pattern = "yyyy-MM-dd")//:ss
	private Date registration_dateZ;
}
