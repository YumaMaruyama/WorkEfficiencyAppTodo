package com.example.demo.login.domain.repository.jdbc;

import org.springframework.stereotype.Repository;

@Repository("UsersDaoNamedJdbcImpl")
public class UsersDaoNamedJdbcImpl{

//	@Autowired
//	private NamedParameterJdbcTemplate jdbc;
//
//	//Usersテーブルの件数を取得
//	@Override
//	public int count() {
//		//SQL文
//		String sql = "select count(*) from Users";
//
//		//パラメーター生成
//		SqlParameterSource params = new MapSqlParameterSource();
//
//		//全件取得してカウント
//		return jdbc.queryForObject(sql, params, Integer.class);
//	}
//
//	//Usersテーブルにデータを一件insert
//	@Override
//	public int insertOne(UsersDTO usersdto) {
//
//		//SQL文にキー名を指名
//		//SQL文
//		String sql = "insert into Users(user_id,"
//				+ " password,"
//				+ " user_name,"
//				+ " birthday,"
//				+ " age,"
//				+ " MaleFemale,"
//				+ " role)"
//				+ "values(:user_id,"
//				+ " :password,"
//				+ " :user_name,"
//				+ " :birthday,"
//				+ " :MaleFemale,"
//				+ " :role)";
//
//		//パラメータの設定
//		//SQLに入れられるパラメーターを設定するため、SqlParameterSourceクラスを使う
//		//SqlParameterSourceクラスをnewしてaddValue()のメソッドチェーンでキーと値をセットする
//		//.addValueの1引数にキー名　２引数には値をセット
//		SqlParameterSource params = new MapSqlParameterSource()
//				.addValue("user_id", usersdto.getUser_id())
//				.addValue("password", usersdto.getPassword())
//				.addValue("user_name", usersdto.getUser_name())
//				.addValue("birthday", usersdto.getBirthday())
//				.addValue("age", usersdto.getAge())
//				.addValue("MaleFemale", usersdto.isMaleFemale())
//				.addValue("Role", usersdto.getRole());
//
//		//SQL実行
//		return jdbc.update(sql, params);
//
//	}
//
//	//Usersテーブルのデータを一件取得
//	@Override
//	public UsersDTO selectOne(String user_id) {
//
//		//SQL文
//		String sql = "select * from Users user_id";
//
//		//パラメーター
//		SqlParameterSource params = new MapSqlParameterSource();
//
//		//SQL文実行
//		Map<String, Object> map = jdbc.queryForMap(sql, params);
//
//		//結果返却用のインスタンスを生成
//		UsersDTO usersdto = new UsersDTO();
//
//		//取得データをインスタンスにセットしていく
//		usersdto.setUser_id((String) map.get("user_id"));
//		usersdto.setPassword((String) map.get("password"));
//		usersdto.setUser_name((String) map.get("user_name"));
//		usersdto.setBirthday((Date) map.get("birthday"));
//		usersdto.setAge((Integer) map.get("age"));
//		usersdto.setMaleFemale((Boolean) map.get("MaleFemale"));
//		usersdto.setRole((String) map.get("role"));
//
//		return usersdto;
//	}
//
//
//	//Usersテーブルを一件更新
//	@Override
//	public int updateOne(UsersDTO usersdto) {
//
//		//SQL文
//		String sql = "update Users"
//				+ " set"
//				+ " password = :password,"
//				+ " user_name = :user_name,"
//				+ " birthday = :birthday,"
//				+ " age = :age,"
//				+ " maleFemale = :maleFemale"
//				+ " where user_id = :user_id";
//
//		//パラメーター
//		SqlParameterSource params = new MapSqlParameterSource()
//				.addValue("user_id", usersdto.getUser_id())
//				.addValue("password", usersdto.getPassword())
//				.addValue("user_name", usersdto.getUser_name())
//				.addValue("birthday", usersdto.getBirthday())
//				.addValue("age", usersdto.getAge())
//				.addValue("maleFemale", usersdto.isMaleFemale())
//				.addValue("role", usersdto.getRole());
//
//		//SQL実行
//		return jdbc.update(sql, params);
//	}
//
//	//Usersテーブルを一件削除
//	@Override
//	public int deleteOne(String user_id) {
//
//		//SQL文
//		String sql = "delete from Users where user_id = ;user_id";
//
//		//パラメーター
//		SqlParameterSource params = new MapSqlParameterSource()
//				.addValue("user_id", user_id);
//
//		//SQL実行
//		int rowNumber = jdbc.update(sql, params);
//
//		return rowNumber;
//	}
//
//	//SQL取得結果をサーバーにCSVで保存する
//	@Override
//	public void usersCsvOut() {
//
//		//Usersテーブルのデータを全件取得するSQL
//		String sql = "select * from Users";
//
//		//ResultSetExtractorの生成
//		UsersRowCallbackHandler handler = new UsersRowCallbackHandler();
//
//		jdbc.query(sql, handler);
//	}
//
//	@Override
//	public UsersDTO selectTwo(String id) throws DataAccessException {
//
//		return null;
//	}
//
//
//	@Override
//	public int check(String user_id) throws DataAccessException {
//
//		return 0;
//	}
//
//	@Override
//	public List<UsersDTO> selectManyUsersNotice() throws DataAccessException {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public List<UsersDTO> searchPersonUsersNotice(String user_id, String user_name) {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public int countPersonUsersNotice() throws DataAccessException {
//		// TODO 自動生成されたメソッド・スタブ
//		return 0;
//	}
//
//	@Override
//	public List<UsersDTO> selectManyOne_to_oneMail(String getName) throws DataAccessException {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public List<UsersDTO> searchOne_to_oneMailNotice(String user_id, String user_name, String Role,String getName) {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public List<UsersDTO> selectMany(String admin) throws DataAccessException {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public List<UsersDTO> search(String userId, String userName, Date birthdayAA, Date birthdayZZ, Date hireDateAA,
//			Date hireDateZZ, int maleFemaleSearch, String admin) {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//
//
////	@Override
////	public int insertOnePersonUsersNotice(UsersDTO usersdto) throws DataAccessException {
////		// TODO 自動生成されたメソッド・スタブ
////		return 0;
////	}


}
