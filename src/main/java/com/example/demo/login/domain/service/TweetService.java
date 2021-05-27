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

import com.example.demo.login.domain.model.TweetDTO;
import com.example.demo.login.domain.repository.TweetDao;

@Service
public class TweetService {

	@Autowired
	TweetDao dao;

	public int count() {
		return dao.count();
	}

	public boolean insertOne(TweetDTO tweetdto) {

		int rowNumber = dao.insertOne(tweetdto);

		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	public TweetDTO selectOne(String id) {

		return dao.selectOne(id);
	}

	public List<TweetDTO> selectMany() {

		return dao.selectMany();

	}

	public void tweetCsvOut() {
		dao.tweetCsvOut();
	}

	//byteの配列型
	public byte[] file(String fileName) throws IOException {

		//ファイルシステム（デフォルト）の取得
		FileSystem fs = FileSystems.getDefault();

		//ファイル取得
		Path p = fs.getPath(fileName);

		//ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}

	public int deleteOne(int id) {

		System.out.println("tweetDeleteService到達");
		return dao.deleteOne(id);
	}

	public List<TweetDTO> search(String user_id, String contents, Date registration_dateA, Date registration_dateZ) {
		return dao.search(user_id, contents, registration_dateA, registration_dateZ);
	}
}
