package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PersonUsersNoticeSendingSearchForm {

	private int id;
	@Length(max = 150)
	private String content;
	@Length(max = 20)
	private String user_id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateFrom;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateTo;

	private String is_deleted;
}
