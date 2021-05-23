package com.example.demo.login.domain.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Todo_itemsDTO {

	private int id;
	private String user_name;
	private String item_name;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date registration_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date expire_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date finished_date;
	private String details;
	private String details2;
	private String details3;
	private int is_deleted;
	private Date create_date_time;
	private Date update_date_time;

}
