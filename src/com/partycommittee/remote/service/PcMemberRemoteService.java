package com.partycommittee.remote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.partycommittee.remote.vo.PcMemberVo;
import com.partycommittee.service.PcMemberService;

@Service("PcMemberRo")
@RemotingDestination(channels={"my-amf"})
public class PcMemberRemoteService {

	@Resource(name="PcMemberService")
	private PcMemberService pcMemberService;
	public void setPcMemberService(PcMemberService pcMemberService) {
		this.pcMemberService = pcMemberService;
	}
	
	@RemotingInclude
	public List<PcMemberVo> getMemberListByAgencyId(int agencyId) {
		return pcMemberService.getMemberListByAgencyId(agencyId);
	}
	
	@RemotingInclude
	public void createMember(PcMemberVo memberVo) {
		pcMemberService.createMember(memberVo);
	}
	
	@RemotingInclude
	public void updateMember(PcMemberVo memberVo) {
		pcMemberService.updateMember(memberVo);
	}
	
	@RemotingInclude
	public void deleteMembers(List<PcMemberVo> memberList) {
		if (memberList == null || memberList.size() == 0) {
			return;
		}
		for (PcMemberVo memberItem : memberList) {
			pcMemberService.deleteMember(memberItem);
		}
	}
}
