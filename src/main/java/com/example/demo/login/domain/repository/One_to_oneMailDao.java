package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.One_to_oneMailDTO;

public interface One_to_oneMailDao {

	public int count() throws DataAccessException;

	public int countSending(String getName) throws DataAccessException;

	public int insertOne(One_to_oneMailDTO one_to_onemaildto,String getName) throws DataAccessException;

	public int insertOneReply(One_to_oneMailDTO one_to_onemaildto,String getName) throws DataAccessException;

	public One_to_oneMailDTO selectOneSendingDetail(int id) throws DataAccessException;

	public List<One_to_oneMailDTO> selectMany(String getName) throws DataAccessException;

	public One_to_oneMailDTO selectOneReply(int id) throws DataAccessException;

	public List<One_to_oneMailDTO> selectManySending(String getName) throws DataAccessException;

	public One_to_oneMailDTO selectOne(String id) throws DataAccessException;

	public int deleteOne(int id) throws DataAccessException;

	public int deleteOneSending(int id) throws DataAccessException;

	public List<One_to_oneMailDTO> search(String user_name,String mail,Date registration_dateFrom,Date registration_dateTo,String getUser_id,String getUser_id2);

	public List<One_to_oneMailDTO> searchSending(String user_name,String mail,Date registration_dateFrom,Date registration_dateTo,String getName) throws DataAccessException;

	public void one_to_oneMailCsvOut();

}

