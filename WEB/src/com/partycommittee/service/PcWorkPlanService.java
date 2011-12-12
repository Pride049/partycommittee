package com.partycommittee.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcWorkPlanDaoImpl;
import com.partycommittee.persistence.daoimpl.PcWorkPlanContentDaoImpl;
import com.partycommittee.persistence.po.PcWorkPlan;
import com.partycommittee.persistence.po.PcWorkPlanContent;
import com.partycommittee.remote.vo.PcWorkPlanContentVo;
import com.partycommittee.remote.vo.PcWorkPlanVo;

@Transactional
@Service("PcWorkPlanService")
public class PcWorkPlanService {

	@Resource(name="PcWorkPlanDaoImpl")
	private PcWorkPlanDaoImpl pcWorkPlanDaoImpl;
	public void setPcWorkPlanDaoImpl(PcWorkPlanDaoImpl pcWorkPlanDaoImpl) {
		this.pcWorkPlanDaoImpl = pcWorkPlanDaoImpl;
	}
	
	@Resource(name="PcWorkPlanContentDaoImpl")
	private PcWorkPlanContentDaoImpl pcWorkPlanContentDaoImpl;
	public void setPcWorkPlanContentDaoImpl(PcWorkPlanContentDaoImpl pcWorkPlanContentDaoImpl) {
		this.pcWorkPlanContentDaoImpl = pcWorkPlanContentDaoImpl;
	}
	
	public void createWorkPlan(PcWorkPlanVo workPlan) {
		PcWorkPlan pcWorkPlan = pcWorkPlanDaoImpl.createWorkPlan(PcWorkPlanVo.toPcWorkPlan(workPlan));
		if (workPlan.getWorkPlanContent() != null) {
			PcWorkPlanContentVo content = workPlan.getWorkPlanContent();
			content.setWorkplanId(pcWorkPlan.getId());
			pcWorkPlanContentDaoImpl.createContent(PcWorkPlanContentVo.toPcWorkPlanContent(workPlan.getWorkPlanContent()));
		}
	}
	
	public void updateWorkPlan(PcWorkPlanVo workPlan) {
		pcWorkPlanDaoImpl.updateWorkPlan(PcWorkPlanVo.toPcWorkPlan(workPlan));
		if (workPlan.getWorkPlanContent() != null) {
			pcWorkPlanContentDaoImpl.upateContent(PcWorkPlanContentVo.toPcWorkPlanContent(workPlan.getWorkPlanContent()));
		}
	}
	
	public PcWorkPlanVo getWorkPlayYearly(Integer agencyId, Integer year) {
		PcWorkPlan workPlan = pcWorkPlanDaoImpl.getWorkPlanYearlyByAgencyId(agencyId, year);
		if (workPlan == null) {
			return null;
		}
		PcWorkPlanContent workPlanContent = pcWorkPlanContentDaoImpl.getContentByWorkPlanId(workPlan.getId());
		PcWorkPlanVo workPlanVo = PcWorkPlanVo.fromPcWorkPlan(workPlan);
		workPlanVo.setWorkPlanContent(PcWorkPlanContentVo.fromPcWorkPlanContent(workPlanContent));
		return workPlanVo;
	}
}
