package com.github.chenjianhua.dao;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import com.github.chenjianhua.model.MUser;

public class MUserMapperImpl implements MUserMapper{
	private List<MUser> muserList;
	public MUserMapperImpl(){
		this.muserList = new ArrayList<MUser>();
		MUser usera = new MUser();
		usera.setId(UUID.randomUUID().toString());
		usera.setName("fangfang");
		usera.setAge(22);
		muserList.add(usera);
		MUser userb = new MUser();
		userb.setId(UUID.randomUUID().toString());
		userb.setName("jianhua");
		userb.setAge(24);
		muserList.add(userb);
	}
	public int insert(MUser user){
		int result = muserList.add(user) ? 1 : 0;;
		return result;
	};
	
	public int updateByPrimaryKey(String primaryKey, MUser user){
		int result = 0;
		for (int i = 0; i < muserList.size(); i++){
			if(muserList.get(i).getId().equals(primaryKey)){
				muserList.set(i, user);
				result = 1;
				break;
			}
		}
		return result;
	};
	
	public MUser selectMUserByPrimaryKey(String primaryKey){
		MUser user = new MUser();
		for (int i = 0; i < muserList.size(); i++){
			if(muserList.get(i).getId().equals(primaryKey)){
				user = muserList.get(i);
				break;
			}
		}
		return user;
	}
	public int deleteByprimaryKey(String primaryKey){
		int result = 0;
		for (int i = 0; i < muserList.size(); i++){
			if(muserList.get(i).getId().equals(primaryKey)){
				muserList.remove(i);
				result = 1;
				break;
			}
		}
		System.out.println(muserList.toString());
		return result;
	};
	
	public List<MUser> getAll(){
		return muserList;
	};
}
