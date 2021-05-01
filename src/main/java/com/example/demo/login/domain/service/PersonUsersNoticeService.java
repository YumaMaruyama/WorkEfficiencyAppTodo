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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.PersonUsersNoticeDTO;
import com.example.demo.login.domain.repository.PersonUsersNoticeDao;
@Transactional
@Service
public class PersonUsersNoticeService {

	@Autowired
	@Qualifier("PersonUsersNoticeDaoJdbcImpl")
	PersonUsersNoticeDao dao;

	public int count(String getName) {
		return dao.count(getName);
	}

	public int countSending() {
		return dao.countSending();
	}

	//insertメソッド　personUsersNotice用
		public boolean insertOne(PersonUsersNoticeDTO personusersnoticedto) {
			int rowNumber = dao.insertOne(personusersnoticedto);

			boolean result = false;
			if(rowNumber > 0) {
				result = true;
			}

			return result;

		}

		public List<PersonUsersNoticeDTO> selectMany() {
			return dao.selectMany();
		}
		public List<PersonUsersNoticeDTO> selectMany(String getName) {
			return dao.selectMany(getName);
		}

		public PersonUsersNoticeDTO selectOne(int Id) {

			return dao.selectOne(Id);


		}

		public PersonUsersNoticeDTO selectOneSendingDetail(int id) {
			return dao.selectOneSendingDetail(id);
		}

		public int deleteOne(int id) {
			return dao.deleteOne(id);

		}

		public int deleteOneSendingDetail(int id) {
			return dao.deleteOneSendingDetail(id);
		}

		public List<PersonUsersNoticeDTO> search(String content,Date registration_dateA,Date registration_dateZ,String getName) {
			return dao.search(content,registration_dateA,registration_dateZ,getName);
		}

		public List<PersonUsersNoticeDTO> searchSending(String user_id,String content,Date registration_dateFrom,Date registration_dateTo) {
			return dao.searchSending(user_id,content,registration_dateFrom,registration_dateTo);
		}

		public void personUsersNoticeCsvOut() {
			System.out.println("personUsersNoticeCsvOut到達");
			//CSV出力
			dao.personUsersNoticeCsvOut();
		}

		//サーバーに保存されているファイルを取得して、byte配列に変換する
		public byte[] getFile(String fileName) throws IOException {

			System.out.println("personUsersNoticeServiceGetFile到達");
			//ファイルシステム（デフォルト）の取得
			FileSystem fs = FileSystems.getDefault();

			//ファイル取得
			Path p = fs.getPath(fileName);

			System.out.println("p" + p);
			//ファイルをbyte配列に変換
			byte[] bytes = Files.readAllBytes(p);
			System.out.println("bytes" + bytes);

			return bytes;
		}


}
