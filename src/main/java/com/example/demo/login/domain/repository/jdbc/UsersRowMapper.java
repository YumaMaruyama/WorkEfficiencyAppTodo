package com.example.demo.login.domain.repository.jdbc;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.login.domain.model.UsersDTO;
//RowMapperを使うには、RowMapper<?>インターフェースをimplementsする
//？の部分にはマッピングを使うJavaオブジェクトクラスを指定する（今回はUsersDTO）
public class UsersRowMapper implements RowMapper<UsersDTO>{
//RowMapperをimplementsするとmapRowメソッドをOverrideする　引数のResultSetにはSELECT結果
//が入っており、mapRowメソッド内で、ResultSetとUsersDTOクラスのマッピングをする
//そしてUsersDTOクラスのインスタンスをreturnすればRowMapperは完成
	@Override
	public UsersDTO mapRow(ResultSet rs,int rowNum) throws SQLException {

		UsersDTO usersdto = new UsersDTO();

		//ResultSetの取得結果をUserインスタンスにセット
		usersdto.setUser_id(rs.getString("user_id"));
		usersdto.setPassword(rs.getString("password"));
		usersdto.setUser_name(rs.getString("user_name"));
		usersdto.setBirthday(rs.getDate("birthday"));
		usersdto.setAge(rs.getInt("age"));
		usersdto.setMaleFemale(rs.getBoolean("MaleFemale"));
		usersdto.setRole(rs.getString("role"));

		return usersdto;
	}
}
