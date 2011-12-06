package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcUserDaoImpl;
import com.partycommittee.persistence.po.PcUser;
import com.partycommittee.remote.vo.PcUserVo;

@Transactional
@Service("PCUserService")
public class PcUserService {
	
	@Resource(name="PCUserDaoImpl")
	private PcUserDaoImpl pcUserDaoImpl;
	public void setPCUserDaoImpl(PcUserDaoImpl pcUserDaoImpl) {
		this.pcUserDaoImpl = pcUserDaoImpl;
	}
	
	public PcUserVo login(String username, String password) {
		PcUser user = pcUserDaoImpl.login(username, password);
		if (user == null) {
			return null;
		}
		PcUserVo userVo = PcUserVo.fromPCUser(user);
		return userVo;
	}
	
	public List<PcUserVo> getUserList() {
		List<PcUserVo> list = new ArrayList<PcUserVo>();
		List<PcUser> userList = pcUserDaoImpl.getUserList();
		if (userList != null && userList.size() > 0) {
			for (PcUser user : userList) {
				list.add(PcUserVo.fromPCUser(user));
			}
		}
		return list;
	}
	
	public void createUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		pcUserDaoImpl.createUser(user);
	}
	
	public void updateUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		pcUserDaoImpl.updateUser(user);
	}
	
	public void deleteUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		pcUserDaoImpl.deleteUser(user);
	}
	
}
