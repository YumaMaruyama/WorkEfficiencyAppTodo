package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.TweetDTO;

public interface TweetDao {

	public int count() throws DataAccessException;

	public int insertOne(TweetDTO tweetdto) throws DataAccessException;

	public TweetDTO selectOne(String id) throws DataAccessException;

	public List<TweetDTO> selectMany() throws DataAccessException;

	public int deleteOne(int id) throws DataAccessException;

	public List<TweetDTO> search(String user_id, String contents, Date registration_dateA, Date registration_dateZ)
			throws DataAccessException;


	public void tweetCsvOut() throws DataAccessException;
}
