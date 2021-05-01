package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class ClientDetailUpdateForm {

	private int id;

	private String user_name;

	private String telephone;

	private String mailaddress;
}
