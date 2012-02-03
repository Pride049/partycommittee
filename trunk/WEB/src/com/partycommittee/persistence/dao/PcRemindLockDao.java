package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcRemindLock;;

public interface PcRemindLockDao {
	
	public List<PcRemindLock> getRemindLockByFilters(List<Object> filters);
	
	public void updateRemindLock(PcRemindLock vo);
	
}
