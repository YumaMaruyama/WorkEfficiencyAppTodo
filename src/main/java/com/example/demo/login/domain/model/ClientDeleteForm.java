package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class ClientDeleteForm {

	private int id;

	private String user_name;

	private String mailaddress;

	private String address;

	private String telephone;

	private String is_deleted;

	private Date registration_date;

	private String company;
}
