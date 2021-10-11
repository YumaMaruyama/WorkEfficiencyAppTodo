package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
//セキュリティ設定用クラスには@EnableWebSecurityをつける　また、WebSecurityConfigurerAdapterクラスを継承する
//WebSecurityConfigurerAdapterの各種メソッドをオーバーライドすることでセキュリティの設定を行っていける
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//パスワードエンコードのBean定義
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	//データソース　 SQL実行できるようにAutowiredする ユーザーデータを取得するためにSQL文を２つ用意
	//一つ目はユーザーIDとパスワードを使用不可を検索するようにする　
	//2つ目はユーザーIDと権限を検索するようにする
	@Autowired
	private DataSource dataSource;

	//ユーザーIDとパスワードを取得するSQL文
	private static final String USER_SQL = "select"
			+ " user_id,"
			+ " password,"
			+ " true"
			+ " from"
			+ " users"
			+ " where"
			+ " user_id = ?";

	//ユーザーのロールを取得するSQL文
	private static final String ROLE_SQL = "select"
			+ " user_id,"
			+ " role"
			+ " from"
			+ " users"
			+ " where"
			+ " user_id = ?";


	@Override
	public void configure(WebSecurity web)throws Exception {
		//静的リソースを除外
		//静的リソースへのアクセスには、セキュリティーを適用しない
		web.ignoring().antMatchers("/webjars/**","/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//直リンクの禁止
		//ログイン不要ページの設定
		//静的リソースを除外 wevjars.cssなどの静的リソースにはだれでもアクセスできるようにする
		//そのため下記の通りに書くとセキュリティ設定を適用しないようにできる
		//**を二つつけると正規表現でいずれかのファイルとなる　下記のファイルだと/webjars配下と/css配下のファイルに対しては、セキュリティの対象外となる
		http
		.authorizeRequests()
		//上記使用すると直リンク禁止先の条件を追加してくれる　メソッドチェーンとは.()で
		//メソッドを連続で呼び出すこと
		.antMatchers("/wevjars/**").permitAll()//webjarsへアクセス可能
		.antMatchers("/js/**").permitAll()
		.antMatchers("/css/**").permitAll()//cssへアクセス可能
		.antMatchers("/login").permitAll()//ログインページは直リンクOK
		.antMatchers("/signup").permitAll()//ユーザー登録画面は直リンクOK
		.antMatchers("/termsOfServiceLogin").permitAll()//ログイン画面の利用規約画面は直リンクOK
		.antMatchers("/privacyPolicyLogin").permitAll()//ログイン画面のプライバシーポリシー画面は直リンクOK
		.antMatchers("/inquiryLogin").permitAll()//ログイン画面のお問い合わせは直リンクOK
		.antMatchers("/guideLogin").permitAll()//ログイン画面のガイドは直リンクOK
		.antMatchers("/applicationDetail").permitAll()//ログイン画面のアプリ詳細は直リンクOK
		.antMatchers("/snsShareLogin").permitAll()//ログイン画面のSNS詳細は直リンクOK
		.antMatchers("/rest/**").permitAll()
		.antMatchers("/admin").hasAuthority("ROLE_ADMIN")//role_admin以外は表示されない
		.anyRequest().authenticated();//それ以外は直リンク禁止
		//antMatchers("リンク先").permitAll()を使用するとリンク先に対しての設定をできる
		//リンク先に.permitAllをつけるとログインしてないユーザーでもリンク先にアクセス可能
		//anyRequestはすべてのリンクが対象 .authenticated()をつけると認証しないとアクセスできないようになる
		//なのでこれが一番最後になるように書かないといけない
		//hasAuthority()かっこの中にはデータベースから持ってきた名前を入れる
		//


		//ログイン処理
		//http.formLogin()にメソッドチェーンで条件追加
		//LoginProcessingUri("リンク先") ログイン処理をするURLを指定する　
		//loginPage("リンク先") これを設定してないと、Springデフォルトページが表示されてしまうので自分でぺーじをつくっているなら注意
		http
		.formLogin()
		.loginProcessingUrl("/login")//ログインページのパス
		.loginPage("/login")//ログインページの指定
		.failureUrl("/login")//failureUrl("リンク先") 失敗時
		//.usernameParameter(パラメーター名)　.passwordParameter これらはログインページのパラメーターを入れる
		//指定するパラメーターはHTMLファイルから探す　今回はlogin/htmlから探す　
		//具体的には（【login.html(一部抜粋】<label>ユーザーID<label><input type="text" name="*{user_id}"/>）
		.usernameParameter("user_id")
		.passwordParameter("password")
		.defaultSuccessUrl("/workspace",true);//ログイン成功時の推移先

		//ログアウト処理

		http
			.logout()
			//Springのデフォルトでは、ログアウト処理はPOSTメソッドで送るため、GETメソッドでリクエストを送る場合、logoutRequestMatcher()を使う
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login");

		//CSRFを無効にするURLを設定
		RequestMatcher csrfMatcher = new RestMatcher("/rest/**");

		//RESTのみCSRF対策を無効に設定
		http.csrf().requireCsrfProtectionMatcher(csrfMatcher);



		//CSRF対策を無効に設定
		//http.csrf().disable();
	}

	//上記で書いたSQL文をusersByUsernameQueryとauthoritiesByUsernameQueryメソッドの引数に入れる
	//これで入力されたユーザーIDとパスワードを使って認証処理をしてくれる
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//ユーザーデータの取得（DB）
		//ログイン処理時のユーザー情報をDBから取得する
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(USER_SQL)
		.authoritiesByUsernameQuery(ROLE_SQL)
		//PasswordEncoderを実装したクラスは、BcrypPasswordEncoder以外にもいくつかある、暗号化のアルゴリズムによっていくつかのクラスがある
		.passwordEncoder(passwordEncoder());

	}

}
