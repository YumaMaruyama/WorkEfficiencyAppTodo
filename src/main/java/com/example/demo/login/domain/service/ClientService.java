package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.ClientDTO;
import com.example.demo.login.domain.repository.ClientDao;

@Service
public class ClientService {

	@Autowired
	ClientDao dao;

	public int count() {
		return dao.count();
	}

	public int updateOne(ClientDTO clientdto) {
		return dao.updateOne(clientdto);
	}

	public int insertOne(ClientDTO clientdto) {
		return dao.insertOne(clientdto);
	}

	public int deleteOne(int id) {
		return dao.deleteOne(id);
	}

	public ClientDTO selectOne(int id) {
		return dao.selectOne(id);
	}

	public List<ClientDTO> selectMany() {
		return dao.selectMany();
	}

	public List<ClientDTO> search(String company, String address, String user_name, Date registration_dateFrom,
			Date registration_dateTo, String telephone, String mailAddress) {
		return dao.search(company, address, user_name, registration_dateFrom, registration_dateTo, telephone,
				mailAddress);
	}

	public void clientCsvOut() {
		dao.clientCsvOut();
	}

	public byte[] file(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}
}
