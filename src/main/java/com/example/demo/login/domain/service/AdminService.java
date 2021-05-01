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

import com.example.demo.login.domain.model.AdminDTO;
import com.example.demo.login.domain.repository.AdminDao;

@Service
public class AdminService {

	@Autowired
	AdminDao dao;

	public  boolean insertOne(AdminDTO admindto) {
		int rowNumber = dao.insertOne(admindto);

		 boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}
		return result;

	}


	public AdminDTO selectOne(int id,String contents,Date registration_date) {
		return dao.selectOne(id,contents,registration_date);
	}

	public List<AdminDTO> selectMany() {
		return dao.selectMany();
	}

	public boolean updateOne(AdminDTO admindto) {
		System.out.println("updateOneSer到達");
		int rowNumber = dao.updateOne(admindto);

		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
	public boolean deleteOne(int id) {

		int rowNumber = dao.deleteOne(id);

		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
		public List<AdminDTO> search(String contentsA,Date registration_dateAA,Date registration_dateZZ) {
			return dao.search(contentsA,registration_dateAA,registration_dateZZ);
		}

	public int count() {
		return dao.count();
	}

	public void adminCsvOut() {
		System.out.println("adminCsvOut到達");
		//CSV出力
		dao.adminCsvOut();
	}

	//サーバーに保存されているファイルを取得して、byte配列に変換する
	public byte[] getFile(String fileName) throws IOException {

		System.out.println("adminServiceGetFile到達");
		//ファイルシステム（デフォルト）の取得
		FileSystem fs = FileSystems.getDefault();

		//ファイル取得
		Path p = fs.getPath(fileName);

		//ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}
}
