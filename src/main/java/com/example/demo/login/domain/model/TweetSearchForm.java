package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TweetSearchForm {

	private int id;

	@Length(max = 20)
	private String user_id;

	@Length(max = 100)
	private String contents;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateA;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateZ;
}
