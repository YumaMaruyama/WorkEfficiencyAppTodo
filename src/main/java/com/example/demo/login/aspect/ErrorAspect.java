package com.example.demo.login.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorAspect {


	@AfterThrowing(value="execution(* *..*..*(..))"
			+ " &&(bean(*Controller) || bean(*Service) || bean(*Repository))"
			, throwing = "ex")
	public void throwingNull(DataAccessException ex) {

		//例外処理の内容（ログ出力）主にデータベースでアクセスエラーが出た時に出る　varcharaの（）のなかの数字に注意
		System.out.println("==============================================");
		System.out.println("DataAccessExceptionが発生しました。:" + ex);
		System.out.println("==============================================");


	}
}
