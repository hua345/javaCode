package com.github.chenjianhua.controller;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.chenjianhua.model.MUser;
import com.github.chenjianhua.service.MUserServiceImpl;
import com.github.chenjianhua.service.MUserService;


@Controller
@RequestMapping("/muserController")

public class MUserController {
	private MUserServiceImpl mUserService;

	public MUserController(){
		this.mUserService = new MUserServiceImpl();
	}
	public MUserServiceImpl getmUserService() {
		return mUserService;
	}

	public void setmUserService(MUserServiceImpl mUserService) {
		this.mUserService = mUserService;
	}
	
	@RequestMapping(value="listUser")
	public String listUser(HttpServletRequest request){
		List<MUser> list = mUserService.getAll();
		request.setAttribute("userlist", list);
		return "listUser";
	}
	@RequestMapping(value="addUser")
	public String addUser(MUser muser){
		String id = UUID.randomUUID().toString();
		muser.setId(id);
		mUserService.insert(muser);
		return "redirect:/muserController/listUser.do";
	}
	@RequestMapping(value="deleteUser")
	public String deleteUser(String userId){
		mUserService.deleteByprimaryKey(userId);
		return "redirect:/muserController/listUser.do";
	}
	@RequestMapping(value="updateUser")
	public String updateUser(MUser muser){
		mUserService.updateByPrimaryKey(muser.getId(), muser);
		return "redirect:/muserController/listUser.do";
	}
	@RequestMapping(value="updateUserUI")
	public String updateUserUI(String userId, HttpServletRequest request){
		MUser mUser = mUserService.selectMUserByPrimaryKey(userId);
		request.setAttribute("user", mUser);
		return "updateUser";
	}
}
