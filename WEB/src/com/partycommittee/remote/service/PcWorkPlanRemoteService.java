package com.partycommittee.remote.service;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.partycommittee.remote.vo.PcWorkPlanVo;
import com.partycommittee.service.PcWorkPlanService;

@Service("PcWorkPlan")
@RemotingDestination(channels={"my-amf"})
public class PcWorkPlanRemoteService {
	
	@Resource(name="PcWorkPlanService")
	private PcWorkPlanService pcWorkPlanService;
	public void setPcWorkPlanService(PcWorkPlanService pcWorkPlanService) {
		this.pcWorkPlanService = pcWorkPlanService;
	}

	@RemotingInclude
	public void createWorkPlan(PcWorkPlanVo workPlan) {
		pcWorkPlanService.createWorkPlan(workPlan);
	}
	
	@RemotingInclude
	public void updateWorkPlan(PcWorkPlanVo workPlan) {
		pcWorkPlanService.updateWorkPlan(workPlan);
	}
	
	@RemotingInclude
	public PcWorkPlanVo getWorkPlayYearly(Integer agencyId, Integer year) {
		return pcWorkPlanService.getWorkPlayYearly(agencyId, year);
	}
}
