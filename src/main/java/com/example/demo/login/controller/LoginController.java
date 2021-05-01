package com.example.demo.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.repository.jdbc.UsersDaoJdbcImpl;

@Controller
public class LoginController {

	//ログイン画面のGET
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("contents", "login/login::loginLayout_contents");
		//loginのHTMLに飛んでいく
		return "login/loginLayout";
	}

	//ログイン画面のPOST
	@PostMapping("/login")
	public String postLogin(@AuthenticationPrincipal UsersDaoJdbcImpl usersdtoimpl ,Model model) {
		System.out.println("Postログイン到達");

		//ログイン者のuser_id(メールアドレス)をもとに、user_name（丸山佑馬）を取得する
		//springsecrityの機能でuser_idがuser_nameで持ってこれる


		//Todo_itemsDTO headerName = todo_itemsService.selectTwo(principal.username);

		//workspaceのhtmlに飛んでいく
		return "redirect:/workspace";
	}

}
