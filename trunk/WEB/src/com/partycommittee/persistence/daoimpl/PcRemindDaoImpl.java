package com.partycommittee.persistence.daoimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcRemindDao;
import com.partycommittee.persistence.po.PcRemind;;

@Repository("PcRemindDaoImpl")
public class PcRemindDaoImpl extends JpaDaoBase implements PcRemindDao {
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PcRemind> getListNoCommitByParentId(Integer id, Integer year, Integer q, Integer tid) {
		try {
			if (id == null) {
				return null;
			}
			return super.find("from PcRemind where parent_id = " + id + " AND year = " + year + " and quarter = " + q + " AND type_id = " + tid + " AND status <= 1 Order by agency_id ASC ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PcRemind> getListByParentId(Integer id, Integer year, Integer q, Integer tid, Integer sid) {
		try {
			if (id == null) {
				return null;
			}
			return super.find("from PcRemind where parent_id = " + id + " AND year = " + year + " and quarter = " + q + " AND type_id = " + tid + " AND status = " + sid + " Order by agency_id ASC ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
