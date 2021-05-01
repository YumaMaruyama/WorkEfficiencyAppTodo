package com.example.demo.login.domain.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.UsersDTO;

@Repository("UsersDaoJdbcImpl3")
public class UsersDaoJdbcImpl3 extends UsersDaoJdbcImpl{


	@Autowired
	private JdbcTemplate jdbc;

	//ユーザー一件取得
	@Override
	public UsersDTO selectOne(String user_id) {

		//一件取得用SQL
		String sql = "select * from Users where user_id = ?";

		//BeanPropertyRowMapperはデータベースから取得してきたカラム名と同一のフィールド名が自動で
		//マッピングしてくれる　RowMapperのようにどのカラムにどのフィールドを一致させるかする必要がない
		//RowMapperの生成
		RowMapper<UsersDTO> rowMapper = new BeanPropertyRowMapper<UsersDTO>(UsersDTO.class);

		//SQL実行
		return jdbc.queryForObject(sql,rowMapper,user_id);
	}


}
