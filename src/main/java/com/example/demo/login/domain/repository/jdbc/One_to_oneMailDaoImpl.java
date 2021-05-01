package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.One_to_oneMailDTO;
import com.example.demo.login.domain.repository.One_to_oneMailDao;

@Repository
public class One_to_oneMailDaoImpl implements One_to_oneMailDao {

	@Autowired
	JdbcTemplate jdbc;

	public int count() throws DataAccessException {

		int count = jdbc.queryForObject("select count(*)from one_to_onemail", Integer.class);

		return count;
	}

	public int countSending(String getName) throws DataAccessException {
		int count = jdbc.queryForObject(
				"select count(*)from one_to_oneMail where is_deleted = 0 and user_id != ? and sender = ?",
				Integer.class, getName, getName);

		return count;
	}

	@Override
	public int insertOne(One_to_oneMailDTO one_to_onemaildto, String getName) throws DataAccessException {

		int rowNumber = jdbc.update("insert into one_to_oneMail (user_id,"
				+ " mail,"
				+ " sender,"
				+ " registration_date)"
				+ " values(?,?,?,?)", one_to_onemaildto.getUser_id(), one_to_onemaildto.getMail(), getName,
				one_to_onemaildto.getRegistration_date());

		return rowNumber;
	}

	public int insertOneReply(One_to_oneMailDTO one_to_onemaildto, String getName) throws DataAccessException {

		int rowNumber = jdbc.update("insert into one_to_oneMail (user_id,"
				+ " mail,"
				+ " sender,"
				+ " registration_date)"
				+ " values(?,?,?,?)", one_to_onemaildto.getSender(), one_to_onemaildto.getMail(), getName,
				one_to_onemaildto.getRegistration_date());

		return rowNumber;
	}

	public One_to_oneMailDTO selectOneSendingDetail(int id) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap(
				"select one_to_oneMail.id,one_to_oneMail.mail,one_to_oneMail.registration_date,one_to_oneMail.user_name,users.user_name as UN from one_to_oneMail JOIN users ON one_to_oneMail.user_id = users.user_id where one_to_oneMail.id = ?",
				id);
		One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();
		one_to_onemaildto.setId((int) map.get("id"));

		one_to_onemaildto.setMail((String) map.get("mail"));
		one_to_onemaildto.setRegistration_date((Date) map.get("registration_date"));
		one_to_onemaildto.setUser_name((String) map.get("UN"));

		System.out.println("one_to_onemaildto.getUser_name()  " + one_to_onemaildto.getUser_name());
		return one_to_onemaildto;
	}

	public List<One_to_oneMailDTO> selectMany(String getName) throws DataAccessException {
		List<Map<String, Object>> getOne_to_onemailList = jdbc.queryForList(
				"select one_to_onemail.id, one_to_onemail.mail, one_to_onemail.sender, one_to_onemail.registration_date, users.user_name from one_to_onemail JOIN users ON one_to_onemail.sender = users.user_id where one_to_onemail.user_id = ?",
				getName);

		List<One_to_oneMailDTO> one_to_onemailList = new ArrayList<>();

		for (Map<String, Object> map : getOne_to_onemailList) {
			One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();

			one_to_onemaildto.setId((int) map.get("id"));
			one_to_onemaildto.setMail((String) map.get("mail"));
			one_to_onemaildto.setSender((String) map.get("sender"));
			one_to_onemaildto.setRegistration_date((Date) map.get("registration_date"));
			one_to_onemaildto.setUser_name((String) map.get("user_name"));
			one_to_onemailList.add(one_to_onemaildto);
		}
		return one_to_onemailList;
	}

	public One_to_oneMailDTO selectOneReply(int id) {
		Map<String, Object> map = jdbc.queryForMap(
				"select one_to_onemail.id,one_to_onemail.mail,one_to_onemail.user_name,one_to_onemail.registration_date,users.user_name as UN from one_to_oneMail JOIN users ON one_to_onemail.sender = users.user_id where one_to_onemail.id = ?",
				id);

		One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();
		one_to_onemaildto.setMail((String) map.get("mail"));
		one_to_onemaildto.setRegistration_date((Date) map.get("registration_date"));
		one_to_onemaildto.setUser_name((String) map.get("UN"));

		return one_to_onemaildto;
	}

	public List<One_to_oneMailDTO> selectManySending(String getName) {
		List<Map<String, Object>> one_to_oneMailList = jdbc.queryForList(
				"select one_to_oneMail.id,one_to_oneMail.mail,one_to_oneMail.registration_date,one_to_oneMail.sender,one_to_oneMail.user_id, users.user_name from one_to_oneMail JOIN users ON one_to_oneMail.user_id = users.user_id where one_to_oneMail.is_deleted = 0 and sender = ?",
				getName);
		List<One_to_oneMailDTO> list = new ArrayList<>();
		for (Map<String, Object> map : one_to_oneMailList) {
			One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();

			one_to_onemaildto.setId((int) map.get("id"));
			one_to_onemaildto.setMail((String) map.get("mail"));
			one_to_onemaildto.setRegistration_date((Date) map.get("registration_date"));
			one_to_onemaildto.setSender((String) map.get("sender"));
			one_to_onemaildto.setUser_id((String) map.get("user_id"));
			one_to_onemaildto.setUser_name((String) map.get("user_name"));

			list.add(one_to_onemaildto);
		}
		return list;
	}

	public One_to_oneMailDTO selectOne(String id) {
		Map<String, Object> map = jdbc.queryForMap(
				"select one_to_oneMail.id,one_to_oneMail.mail,one_to_oneMail.registration_date,users.user_name from one_to_oneMail JOIN users ON one_to_onemail.sender = users.user_id where one_to_oneMail.id = ?",
				id);

		One_to_oneMailDTO one_to_onmailedto = new One_to_oneMailDTO();
		one_to_onmailedto.setId((int) map.get("id"));
		one_to_onmailedto.setMail((String) map.get("mail"));
		one_to_onmailedto.setRegistration_date((Date) map.get("registration_date"));
		one_to_onmailedto.setUser_name((String) map.get("user_name"));

		return one_to_onmailedto;
	}

	public int deleteOne(int id) {
		int rowNumber = jdbc.update("delete from one_to_oneMail where id = ?", id);
		System.out.println("rowNumberの中身" + rowNumber);
		return rowNumber;
	}

	public int deleteOneSending(int id) {
		int rowNumber = jdbc.update("update one_to_oneMail set is_deleted = 1 where id = ?", id);

		return rowNumber;
	}

	public List<One_to_oneMailDTO> search(String user_name, String mail, Date registration_dateFrom,
			Date registration_dateTo, String getUser_id, String getUser_id2) {
		System.out.println("One_to_oneMail到達");

		StringBuilder getSql = new StringBuilder();
		List<Object> getList = new ArrayList<>();

		getSql.append(
				"select one_to_onemail.id,one_to_onemail.mail,one_to_onemail.sender,one_to_onemail.registration_date,users.user_name from one_to_onemail JOIN users ON one_to_onemail.sender = users.user_id where one_to_onemail.sender != ? and one_to_onemail.user_id = ?");
		getList.add(getUser_id);
		getList.add(getUser_id2);

		if ((user_name != null) && (!user_name.isEmpty())) {
			getSql.append(" and users.user_name like ?");
			getList.add("%" + user_name + "%");
		}
		if ((mail != null) && (!mail.isEmpty())) {
			getSql.append(" and one_to_onemail.mail like ?");
			getList.add("%" + mail + "%");
		}

		if ((registration_dateFrom != null) && (registration_dateTo != null)) {
			getSql.append(" and one_to_onemail.registration_date BETWEEN ? and ?");
			getList.add(registration_dateFrom);
			getList.add(registration_dateTo);
		}
		if ((registration_dateFrom != null) && (registration_dateTo == null)) {
			getSql.append(" and one_to_onemail.registration_date >= ?");
			getList.add(registration_dateFrom);
		}
		if ((registration_dateFrom == null) && (registration_dateTo != null)) {
			getSql.append(" and one_to_onemail.registration_date <= ?");
			getList.add(registration_dateTo);
		}

		System.out.println("getSql" + getSql);
		System.out.println("getList" + getList);
		String setSql = getSql.toString();
		System.out.println("setSql  " + setSql);
		Object[] setList = getList.toArray(new Object[getList.size()]);
		System.out.println("setList  " + setList);
		List<Map<String, Object>> getSearchList = jdbc.queryForList(setSql, setList);

		System.out.println("getSearchList  " + getSearchList);
		List<One_to_oneMailDTO> list = new ArrayList<>();
		for (Map<String, Object> map : getSearchList) {
			One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();
			one_to_onemaildto.setUser_name((String) map.get("user_name"));
			one_to_onemaildto.setMail((String) map.get("mail"));
			one_to_onemaildto.setRegistration_date((Date) map.get("registration_date"));

			list.add(one_to_onemaildto);

		}
		System.out.println("list  " + list);
		return list;

	}

	public List<One_to_oneMailDTO> searchSending(String user_name, String mail, Date registration_dateFrom,
			Date registration_dateTo, String getName) {

		StringBuilder sql = new StringBuilder();
		List<Object> list = new ArrayList<>();

		sql.append(
				"select one_to_oneMail.id,one_to_oneMail.mail,one_to_oneMail.registration_date,users.user_name from one_to_oneMail JOIN users ON one_to_oneMail.user_id = users.user_id where one_to_oneMail.is_deleted = 0 and one_to_oneMail.user_id != ? and one_to_onemail.sender = ?");
		list.add(getName);
		list.add(getName);
		if ((user_name != null) && (!user_name.isEmpty())) {
			sql.append(" and users.user_name like ?");
			list.add("%" + user_name + "%");
		}

		if ((mail != null) && (!mail.isEmpty())) {
			sql.append(" and one_to_oneMail.mail like ?");
			list.add("%" + mail + "%");
		}

		if ((registration_dateFrom != null) && (registration_dateTo != null)) {
			sql.append(" and one_to_oneMail.registration_date BETWEEN ? AND ?");
			list.add(registration_dateFrom);
			list.add(registration_dateTo);
		}
		if ((registration_dateFrom != null) && (registration_dateTo == null)) {
			sql.append(" and one_to_oneMail.registration_date > ?");
			list.add(registration_dateFrom);
		}
		if ((registration_dateFrom == null) && (registration_dateTo != null)) {
			sql.append(" and one_to_oneMail.registration_date < ?");
			list.add(registration_dateTo);
		}

		System.out.println("sql  " + sql);
		System.out.println("list  " + list);
		String getSql = sql.toString();
		System.out.println("getSql  " + sql);
		Object[] getList = list.toArray(new Object[list.size()]);
		System.out.println("getList  " + getList);
		List<Map<String, Object>> searchList = jdbc.queryForList(getSql, getList);

		System.out.println("searchList  " + searchList);
		List<One_to_oneMailDTO> list2 = new ArrayList<>();
		for (Map<String, Object> map : searchList) {
			One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();
			one_to_onemaildto.setId((int) map.get("id"));
			one_to_onemaildto.setUser_id((String) map.get("user_id"));
			one_to_onemaildto.setUser_name((String) map.get("user_name"));
			one_to_onemaildto.setMail((String) map.get("mail"));
			one_to_onemaildto.setRegistration_date((Date) map.get("registration_date"));

			list2.add(one_to_onemaildto);
		}
		return list2;
	}

	public void one_to_oneMailCsvOut() throws DataAccessException {

		String sql = "select * from one_to_oneMail where user_id != ? and sending = ?";

		One_to_oneMailRowCallbackHandler handler = new One_to_oneMailRowCallbackHandler();

		jdbc.query(sql, handler);

	}

}
