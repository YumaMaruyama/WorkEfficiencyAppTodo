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

import com.example.demo.login.domain.model.One_to_oneMailDTO;
import com.example.demo.login.domain.repository.One_to_oneMailDao;

@Service
public class One_to_oneMailService {

	@Autowired
	One_to_oneMailDao dao;

	public int count() {
		return dao.count();
	}

	public int countSending(String getName) {
		return dao.countSending(getName);
	}

	public boolean insertOne(One_to_oneMailDTO one_to_onemaildto,String getName) {

	 int rowNumber = dao.insertOne(one_to_onemaildto,getName);

	 boolean result = false;
	 if(rowNumber > 0) {
		 result = true;
	 }
	 return result;
	}

	public boolean insertOneReply(One_to_oneMailDTO one_to_onemaildto,String getName) {
		int rowNumber = dao.insertOneReply(one_to_onemaildto,getName);

		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}

	public List<One_to_oneMailDTO> selectMany(String getName) {
		return dao.selectMany(getName);
	}

	public One_to_oneMailDTO selectOne(String id) {

		return dao.selectOne(id);
	}

	public One_to_oneMailDTO selectOneReply(int id) {
		return dao.selectOneReply(id);
	}

	public One_to_oneMailDTO selectOneSendingDetail(int id) {
		return dao.selectOneSendingDetail(id);
	}
	public int deleteOne(int id) {

		return dao.deleteOne(id);

	}

	public int deleteOneSending(int id) {
		return dao.deleteOneSending(id);
	}

	public List<One_to_oneMailDTO> selectManySending(String getName) {
		return dao.selectManySending(getName);
	}

	public List<One_to_oneMailDTO> search(String user_name,String mail,Date registration_dateFrom,Date registration_dateTo,String getUser_id,String getUser_id2) {

		return dao.search(user_name,mail,registration_dateFrom,registration_dateTo,getUser_id,getUser_id2);
	}

	public List<One_to_oneMailDTO> searchSending(String user_name,String mail,Date registration_dateFrom,Date registration_dateTo,String getName) {
		return dao.searchSending(user_name,mail,registration_dateFrom,registration_dateTo,getName);
	}
	public void one_to_oneMailCsvOut(String getName) {
		dao.one_to_oneMailCsvOut(getName);
	}

	public void one_to_oneMailSendingCsvOut(String getName) {
		dao.one_to_oneMailSendingCsvOut(getName);
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
