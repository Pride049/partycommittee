package com.partycommittee.persistence.dao;

import com.partycommittee.persistence.po.PcWorkPlanContent;

public interface PcWorkPlanContentDao {

	public PcWorkPlanContent getContentByWorkPlanId(Integer workPlanId);
	
	public void createContent(PcWorkPlanContent content);

	public void upateContent(PcWorkPlanContent content);
	
}
