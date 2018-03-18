package com.github.chenjianhua.service;

import java.util.List;
import com.github.chenjianhua.model.MUser;

public interface MUserService {
	
	int insert(MUser user);
	
	int updateByPrimaryKey(String primaryKey, MUser user);
	
	int deleteByprimaryKey(String primaryKey);
	
	MUser selectMUserByPrimaryKey(String primaryKey);
	
	public List<MUser> getAll();
}
