package com.example.demo;


import static org.junit.Assert.*;

import org.junit.Test;
//JUnitを使用する際はコードの記述量を少なくし、可読性を上げるため、staticインポートをよく使う　
//こうすることでAssert.assertEqualsだけでなく、assertEqualsだけでメソッドをよびだすことができる
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.repository.UsersDao;

@RunWith(SpringRunner.class)//テストを何で実行するか指定できる　SpringRunnerはSpring用のJUnitを使えるクラス
@SpringBootTest//SpringBootを起動してからテストを始めてくれる
//テスト用クラスには上記二つのアノテーションをつける　
@Transactional
public class UsersDaoTest {

	@Autowired
	@Qualifier("UsersDaoJdbcImpl")
	UsersDao dao;


	//カウントメソッドテスト１
	public void countTest1() {
	//カウントメソッドの結果が２件であることをテスト
		assertEquals(dao.count(),2);
	}

	//カウントメソッドテスト２
	//Sqlアノテーションをつけると、そのSQLを実行した後の状態でテストされる　ただし@SQLに記載されているSQLは
	//そのメソッドだけで有効です　ほかにテストメソッドを追加してカウントメソッドを実行しても、２件のデータしか返ってきません
	@Test
	@Sql("/testdata.sql")
	public void countTest2() {
	//カウントメソッドの結果が３件であることをテスト
		assertEquals(dao.count(),3);
	}

}