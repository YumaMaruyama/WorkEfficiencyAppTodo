package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class PersonUsersNoticeDTO {

	private int id;

	private String user_id;

	private String content;

	private Date registration_date;
}
