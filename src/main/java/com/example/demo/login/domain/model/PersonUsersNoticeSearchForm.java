package com.example.demo.login.domain.model;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PersonUsersNoticeSearchForm {

	@Length(max=35)
	private String user_id;
	@Length(max=20)
	private String user_name;
}
