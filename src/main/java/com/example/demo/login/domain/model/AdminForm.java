package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class AdminForm {

	private int id;
	@NotBlank(groups = ValidGroup1.class)
	@Length(max=190,groups = ValidGroup2.class)
	private String contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date registration_date;
}
