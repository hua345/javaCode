package com.github.chenjianhua.service;

import java.util.List;

import com.github.chenjianhua.model.MUser;
import com.github.chenjianhua.dao.MUserMapper;
import com.github.chenjianhua.dao.MUserMapperImpl;
public class MUserServiceImpl implements MUserService{
	private MUserMapperImpl mUserMapper;
	
	public MUserServiceImpl(){
		this.mUserMapper = new MUserMapperImpl();
	}
	public MUserMapper getmUserMapper() {
		return mUserMapper;
	}

	public void setmUserMapper(MUserMapperImpl mUserMapper) {
		this.mUserMapper = mUserMapper;
	}
	
	public int insert(MUser user){
		return mUserMapper.insert(user);
	};
	
	public int updateByPrimaryKey(String primaryKey, MUser user){
		return mUserMapper.updateByPrimaryKey(primaryKey, user);
	};
	
	public MUser selectMUserByPrimaryKey(String primaryKey){
		return mUserMapper.selectMUserByPrimaryKey(primaryKey);
	}
	public int deleteByprimaryKey(String primaryKey){
		return mUserMapper.deleteByprimaryKey(primaryKey);
	};
	
	public List<MUser> getAll(){
		System.out.println("user List:");
		List<MUser> muserList = mUserMapper.getAll();
		for (int i = 0; i < muserList.size(); i++){
			System.out.println(muserList.get(i).toString());
		}
		return mUserMapper.getAll();
	};
}
