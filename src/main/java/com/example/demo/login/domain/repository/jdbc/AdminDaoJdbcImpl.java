package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.AdminDTO;
import com.example.demo.login.domain.repository.AdminDao;

@Repository
public class AdminDaoJdbcImpl implements AdminDao {

	@Autowired //SpringにJdbcTemplateが入っており、Bean定義がされている このアノテーションをつけるだけで、このクラスのメソッドを使ってSQLを実行できる
	JdbcTemplate jdbc;

	@Override
	public int insertOne(AdminDTO admindto) throws DataAccessException {
		int rowNumber = jdbc.update("insert into admin (id,"
				+ " contents,"
				+ " registration_date)"
				+ " values(?,?,?)", admindto.getId(), admindto.getContents(), admindto.getRegistration_date());

		return rowNumber;

	}

	@Override
	public AdminDTO selectOne(int id, String contents, Date registration_date) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("select * from admin where id = ?", id);

		AdminDTO admindto = new AdminDTO();

		admindto.setId((int) map.get("id"));
		admindto.setContents((String) map.get("contents"));
		admindto.setRegistration_date((Date) map.get("registration_date"));
		return admindto;
	}

	@Override
	public List<AdminDTO> selectMany() throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("select * from admin");
		//queryForMap 戻り値はMap<String,Object>型です
		//結果返却用のList
		List<AdminDTO> adminList = new ArrayList<>();
		//取得したデータを結果返却用のListに格納
		for (Map<String, Object> map : getList) {
			//adminインスタンス作成
			AdminDTO admindto = new AdminDTO();

			//adminDTOインスタンスに取得したデータをセット
			admindto.setId((int) map.get("id"));
			admindto.setContents((String) map.get("contents"));
			admindto.setRegistration_date((Date) map.get("registration_date"));

			adminList.add(admindto);
		}
		return adminList;
	}

	@Override
	public int updateOne(AdminDTO admindto) throws DataAccessException {
		System.out.println("DaoInplUpdateOne到達");
		int rowNumber = jdbc.update("update admin"
				+ " set"
				+ " contents = ?"
				+ " where id = ?", admindto.getContents(), admindto.getId());
		return rowNumber;
	}

	@Override
	public int deleteOne(int id) throws DataAccessException {
		System.out.println("DaoInplDeleteOne到達");
		int rowNumber = jdbc.update("delete from admin where id = ?", id);

		return rowNumber;
	}

	//adminテーブルの数をカウント
	@Override
	public int count() throws DataAccessException {

		int count = jdbc.queryForObject("select count(*)from admin", Integer.class);

		return count;
	}

	@Override
	public void adminCsvOut() throws DataAccessException {

		System.out.println("adminCsvOutImpl到達");
		//inquiryテーブルのデータを全件取得するSQL
		String sql = "select * from admin";
		System.out.println("sql" + sql);
		//ResultSetExceptionの生成
		AdminRowCallbackHandler handler = new AdminRowCallbackHandler();

		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}

	@Override //検索ボックスメソッド(Adminテーブルから条件に一致したものを取り出す)
	public List<AdminDTO> search(String contentsA, Date registration_dateAA, Date registration_dateZZ) {

		System.out.println("AdminDaoImplsearch到達");

		System.out.println("contentsA" + contentsA);
		System.out.println("registration_dateAA" + registration_dateAA);
		System.out.println("registration_dateZZ" + registration_dateZZ);

		StringBuilder sql = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		//条件に合った所のｓｑｌ文を追加していく
		sql.append("select * from admin where is_deleted = 0");

		if ((contentsA != null) && (!contentsA.isEmpty())) {
			sql.append(" and contents like ?");
			list.add("%" + contentsA + "%");
		}
		if ((registration_dateAA != null) && (registration_dateZZ != null)) {
			sql.append(" and registration_date BETWEEN ? AND ?");
			list.add(registration_dateAA);
			list.add(registration_dateZZ);
		} else if ((registration_dateAA != null) && (registration_dateZZ == null)) {
			sql.append(" and registration_date >= ?");
			list.add(registration_dateAA);
		} else if ((registration_dateAA == null) && (registration_dateZZ != null)) {
			sql.append(" and registration_date <= ?");
			list.add(registration_dateZZ);
		}

		System.out.println("sql" + sql);
		System.out.println("list" + list);
		Object[] addList = list.toArray(new Object[list.size()]);
		String sql1 = sql.toString();
		List<Map<String, Object>> rowNumber = jdbc.queryForList(sql1, addList);

		System.out.println("rouNumber" + rowNumber);

		List<AdminDTO> adminList = new ArrayList<>();

		for (Map<String, Object> map : rowNumber) {

			AdminDTO admindto = new AdminDTO();

			admindto.setContents((String) map.get("contents"));
			admindto.setRegistration_date((Date) map.get("registration_date"));

			adminList.add(admindto);
		}

		return adminList;
	}

}
