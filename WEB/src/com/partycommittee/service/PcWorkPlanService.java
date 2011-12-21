package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcAgencyRelationDaoImpl;
import com.partycommittee.persistence.daoimpl.PcWorkPlanDaoImpl;
import com.partycommittee.persistence.daoimpl.PcWorkPlanContentDaoImpl;
import com.partycommittee.persistence.po.PcAgencyRelation;
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
	
	@Resource(name="PcAgencyRelationDaoImpl")
	private PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl;
	public void setPcAgencyRelationDaoImpl(PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl) {
		this.pcAgencyRelationDaoImpl = pcAgencyRelationDaoImpl;
	}
	
	public void createWorkPlan(PcWorkPlanVo workPlan) throws Exception {
		if (workPlan == null) {
			return;
		}
		PcWorkPlan pcWorkPlan = pcWorkPlanDaoImpl.createWorkPlan(PcWorkPlanVo.toPcWorkPlan(workPlan));
		if (pcWorkPlan != null && workPlan.getWorkPlanContent() != null) {
			PcWorkPlanContentVo content = workPlan.getWorkPlanContent();
			content.setWorkplanId(pcWorkPlan.getId());
			pcWorkPlanContentDaoImpl.createContent(PcWorkPlanContentVo.toPcWorkPlanContent(content));
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

	public List<PcWorkPlanVo> getCommitWorkplanListByParentId(Integer agencyId) {
		List<PcWorkPlanVo> list = new ArrayList<PcWorkPlanVo>();
		List<PcAgencyRelation> agencyRelationList = pcAgencyRelationDaoImpl.getChildrenByParentId(agencyId);
		if (agencyRelationList == null || agencyRelationList.size() == 0) {
			return null;
		}
		List<Integer> agencyIds = new ArrayList<Integer>();
		for (PcAgencyRelation agencyRelation : agencyRelationList) {
			agencyIds.add(agencyRelation.getAgencyId());
		}
		List<PcWorkPlan> workPlanList = new ArrayList<PcWorkPlan>();
		workPlanList = pcWorkPlanDaoImpl.getCommitWorkPlanListByAgencyIds(agencyIds);
		for (PcWorkPlan workPlan : workPlanList) {
			list.add(PcWorkPlanVo.fromPcWorkPlan(workPlan));
		}
		return list;
	}
	
	public PcWorkPlanContentVo getWorkPlanContentByWorkPlanId(Integer workPlanId) {
		PcWorkPlanContent content = pcWorkPlanContentDaoImpl.getContentByWorkPlanId(workPlanId);
		return PcWorkPlanContentVo.fromPcWorkPlanContent(content);
	}
	
	public void approvalWorkPlan(PcWorkPlanVo workPlanVo) {
		
	}
	
	public void evaluateWrokplan(PcWorkPlanVo workPlanVo) {
		
	}
}