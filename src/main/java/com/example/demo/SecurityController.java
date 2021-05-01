package com.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.login.domain.model.UsersDTO;

@Controller
public class SecurityController {

	@RequestMapping("/")
	public void index(@AuthenticationPrincipal UsersDTO usersdto) {



	}
}
