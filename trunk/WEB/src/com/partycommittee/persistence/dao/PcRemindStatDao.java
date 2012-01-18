package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcRemindStat;;

public interface PcRemindStatDao {

	public List<PcRemindStat> getWorkPlanById(Integer id, Integer year, Integer q);
		
	public List<PcRemindStat> getListWorkPlanByParentId(Integer id, Integer year, Integer q);

	public List<PcRemindStat> getMeetingById(Integer id, Integer year, Integer q);
	
	public List<PcRemindStat> getListMeetingByParentId(Integer id, Integer year, Integer q);	
}
