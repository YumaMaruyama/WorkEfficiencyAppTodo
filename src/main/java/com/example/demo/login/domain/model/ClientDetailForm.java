package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ClientDetailForm {

	private int id;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 20, groups = ValidGroup2.class)
	private String user_name;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 35, groups = ValidGroup2.class)
	private String mailaddress;

	private String address;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 15, groups = ValidGroup2.class)
	private String telephone;

	private String company;

	private Date registration_date;
}
