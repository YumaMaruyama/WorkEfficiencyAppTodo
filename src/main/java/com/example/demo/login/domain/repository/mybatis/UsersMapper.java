package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.login.domain.model.UsersDTO;

@Mapper//MyBatisでSQLを実行するクラスにはつける
public interface UsersMapper {

	//変数の指定↓　InsertやSelectなどのアノテーションをつける　その引数にSQL文をセットします　これがMyBatisの使い方


	//登録用メソッド
	@Insert("insert into users("
			+ " user_id,"
			+ " password,"
			+ " user_name,"
			+ " birthday,"
			+ " age,"
			+ " MaleFemale,"
			+ " role)"
			+ " values("
			+ " #{user_id},"
			+ " #{password},"
			+ " #{user_name},"
			+ " #{birthday},"
			+ " #{age},"
			+ " #{MaleFemale},"
			+ " #{role})")
	public boolean insert(UsersDTO usersdto);

	//Select文を実行してその戻り値をUserなどの参照型にしているメソッドには注意する　テーブルのカラム名とクラスのフィールド名を一致させないといけない
	//Usersテーブルのカラム名はuser_id UsersDTOクラスのフィールド名はuser_idならいいが片方がuserIdなどならSQL文にAS句を使ってカラム名を変更する

	//一件検索用メソッド
	@Select("select user_id AS user_id,"
			+ " password,"
			+ " user_name,"
			+ " birthday,"
			+ " age,"
			+ " MaleFemale,"
			+ " role"
			+ " from users"
			+ " where user_id = #{user_id}")
	public UsersDTO selectOne(String user_id);


	//全件検索用メソッド
	@Select("select user_id AS user_id,"
			+ " password,"
			+ " birthday,"
			+ " age,"
			+ " MaleFemale,"
			+ " role"
			+ " from users")
	public List<UsersDTO> selectMany();

	//一件更新用メソッド
	@Update("update users set"
			+ " password = #{password},"
			+ " user_name = #{user_name},"
			+ " birthday = #{birthday},"
			+ " age = #{age},"
			+ " MaleFemale = #{MaleFemale}"
			+ " where user_id = #{user_id}")
	public boolean updateOne(UsersDTO usersdto);

	//一件削除用メソッド
	@Delete("DELETE FROM users where user_id = #{user_id}")
	public boolean deleteOne(String user_id);
}