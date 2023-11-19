package com.ssafy.video.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.video.model.dto.User;
import com.ssafy.video.model.service.UserService;
import com.ssafy.video.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "유저 컨트롤러")
@RequestMapping("/api")
public class UserController {
	// 응답을 편하게 하기 위해 상수로 지정
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtUtil jwtUtil;

	// UserService 라고 하는 친구를 주입
	@Autowired
	private UserService userService;

	// 유저 등록하기(회원가입)
	@ApiOperation("유저 등록하기(회원가입)")
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user) {
		userService.signup(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	// 로그인
	@ApiOperation("로그인")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		Map<String, Object> result = new HashMap<String, Object>();

		// User Service -> DAO -> DB //실제 유저인지 아닌지 확인 등등등....

		HttpStatus status = null;

		// User의 id가 Null이 아니거나 뭔가 작성이 되어있다면 로그인 성공 이라고 가정
		try {
			// 입력받은 유저의 아이디를 이용해 db에서 유저를 가져옴
			User dbUser = userService.login(user);

			// db에 저장된 유저의 비밀번호랑 입력받은 유저의 비밀번호를 비교
			if (dbUser != null && user.getUserPw().equals(dbUser.getUserPw())) {
				// 비밀번호가 일치하면 로그인 성공
				System.out.println(user);
				result.put("access-token", jwtUtil.createToken("id", user.getUserId()));
				result.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
			// 로그인 실패!
			else {
				result.put("message", FAIL);
				status = HttpStatus.NO_CONTENT;
			}
		} catch (UnsupportedEncodingException e) {
			result.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	// 유저아이디에 해당하는 유저 조회
	@ApiOperation("유저아이디에 해당하는 유저 조회")
	@GetMapping("/user/{id}")
	public ResponseEntity<User> selectList(@PathVariable String id) {
		User user = userService.getUserOne(id);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	// 유저 정보 수정하기
	@ApiOperation("유저 정보 수정하기")
	@PutMapping("/mypage/userInfo")
	public ResponseEntity<Void> doUpdate(@RequestBody User user){
		userService.updateUser(user);		
		return new ResponseEntity<Void>(HttpStatus.OK);		
	}
	
	// 유저 정보 삭제(탈퇴)
	@ApiOperation("유저 정보 삭제(탈퇴)")
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Void> UserRemove(@PathVariable String id){
		userService.UserRemove(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	

}