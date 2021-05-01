package com.example.demo.login.domain.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
@Data
public class GuideForm {

	private int id;

	@NotBlank(groups = ValidGroup1.class)
	@Length(min=1,max=20,groups = ValidGroup2.class)
	private String contents;


	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 1,max = 100,groups = ValidGroup2.class)
	private String guide;
}
