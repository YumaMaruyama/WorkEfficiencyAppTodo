package com.example.demo.login.domain.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UsersSearchForm {

	//Dateでtypemismatchが出たら日付の/と-が逆

	//groups属性にインターフェースのクラスを指定することで、フィールドとグループの紐付けができる
	//NotNull nullでないことをチェック　NotBlank Null、空文字、空白spaceでないことをチェック　Email 表記制限　Length　文字列の長さが指定した範囲内かどうか
	//Pattern 指定した正規表現かどうか  AssertFalse falseのみ可能
	//@NotBlank(groups = ValidGroup1.class)
	//@Email(groups = ValidGroup2.class)
	@Length(max=35)
	private String user_id;

	//@NotBlank(groups = ValidGroup1.class)
	@Length(max=25)
	private String userName;

	//@NumberFormatは指定されたフォーマットの文字列を数値型に変換できる
	@DateTimeFormat(pattern = "yyyy-MM-dd") //フィールドに付けると、画面から渡されてきた、文字列を日付型に
	//変換してくれる　pattern属性にどのようなフォーマットをつけるか指定する。　
	//@NotNull(groups = ValidGroup1.class)
	private Date birthdayA;

	//@NumberFormatは指定されたフォーマットの文字列を数値型に変換できる
	@DateTimeFormat(pattern = "yyyy-MM-dd") //フィールドに付けると、画面から渡されてきた、文字列を日付型に
	//変換してくれる　pattern属性にどのようなフォーマットをつけるか指定する。　
	//@NotNull(groups = ValidGroup1.class)
	private Date birthdayZ;

//	@Min(value = 20, groups = ValidGroup2.class)
//	@Max(value = 100, groups = ValidGroup2.class)
//	private String ageAA;
//
//	@Min(value = 20, groups = ValidGroup2.class)
//	@Max(value = 100, groups = ValidGroup2.class)
//	private String ageZZ;

	//@NumberFormatは指定されたフォーマットの文字列を数値型に変換できる
	@DateTimeFormat(pattern = "yyyy-MM-dd") //フィールドに付けると、画面から渡されてきた、文字列を日付型に
	//変換してくれる　pattern属性にどのようなフォーマットをつけるか指定する。　
	//@NotNull(groups = ValidGroup1.class)
	private Date hireDateAA;

	//@NumberFormatは指定されたフォーマットの文字列を数値型に変換できる
	@DateTimeFormat(pattern = "yyyy-MM-dd") //フィールドに付けると、画面から渡されてきた、文字列を日付型に
	//変換してくれる　pattern属性にどのようなフォーマットをつけるか指定する。　
	//@NotNull(groups = ValidGroup1.class)
	private Date hireDateZZ;

	private String MaleFemale;
}
