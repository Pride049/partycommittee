package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcWorkPlan;

public interface PcWorkPlanDao {

	public PcWorkPlan getWorkPlanYearlyByAgencyId(Integer agencyId, Integer year);
	
	public PcWorkPlan createWorkPlan(PcWorkPlan workPlan);

	public void updateWorkPlan(PcWorkPlan workPlan);
	
	public List<PcWorkPlan> getCommitWorkPlanListByAgencyIds(List<Integer> agencyIds);
	
	public PcWorkPlan getWorkPlanQuarterByAgencyId(Integer agencyId,
			Integer year, Integer quarter);

}
