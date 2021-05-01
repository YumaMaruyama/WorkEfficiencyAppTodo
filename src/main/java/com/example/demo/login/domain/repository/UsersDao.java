package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.UsersDTO;

public interface UsersDao {

	//DataAccessException
	//Usersテーブルの件数を取得
	public int count() throws DataAccessException;

	public int countPersonUsersNotice() throws DataAccessException;

	public int check(String user_id) throws DataAccessException;

	//Usersテーブルにデータを一件insert
	public int insertOne(UsersDTO usersdto) throws DataAccessException;

	//Usersテーブルのデータを一件取得
	public UsersDTO selectOne(String user_id) throws DataAccessException;

	public UsersDTO selectTwo(String id) throws DataAccessException;

	//Usersテーブルのデータを全件取得
	public List<UsersDTO> selectMany(String admin) throws DataAccessException;

	//Usersテーブルのデータを全件取得　personUsersNotice用
	public List<UsersDTO> selectManyUsersNotice(String admin) throws DataAccessException;

	public List<UsersDTO> selectManyOne_to_oneMail(String getName) throws DataAccessException;

	//Usersテーブルのデータを一件更新
	public int updateOne(UsersDTO usersdto) throws DataAccessException;

	//Usersテーブルのデータを一件削除
	public int deleteOne(String user_id) throws DataAccessException;

	//Usersテーブルのデータからsearch
	public List<UsersDTO> search(String userId,String userName,Date birthdayAA,Date birthdayZZ,Date hireDateAA, Date hireDateZZ, int maleFemaleSearch,String admin);

	//Usersテーブルのデータからsearch　personUsersNotice用
	public List<UsersDTO> searchPersonUsersNotice(String user_id,String user_name,String admin);

	public List<UsersDTO> searchOne_to_oneMailNotice(String user_id,String user_name,String admin,String getName);

	//SQL取得結果をサーバーにCSVで保存する
	public void usersCsvOut() throws DataAccessException;

}
