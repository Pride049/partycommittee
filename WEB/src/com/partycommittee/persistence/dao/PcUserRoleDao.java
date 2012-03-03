package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcUserRole;;

public interface PcUserRoleDao {

	public List<PcUserRole> getRolesByUserId(int userId);
	
}
