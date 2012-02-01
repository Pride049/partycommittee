package com.partycommittee.remote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.partycommittee.remote.vo.PcRemindStatVo;
import com.partycommittee.remote.vo.PcRemindVo;
import com.partycommittee.service.PcRemindService;;

@Service("PcRemindRo")
@RemotingDestination(channels={"my-amf"})
public class PcRemindRemoteService {
	
	@Resource(name="PcRemindService")
	private PcRemindService pcRemindService;
	public void setPcRemindService(PcRemindService pcRemindService) {
		this.pcRemindService = pcRemindService;
	}

	@RemotingInclude
	public List<PcRemindVo> getRealRemindById(Integer id, Integer year, Integer q) {
		return pcRemindService.getRealRemindById(id, year, q);
	}		
	
	@RemotingInclude
	public List<PcRemindStatVo> getListRemindStatById(Integer id, Integer year, Integer q) {
		return pcRemindService.getListRemindStatById(id, year, q);
	}	
	
	@RemotingInclude
	public List<PcRemindVo> getListRemindByParentIdForOther(Integer id, Integer year, Integer q) {
		return pcRemindService.getListRemindStatByParentIdForOther(id, year, q);
	}
	
	@RemotingInclude
	public List<PcRemindStatVo> getListRemindStatByParentIdForAdmin(Integer id, Integer year, Integer q) {
		return pcRemindService.getListRemindStatByParentIdForAdmin(id, year, q);
	}

}
