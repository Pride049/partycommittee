package com.partycommittee.persistence.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcMeetingDao;
import com.partycommittee.persistence.po.PcMeeting;

@Repository("PcMeetingDaoImpl")
public class PcMeetingDaoImpl extends JpaDaoBase implements PcMeetingDao {

	@SuppressWarnings("unchecked")
	public List<PcMeeting> getMeetingList(Integer agencyId, Integer year, Integer typeId) {
		try {
			return super.find("from PcMeeting where agencyId = ? and year = ? and typeId = ?", 
					agencyId, year, typeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void submitMeeting(PcMeeting pcMeeting) {
		try {
			pcMeeting.setStatusId(1);
			super.merge(pcMeeting);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PcMeeting createMeeting(PcMeeting pcMeeting) {
		try {
			pcMeeting.setId(null);
			super.persist(pcMeeting);
			return pcMeeting;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateMeeting(PcMeeting pcMeeting) {
		try {
			super.merge(pcMeeting);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<PcMeeting> getCommitMeetingListByAgencyIds(
			List<Integer> agencyIds) {
		try {
			if (agencyIds == null || agencyIds.size() == 0) {
				return null;
			}
			String ids = "";
			for (Integer idItem : agencyIds) {
				if (ids.equals("")) {
					ids = idItem + "";
				} else {
					ids += "," + idItem;
				}
			}
			return super.find("from PcMeeting where agencyId in (" + ids + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public PcMeeting getMeeting(Integer agencyId, Integer year, Integer quarter, Integer typeId) {
		try {
			List<PcMeeting> list = new ArrayList<PcMeeting>();
			list = super.find("from PcMeeting where agencyId = ? and year = ? and quarter = ? and typeId = ?", 
					agencyId, year, quarter, typeId);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
