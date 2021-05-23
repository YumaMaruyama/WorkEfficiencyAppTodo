package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UsersSearchForm {

	//Dateでtypemismatchが出たら日付の/と-が逆

	@Length(max = 35)
	private String user_id;

	@Length(max = 25)
	private String userName;

	//@NumberFormatは指定されたフォーマットの文字列を数値型に変換できる
	@DateTimeFormat(pattern = "yyyy-MM-dd") //フィールドに付けると、画面から渡されてきた、文字列を日付型に
	//変換してくれる　pattern属性にどのようなフォーマットをつけるか指定する。　
	//@NotNull(groups = ValidGroup1.class)
	private Date birthdayA;

	@DateTimeFormat(pattern = "yyyy-MM-dd")

	private Date birthdayZ;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hireDateAA;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hireDateZZ;

	private String MaleFemale;
}
