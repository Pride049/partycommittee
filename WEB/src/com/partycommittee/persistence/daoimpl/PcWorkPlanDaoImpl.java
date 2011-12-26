package com.partycommittee.persistence.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcWorkPlanDao;
import com.partycommittee.persistence.po.PcWorkPlan;

@Repository("PcWorkPlanDaoImpl")
public class PcWorkPlanDaoImpl extends JpaDaoBase implements PcWorkPlanDao {

	@SuppressWarnings("unchecked")
	@Override
	public PcWorkPlan getWorkPlanYearlyByAgencyId(Integer agencyId, Integer year) {
		try {
			List<PcWorkPlan> list = super.find("from PcWorkPlan where agencyId = ? and year = ? and typeId = ?"
					, agencyId, year, 1);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public PcWorkPlan getWorkPlanYearlySummaryByAgencyId(Integer agencyId,
			Integer year) {
		try {
			List<PcWorkPlan> list = super.find("from PcWorkPlan where agencyId = ? and year = ? and typeId = ?"
					, agencyId, year, 4);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PcWorkPlan createWorkPlan(PcWorkPlan workPlan) {
		try {
			workPlan.setId(null);
			super.persist(workPlan);
			return workPlan;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void updateWorkPlan(PcWorkPlan workPlan) {
		try {
			super.merge(workPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcWorkPlan> getCommitWorkPlanListByAgencyIds(List<Integer> agencyIds) {
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
			return super.find("from PcWorkPlan where agencyId in (" + ids + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public PcWorkPlan getWorkPlanQuarterByAgencyId(Integer agencyId,
			Integer year, Integer quarter) {
		try {
			List<PcWorkPlan> list = super.find("from PcWorkPlan where agencyId = ? and year = ? and quarter = ? and typeId = ?"
					, agencyId, year, quarter, 2);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<PcWorkPlan> getWorkPlanQuarterByYear(Integer agencyId, Integer year) {
		try {
			return super.find("from PcWorkPlan where agencyId = ? and year = ? and typeId = ?",
					agencyId, year, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<PcWorkPlan> getResultQuarterByYear(Integer agencyId,
			Integer year) {
		try {
			return super.find("from PcWorkPlan where agencyId = ? and year = ? and typeId = ?",
					agencyId, year, 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public PcWorkPlan getResultQuarterByAgencyId(Integer agencyId,
			Integer year, Integer quarter) {
		try {
			List<PcWorkPlan> list = super.find("from PcWorkPlan where agencyId = ? and year = ? and quarter = ? and typeId = ?"
					, agencyId, year, quarter, 3);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
