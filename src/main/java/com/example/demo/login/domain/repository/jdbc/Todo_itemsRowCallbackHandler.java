package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowCallbackHandler;

public class Todo_itemsRowCallbackHandler implements RowCallbackHandler{

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {
			File file = new File("workspace.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			do {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 ah時mm分");
				String str =  "『タイトル』 " +  rs.getString("item_name") + ","
						+ "『担当者』 " + rs.getString("user_name") + ","
						+ "『登録日』 "+ sdf.format(rs.getTimestamp("registration_date")) + ","
						+ "『期限日』 "+ sdf.format(rs.getTimestamp("expire_date"));

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
