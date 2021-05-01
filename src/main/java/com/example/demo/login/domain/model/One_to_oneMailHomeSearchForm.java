package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class One_to_oneMailHomeSearchForm {

	private int id;

	@Length(max=20)
	private String user_name;
	@Length(max=320)
	private String mail;

	private String sender;
//	@DateTimeFormat(pattern="yyyy-MM-dd")
//	private Date registration_date;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date registration_dateFrom;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date registration_dateTo;
}
