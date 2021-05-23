package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ClientAddtoForm {

	private int id;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 20, groups = ValidGroup2.class)
	private String user_name;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 35, groups = ValidGroup2.class)
	private String mailaddress;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 35, groups = ValidGroup2.class)
	private String address;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 15, groups = ValidGroup2.class)
	private String telephone;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registration_date;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 25, groups = ValidGroup2.class)
	private String company;

	private String is_deleted;
}
