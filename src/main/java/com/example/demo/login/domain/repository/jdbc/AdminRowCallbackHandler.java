package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowCallbackHandler;

public class AdminRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		System.out.println("AdminRowCallbackHandler到達");

		try {

			//ファイル書き込み準備
			File file = new File("Admin.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			do {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 ah時mm分");
				//ResultSetから値を取得してStringにセット
				String str = "『お知らせ内容』" + rs.getString("contents") + ","
						+ "『投稿日』" + sdf.format(rs.getTimestamp("registration_date"));
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
