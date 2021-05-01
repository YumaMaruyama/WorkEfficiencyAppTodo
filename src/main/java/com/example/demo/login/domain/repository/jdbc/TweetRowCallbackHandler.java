package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

public class TweetRowCallbackHandler implements RowCallbackHandler{

		@Override
		public void processRow(ResultSet rs) throws SQLException {

			try {
				File file = new File("tweet.csv");
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);

				do {
					String str = rs.getString("contents") + ","
							+ rs.getString("registration_date");

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

