package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TweetForm {

	private int id;

	//@NotBlank(groups = ValidGroup1.class)
	@Length( max = 20, groups = ValidGroup2.class)
	private String user_id;

	@Length( max = 50, groups = ValidGroup2.class)
	private String user_id2;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 100, groups = ValidGroup2.class)
	private String contents;

//	@NotBlank(groups = ValidGroup1.class)
//	@Length(min = 1, max = 50, groups = ValidGroup2.class)
//	private String tweetContents;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date registration_date;

//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date registration_dateA;//search用
//
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date registration_dateZ;//search用
}
