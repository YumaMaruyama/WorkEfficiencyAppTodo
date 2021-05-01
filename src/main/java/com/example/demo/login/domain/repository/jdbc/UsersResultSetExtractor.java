package com.example.demo.login.domain.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.demo.login.domain.model.UsersDTO;
//ResultSetExtractorはしていることはRowMapperと似ている　ResultSetExtractor<List<T>>を実装
//＜T＞の部分には任意の型を入れる（今回はUserDTO）
//あとはextracData()メソッドをOverrideする　そのあとRowMapperでResultSetとオブジェクトのマッピングを行う
public class UsersResultSetExtractor implements ResultSetExtractor<List<UsersDTO>> {

	@Override
	public List<UsersDTO> extractData(ResultSet rs) throws SQLException,DataAccessException {

		//Users格納用List
		List<UsersDTO> usersList = new ArrayList<>();

		//取得件数用のloop
		while(rs.next()) {

			//Listに追加するインスタンスを生成する
			UsersDTO usersdto = new UsersDTO();

			//取得したレコードをUsersDTOインスタンスにセット
			usersdto.setUser_id(rs.getString("user_id"));
			usersdto.setPassword(rs.getString("password"));
			usersdto.setUser_name(rs.getString("user_name"));
			usersdto.setBirthday(rs.getDate("birthday"));
			usersdto.setAge(rs.getInt("age"));
			usersdto.setMaleFemale(rs.getBoolean("MaleFemale"));
			usersdto.setRole(rs.getString("role"));

			//ListにUsersDTOを追加
			usersList.add(usersdto);
		}

		//一件もなかった場合は例外を渡す
		if(usersList.size() == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		return usersList;
	}
}
