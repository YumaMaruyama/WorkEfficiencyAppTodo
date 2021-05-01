package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc//Springのモック＿画面表示内容の確認をするためにはモックを使う　これを使うとコントローラークラスのテストが簡単にできる
//@AutoConfigureMockMvcをつけて、そのあとMockMvcクラスを@AutowiredすればSpringのモックを使うことができる
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void ログイン画面表示() throws Exception {

		//画面表示内容の確認　
		mockMvc.perform(get("/login"))//ログイン画面をゲット
		.andExpect(status().isOk())//HTTPリクエストが正常に終了したかをチェック
		.andExpect(content().string(containsString("ユーザーID")));//ログイン画面に「ユーザーID」という文字が含まれているかをチェック
	}
}
