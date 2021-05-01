package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.InquiryDTO;
import com.example.demo.login.domain.repository.InquiryDao;

@Service
public class InquiryService {

	@Autowired
	InquiryDao dao;

	//insert用メソッド
	public boolean insert(InquiryDTO inquirydto,String user_idT) {

		int rowNumber = dao.insertOne(inquirydto,user_idT);

		//判定用変数
		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	public boolean insertLogin(InquiryDTO inquirydto) {

		int rowNumber = dao.insertOneLogin(inquirydto);

		//判定用変数
		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	//カウント用メソッド
	public int count() {
		return dao.count();

	}

	//一件取得用メソッド
	public InquiryDTO selectOne(String id) {

		return dao.selectOne(id);
	}

	//全件取得用メソッド
	public List<InquiryDTO> selectMany() {
		return dao.selectMany();
	}

	//一件削除用メソッド
	public boolean deleteOne(int id) {

		//一件削除
		int rowNumber = dao.deleteOne(id);

		//判定用変数
		boolean result = false;
		System.out.println("Sresult1" + result);
		if (rowNumber > 0) {
			//delete成功
			System.out.println("Sresult2" + result);
			result = true;

		}
		return result;

	}

	public List<InquiryDTO> search(String title,String content,Date registration_dateA,Date registration_dateZ,Date finished_dateA,Date finished_dateZ,String finished_dateT) throws DataAccessException {

		return dao.search(title,content,registration_dateA,registration_dateZ,finished_dateA,finished_dateZ,finished_dateT);
	}
	//一件完了ボタン
	//ボタン切り替えのためにidとfinished_dateも受け取る
	public boolean completedOne(int id, Date finished_date) {

		//一件登録
		int rowNumber = dao.completedOne(id,finished_date);

		boolean result = false;

		if (rowNumber > 0) {
			result = true;

		}
		return result;
	}

	//inquiry一覧をcsv出力する
	public void inquiryCsvOut() throws DataAccessException {

		//CSV出力
		dao.inquirydtoCsvOut();
	}

	//サーバーに保存されているファイルを取得して、byte配列に変換する
	public byte[] getFile(String fileName) throws IOException {

		System.out.println("inquiryService到達");
		//ファイルシステム（デフォルト）の取得
		FileSystem fs = FileSystems.getDefault();

		//ファイル取得
		Path p = fs.getPath(fileName);

		//ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}
}
