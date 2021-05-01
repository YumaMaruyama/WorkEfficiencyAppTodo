package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Todo_itemsDTO;

public interface Todo_itemsDao {

	public int count() throws DataAccessException;

	public int insertOne(Todo_itemsDTO todo_itemsdto) throws DataAccessException;

	public Todo_itemsDTO selectOne(String user_id) throws DataAccessException;

	public Todo_itemsDTO selectOneX(int id) throws DataAccessException;

	public List<Todo_itemsDTO> selectMany() throws DataAccessException;

	public int updateOne(Todo_itemsDTO todo_itemsdto) throws DataAccessException;

	public int updateOnex(Todo_itemsDTO todo_itemsdto) throws DataAccessException;

	public int deleteOne(int id) throws DataAccessException;

	public int completedOne(int id,Date finished_date) throws DataAccessException;

	public void todo_itemsCsvOut() throws DataAccessException;

	public List <Todo_itemsDTO> search(String item_name,String user_name,Date registration_date,Date registration_dateA,Date expire_date,Date expire_dateA,Date finished_date,Date finished_dateA,String finished_dateM) throws DataAccessException;
//,String finished_dateM
}
