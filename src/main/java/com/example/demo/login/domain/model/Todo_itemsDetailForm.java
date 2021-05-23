package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Todo_itemsDetailForm {

	private int id;
	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 1, max = 20, groups = ValidGroup2.class)
	private String item_name;
	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 1, max = 25, groups = ValidGroup2.class)
	private String user_name;
	private Date registration_date;
	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date expire_date;

}
