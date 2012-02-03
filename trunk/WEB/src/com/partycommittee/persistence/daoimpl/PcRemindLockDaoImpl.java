package com.partycommittee.persistence.daoimpl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcRemindLockDao;
import com.partycommittee.persistence.po.PcRemindLock;

@Repository("PcRemindLockDaoImpl")
public class PcRemindLockDaoImpl extends JpaDaoBase implements PcRemindLockDao {

	@Override
	public void updateRemindLock(PcRemindLock vo) {
		try {
			super.merge(vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PcRemindLock> getRemindLockByFilters(List<Object> filters) {
		try {
			return super.find("from PcRemindLock");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
