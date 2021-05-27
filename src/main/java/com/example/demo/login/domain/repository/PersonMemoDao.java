package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.PersonMemoDTO;

public interface PersonMemoDao {

	public int count() throws DataAccessException;

	public int insert(PersonMemoDTO personmemodto) throws DataAccessException;

	public List<PersonMemoDTO> selectMany(String getName) throws DataAccessException;//Authentication user_id

	public PersonMemoDTO selectOne(int id) throws DataAccessException;

	public PersonMemoDTO selectOneCompleted(String id) throws DataAccessException;

	public int updateOne(PersonMemoDTO personmemodto) throws DataAccessException;

	public int completed(int id, Date finished_date) throws DataAccessException;

	public int deleteOne(int id) throws DataAccessException;

	public List<PersonMemoDTO> search(String memo, Date registration_dateA, Date registration_dateZ,
			Date finished_dateA, Date finished_dateZ, String finished_dateT, String getName) throws DataAccessException;

	public void personMemoCsvOut(String getName);
}
