package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.login.domain.service.UsersService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorkspaceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean//Springが用意するMockitoを使ってメソッドの戻り値を任意の値に変更することができる
	//まずモックとして使用するBeanに@MockBeanをつける　そのに※１の部分でメソッドの戻り値を任意の値にできる
	private UsersService service;

	@Test
	@WithMockUser//ログインした後にした表示できない画面のテストをすることができる
	//@WithMockUser(username="maru",roles={"ROLE_ADMIN"})
	public void ユーザーリスト画面のユーザー件数のテスト()throws Exception {

		when(service.count()).thenReturn(10);//※１ これはservice.count()メソッドの戻り値を任意の値にしています　

		mockMvc.perform(get("/usersList"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("合計:10件")));
	}
}
