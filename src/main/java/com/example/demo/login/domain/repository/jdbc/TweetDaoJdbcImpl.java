package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.TweetDTO;
import com.example.demo.login.domain.repository.TweetDao;

@Repository("TweetDaoJdbcImpl")
public class TweetDaoJdbcImpl implements TweetDao {

	@Autowired //SpringにJdbcTemplateが入っており、Bean定義がされている　なのでこのアノテーションをつけるだけでよい　このクラスのメソッドを使ってSQLを実行できる
	JdbcTemplate jdbc;

	@Override
	public int count() throws DataAccessException {

		//Objectの取得
		//全件取得してカウント
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM tweet", Integer.class);
		//カウントの結果やカラムを一つだけ取得してくるような場合はqueryForObjectメソッドを使う
		//1引数にSQL文　2引数に戻り値のオブジェクトのclassを指定する
		return count;
	}

	@Override
	public int insertOne(TweetDTO tweetdto) throws DataAccessException {

		int rowNumber = jdbc.update("insert into tweet(id,"
				+ " contents,"
				+ " registration_date,"
				+ " user_id,"
				+ " user_id2)"
				+ " values(?,?,?,?,?)", tweetdto.getId(), tweetdto.getContents(), tweetdto.getRegistration_date(),
				tweetdto.getUser_id(), tweetdto.getUser_id2());

		return rowNumber;
	}

	@Override
	public TweetDTO selectOne(String id) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap(
				"select tweet.id,tweet.user_id,tweet.contents,tweet.registration_date,users.user_name from tweet JOIN users ON tweet.user_id = users.user_id where tweet.id = ?",
				id);

		TweetDTO tweetdto = new TweetDTO();

		tweetdto.setId((int) map.get("id"));
		tweetdto.setUser_id((String) map.get("user_name"));
		tweetdto.setContents((String) map.get("contents"));
		tweetdto.setRegistration_date((Date) map.get("registration_date"));

		return tweetdto;
	}

	//複数件のselect
	//tweetテーブルのデータを全件取得
	@Override
	public List<TweetDTO> selectMany() throws DataAccessException {

		//queryForMapは１レコードだけで、上位互換にqueryForMapをリストにした全部持ってこれるqueryForListがある。ManyするときはForListを使う。１レコードだけの時も
		//ForMapではなくてForList使ってもいい。<Map<String,Object>> 変数名の先頭にList<>をつけるとMapのListという意味になり、複数表持ってこれて、
		//jdbcの中にのjdbc.queryForListを使えばできる。String（Key）にカラム名("id"や"contents")がはいって、Objectにはカラムそれぞれに型が入ってくる。②に行く
		List<Map<String, Object>> getList = jdbc.queryForList(
				"select tweet.id,tweet.contents ,tweet.registration_date ,user_id2,users.user_name from tweet JOIN users ON tweet.user_id = users.user_id order by registration_date desc");
		//複数件のselectをする場合はqueryForListメソッドを使う　戻り値の方にはList<Map<String,Object>>を指定　
		//Listが行　Mapが列　を表している　Mapのgetメソッドを使って、テーブルのカラム名を指定できる

		//結果返却用のリスト
		List<TweetDTO> tweetList = new ArrayList<>();
		for (Map<String, Object> map : getList) {

			TweetDTO tweetdto = new TweetDTO();
			//②	List<Map<String, Object>> getListの中身を変換する。TweenDTOのインスタンス生成し、その変数に入れていく。そのあとTweenDTOをまとめるList<TweenDTO>
			//もインスタンスしているのでその変数にtweendtoの中身を入れてtweetListでまとめている。そうするとhtmlなどで表示させるときもとても楽になって全体的に管理で便利。
			tweetdto.setId((int) map.get("id"));
			tweetdto.setContents((String) map.get("contents"));
			tweetdto.setRegistration_date((Date) map.get("registration_date"));
			tweetdto.setUser_id((String) map.get("user_name"));
			tweetdto.setUser_id2((String) map.get("user_id2"));
			//結果返却用のListに追加
			tweetList.add(tweetdto);

		}

		return tweetList;
	}

	public int deleteOne(int id) {

		System.out.println("tweetDeleteDaoImpl到達");
		int rowNumber = jdbc.update("delete from tweet where id = ?", id);

		return rowNumber;
	}

	@Override
	public List<TweetDTO> search(String user_id, String contents, Date registration_dateA, Date registration_dateZ)
			throws DataAccessException {

		System.out.println("tweetSearchDaoImpl到達");

		System.out.println("user_id" + user_id);
		System.out.println("contents" + contents);
		System.out.println("registration_dateA" + registration_dateA);
		System.out.println("registration_dateZ" + registration_dateZ);

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select tweet.id,tweet.contents,tweet.registration_date,tweet.user_id2,users.user_name from tweet JOIN users ON tweet.user_id = users.user_id where tweet.is_deleted = 0");
		List<Object> list = new ArrayList<>();

		if ((user_id != null) && (!user_id.isEmpty())) {
			sql.append(" and user_name like ?");
			list.add("%" + user_id + "%");
		}
		if ((contents != null) && (!contents.isEmpty())) {
			sql.append(" and contents like ?");
			list.add("%" + contents + "%");
		}
		if ((registration_dateA != null) && (registration_dateZ != null)) {
			sql.append(" and registration_date BETWEEN ? and ?");
			list.add(registration_dateA);
			list.add(registration_dateZ);
		} else if ((registration_dateA == null) && (registration_dateZ != null)) {
			sql.append(" and registration_date < ?");
			list.add(registration_dateZ);
		} else if ((registration_dateA != null) && (registration_dateZ == null)) {
			sql.append(" and registration_date > ?");
			list.add(registration_dateA);
		}
		if (true) {
			sql.append(" order by registration_date desc");
		}

		System.out.println("sql" + sql);
		System.out.println("list" + list);
		Object[] addList = list.toArray(new Object[list.size()]);
		System.out.println("addList" + addList);
		String sqlTo = sql.toString();
		System.out.println("sqlTo" + sqlTo);
		List<Map<String, Object>> rowNumber = jdbc.queryForList(sqlTo, addList);
		System.out.println("rowNumber" + rowNumber);
		List<TweetDTO> tweetList = new ArrayList<>();
		for (Map<String, Object> map : rowNumber) {

			TweetDTO tweetdto = new TweetDTO();
			tweetdto.setId((int) map.get("id"));
			tweetdto.setUser_id((String) map.get("user_name"));
			tweetdto.setContents((String) map.get("contents"));
			tweetdto.setRegistration_date((Date) map.get("registration_date"));
			tweetdto.setUser_id2((String) map.get("user_id2"));

			tweetList.add(tweetdto);

		}

		return tweetList;

	}

	public void tweetCsvOut() {
		//全部データベースからselectしてくる
		String sql = "select tweet.id,tweet.user_id,tweet.contents,tweet.registration_date,users.user_name from tweet JOIN users ON tweet.user_id = users.user_id order by registration_date desc";
		//TweetRowCallbackHandlerインスタンス生成
		TweetRowCallbackHandler handler = new TweetRowCallbackHandler();
		//RowMapperインターフェースを実装した結果をListで受け取るにはquery
		jdbc.query(sql, handler);

	}
}
