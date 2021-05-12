package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

public class PersonUsersNoticeSendingRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {
			File file = new File("personUsersNoticeSending.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			do {
				String str = "『送信先』 " + rs.getString("user_name") + ","
						+ "『連絡内容』 " + rs.getString("content") + ","
						+ "『送信日』 " + rs.getDate("registration_date");


				bw.write(str);
				bw.newLine();
			}while(rs.next());

			bw.flush();
			bw.close();

		}catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

	}

}
