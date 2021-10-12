package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.PersonUsersNoticeDTO;
import com.example.demo.login.domain.repository.PersonUsersNoticeDao;

@Repository("PersonUsersNoticeDaoJdbcImpl")
public class PersonUsersNoticeDaoJdbcImpl implements PersonUsersNoticeDao {

	@Autowired 
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	public int count(String getName) {

		int count = jdbc.queryForObject("select count(*)from personusersnotice where user_id = ?", Integer.class,
				getName);

		return count;
	}

	public int countSending() {
		int count = jdbc.queryForObject("select count(*)from personusersnotice where is_deleted = 0", Integer.class);

		return count;
	}

	//Usersテーブルに一件データをinsert personUsersNotice用
	public int insertOne(PersonUsersNoticeDTO personUsersNoticedto) {

		int rowNumber = jdbc.update("insert into personusersnotice(content,"
				+ " user_id)"
				+ " values(?,?)", personUsersNoticedto.getContent(), personUsersNoticedto.getUser_id());

		return rowNumber;

	}

	public PersonUsersNoticeDTO selectOneSendingDetail(int id) {
		Map<String, Object> map = jdbc.queryForMap(
				"select personusersnotice.id,personusersnotice.user_id,personusersnotice.content,personusersnotice.registration_date,users.user_name from personusersnotice join users on personusersnotice.user_id = users.user_id where personusersnotice.id = ?",
				id);
		PersonUsersNoticeDTO personusersnoticedto = new PersonUsersNoticeDTO();
		personusersnoticedto.setId((int) map.get("id"));
		personusersnoticedto.setUser_id((String) map.get("user_name"));
		personusersnoticedto.setContent((String) map.get("content"));
		personusersnoticedto.setRegistration_date((Date) map.get("registration_date"));

		return personusersnoticedto;
	}

	public List<PersonUsersNoticeDTO> selectMany() {
		List<Map<String, Object>> personusersnoticeList = jdbc.queryForList(
				"select personusersnotice.id,personusersnotice.content,personusersnotice.registration_date,personusersnotice.user_id,users.user_name from personusersnotice join users on personusersnotice.user_id = users.user_id where personusersnotice.is_deleted = 0");
		List<PersonUsersNoticeDTO> list = new ArrayList<>();
		for (Map<String, Object> map : personusersnoticeList) {
			PersonUsersNoticeDTO personusersnoticedto = new PersonUsersNoticeDTO();

			personusersnoticedto.setId((int) map.get("id"));
			personusersnoticedto.setContent((String) map.get("content"));
			personusersnoticedto.setRegistration_date((Date) map.get("registration_date"));
			personusersnoticedto.setUser_id((String) map.get("user_name"));

			list.add(personusersnoticedto);
		}

		return list;
	}

	public List<PersonUsersNoticeDTO> selectMany(String getName) {

		List<Map<String, Object>> personusersnoticeList = jdbc
				.queryForList("select * from personusersnotice where user_id = ?", getName);

		System.out.println("personusersnoticeList" + personusersnoticeList);

		List<PersonUsersNoticeDTO> getList = new ArrayList<>();
		for (Map<String, Object> map : personusersnoticeList) {
			PersonUsersNoticeDTO personusersnoticedto = new PersonUsersNoticeDTO();

			personusersnoticedto.setId((int) map.get("id"));
			personusersnoticedto.setUser_id((String) map.get("user_id"));
			personusersnoticedto.setContent((String) map.get("content"));
			personusersnoticedto.setRegistration_date((Date) map.get("registration_date"));

			getList.add(personusersnoticedto);
		}
		return getList;
	}

	public PersonUsersNoticeDTO selectOne(int Id) {

		Map<String, Object> map = jdbc.queryForMap("select * from personusersnotice where id = ?", Id);

		System.out.println("map" + map);

		PersonUsersNoticeDTO personusersnoticedto = new PersonUsersNoticeDTO();

		personusersnoticedto.setId((int) map.get("id"));
		personusersnoticedto.setContent((String) map.get("content"));
		personusersnoticedto.setRegistration_date((Date) map.get("registration_date"));

		return personusersnoticedto;

	}

	public int deleteOne(int id) {
		int rowNumber = jdbc.update("delete from personusersnotice where id = ?", id);

		return rowNumber;
	}

	public int deleteOneSendingDetail(int id) {
		System.out.println("deleteOneSendingDetail到達");
		int rowNumber = jdbc.update("update personusersnotice set is_deleted = 1 where id = ?", id);

		return rowNumber;
	}

	public List<PersonUsersNoticeDTO> search(String content, Date registration_dateFrom, Date registration_dateTo,
			String user_id) {
		StringBuilder sql = new StringBuilder();
		List<Object> list = new ArrayList<>();

		//sql文の比較は＝一つ
		sql.append("select * from personusersnotice where is_deleted = 0 and user_id = ?");
		list.add(user_id);

		if ((content != null) && (!content.isEmpty())) {
			sql.append(" and content like ?");
			list.add("%" + content + "%");
		}
		if ((registration_dateFrom != null)) {
			sql.append(" and registration_date > ?");
			list.add(registration_dateFrom);
		}
		if ((registration_dateTo != null)) {
			sql.append(" and registration_date < ?");
			list.add(registration_dateTo);
		}

		System.out.println("sql" + sql);
		System.out.println("list" + list);
		Object[] getList = list.toArray(new Object[list.size()]);
		System.out.println("getList" + getList);
		String getSql = sql.toString();

		List<Map<String, Object>> getSearchSql = jdbc.queryForList(getSql, getList);
		System.out.println("getSearchSql" + getSearchSql);

		List<PersonUsersNoticeDTO> getDtoList = new ArrayList<>();

		for (Map<String, Object> map : getSearchSql) {
			PersonUsersNoticeDTO personusersnoticedto = new PersonUsersNoticeDTO();

			personusersnoticedto.setId((int)map.get("id"));
			personusersnoticedto.setContent((String) map.get("content"));
			personusersnoticedto.setRegistration_date((Date) map.get("registration_Date"));

			getDtoList.add(personusersnoticedto);
		}

		return getDtoList;
	}

	public List<PersonUsersNoticeDTO> searchSending(String user_id, String content, Date registration_dateFrom,
			Date registration_dateTo) {
		System.out.println("registration_dateFrom" + registration_dateFrom);
		StringBuilder sql = new StringBuilder();
		List<Object> list = new ArrayList<>();

		sql.append("select personusersnotice.id,personusersnotice.content,personusersnotice.registration_date,personusersnotice.user_id,users.user_name from personusersnotice join users on personusersnotice.user_id = users.user_id where personusersnotice.is_deleted = 0");

		if((user_id != null) && (!user_id.isEmpty())) {
			sql.append(" and users.user_name like ?");
			list.add("%" + user_id + "%");
		}

		if((content != null) && (!content.isEmpty())) {
			sql.append(" and personusersnotice.content like ?");
			list.add("%" + content + "%");
		}

		if((registration_dateFrom != null) && (registration_dateTo != null)) {
			sql.append(" and personusersnotice.registration_date BETWEEN ? AND ?");
			list.add(registration_dateFrom);
			list.add(registration_dateTo);
		}

		if((registration_dateFrom != null) && (registration_dateTo == null)) {
			sql.append(" and personusersnotice.registration_date >= ?");
			list.add(registration_dateFrom);
		}

		if((registration_dateFrom == null) && (registration_dateTo != null)) {
			sql.append(" and personusersnotice.registration_date <= ?");
			list.add(registration_dateTo);
		}

		System.out.println("sql   " + sql);
		System.out.println("list   " + list);

		String getSql = sql.toString();
		System.out.println("getSql   " + getSql);
		Object[] getList = list.toArray(new Object[list.size()]);
		System.out.println("getList  " + getList);
		List<Map<String,Object>> personusersnoticeList = jdbc.queryForList(getSql,getList);

		System.out.println("personusersnoticeList   " + personusersnoticeList);

		List<PersonUsersNoticeDTO> arrayList = new ArrayList<>();

		for (Map<String, Object> map : personusersnoticeList) {

			PersonUsersNoticeDTO personusersnoticedto = new PersonUsersNoticeDTO();

			personusersnoticedto.setId((int) map.get("id"));
			personusersnoticedto.setUser_id((String) map.get("user_name"));
			personusersnoticedto.setContent((String) map.get("content"));
			personusersnoticedto.setRegistration_date((Date) map.get("registration_date"));

			arrayList.add(personusersnoticedto);
		}
		return arrayList;
	}

	@Override
	public void personUsersNoticeCsvOut() throws DataAccessException {

		System.out.println("personUsersNoticeCsvOutImpl到達");		//inquiryテーブルのデータを全件取得するSQL
		String sql = "select * from personUsersnotice";
		System.out.println("sql" + sql);
		//ResultSetExceptionの生成
		PersonUsersNoticeRowCallbackHandler handler = new PersonUsersNoticeRowCallbackHandler();

		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}

	@Override
	public void personUsersNoticeSendingCsvOut(String getName) throws DataAccessException {

		System.out.println("personUsersNoticeSendingCsvOutImpl到達");

		String sql = "select personusersnotice.content,personusersnotice.registration_date,users.user_name from personusersnotice join users on personusersnotice.user_id = users.user_id where personusersnotice.is_deleted = 0";
		System.out.println("sql" + sql);
		//ResultSetExceptionの生成
		PersonUsersNoticeSendingRowCallbackHandler handler = new PersonUsersNoticeSendingRowCallbackHandler();

		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}



}
