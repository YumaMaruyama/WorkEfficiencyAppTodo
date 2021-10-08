package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.InquiryDTO;
import com.example.demo.login.domain.repository.InquiryDao;

@Repository
public class InquiryDaoJdbcImpl implements InquiryDao {

	@Autowired
	JdbcTemplate jdbc;

	//inquiryテーブルの件数をカウント
	@Override
	public int count() throws DataAccessException {
		//count
		int count = jdbc.queryForObject("select count(*)from inquiry", Integer.class);
		return count;
	}

	//inquiryテーブルのデータを一件登録
	@Override
	public int insertOne(InquiryDTO inquirydto, String user_id) throws DataAccessException {

		//insert
		int rowNumber = jdbc.update("insert into inquiry(id,"
				+ " title,"
				+ " content,"
				+ " mail,"
				+ " user_id)"
				+ " values(?,?,?,?,?)", inquirydto.getId(), inquirydto.getTitle(), inquirydto.getContent(),
				inquirydto.getMail(), user_id);

		return rowNumber;
	}

	//inquiryテーブルのデータを一件登録
	@Override
	public int insertOneLogin(InquiryDTO inquirydto,String user_id) throws DataAccessException {

		//insert
		int rowNumber = jdbc.update("insert into inquiry(id,"
				+ " title,"
				+ " content,"
				+ " user_id)"
				+ " values(?,?,?,?)", inquirydto.getId(), inquirydto.getTitle(), inquirydto.getContent(),
				inquirydto.getMail());

		return rowNumber;
	}

	//inquiryテーブルのデータを一件取得
	@Override
	public InquiryDTO selectOne(String id) throws DataAccessException {

		//一件取得
		Map<String, Object> map = jdbc.queryForMap("select * from inquiry"
				+ " where id = ?", id);
		InquiryDTO inquirydto = new InquiryDTO();

		//取得したデータを結果返却の変数にセットしていく
		inquirydto.setId((int) map.get("id"));
		inquirydto.setTitle((String) map.get("title"));
		inquirydto.setContent((String) map.get("content"));
		inquirydto.setRegistration_date((Date) map.get("registration_date"));
		inquirydto.setFinished_date((Date) map.get("finished_date"));

		return inquirydto;
	}

	//inquiryテーブルのデータを全件取得
	@Override
	public List<InquiryDTO> selectMany() throws DataAccessException {

		//複数件select
		//inquiryテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("select * from inquiry");

		System.out.println("getList" + getList);//getListちゃんと取れてた！！
		//結果返却用変数
		List<InquiryDTO> inquiryList = new ArrayList<>();

		System.out.println("ServiceのinquiryList" + inquiryList);
		//取得したデータを結果返却用Listに格納していく
		for (Map<String, Object> map : getList) {

			System.out.println("map" + map);//mapちゃんと取れてた！！
			InquiryDTO inquirydto = new InquiryDTO();

			//inquiryインスタンスに取得したデータをセットする
			inquirydto.setId((int) map.get("id"));
			inquirydto.setTitle((String) map.get("title"));
			inquirydto.setContent((String) map.get("content"));
			inquirydto.setMail((String) map.get("mail"));
			inquirydto.setUser_id((String) map.get("user_id"));
			inquirydto.setRegistration_date((Date) map.get("registration_date"));
			inquirydto.setFinished_date((Date) map.get("finished_date"));

			inquiryList.add(inquirydto);
		}
		System.out.println("returninquiryList" + inquiryList);
		return inquiryList;

	}

	//inquiryテーブルのデータを一件削除
	@Override
	public int deleteOne(int id) throws DataAccessException {

		//一件更新
		int rowNumber = jdbc.update("delete from inquiry where id = ?", id);
		return rowNumber;
	}

	public List<InquiryDTO> search(String title, String content, Date registration_dateA, Date registration_dateZ,
			Date finished_dateA, Date finished_dateZ, String finished_dateT) {

		System.out.println("inquirySearchDaoImpl到達");
		System.out.println(title);
		System.out.println(content);
		System.out.println(registration_dateA);
		System.out.println(registration_dateZ);
		System.out.println(finished_dateA);
		System.out.println(finished_dateZ);
		System.out.println(finished_dateT);

		StringBuilder sql = new StringBuilder();
		sql.append("select * from inquiry where is_deleted = 0");

		List<Object> list = new ArrayList<Object>();

		if ((title != null) && (!title.isEmpty())) {
			sql.append(" and title like ?");
			list.add("%" + title + "%");
		}
		if ((content != null) && (!content.isEmpty())) {
			sql.append(" and content like ?");
			list.add("%" + content + "%");
		}
		if ((registration_dateA != null) && (registration_dateZ != null)) {
			sql.append(" and registration_date BETWEEN ? AND ?");
			list.add(registration_dateA);
			list.add(registration_dateZ);
		} else if ((registration_dateA != null) && (registration_dateZ == null)) {
			sql.append(" and registration_date > ?");
			list.add(registration_dateA);
		} else if ((registration_dateA == null) && (registration_dateZ != null)) {
			sql.append(" and registration_date < ?");
			list.add(registration_dateZ);
		}
		if ((finished_dateA != null) && (finished_dateZ != null)) {
			sql.append(" and finished_date BETWEEN ? AND ?");
			list.add(finished_dateA);
			list.add(finished_dateZ);
		} else if ((finished_dateA != null) && (finished_dateZ == null)) {
			sql.append(" and finished_date > ?");
			list.add(finished_dateA);
		} else if ((finished_dateA == null) && (finished_dateZ != null)) {
			sql.append(" and finished_date < ?");
			list.add(finished_dateZ);
		}
		if (finished_dateT != null) {
			sql.append(" and finished_date is null");
		}

		System.out.println("sql" + sql);
		System.out.println("list" + list);
		//queryForListに入れるには、配列にしないとだめで、取ってきたリストをtoArrayを使用し配列に変えている
		Object[] addList = list.toArray(new Object[list.size()]);
		//取ってきたsqlをtoStringでとってきている
		String sqlTo = sql.toString();
		//MapのListにsql文とパラメーターを合わせて入れている
		List<Map<String, Object>> rowNumber = jdbc.queryForList(sqlTo, addList);
		System.out.println("rowNumber" + rowNumber);

		List<InquiryDTO> inquiryList = new ArrayList<>();

		for (Map<String, Object> map : rowNumber) {

			InquiryDTO inquirydto = new InquiryDTO();
			inquirydto.setId((int) map.get("id"));
			inquirydto.setTitle((String) map.get("title"));
			inquirydto.setContent((String) map.get("content"));
			inquirydto.setRegistration_date((Date) map.get("registration_date"));
			inquirydto.setFinished_date((Date) map.get("finished_date"));

			inquiryList.add(inquirydto);
		}
		return inquiryList;
	}

	//inquiryテーブルのfinished_dateの切り替え
	//idとfinished_dateを受け取る
	public int completedOne(int id, Date finished_date) throws DataAccessException {
		//一件設定
		System.out.println("inquiryImpl到達");
		int rowNumber = jdbc.update("update inquiry"
				+ " set"
				+ " finished_date = ?"
				+ " where id = ?", finished_date, id);//SQL文で受け取る引数
		System.out.println("implRowNumber" + rowNumber);
		return rowNumber;
	}

	//inquiryテーブルのSQL取得結果をサーバーにCSVで保存する
	@Override
	public void inquirydtoCsvOut() throws DataAccessException {

		System.out.println("inquirydtoCsvOutImpl到達");
		//inquiryテーブルのデータを全件取得するSQL
		String sql = "select * from inquiry";
		System.out.println("sql" + sql);
		//ResultSetExceptionの生成
		InquiryRowCallbackHandler handler = new InquiryRowCallbackHandler();

		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}

}
