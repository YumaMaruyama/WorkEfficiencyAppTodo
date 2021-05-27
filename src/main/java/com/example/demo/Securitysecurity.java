package com.example.demo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.login.domain.model.UsersDTO;

public class Securitysecurity extends UsersDTO implements UserDetails {

		private final UsersDTO usersdto;

		public Securitysecurity (UsersDTO usersdto) {
			this.usersdto = usersdto;
		}



		//ログイン者（データベースのusersテーブル）のuser_name（丸山佑馬）をヘッダーに表示させるために作ったけど、SpringSecurityの設定でuser_nameがuser_idになってる
		public String getUser_name() {
			return usersdto.getUser_name();
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
		@Override
		public String getPassword() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
		@Override
		public String getUsername() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
		@Override
		public boolean isAccountNonExpired() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}
		@Override
		public boolean isAccountNonLocked() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}
		@Override
		public boolean isEnabled() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}
}
