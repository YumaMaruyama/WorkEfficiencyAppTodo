package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.PersonMemoDTO;
import com.example.demo.login.domain.repository.PersonMemoDao;

//Bean名をセット そうすることで@Autowiredする際、どのクラスを使うか指定できる
@Repository("PersonMemoDaoJdbcImpl")
public class PersonMemoDaoJdbcImpl implements PersonMemoDao {

	@Autowired //SpringにJdbcTemplateが入っており、Bean定義がされている このアノテーションをつけるだけで、このクラスのメソッドを使ってSQLを実行できる
	JdbcTemplate jdbc;

	public int count() {

		int count = jdbc.queryForObject("select count(*) from personmemo", int.class);

		return count;
	}

	public int insert(PersonMemoDTO personMemodto) {
		System.out.println("PersonMemoDaoJdbcImplInsert到達");

		int rowNumber = jdbc.update("insert into personmemo (id,"
				+ " memo,"
				+ " user_id)"
				+ " values(?,?,?)", personMemodto.getId(), personMemodto.getMemo(), personMemodto.getUser_id());

		return rowNumber;

	}

	public int updateOne(PersonMemoDTO personmemodto) {
		System.out.println("PersonMemoDaoJdbcImplUpdate到達");

		int rowNumber = jdbc.update("update personmemo"
				+ " set"
				+ " memo = ?"
				+ " where id = ?", personmemodto.getMemo(), personmemodto.getId());

		return rowNumber;
	}

	public int deleteOne(int id) {
		System.out.println("PersonMemoDeteleDaoImpl到達");

		int rowNumber = jdbc.update("delete from personmemo where id = ?", id);

		return rowNumber;
	}

	public List<PersonMemoDTO> selectMany(String getName) {

		System.out.println("PersonMemoDaoJdbcImpl到達");

		List<Map<String, Object>> getList = jdbc
				.queryForList("select * from personmemo where user_id = ? order by registration_date asc", getName);

		System.out.println("getList" + getList);
		List<PersonMemoDTO> personMemoList = new ArrayList<>();

		for (Map<String, Object> map : getList) {

			PersonMemoDTO personmemodto = new PersonMemoDTO();

			personmemodto.setId((int) map.get("id"));
			personmemodto.setMemo((String) map.get("memo"));
			personmemodto.setRegistration_date((Date) map.get("registration_date"));
			personmemodto.setFinished_date((Date) map.get("finished_date"));

			personMemoList.add(personmemodto);
		}

		return personMemoList;
	}

	public PersonMemoDTO selectOne(int id) {
		Map<String, Object> map = jdbc.queryForMap("select * from personmemo where id = ?", id);

		PersonMemoDTO personmemodto = new PersonMemoDTO();

		personmemodto.setId((int) map.get("id"));
		personmemodto.setMemo((String) map.get("memo"));
		personmemodto.setRegistration_date((Date) map.get("registration_date"));
		personmemodto.setFinished_date((Date) map.get("finished_date"));

		return personmemodto;
	}

	public PersonMemoDTO selectOneCompleted(String id) {
		Map<String, Object> map = jdbc.queryForMap("select * from personmemo where id = ?", id);

		PersonMemoDTO personmemodto = new PersonMemoDTO();

		personmemodto.setId((int) map.get("id"));
		personmemodto.setFinished_date((Date) map.get("finished_date"));

		return personmemodto;
	}

	@Override
	public List<PersonMemoDTO> search(String memo, Date registration_dateA, Date registration_dateZ,
			Date finished_dateA, Date finished_dateZ, String finished_dateT, String getName)
			throws DataAccessException {

		System.out.println("PersonmemoSearchDaoImpl到達");

		System.out.println("memo" + memo);
		System.out.println("registration_dateA" + registration_dateA);
		System.out.println("registration_dateZ" + registration_dateZ);
		System.out.println("finished_dateA" + finished_dateA);
		System.out.println("finished_dateZ" + finished_dateZ);
		System.out.println("finished_dateT" + finished_dateT);

		List<Object> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from personmemo where is_deleted = 0 and user_id = ?");
		list.add(getName);

		if ((memo != null) && (!memo.isEmpty())) {
			sql.append(" and personmemo.memo like ?");
			list.add("%" + memo + "%");
		}

		if ((registration_dateA != null) && (registration_dateZ != null)) {
			sql.append(" and personmemo.registration_date between ? and ?");
			list.add(registration_dateA);
			list.add(registration_dateZ);
		} else if ((registration_dateA != null) && (registration_dateZ == null)) {
			sql.append(" and personmemo.registration_date >= ?");
			list.add(registration_dateA);
		} else if ((registration_dateA == null) && (registration_dateZ != null)) {
			sql.append(" and personmemo.registration_date <= ?");
			list.add(registration_dateZ);
		}

		if ((finished_dateA != null) && (finished_dateZ != null)) {
			sql.append(" and personmemo.finished_date between ? and ?");
			list.add(finished_dateA);
			list.add(finished_dateZ);
		} else if ((finished_dateA != null) && (finished_dateZ == null)) {
			sql.append(" and personmemo.finished_date >= ?");
			list.add(finished_dateA);
		} else if ((finished_dateA == null) && (finished_dateZ != null)) {
			sql.append(" and personmemo.finished_datet <= ?");
			list.add(finished_dateZ);
		}

		if (finished_dateT != null) {
			sql.append(" and personmemo.finished_date is null");

		}

		sql.append(" order by registration_date asc");

		System.out.println("sql" + sql);
		System.out.println("list" + list);
		Object[] addList = list.toArray(new Object[list.size()]);
		System.out.println("addList" + addList);
		String addSql = sql.toString();
		System.out.println("addSql" + addSql);
		List<Map<String, Object>> rowNumber = jdbc.queryForList(addSql, addList);

		System.out.println("rowNumber" + rowNumber);

		List<PersonMemoDTO> personmemoList = new ArrayList<>();

		for (Map<String, Object> map : rowNumber) {
			PersonMemoDTO personmemodto = new PersonMemoDTO();

			personmemodto.setId((int) map.get("id"));
			personmemodto.setMemo((String) map.get("memo"));
			personmemodto.setRegistration_date((Date) map.get("registration_date"));
			personmemodto.setFinished_date((Date) map.get("finished_date"));

			personmemoList.add(personmemodto);

		}
		System.out.println("PersonmemoListDaoImpl" + personmemoList);
		return personmemoList;
	}

	@Override
	public int completed(int id, Date finished_date) throws DataAccessException {
		System.out.println("personMemoDaoImplCompleted到達");

		int rowNumber = jdbc.update("update personmemo"
				+ " set"
				+ " finished_date = ?"
				+ " where id = ?", finished_date, id);

		return rowNumber;
	}

	public void personMemoCsvOut(String getName) throws DataAccessException {

		String sql = "select * from personmemo where user_id = ?";

		PersonMemoRowCallbackHandler handler = new PersonMemoRowCallbackHandler();

		jdbc.query(sql, handler, getName);

	}
}
