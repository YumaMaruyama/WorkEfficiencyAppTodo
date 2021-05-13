package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.PersonUsersNoticeDTO;

public interface PersonUsersNoticeDao {

	public int count(String getName) throws DataAccessException;

	public int countSending() throws DataAccessException;

	public int insertOne(PersonUsersNoticeDTO personUsersNoticedto) throws DataAccessException;

	public PersonUsersNoticeDTO selectOneSendingDetail(int id) throws DataAccessException;

	public List<PersonUsersNoticeDTO> selectMany() throws DataAccessException;

	public List<PersonUsersNoticeDTO> selectMany(String getName) throws DataAccessException;

	public PersonUsersNoticeDTO selectOne(int Id) throws DataAccessException;

	public int deleteOne(int id) throws DataAccessException;

	public int deleteOneSendingDetail(int id) throws DataAccessException;

	public List<PersonUsersNoticeDTO> search(String content,Date registration_dateFrom,Date registration_dateTo,String user_id) throws DataAccessException;

	public List<PersonUsersNoticeDTO> searchSending(String user_id,String content,Date registration_dateFrom,Date registration_dateTo) throws DataAccessException;

	public void personUsersNoticeCsvOut() throws DataAccessException;

	public void personUsersNoticeSendingCsvOut(String getName) throws DataAccessException;
	//public void adminPersonNoticeCsvOut() throws DataAccessException;
}
