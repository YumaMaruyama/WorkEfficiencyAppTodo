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

	//	public Todo_items(int id,String user_Name,String item_name,Date registration_date,Date expire_date,Date finished_date,int is_deleted,Date create_date_time,Date update_date_time ) {
	//		this.id = id;
	//		this.user_Name = user_Name;
	//		this.item_name = item_name;
	//		this.registration_date = registration_date;
	//		this.expire_date = expire_date;
	//		this.finished_date = finished_date;
	//		this.is_deleted = is_deleted;
	//		this.create_date_time = create_date_time;
	//		this.update_date_time = update_date_time;
	//	}

	//	public int getId() {
	//		return id;
	//	}
	//
	//	public String getUser_Name() {
	//		return user_Name;
	//	}
	//
	//	public String getItem_name() {
	//		return item_name;
	//	}
	//
	//	public Date getRegistration_date() {
	//		return registration_date;
	//	}
	//
	//	public Date getExpire_date() {
	//		return expire_date;
	//	}
	//
	//	public Date getFinished_date() {
	//		return finished_date;
	//	}
	//
	//	public int getIs_deleted() {
	//		return is_deleted;
	//	}
	//
	//	public Date getCreate_date_time() {
	//		return create_date_time;
	//	}
	//
	//	public Date getUpdate_date_time() {
	//		return update_date_time;
	//	}
}
