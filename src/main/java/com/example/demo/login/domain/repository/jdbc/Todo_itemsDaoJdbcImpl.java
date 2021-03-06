package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Todo_itemsDTO;
import com.example.demo.login.domain.repository.Todo_itemsDao;

//Bean名をセット　そうすることで、@Autowiredする際、どのクラスを使うか指定できる
@Repository("Todo_itemsDaoJdbcImpl")
public class Todo_itemsDaoJdbcImpl implements Todo_itemsDao {

	@Autowired //SpringにJdbcTemplateが入っており、Bean定義がされている　なのでこのアノテーションをつけるだけでよい　このクラスのメソッドを使ってSQLを実行できる
	JdbcTemplate jdbc;

	@Override
	public int count() throws DataAccessException {

		int count = jdbc.queryForObject("select count(*) from todo_items", Integer.class);

		return count;
	}

	//Todo_itemsテーブルにデータを一件insert
	@Override
	public int insertOne(Todo_itemsDTO todo_itemsdto) throws DataAccessException {

		int rowNumber = jdbc.update("insert into Todo_items(user_name,"

				+ " item_name,"
				+ " details,"
				+ " details2,"
				+ " details3,"		
				+ " expire_date)"
				+ " values(?,?,?,?,?,?)"

				, todo_itemsdto.getUser_name(), todo_itemsdto.getItem_name(),todo_itemsdto.getDetails(),todo_itemsdto.getDetails2(),todo_itemsdto.getDetails3()
				
				, todo_itemsdto.getExpire_date());
	

		return rowNumber;
	}

	//Todo_itemsテーブルのデータを一件取得
	@Override
	public Todo_itemsDTO selectOne(String id) throws DataAccessException {

		//一件取得
		Map<String, Object> map = jdbc.queryForMap("select * from todo_items"
				+ " where id = ?", id);
		//queryForMap 戻り値はMap<String,Object>型です
		//結果返却用の変数
		Todo_itemsDTO todo_itemsdto = new Todo_itemsDTO();

		//取得したデータを結果返却の変数にセットしていく
		todo_itemsdto.setId((int) map.get("id"));
		todo_itemsdto.setUser_name((String) map.get("user_name"));
		todo_itemsdto.setItem_name((String) map.get("item_name"));
		todo_itemsdto.setRegistration_date((Date) map.get("registration_date"));
		todo_itemsdto.setExpire_date((Date) map.get("expire_date"));
		todo_itemsdto.setFinished_date((Date) map.get("finished_date"));

		return todo_itemsdto;
	}
	@Override
	public Todo_itemsDTO selectOneX(int id) throws DataAccessException {

		Map<String,Object> map = jdbc.queryForMap("select * from todo_items" + " where id =?", id);

		Todo_itemsDTO todo_itemsdto = new Todo_itemsDTO();

		todo_itemsdto.setDetails((String)map.get("details"));
		todo_itemsdto.setDetails2((String)map.get("details2"));
		todo_itemsdto.setDetails3((String)map.get("details3"));

		return todo_itemsdto;
	}


	//Todo_itemsテーブルのデータを全件取得
	@Override
	public List<Todo_itemsDTO> selectMany() throws DataAccessException {

		//複数行のselect
		//Todo_itemsテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("select * from todo_items order by expire_date asc");
		//複数件のselectをする場合はqueryForListメソッドを使う　戻り値の方にはList<Map<String,Object>>を指定　
		//Listが行　Mapが列　を表している　Mapのgetメソッドを使って、テーブルのカラム名を指定できる

		//結果返却用のList
		List<Todo_itemsDTO> todo_itemsList = new ArrayList<>();

		//取得したデータを結果返却用のListに格納
		for (Map<String, Object> map : getList) {

			//Todo_itemsDTOインスタンス作成
			Todo_itemsDTO todo_itemsdto = new Todo_itemsDTO();

			//Todo_itemsDTOインスタンスに取得したデータをセット
			todo_itemsdto.setId((int) map.get("id"));
			todo_itemsdto.setUser_name((String) map.get("user_name"));
			todo_itemsdto.setItem_name((String) map.get("item_name"));
			todo_itemsdto.setRegistration_date((Date) map.get("registration_date"));
			todo_itemsdto.setExpire_date((Date) map.get("expire_date"));
			todo_itemsdto.setFinished_date((Date) map.get("finished_date"));
			

			//結果返却用のListに追加
			todo_itemsList.add(todo_itemsdto);

		
		}
		
		return todo_itemsList;
	}

	@Override
	public int updateOnex(Todo_itemsDTO todo_itemsdto) throws DataAccessException {

		System.out.println("updateOnexDaoImpl到達");

		int rowNumber = jdbc.update("update todo_items"
				+ " set"
				+ " details = ?,"
				+ " details2 = ?,"
				+ " details3 = ?"
				+ " where id = ?",todo_itemsdto.getDetails(),todo_itemsdto.getDetails2(),todo_itemsdto.getDetails3(),todo_itemsdto.getId());


		return rowNumber;
	}


	//Todo_itemsテーブルを一件更新
	@Override
	public int updateOne(Todo_itemsDTO todo_itemsdto) throws DataAccessException {

		int rowNumber = jdbc.update("update Todo_items"
				+ " set"
				+ " user_name = ?,"
				+ " item_name = ?,"
				+ " registration_date = ?,"
				+ " expire_date = ?"
				+ " where id = ?", todo_itemsdto.getUser_name(), todo_itemsdto.getItem_name(),
				todo_itemsdto.getRegistration_date(), todo_itemsdto.getExpire_date(),
				todo_itemsdto.getId());

		return rowNumber;
	}

	//Todo_itemsテーブルを一件削除
	@Override
	public int deleteOne(int id) throws DataAccessException {

		//一件更新
		int rowNumber = jdbc.update("delete from Todo_items where id = ?", id);
		return rowNumber;
	}

	//Todo_itemsテーブルの完了日を出す
	public int completedOne(int id, Date finished_date) throws DataAccessException {
	
		int rowNumber = jdbc.update("update todo_items"
				+ " set"
				+ " finished_date = ?"
				+ " where id = ?", finished_date, id);
		return rowNumber;
	}

	@Override
	public void todo_itemsCsvOut() throws DataAccessException {

		String sql = "select * from todo_items";

		Todo_itemsRowCallbackHandler handler = new Todo_itemsRowCallbackHandler();

		jdbc.query(sql,handler);
	}


	//検索ボックスメソッド(Todo_itemテーブルから条件に一致したものを取り出す)
	public List<Todo_itemsDTO> search(String item_name, String user_name, Date registration_date,
			Date registration_dateA, Date expire_date, Date expire_dateA, Date finished_date, Date finished_dateA,
			String finished_dateM) throws DataAccessException {

		System.out.println("searchDaoImpl到達");
	
		System.out.println("item_name" + item_name);
		System.out.println("user_name" + user_name);
		System.out.println("registration_date" + registration_date);
		System.out.println("registration_dateA" + registration_dateA);
		System.out.println("expire_date" + expire_date);
		System.out.println("expire_dateA" + expire_dateA);
		System.out.println("finished_date" + finished_date);
		System.out.println("finished_dateA" + finished_dateA);
		System.out.println("finished_dateM" + finished_dateM);



	

		 StringBuilder sql = new StringBuilder();
		 	sql.append("select * from todo_items where is_deleted = 0");

		 	List<Object> list = new ArrayList<Object>();

		if((item_name != null) && (!item_name.isEmpty())){
			sql.append(" and todo_items.item_name like ?");
			list.add("%" + item_name + "%");
		}
		if((user_name != null) && (!user_name.isEmpty())){
			sql.append(" and todo_items.user_name like ?");
			list.add("%" + user_name + "%");

		}
		
	
		if((registration_date != null) && (registration_dateA != null)) {
			sql.append(" and todo_items.registration_date between ? and ?");
			list.add(registration_date);
			list.add(registration_dateA);
		}else if((registration_date != null) && (registration_dateA == null)) {
			sql.append(" and todo_items.registration_date >= ?");
			list.add(registration_date);
		}else if((registration_date == null) && (registration_dateA != null)) {
			sql.append(" and todo_items.registration_date <= ?");
			list.add(registration_dateA);
		}

		if((expire_date != null) && (expire_dateA != null)) {
			sql.append(" and todo_items.expire_date between ? and ?");
			list.add(expire_date);
			list.add(expire_dateA);
		}else if((expire_date != null) && (expire_dateA == null)) {
			sql.append(" and todo_items.expire_date >= ?");
			list.add(expire_date);
		}else if((expire_date == null) && (expire_dateA != null)) {
			sql.append(" and todo_items.expire_date <= ?");
			list.add(expire_dateA);
		}
		
		if((finished_date != null) && (finished_dateA != null)) {
			sql.append(" and todo_items.finished_date between ? and ?");
			list.add(finished_date);
			list.add(finished_dateA);
		}else if((finished_date != null) && (finished_dateA == null)) {
			sql.append(" and todo_items.finished_date >= ?");
			list.add(finished_date);
		}else if((finished_date == null) && (finished_dateA != null)) {
			sql.append(" and todo_items.finished_date <= ?");
			list.add(finished_dateA);
		}
		//nullを取得はis.nullを使う
		if(finished_dateM != null) {
			sql.append(" and todo_items.finished_date is null");
		}

		sql.append(" order by expire_date asc");

		System.out.println("sql" + sql);
		System.out.println("list" + list);
		//queryForListに入れるには、配列にしないとだめで、取ってきたリストをtoArrayを使用し配列に変えている
		Object[] addList = list.toArray(new Object[list.size()]);
		System.out.println("addList" + addList);
		//取ってきたsqlをtoStringでとってきている
		String sql1 = sql.toString();
		//MapのListにsql文とパラメーターを合わせて入れている
		List<Map<String,Object>> rowNumber = jdbc.queryForList(sql1,addList);




		System.out.println("rowNumberの中身" + rowNumber);
		//結果返却用(return)のList
		List<Todo_itemsDTO> todo_itemsList = new ArrayList<>();

		//取得したデータを結果返却用のListに格納
		for (Map<String, Object> map : rowNumber) {

			//Todo_itemsDTOインスタンス作成
			Todo_itemsDTO todo_itemsdto = new Todo_itemsDTO();

			//Todo_itemsDTOインスタンスに取得したデータをセット
			todo_itemsdto.setId((int)map.get("id"));
			todo_itemsdto.setItem_name((String) map.get("item_name"));
			todo_itemsdto.setUser_name((String)map.get("user_name"));
			todo_itemsdto.setRegistration_date((Date)map.get("registration_date"));
			todo_itemsdto.setExpire_date((Date)map.get("expire_date"));
			todo_itemsdto.setFinished_date((Date)map.get("finished_date"));
			//結果返却用のListに追加
			todo_itemsList.add(todo_itemsdto);
			System.out.println("DaoImplTodo_itemsList" + todo_itemsList);
		}

		return todo_itemsList;

	}



}