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

import com.example.demo.login.domain.model.Todo_itemsDTO;
import com.example.demo.login.domain.repository.Todo_itemsDao;

@Service
public class Todo_itemsService {

	@Autowired
	Todo_itemsDao dao;

	public int count() {
		return dao.count();
	}

	//insert用メソッド
	public boolean insert(Todo_itemsDTO todo_itemsdto) {

		//insert実行
		int rowNumber = dao.insertOne(todo_itemsdto);

		//判定用変数
		boolean result = false;

		if (rowNumber > 0) {
			//insert成功
			result = true;
		}
		return result;
	}

	//全件取得用メソッド
	public List<Todo_itemsDTO> selectMany() {
		//全件取得
		return dao.selectMany();
	}

	//一件取得用メソッド
	public Todo_itemsDTO selectOne(String id) {

		//selectOne実行
		return dao.selectOne(id);

	}

	//work詳細用一件取得用メソッド
	public Todo_itemsDTO selectOneX(int id) {
		return dao.selectOneX(id);
	}

	public boolean updateOnex(Todo_itemsDTO todo_itemsdto) {

		System.out.println("Todo_itemsServiceUpdateOnex到達");
		int rowNumber = dao.updateOnex(todo_itemsdto);

		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	//一件更新用メソッド
	public boolean updateOne(Todo_itemsDTO todo_itemsdto) {

		//一件更新
		int rowNumber = dao.updateOne(todo_itemsdto);

		boolean result = false;

		//判定用変数
		if (rowNumber > 0) {

			//Update成功
			result = true;
		}

		return result;
	}

	//一件削除用メソッド
	public boolean deleteOne(int id) {

		//一件削除
		int rowNumber = dao.deleteOne(id);

		//判定用変数
		boolean result = false;

		if (rowNumber > 0) {
			//delete成功
			result = true;
		}
		return result;
	}

	//一件完了ボタン
	public boolean completedOne(int id, Date finished_date) {

		//一件登録
		int rowNumber = dao.completedOne(id, finished_date);

		boolean result = false;

		if (rowNumber > 0) {

			result = true;
		}
		return result;
	}

	public void todo_itemsCsvOut() throws DataAccessException {
		//CSV出力
		dao.todo_itemsCsvOut();
	}

	public byte[] getFile(String fileName) throws IOException {

		FileSystem fs = FileSystems.getDefault();

		Path p = fs.getPath(fileName);

		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}

	//searchメソッド
	public List<Todo_itemsDTO> search(String item_name, String user_name, Date registration_date,
			Date registration_dateA, Date expire_date, Date expire_dateA, Date finished_date, Date finished_dateA,
			String finished_dateM) throws DataAccessException {

		System.out.println("Serviceクラスのsearchメソッド到達");

		System.out.println("registration_dateFrom" + registration_date);
		System.out.println("registration_dateTo" + registration_dateA);
		System.out.println("expire_dateFrom" + expire_date);
		System.out.println("expire_dateTo" + expire_dateA);
		System.out.println("finished_dateFrom" + finished_date);
		System.out.println("finished_dateTO" + finished_dateA);
		System.out.println("finished_dateCheck" + finished_dateM);
		return dao.search(item_name, user_name, registration_date, registration_dateA, expire_date, expire_dateA,
				finished_date, finished_dateA, finished_dateM);

	}
}
