package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowCallbackHandler;

public class One_to_oneMailRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {
			File file = new File("one_to_oneMail.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			do {
				//registration_dateを秒数まで表示するフォーマットを生成(3つ目のgetTimestampが正常に変換可能)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 ah時mm分ss秒");
				String str = "『送信者』 " + rs.getString("user_name") + ","
						+ "『メール内容』 " + rs.getString("mail") + ","
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
