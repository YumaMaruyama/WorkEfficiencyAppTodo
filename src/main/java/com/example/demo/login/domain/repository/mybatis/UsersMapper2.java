package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.login.domain.model.UsersDTO;

@Mapper
public interface UsersMapper2 {

	//登録用メソッド
	public boolean insert(UsersDTO usersdto);

	//一件検索用メソッド
	public UsersDTO selectOne(String user_id);

	//全件検索用メソッド
	public List<UsersDTO> selectMany();

	//一件更新メソッド
	public boolean updateOne(UsersDTO usersdto);

	//一件削除用メソッド
	public boolean deleteOne(String user_id);
}
