package com.partycommittee.remote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.partycommittee.remote.vo.PcAgencyStatsVo;
import com.partycommittee.service.PcStatService;

@Service("PcStatRo")
@RemotingDestination(channels={"my-amf"})
public class PcStatRemoteService {
	
	@Resource(name="PcStatService")
	private PcStatService pcStatService;
	public void setPcStatService(PcStatService pcStatService) {
		this.pcStatService = pcStatService;
	}
	
	@RemotingInclude
	public List<PcAgencyStatsVo> getAgencyStatById(Integer id) {
		return pcStatService.getAgencyStatsByParentId(id);
	}

}
