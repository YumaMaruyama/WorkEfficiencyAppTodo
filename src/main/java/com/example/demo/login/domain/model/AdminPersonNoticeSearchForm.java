package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AdminPersonNoticeSearchForm {
	@Length(max = 150)
	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateA;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateZ;
}
