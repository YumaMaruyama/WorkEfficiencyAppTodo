package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class AdminDTO {
	private int id;
	private String contents;
	private Date registration_date;

}
