package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

//RoCallbackHandler これもRowMapperと似てる　ますRowCallbackHandlerを実装
//その後processRowメソッド内でResultSetから取得した値をsmple.csvに書き込む処理をしている
//ResultSetExceptionではResultSetのnext()メソッドを使わなければレコードの値を取得できなかった
//だが、RowCallbackHandlerの場合はすでに一回next()メソッドが実行された状態になっているため、while文ではなく
//do while文でループを行っている
//sample.csvはプロジェクトフォルダの直下に作られる
public class UsersRowCallbackHandler implements RowCallbackHandler {

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		try {

			//ファイル書き込みの準備
			File file = new File("users.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			do {

				//ResultSetから値を取得してStringにセット
				String str = "『ユーザーID』 " + rs.getString("user_id") + ","
						+ "『ユーザー名』 " + rs.getString("user_name") + ","
						+ "『誕生日』 " + rs.getString("birthday") + ","
						+ "『会社入社日』 " + rs.getString("hireDate");
					//性別も追加するかも	+ "『』 " + rs.getDate("");

				//ファイルへ書込と改行
				bw.write(str);
				bw.newLine();

			} while (rs.next());

			//強制的に書き込み&ファイルクローズ
			bw.flush();
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}
