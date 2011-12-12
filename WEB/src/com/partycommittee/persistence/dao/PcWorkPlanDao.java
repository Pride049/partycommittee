package com.partycommittee.persistence.dao;

import com.partycommittee.persistence.po.PcWorkPlan;

public interface PcWorkPlanDao {

	public PcWorkPlan getWorkPlanYearlyByAgencyId(Integer agencyId, Integer year);
	
	public PcWorkPlan createWorkPlan(PcWorkPlan workPlan);

	public void updateWorkPlan(PcWorkPlan workPlan);

}
