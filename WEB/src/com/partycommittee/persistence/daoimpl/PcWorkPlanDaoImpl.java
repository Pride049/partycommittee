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
			return super.find("from PcWorkPlan where ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
