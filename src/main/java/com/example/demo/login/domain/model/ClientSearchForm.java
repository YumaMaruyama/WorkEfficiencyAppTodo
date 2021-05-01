package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ClientSearchForm {

	private int id;

	@Length(max = 20)
	private String user_name;

	@Length(max = 35)
	private String mailaddress;

	@Length(max = 35)
	private String address;

	@Length(max = 15)
	private String telephone;

	@Length(max = 25)
	private String company;


	private String is_deleted;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_date;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateFrom;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_dateTo;

}
