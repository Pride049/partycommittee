package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcMember;

public interface PcMemberDao {

	public List<PcMember> getMemberList();
	
	public List<PcMember> getMemberListByAgencyId(int agencyId);
	
	public PcMember getMemberById(int id);
	
	public void createPcMember(PcMember member);
	
	public void updatePcMember(PcMember member);
	
	public void deletePcMember(PcMember member);
}
