package com.example.demo.login.domain.service.jdbc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.service.RestService;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {

//	@Autowired
//	@Qualifier("UsersDaoJdbcImpl")
//	UsersDao dao;
//
//	//一件登録用メソッド
//	@Override
//	public boolean insert(UsersDTO usersdto) {
//
//		int result = dao.insertOne(usersdto);
//
//		if (result == 0) {
//			return false;
//		} else {
//			return true;
//		}
//
//	}
//
//	//一件検索用メソッド
//	@Override
//	public UsersDTO selectOne(String user_id) {
//		return dao.selectOne(user_id);
//	}
//
//	//全件検索用メソッド
//	@Override
//	public List<UsersDTO> selectMany() {
//		return dao.selectMany();
//	}
//
//	//一件更新用メソッド
//	@Override
//	public boolean updateOne(UsersDTO usersdto) {
//
//		int result = dao.updateOne(usersdto);
//
//		if(result == 0) {
//			return false;
//		}else {
//			return true;
//		}
//	}
//
//	//一件削除用メソッド
//	@Override
//	public boolean deleteOne(String user_id) {
//
//		int result = dao.deleteOne(user_id);
//
//		if(result == 0) {
//			return false;
//		}else {
//			return true;
//		}
//	}

}
