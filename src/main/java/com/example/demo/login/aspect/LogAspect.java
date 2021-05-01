package com.example.demo.login.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //AOPクラスには付ける
@Component //上記のアノテーションと同時にDIコンテナへBean定義するためこれもつける
public class LogAspect {


	//指定方法はexecution(<戻り値><パッケージ名>.<クラス名>.<メソッド名>(引数))"
	//Aroundを使うとアノテーションをつけた中でAOP対象クラスのメソッドを直接実行するので、メソッド実行の前後で任意の処理をすることができる
	//戻り値を直接実行しているためreturnを忘れないこと
	@Around("execution(* *..*.*Controller.*(..))")
	//@Around("bean(*Controller)") 実行場所（Pointcut）にbeanを使うとDIに登録されているBean名でAOPの対象指定できる
	//@Around("annotation(org.springframeword.web.bind.annotation.GetMapping)") @annotationを使うと指定したアノテーションがついてくるメソッドすべてを対象とする
	//その際はパッケージを含めたクラス名を指定する
	//@Around("@within(org.springframeword.stereotype.Controller)") @withinを使うと指定したアノテーションがついているクラスのすべてのメソッドが対象となる　これもパッケージすべて指定

	public Object startLog(ProceedingJoinPoint jp) throws Throwable {

		System.out.println("メソッド開始：" + jp.getSignature());

		//戻り値は:*（すべての戻り値にしている）パッケージ名:*..*(こう書くと全てのパッケージ対象)
		//クラス名：*Controller（こう書くと末尾にControllerがつくクラスが対象になる）
		//メソッド名：*を指定　引数：..(ドット2つですべての引数が対象になる)


		try {

			Object result = jp.proceed();
			System.out.println("メソッド終了:" + jp.getSignature());

			return result;

		} catch (Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
	//UsersDaoクラスのログ出力
	@Around("execution(* *..*.*UsersDao*.*(..))")
	public Object daoLog(ProceedingJoinPoint jp) throws Throwable {

		System.out.println("メソッド開始：" + jp.getSignature());

		try {

			Object result = jp.proceed();

			System.out.println("メソッド終了：" + jp.getSignature());

			return result;

		}catch (Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}

}
//AOPを実装するメソッドには、Before.After等のアノテーションをつける　
	//Beforeはメソッドが実行される前にAOPの処理（Advisce）を実行する
	//Afterはメソッドが実行された後にAOPの処理（Advisce）を実行する
//ログインのみ⇒@Before("execution(* com.example.demo.login.controller.LoginController.getLogin(..))")
//ログインのみ⇒@After("execution(* com.example.demo.login.controller.LoginController.getLogin(..))")
//@After("execution(* *..*.*Controller.*(..))")
//public void endLog(JoinPoint jp) {
//	System.out.println("メソッド終了:" + jp.getSignature());
//}