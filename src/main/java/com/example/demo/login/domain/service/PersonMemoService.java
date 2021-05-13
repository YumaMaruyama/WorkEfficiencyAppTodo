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

import com.example.demo.login.domain.model.PersonMemoDTO;
import com.example.demo.login.domain.repository.PersonMemoDao;

@Service
public class PersonMemoService {
	@Autowired
	PersonMemoDao dao;

	public int count() {
		return dao.count();
	}

	//「boolean」 のresultと合わせる
	public boolean insert(PersonMemoDTO personmemodto) {

		int rowNumber = dao.insert(personmemodto);

		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}

		return result;

	}

	//真偽を行うのでboolean
	public boolean updateOne(PersonMemoDTO personmemodto) {

		int rowNumber = dao.updateOne(personmemodto);

		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}

		return result;
	}

	public boolean completed(int id, Date finished_date) {

		int rowNumber = dao.completed(id, finished_date);

		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}

		return result;
	}

	public List<PersonMemoDTO> search(String memo, Date registration_dateA, Date registration_dateZ,
			Date finished_dateA, Date finished_dateZ, String finished_dateT,String getName) {
		System.out.println("personMemoSearchServise到達");
		return dao.search(memo, registration_dateA, registration_dateZ, finished_dateA, finished_dateZ, finished_dateT,getName);
	}

	public int deleteOne(int id) {
		System.out.println("PersonMemoServiceDelete到達");

		int rowNumber = dao.deleteOne(id);
		return rowNumber;
	}

	public List<PersonMemoDTO> selectMany(String getName) {
		return dao.selectMany(getName);
	}

	public PersonMemoDTO selectOne(int id) {
		System.out.println("PersonMemoServiceSelectOne到達");
		return dao.selectOne(id);
	}

	public PersonMemoDTO selectOneCompleted(String id) {
		System.out.println("PersonMemoServiceSelectCompleteld到達");
		return dao.selectOneCompleted(id);
	}

	public void personMemoCsvOut(String getName) {
		dao.personMemoCsvOut(getName);
	}

	public byte[] file(String fileName) throws IOException {

		//ファイルシステム（デフォルト）の取得
		FileSystem fs = FileSystems.getDefault();

		//ファイル取得
		Path p = fs.getPath(fileName);

		//ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}
}
