package com.example.demo.login.domain.model;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class WorkaddtoDetailForm {

	private int id;

	@Length(max=65,groups = ValidGroup2.class)
	private String details;
	@Length(max=65,groups = ValidGroup2.class)
	private String details2;
	@Length(max=65,groups = ValidGroup2.class)
	private String details3;
}
