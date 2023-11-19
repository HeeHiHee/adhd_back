package com.ssafy.video.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.video.model.dao.UserDao;
import com.ssafy.video.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	// 회원가입
	@Override
	public void signup(User user) {
		userDao.signup(user);
	}

	// 로그인
	@Override
	public User login(User user) {
		return userDao.getUserOne(user.getUserId());
	}

	// 유저 한 명 조회
	@Override
	public User getUserOne(String id) {
		return userDao.getUserOne(id);
	}
	
	// 유저 정보 수정
	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);		
	}
	
	// 유저 정보 삭제
	@Override
	public void UserRemove(String id) {
		userDao.UserRemove(id);
		
	}

}
