package com.example.demo.login.domain.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ClientDTO {

	private int id;

	private String user_name;

	private String mailaddress;

	private String address;

	private String telephone;

	private String is_deleted;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date registration_date;

	private String company;
}
