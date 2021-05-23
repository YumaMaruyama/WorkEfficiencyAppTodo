package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupUpdateForm {

	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String user_id;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 20, groups = ValidGroup2.class)
	private String userName;

	//@NumberFormatは指定されたフォーマットの文字列を数値型に変換できる
	@DateTimeFormat(pattern = "yyyy-MM-dd") //フィールドに付けると、画面から渡されてきた、文字列を日付型に
	//変換してくれる　pattern属性にどのようなフォーマットをつけるか指定する。　
	@NotNull(groups = ValidGroup1.class)
	private Date birthday;

	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hireDate;

	private boolean MaleFemale;
}
