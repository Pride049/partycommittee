package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcRemind;;

public interface PcRemindDao {

	public List<PcRemind> getWorkPlanById(Integer agencyId, Integer year, Integer q);
	
	public List<PcRemind> getListWorkPlanByParentId(Integer parentId, Integer year, Integer q);
	
	
	public List<PcRemind> getMeetingById(Integer agencyId, Integer year, Integer q);
		
	public List<PcRemind> getListMeetingByParentId(Integer parentId, Integer year, Integer q);
	
}
