package com.partycommittee.remote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.partycommittee.remote.vo.PcAgencyVo;
import com.partycommittee.remote.vo.PcRemindLockVo;
import com.partycommittee.service.PcRemindLockService;;

@Service("PcRemindLockRo")
@RemotingDestination(channels={"my-amf"})
public class PcRemindLockRemoteService {
	
	@Resource(name="PcRemindLockService")
	private PcRemindLockService pcRemindLockService;
	public void setPcRemindLockService(PcRemindLockService pcRemindLockService) {
		this.pcRemindLockService = pcRemindLockService;
	}

	@RemotingInclude
	public List<PcRemindLockVo> getRemindLockByFilters(List<Object> filters) {
		return pcRemindLockService.getRemindLockByFilters(filters);
	}		
		
	@RemotingInclude
	public void updateRemindLock(PcRemindLockVo vo) {
		pcRemindLockService.updateRemindLock(vo);
	}	

}
