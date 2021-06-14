package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	//ログイン画面のGET]
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("contents", "login/login::loginLayout_contents");
		//loginLayoutに飛んでいく
		return "login/loginLayout";
	}

	//ログイン画面のPOST
	@PostMapping("/login")
	public String postLogin( Model model) {
		System.out.println("Postログイン到達");

		//workspaceに飛んでいく
		return "redirect:/workspace";
	}

}
