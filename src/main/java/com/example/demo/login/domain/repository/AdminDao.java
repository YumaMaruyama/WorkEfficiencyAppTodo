package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.AdminDTO;

public interface AdminDao {

	public int insertOne(AdminDTO adminDTO) throws DataAccessException;

	public AdminDTO selectOne(int id,String contents,Date registration_date ) throws DataAccessException;

	public List<AdminDTO> selectMany() throws DataAccessException;

	public int updateOne(AdminDTO admindto) throws DataAccessException;

	public int deleteOne(int id) throws DataAccessException;

	public int count() throws DataAccessException;

	public List<AdminDTO> search(String contentsA,Date registration_dateAA,Date registration_dateZZ) throws DataAccessException ;

	public void adminCsvOut() throws DataAccessException;
}
