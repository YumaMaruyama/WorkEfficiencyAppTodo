package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowCallbackHandler;

public class ClientRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		System.out.println("ClientRowCallbackHandler到達");

		try {

			//ファイル書き込み準備
			File file = new File("Client.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			do {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				//ResultSetから値を取得してStringにセット
				String str =  "『会社名』 " +  rs.getString("company") + ","
						+  "『住所』 "  + rs.getString("address") + ","
						+  "『担当者』 " + rs.getString("user_name") + ","
						+  "『担当者メールアドレス』 " + rs.getString("mailaddress") + ","
						+  "『担当者電話番号』 " + rs.getString("telephone") + ","
						+  "『登録日』 " + sdf.format(rs.getTimestamp("registration_date"));
				//ファイルに書き込み＆改行
				bw.write(str);
				bw.newLine();

			} while (rs.next());

			//強制的にファイルクローズ
			bw.flush();
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
	}



}
