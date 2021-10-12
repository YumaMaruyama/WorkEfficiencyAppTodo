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

import com.example.demo.login.domain.model.UsersDTO;
import com.example.demo.login.domain.repository.UsersDao;
//Bean名をセット　そうすることで、@Autowiredする際、どのクラスを使うか指定できる
@Repository("UsersDaoJdbcImpl")
public class UsersDaoJdbcImpl implements UsersDao {

	//UsersDaoのpublic　abstractメソッドを実装してオーバーライドしている

	@Autowired //SpringにJdbcTemplateが入っており、Bean定義がされている　なのでこのアノテーションをつけるだけでよい　このクラスのメソッドを使ってSQLを実行
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	//Usersテーブルにデータを件数を取得
	@Override
	public int count() throws DataAccessException {

		//Objectの取得
		//全件取得してカウント
		int count = jdbc.queryForObject("select count(*) from users", Integer.class);
		//カウントの結果やカラムを一つだけ取得してくるような場合はqueryForObjectメソッドを使う
		//1引数にSQL文　2引数に戻り値のオブジェクトのclassを指定する
		return count;
	}

	public int countPersonUsersNotice() throws DataAccessException {
		int count = jdbc.queryForObject("select count(*)from users",Integer.class);

		return count;
	}

	public int check(String user_id) {

		int rowNumber = jdbc.queryForObject("select count(*) from users where user_id = ?",Integer.class,user_id);



		return rowNumber;
	}
	//検索ボックスメソッド(Usersテーブルから条件に一致したものを取り出す)
	public List<UsersDTO> search(String userId,String userName,Date birthdayAA,Date birthdayZZ,Date hireDateAA, Date hireDateZZ, int maleFemaleSearch,String admin) {
		System.out.println("usersDaoSearch到達");

		System.out.println("userId" + userId);
		System.out.println("userName" + userName);
		System.out.println("birthdayAA" + birthdayAA);
		System.out.println("birthdayZZ" + birthdayZZ);
		System.out.println("hireDateAA" + hireDateAA);
		System.out.println("hireDateZZ" + hireDateZZ);
		System.out.println("maleFemaleSearch" + maleFemaleSearch);

		//sql文はFormで自分でつけた名前ではなく、データベースの名前と一緒の名前にする
		List<Object> list = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		 	sql.append("select * from users where is_deleted = 0 and role != ?");
		 	list.add(admin);


		 	//isEmptyで空ではないにしたいので！マークをつける
		 	if((userId != null) && (!userId.isEmpty())) {
		 		sql.append(" and user_id like ?");
		 		list.add("%" + userId + "%");
		 	}
		 	if((userName != null) && (!userName.isEmpty())) {
		 		sql.append(" and user_name like ?");
		 		list.add("%" + userName + "%");
		 	}
		 	if((birthdayAA != null) && (birthdayZZ != null)) {
		 		sql.append(" and birthday between ? and ?");
		 		list.add(birthdayAA);
		 		list.add(birthdayZZ);
		 	}else if ((birthdayAA != null) && (birthdayZZ == null)){
		 		sql.append(" and birthday >= ?");
		 		list.add(birthdayAA);
		 	}else if ((birthdayAA == null) && (birthdayZZ != null)) {
		 		sql.append(" and birthday <= ?");
		 		list.add(birthdayZZ);
		 	}
		 	if((hireDateAA != null) && (hireDateZZ != null)) {
		 		sql.append(" and hireDate between ? and ?");
		 		list.add(hireDateAA);
		 		list.add(hireDateZZ);
		 	}else if ((hireDateAA != null) && (hireDateZZ == null)){
		 		sql.append(" and hireDate >= ?");
		 		list.add(hireDateAA);
		 	}else if ((hireDateAA == null) && (hireDateZZ != null)) {
		 		sql.append(" and hireDate <= ?");
		 		list.add(hireDateZZ);
		 	}
		 	
		 	//Blankはnull,""," ",の三つをチェックしてくれる
		 	if((maleFemaleSearch == 1) || (maleFemaleSearch == 0)) {
		 		sql.append(" and maleFemale like ?");
		 		list.add(maleFemaleSearch);
		 	}

		 	sql.append(" order by hireDate asc");
		 	System.out.println("sql" + sql);
			System.out.println("list" + list);
			Object[] addList = list.toArray(new Object[list.size()]);
			String sql1 = sql.toString();
			List<Map<String,Object>> rowNumber = jdbc.queryForList(sql1,addList);

			System.out.println("rowNumber" + rowNumber);

			List<UsersDTO> usersList = new ArrayList<>();

			for(Map<String,Object> map : rowNumber) {

				UsersDTO usersdto = new UsersDTO();
				//map.getの右はデータベースのカラム名と一緒にする
				usersdto.setUser_id((String)map.get("user_id"));
				usersdto.setUser_name((String)map.get("user_name"));
				usersdto.setBirthday((Date)map.get("birthday"));
				usersdto.setHireDate((Date)map.get("hireDate"));
				usersdto.setMaleFemale((boolean)map.get("maleFemale"));

				usersList.add(usersdto);
			}

			return usersList;


	}

	public List<UsersDTO> searchPersonUsersNotice(String user_id,String user_name,String admin) {

	StringBuilder sql = new StringBuilder();

	List<String> list = new ArrayList<>();

	sql.append("select * from users where is_deleted = 0 and role != ?");
	list.add(admin);

	if((user_id != null) && (!user_id.isEmpty())) {
		sql.append(" and user_id like ?");
		list.add("%" + user_id  + "%");
	}

	if((user_name != null) && (!user_name.isEmpty())) {
		sql.append(" and user_name like ?");
		list.add("%" + user_name + "%");
	}

	System.out.println("sql" + sql);
	System.out.println("List" + list);

	String getSql = sql.toString();
	System.out.println("getSql" + getSql);
	Object[] getList = list.toArray(new Object [list.size()]);
	System.out.println("getList" + getList);

	List<Map<String,Object>>rowNumber = jdbc.queryForList(getSql,getList);

	System.out.println("rowNumber" + rowNumber);

	List<UsersDTO> getUsersList = new ArrayList<>();

	for(Map<String,Object>map : rowNumber) {
		UsersDTO usersdto = new UsersDTO();

	usersdto.setUser_id((String)map.get("user_id"));
	usersdto.setUser_name((String)map.get("user_name"));

	getUsersList.add(usersdto);
	}

	return getUsersList;
	}

	public List<UsersDTO> searchOne_to_oneMailNotice(String user_id,String user_name,String admin,String getName) {

		StringBuilder getSql = new StringBuilder();
		List<Object> getList = new ArrayList<>();

		getSql.append("select * from users where is_deleted = 0 and role != ? and user_id != ?");
		getList.add(admin);
		getList.add(getName);

		if((user_id != null) && (!user_id.isEmpty())) {
			getSql.append(" and user_id like ?");
			getList.add("%" + user_id + "%");
		}
		if((user_name != null) && (!user_name.isEmpty())) {
			getSql.append(" and user_name like ?");
			getList.add("%" + user_name + "%");
		}
		System.out.println("getSql" + getSql);
		System.out.println("getList" + getList);
		Object[] getToArrayList = getList.toArray(new Object[getList.size()]);
		System.out.println("getToArrayList" + getToArrayList);
		String getAddSql = getSql.toString();
		System.out.println("getAddSql" + getAddSql);
		List<Map<String,Object>>searchSql = jdbc.queryForList(getAddSql,getToArrayList);
		List<UsersDTO> list = new ArrayList<>();
		for(Map<String,Object>map:searchSql) {
			UsersDTO usersdto = new UsersDTO();
			usersdto.setUser_id((String)map.get("user_id"));
			usersdto.setUser_name((String)map.get("user_name"));

			list.add(usersdto);
		}

		return list;

	}



	//Usersテーブルにデータを一件insert
	@Override
	public int insertOne(UsersDTO usersdto) throws DataAccessException {


		String password = passwordEncoder.encode(usersdto.getPassword());
		//一件だけ登録

		System.out.println(password);
		String sql = "insert into users(user_id,"
				+ " password,"
				+ " user_name,"
				+ " birthday,"
				+ " hireDate,"
				+ " malefemale,"
				+ " role)"
				+ " values(?,?,?,?,?,?,?)";


				int rowNumber = jdbc.update(sql,
				usersdto.getUser_id()
				,password
				,usersdto.getUser_name()
				,usersdto.getBirthday()
				,usersdto.getHireDate()
				,usersdto.isMaleFemale()
				,usersdto.getRole());

		return rowNumber;

	}

	@Override
	public UsersDTO selectTwo(String id) throws DataAccessException {
		System.out.println("selectTwo到達");
		System.out.println("id  " + id);
		Map<String, Object> map = jdbc.queryForMap("select * from users where user_id = ?",id);

		UsersDTO usersdtoTwo = new UsersDTO();

		//取得したデータを結果返却の変数にセットしていく
				usersdtoTwo.setUser_name((String) map.get("user_name"));
				System.out.println("usersdtoTwo.setUser_name" + usersdtoTwo.getUser_name());
				return usersdtoTwo;

	}
	//Usersテーブルのデータを一件取得
	@Override
	public UsersDTO selectOne(String user_id) throws DataAccessException {

			//一件取得
			Map<String,Object> map = jdbc.queryForMap("select * from users"
					+ " where user_id = ?"
					,user_id);
			//queryForMap 戻り値はMap<String,Object>型です　
			//結果返却用の変数
			UsersDTO usersdto = new UsersDTO();

			//取得したデータを結果返却用の変数にセットしていく
			usersdto.setUser_id((String)map.get("user_id"));
			usersdto.setPassword((String)map.get("password"));
			usersdto.setUser_name((String)map.get("user_name"));
			usersdto.setBirthday((Date)map.get("birthday"));
			usersdto.setHireDate((Date)map.get("hireDate"));
			usersdto.setMaleFemale((Boolean)map.get("malefemale"));
			usersdto.setRole((String)map.get("role"));

			return usersdto;
		}


	//Usersテーブルのデータを全件取得
	@Override
	public List<UsersDTO> selectMany(String admin) throws DataAccessException {

		//複数件のselect
		//Usersテーブルのデータを全件取得
		List<Map<String,Object>> getList = jdbc.queryForList("select * from users where role != ? order by hiredate asc",admin);
		//複数件のselectをする場合はqueryForListメソッドを使う　戻り値の方にはList<Map<String,Object>>を指定　
		//Listが行　Mapが列　を表している　Mapのgetメソッドを使って、テーブルのカラム名を指定できる

		//結果返却用のList
		List<UsersDTO> usersList = new ArrayList<>();

		//取得したデータを結果返却用のListに格納していく
		for(Map<String,Object> map : getList) {

			//UsersDTOインスタンスの作成
			UsersDTO usersdto = new UsersDTO();

			//UsersDTOインスタンスに取得したデータをセットする
			usersdto.setUser_id((String)map.get("user_id"));
			usersdto.setPassword((String)map.get("password"));
			usersdto.setUser_name((String)map.get("user_name"));
			usersdto.setBirthday((Date)map.get("birthday"));
			usersdto.setHireDate((Date)map.get("hireDate"));
			usersdto.setMaleFemale((Boolean)map.get("malefemale"));
			usersdto.setRole((String)map.get("role"));

			//結果返却用のListに追加
			usersList.add(usersdto);


		}

		System.out.println(usersList);
		return usersList;
	}

	//Usersテーブルのデータを全件取得　personUsersNotice用
	@Override
	public List<UsersDTO> selectManyUsersNotice(String admin) throws DataAccessException {

		List<Map<String,Object>> getUsersList = jdbc.queryForList("select * from users where role != ?",admin);

		List<UsersDTO> usersList = new ArrayList<>();

		for(Map<String,Object>map: getUsersList) {

		UsersDTO usersdto = new UsersDTO();

		usersdto.setId((int)map.get("id"));
		usersdto.setUser_id((String)map.get("user_id"));
		usersdto.setUser_name((String)map.get("user_name"));

		usersList.add(usersdto);
		}

		return usersList;

	}

	public List<UsersDTO> selectManyOne_to_oneMail(String getName) {
		List<Map<String,Object>>getUsersList = jdbc.queryForList("select * from users where role = 'role_general' and user_id != ?",getName);

		List<UsersDTO> usersList = new ArrayList<>();

		for(Map<String,Object>map:getUsersList) {
			UsersDTO usersdto = new UsersDTO();

			usersdto.setId((int)map.get("id"));
			usersdto.setUser_id((String)map.get("user_id"));
			usersdto.setUser_name((String)map.get("user_name"));

			usersList.add(usersdto);
		}
		return usersList;


	}

	//Usersテーブルを一件更新
	@Override
	public int updateOne(UsersDTO usersdto) throws DataAccessException {

		System.out.println("usersDaoImplUpdate到達");
		
		int rowNumber = jdbc.update("update Users"
				+ " set"
				+ " user_name = ?,"
				+ " birthday = ?,"
				+ " hireDate = ?,"
				+ " MaleFemale = ?"
				+ " where user_id = ?"
				,usersdto.getUser_name()
				,usersdto.getBirthday()
				,usersdto.getHireDate()
				,usersdto.isMaleFemale()
				,usersdto.getUser_id());

	
		return rowNumber;
	}

	//Usersテーブルを一件削除
	@Override
	public int deleteOne(String user_id) throws DataAccessException {

		//一件更新
		int rowNumber = jdbc.update("delete from users where user_id = ?",user_id);

		return rowNumber;
	}

	//Usersテーブルの全データをCSVに出力する×←　正解↓
	//SQL取得結果をサーバーにCSVで保存する
		@Override
	public void usersCsvOut(String user_id) throws DataAccessException {

			//Usersテーブルのデータを全件取得するSQL文
			String sql = "select * from users where user_id != ?";

			//ResultSetExtractorの生成
			UsersRowCallbackHandler handler = new UsersRowCallbackHandler();

			//SQL文出力＆CSV出力
			jdbc.query(sql, handler,user_id);
	}



}
