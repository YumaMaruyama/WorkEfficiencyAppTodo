package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.UsersDTO;
import com.example.demo.login.domain.repository.UsersDao;
//トランザクションを行うにはアノテーションをつける
@Transactional
@Service
public class UsersService {

	//@Autowiredと一緒に@Qualifierを使用すると、どのBeanを使用するか指定できる
	//UsersDaoのインターフェース作ったが、それを継承したクラスが一つだけであれば、Springが自動でBeanを探してくれる
	//今回はInpl Inpl2と二つあるため、つける
	@Autowired
	@Qualifier("UsersDaoJdbcImpl")
	UsersDao dao;

	//insert用メソッド
	public boolean insert(UsersDTO usersdto) {

		//insert実行　usersdtoに入ったデータを代入
		int rowNumber = dao.insertOne(usersdto);

		//もし入ってなかったとき用に使うやつ
		boolean result = false;

		if (rowNumber > 0) {
			//insert成功したらtrueを入れる
			result = true;
		}
		return result;

	}



	public int check(String user_id) {
		return dao.check(user_id);
	}

	//count用メソッド
	public int count() {
		return dao.count();

	}

	public int countPersonUsersNotice() {
		return dao.countPersonUsersNotice();
	}

	//全件取得用メソッド
	public List<UsersDTO> selectMany(String admin) {
		return dao.selectMany(admin);
	}

	//全件取得用メソッド　personUsersNotice用
	public List<UsersDTO> selectManyUsersNotice(String admin) {
		return dao.selectManyUsersNotice(admin);
	}

	public List<UsersDTO> selectManyOne_to_oneMail(String getName) {
		return dao.selectManyOne_to_oneMail(getName);
	}

	public UsersDTO selectTwo(String id) {

		return dao.selectTwo(id);
	}

	//1件取得用メソッド
	public UsersDTO selectOne(String user_id) {
		//selectOne実行
		return dao.selectOne(user_id);
	}

//	public UsersDTO selectOnename(String user_name) {
//		return dao.selectOne(user_name);
//	}

	//一件更新用メソッド
	public boolean updateOne(UsersDTO usersdto) {

		System.out.println("usersUpdateOne到達");
		//一件更新
		int rowNumber = dao.updateOne(usersdto);

		//判定用変数
		boolean result = false;

		if (rowNumber > 0) {
			//update成功
			result = true;
		}

		return result;
	}

	//一件削除用メソッド
	public boolean deleteOne(String user_id) {

		int rowNumber = dao.deleteOne(user_id);

		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}
		return result;

	}

	//searchメソッド
	public List<UsersDTO> search(String userId,String userName,Date birthdayA,Date birthdayZ,Date hireDateAA, Date hireDateZZ, int maleFemale,String admin) {
		System.out.println("usersSearchService到達");
		System.out.println("userId" + userId);
		System.out.println("userName" + userName);
		System.out.println("birthdayA" + birthdayA);
		System.out.println("birthdayZ" + birthdayZ);
		System.out.println("hireDateAA" + hireDateAA);
		System.out.println("hireDateZZ" + hireDateZZ);
		System.out.println(" maleFemaleSearch" + maleFemale);

		return dao.search(userId, userName, birthdayA, birthdayZ, hireDateAA, hireDateZZ, maleFemale,admin);

	}

	public List<UsersDTO> searchPersonUsersNotice(String user_id,String user_name,String admin) {
		System.out.println("usersSearchService到達");
		System.out.println("user_id" + user_id);
		System.out.println("user_name" + user_name);

		return dao.searchPersonUsersNotice(user_id, user_name,admin);
	}

	public List<UsersDTO> searchOne_to_oneMailNotice(String user_id,String user_name,String admin,String getName) {
		return dao.searchOne_to_oneMailNotice(user_id,user_name,admin,getName);

	}
	//ユーザー一覧をCSV出力する
	//ユーザー一覧をCSVに出力するメソッドはリポジトリークラス（UsersDaoJdbcImpl)
	//のCSV出力メソッドを呼び出している
	//ファイルを呼び出すメソッドについては引数で指定されたファイル名をサーバーから取り出して
	//ファイルの中身をbyte型の配列にしてreturnしている
	public void usersCsvOut(String user_id) throws DataAccessException {
		//CSV出力
		dao.usersCsvOut(user_id);
	}

	//サーバーに保存されているファイルを取得して、byte配列に変換する
	public byte[] getFile(String fileName) throws IOException {

		//ファイルシステム（デフォルト）の取得
		FileSystem fs = FileSystems.getDefault();

		//ファイル取得
		Path p = fs.getPath(fileName);

		//ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
	}
}
