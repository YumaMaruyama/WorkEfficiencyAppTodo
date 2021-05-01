package com.example.demo.login.domain.service.mybatis;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.service.RestService;

@Transactional
@Service("RestServiceMybatisImpl")
public class RestServiceMybatisImpl implements RestService{

//	@Autowired
//	UsersMapper2 usersMapper;
//
//	@Override
//	public boolean insert(UsersDTO usersdto) {
//		//一件insert実行
//		return usersMapper.insert(usersdto);
//	}
//
//	@Override
//	public UsersDTO selectOne(String user_id) {
//		//一件select実行
//		return usersMapper.selectOne(user_id);
//	}
//
//	@Override
//	public List<UsersDTO> selectMany() {
//		//全件select実行
//		return usersMapper.selectMany();
//	}
//
//	@Override
//	public boolean updateOne(UsersDTO usersdto) {
//		//一件update実行
//		return usersMapper.updateOne(usersdto);
//	}
//
//	@Override
//	public boolean deleteOne(String user_id) {
//		//一件delete実行
//		return usersMapper.deleteOne(user_id);
//	}
}
