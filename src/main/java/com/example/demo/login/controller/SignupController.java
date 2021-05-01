package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.UsersDTO;
import com.example.demo.login.domain.service.UsersService;

@Controller
public class SignupController {


	//ラジオボタン(男or女)の実装 Mapに入ったキーと値を画面表示できる
		private Map<String, String> radioMaleFemale;

	@Autowired
	UsersService usersService;


	//ユーザー登録画面のGETコントローラー
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, Model model) {
		model.addAttribute("contents", "login/signup::loginLayout_contents");
		//ModelAttributeをつけると、自動でModelクラスに登録（addAttribute）してくれる
		//model.addAttribute("loginForm",form);
		//ラジオボタンの初期化メソッドの呼び出し
		radioMaleFemale = initRadioMaleFemale();

		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMaleFemale", radioMaleFemale);

		//signup.htmlに飛ぶ
		return "/login/loginLayout";
	}

	//ユーザー登録画面のPOSTコントローラー
	@PostMapping("/signup")
	//@Validated　バリデーションを行う際に付ける　パラメーターに実行順序のインターフェースを指定する
	//そうするとバリデーションがグループ実行される　
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult,
			Model model) {
		//データバインドを受け取るにはBindingResultクラスを追加する　このクラスのhasError()メソッドで、データバインドに失敗してるかが分かる
		//データバインド失敗の場合
		//入力チェックに引っかかった場合、ユーザー登録画面に戻る
		if (bindingResult.hasErrors()) {

			//GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
			return getSignUp(form, model);
		}

		//formの中身をコンソールに出して確認します
		System.out.println(form);

//		String encodePassword = form.getPassword();
//		String digest = new BCryptPasswordEncoder().encode(encodePassword);

		int rowNumber = usersService.check(form.getUser_id());

		if(rowNumber > 0) {

			model.addAttribute("result","入力されたuser_idがすでに使用されています。");
			return getSignUp(form,model);
		}


		UsersDTO usersdto = new UsersDTO();

		usersdto.setUser_id(form.getUser_id());
		usersdto.setPassword(form.getPassword());
		usersdto.setUser_name(form.getUserName());
		usersdto.setBirthday(form.getBirthday());
		usersdto.setHireDate(form.getHireDate());
		usersdto.setMaleFemale(form.isMaleFemale());
		usersdto.setRole("ROLE_GENERAL");

		//ユーザーの誕生日入力をもとに、年齢計算
//		public int getAge(form.getBirthday()) {
//
//		    // ユーザーが入力した誕生日
//		    LocalDate birthday = LocalDate.of(form.getBirthday());
//
//		    // 現在の年月日
//		    LocalDate today = LocalDate.now();
//		    //ChronoUnitは年（12ヶ月）　YEARSは一年の概要を表す　betweenで期間の計算
//		    long period = ChronoUnit.YEARS.between(birthday, today);
//
//		    return (int)period;
//		}





		//新規ユーザー登録処理
		boolean result = usersService.insert(usersdto);

		//新規ユーザー登録結果の判定
		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		//login.htmlにリダイレクトする
		return "redirect:/login";
	}

	//ラジオボタンの初期化
	private Map<String, String> initRadioMaleFemale() {

		Map<String, String> radio = new LinkedHashMap<>();

		//男、女をMapに格納
		radio.put("男", "true");
		radio.put("女", "false");

		return radio;
	}

	//@ExceptionHandlerの使いかた
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		//例外クラスのメッセージをModelに登録
		model.addAttribute("error","内部サーバーエラー（DB）：ExceptionHandler");
		//例外クラスのメッセージをModelに登録
		model.addAttribute("message", "SignupControllerでDataAccessExceptionが発生しました");
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}
		//@ExceptionHandlerの使い方
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {

		//例外クラスのメッセージをModelに登録
	    model.addAttribute("error","内部サーバーエラー：ExceptionHandler");
	    //例外クラスのメッセージをModelに登録
	    model.addAttribute("message","SignupControllerでExceptionが発生しました");
	    //HTTPのエラーコード(500)をModelに登録
	    model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
	    return "error";
	}

}