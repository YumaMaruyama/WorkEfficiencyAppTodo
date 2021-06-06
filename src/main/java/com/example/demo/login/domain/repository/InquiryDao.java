package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.InquiryDTO;

public interface InquiryDao {

	//inquiryテーブルの件数をカウント
	public int count() throws DataAccessException;

	//inquiryテーブルのデータを一件登録
	public int insertOne(InquiryDTO inquirydto, String user_id) throws DataAccessException;

	public int insertOneLogin(InquiryDTO inquirydto,String user_idT) throws DataAccessException;

	//inquiryテーブルのデータを一件取得
	public InquiryDTO selectOne(String id) throws DataAccessException;

	//inquiryテーブルのデータを全件取得
	public List<InquiryDTO> selectMany() throws DataAccessException;

	//inaquiryテーブルからsearch
	public List<InquiryDTO> search(String title, String content, Date registration_dateA, Date registration_dateZ,
			Date finished_dateA, Date finished_dateZ, String finished_dateT) throws DataAccessException;

	//inquiryテーブルのデータを一件削除
	public int deleteOne(int id) throws DataAccessException;

	//inquiryテーブルのfinished_dateを切り替え　finished_dateの値次第で切り替えるのでfinished_dateも入れる
	public int completedOne(int id, Date finished_date) throws DataAccessException;

	//inquiryテーブルのSQL取得結果をサーバーにCSVで保存する
	public void inquirydtoCsvOut() throws DataAccessException;
}
