package com.example.demo.login.domain.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.UsersDTO;

@Repository("UsersDaoJdbcImpl2")
public class UsersDaoJdbcImpl2 extends UsersDaoJdbcImpl {

	@Autowired
	private JdbcTemplate jdbc;

	//ユーザー一件取得
	@Override
	public UsersDTO selectOne(String user_id) {

		//一件取得用SQL
		String sql = "select * from Users where user_id = ?";

		//RowMapperの生成
		UsersRowMapper rowMapper = new UsersRowMapper();

		//SOL実行
		return jdbc.queryForObject(sql, rowMapper, user_id);

	}


}
