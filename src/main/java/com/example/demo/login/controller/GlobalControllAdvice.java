package com.example.demo.login.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//controllerAcviceクラスを用意しておくと、アプリ全体で発生した例外処理を実装できる
//クラスの中身はコントローラークラス毎の例外処理と同じで、@ExceptionHandlerをつけたメソッドを用意するだけ
@ControllerAdvice
@Component
public class GlobalControllAdvice {

	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {

		//例外クラスのメッセージをModelに登録
		model.addAttribute("error", "内部サーバーエラー（BD）：GlobalControllAdvice");

		model.addAttribute("message", "DataAccessExceptionが発生しました");

		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String ExceptionHandler(Exception e, Model model) {

		model.addAttribute("error", "内部サーバーエラー:GlobalControllAdvice");

		model.addAttribute("message", "Exceptionが発生しました");

		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}
}
