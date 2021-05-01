package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.ClientDTO;

public interface ClientDao {

	public int insertOne(ClientDTO clientdto) throws DataAccessException;

	public int deleteOne(int id) throws DataAccessException;

	public int updateOne(ClientDTO clientdto) throws DataAccessException;

	public ClientDTO selectOne(int id) throws DataAccessException;

	public List<ClientDTO> selectMany() throws DataAccessException;

	public int count() throws DataAccessException;

	public List<ClientDTO> search(String company,String address,String user_name,Date registration_dateFrom,Date registration_dateTo,String telephone,String mailaddress);

	public void clientCsvOut();
}
