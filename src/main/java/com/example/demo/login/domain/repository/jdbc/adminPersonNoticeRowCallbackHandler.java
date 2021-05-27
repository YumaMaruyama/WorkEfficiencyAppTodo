package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowCallbackHandler;

public class adminPersonNoticeRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {
			File file = new File("adminNotice.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			do {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh時mm分");
				String str = "『お知らせ内容』 " + rs.getString("content") + ","
						+ "『受信日』 " + sdf.format(rs.getTimestamp("registration_date"));

				bw.write(str);
				bw.newLine();
			} while (rs.next());

			bw.flush();
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

	}

}
