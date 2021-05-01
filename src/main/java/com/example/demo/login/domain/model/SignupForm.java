package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupForm {

	//groups属性にインターフェースのクラスを指定することで、フィールドとグループの紐付けができる
	//NotNull nullでないことをチェック　NotBlank Null、空文字、空白spaceでないことをチェック　Email 表記制限　Length　文字列の長さが指定した範囲内かどうか
	//Pattern 指定した正規表現かどうか  AssertFalse falseのみ可能
	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 8,max = 35,groups = ValidGroup2.class)
	@Email(groups = ValidGroup3.class)
	private String user_id;

	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 8, max = 100,groups = ValidGroup2.class )
	@Pattern(regexp = "^[a-zA-Z0-9]+$",groups = ValidGroup3.class)
	private String password;

	@NotBlank(groups = ValidGroup1.class)
	@Length(max = 20,groups = ValidGroup2.class)
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
