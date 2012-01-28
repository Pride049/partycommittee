package com.partycommittee.persistence.daoimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcRemindStatDao;
import com.partycommittee.persistence.po.PcRemindStat;;

@Repository("PcRemindStatDaoImpl")
public class PcRemindStatDaoImpl extends JpaDaoBase implements PcRemindStatDao {
	
	@SuppressWarnings("unchecked")
	public List<PcRemindStat> getWorkPlanById(Integer id, Integer year, Integer q, Integer s) {
		try {
			List<PcRemindStat> list = super.find("from PcRemindStat where agency_id = ? and year = ?  and type_id = ?", id, year, s);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PcRemindStat> getListWorkPlanByParentId(Integer id, Integer year, Integer q, Integer s) {
		try {
			if (id == null) {
				return null;
			}

			if (year == 0)  {
				Calendar calendar=Calendar.getInstance();  
				calendar.setTime(new Date()); 
				year = calendar.get(Calendar.YEAR);
			}
			return super.find("from PcRemindStat where parent_id = " + id + " AND year = " + year + " AND quarter = " + q + " AND type_id = " + s + " Order by agency_id ASC ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<PcRemindStat> getMeetingById(Integer id, Integer year, Integer q, Integer s) {
		try {
			List<PcRemindStat> list = super.find("from PcRemindStat where agency_id = ? and year = ? and quarter = ? and type_id = ?", id, year, q, s);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PcRemindStat> getListMeetingByParentId(Integer id, Integer year, Integer q, Integer s) {
		try {
			if (id == null) {
				return null;
			}

			if (year == 0)  {
				Calendar calendar=Calendar.getInstance();  
				calendar.setTime(new Date()); 
				year = calendar.get(Calendar.YEAR);
			}
			return super.find("from PcRemindStat where parent_id = " + id + " AND year = " + year + " AND quarter = " + q + " AND type_id = " + s + " Order by agency_id ASC ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}
