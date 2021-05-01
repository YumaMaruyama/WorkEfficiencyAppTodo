package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class PersonUsersNoticeSendingDetailForm {

	private int id;

	private String user_id;

	private String content;

	private Date registration_dateFrom;

	private Date registration_dateTo;
}
