package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class PersonUsersNoticeSendingForm {

	private int id;

	private String content;

	private String is_deleted;

	private Date registration_dateFrom;

	private Date registration_dateTo;

	private String user_id;

	private String user_name;
}
