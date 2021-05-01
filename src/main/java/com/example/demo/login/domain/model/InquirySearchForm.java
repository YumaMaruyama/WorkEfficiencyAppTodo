package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class InquirySearchForm {


private int id;

//@NotBlank(groups = ValidGroup1.class)
@Length(max=80)

private String title;
//@NotBlank(groups = ValidGroup1.class)
@Length(max=110)
private String content;

@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date registration_dateA;//search用
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date registration_dateZ;//search用
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date registration_dateM;//search用
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date finished_dateA;//search用
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date finished_dateZ;//search用
@DateTimeFormat(pattern = "yyyy-MM-dd")
private String finished_dateT;//search用
}
