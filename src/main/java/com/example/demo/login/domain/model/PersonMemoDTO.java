package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class PersonMemoDTO {

	private int id;

	private String memo;

	private Date registration_date;

	private Date finished_date;

	private String user_id;

}
