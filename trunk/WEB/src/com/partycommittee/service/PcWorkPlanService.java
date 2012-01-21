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
	
	public void updateWorkPlanStatus(Integer workPlanId, Integer StatusId) {
		pcWorkPlanDaoImpl.updateWorkPlanStatus(workPlanId, StatusId);
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
	
	public PcWorkPlanVo getWorkPlayYearlySummary(Integer agencyId, Integer year) {
		PcWorkPlan workPlan = pcWorkPlanDaoImpl.getWorkPlanYearlySummaryByAgencyId(agencyId, year);
		if (workPlan == null) {
			return null;
		}
		PcWorkPlanContent workPlanContent = pcWorkPlanContentDaoImpl.getContentByWorkPlanId(workPlan.getId());
		PcWorkPlanVo workPlanVo = PcWorkPlanVo.fromPcWorkPlan(workPlan);
		workPlanVo.setWorkPlanContent(PcWorkPlanContentVo.fromPcWorkPlanContent(workPlanContent));
		return workPlanVo;
	}

	public List<PcWorkPlanVo> getCommitWorkplanListByParentId(Integer agencyId, Integer year) {
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
		workPlanList = pcWorkPlanDaoImpl.getCommitWorkPlanListByAgencyIds(agencyIds, year);
		for (PcWorkPlan workPlan : workPlanList) {
			list.add(PcWorkPlanVo.fromPcWorkPlan(workPlan));
		}
		return list;
	}
	
	public PcWorkPlanContentVo getWorkPlanContentByWorkPlanId(Integer workPlanId) {
		PcWorkPlanContent content = pcWorkPlanContentDaoImpl.getContentByWorkPlanIdAndType(workPlanId, 1);
		return PcWorkPlanContentVo.fromPcWorkPlanContent(content);
	}
	
	public void approvalWorkPlan(Integer workPlanId, PcWorkPlanContentVo contentVo) {
		contentVo.setWorkplanId(workPlanId);
		contentVo.setType(2);
		PcWorkPlan workPlan = pcWorkPlanDaoImpl.getWorkPlanById(workPlanId);
		workPlan.setStatusId(3);
		pcWorkPlanDaoImpl.updateWorkPlan(workPlan);
		pcWorkPlanContentDaoImpl.createContent(PcWorkPlanContentVo.toPcWorkPlanContent(contentVo));
	}
	
	public void evaluateWrokplan(Integer workPlanId, PcWorkPlanContentVo contentVo) {
		contentVo.setWorkplanId(workPlanId);
		contentVo.setType(3);
		PcWorkPlan workPlan = pcWorkPlanDaoImpl.getWorkPlanById(workPlanId);
		workPlan.setStatusId(4);
		pcWorkPlanDaoImpl.updateWorkPlan(workPlan);
		pcWorkPlanContentDaoImpl.createContent(PcWorkPlanContentVo.toPcWorkPlanContent(contentVo));
	}

	public PcWorkPlanVo getWorkPlanQuarter(Integer agencyId, Integer year,
			Integer quarter) {
		PcWorkPlan workPlan = pcWorkPlanDaoImpl.getWorkPlanQuarterByAgencyId(agencyId, year, quarter);
		if (workPlan == null) {
			return null;
		}
		PcWorkPlanContent workPlanContent = pcWorkPlanContentDaoImpl.getContentByWorkPlanId(workPlan.getId());
		PcWorkPlanVo workPlanVo = PcWorkPlanVo.fromPcWorkPlan(workPlan);
		workPlanVo.setWorkPlanContent(PcWorkPlanContentVo.fromPcWorkPlanContent(workPlanContent));
		return workPlanVo;
	}

	public List<PcWorkPlanVo> getWorkPlanListQuarter(Integer agencyId, Integer year) {
		List<PcWorkPlanVo> list = createOriginalQuarterWorkPlanList(agencyId, year, 2);
		List<PcWorkPlan> workPlanList = pcWorkPlanDaoImpl.getWorkPlanQuarterByYear(agencyId, year);
		if (workPlanList == null || workPlanList.size() == 0) {
			return list;
		}
		for (PcWorkPlan workPlanItem : workPlanList) {
			for (int i = 0; i < list.size(); i++) {
				PcWorkPlanVo workPlanVo = list.get(i);
				if (workPlanVo.getQuarter().intValue() == workPlanItem.getQuarter().intValue()) {
					list.remove(i);
					list.add(i, PcWorkPlanVo.fromPcWorkPlan(workPlanItem));
				}
			}
		}
		return list;
	}

	private List<PcWorkPlanVo> createOriginalQuarterWorkPlanList(Integer agencyId, Integer year, Integer typeId) {
		List<PcWorkPlanVo> list = new ArrayList<PcWorkPlanVo>();
		for (int i = 1; i <= 4; i++) {
			PcWorkPlanVo pcWorkPlan = new PcWorkPlanVo();
			pcWorkPlan.setYear(year);
			pcWorkPlan.setQuarter(i);
			pcWorkPlan.setTypeId(typeId);
			pcWorkPlan.setAgencyId(agencyId);
			pcWorkPlan.setStatusId(1);
			list.add(pcWorkPlan);
		}
		return list;
	}

	public List<PcWorkPlanVo> getResultListQuarter(Integer agencyId, Integer year) {
		List<PcWorkPlanVo> list = createOriginalQuarterWorkPlanList(agencyId, year, 3);
		List<PcWorkPlan> workPlanList = pcWorkPlanDaoImpl.getResultQuarterByYear(agencyId, year);
		if (workPlanList == null || workPlanList.size() == 0) {
			return list;
		}
		for (PcWorkPlan workPlanItem : workPlanList) {
			for (int i = 0; i < list.size(); i++) {
				PcWorkPlanVo workPlanVo = list.get(i);
				if (workPlanVo.getQuarter().intValue() == workPlanItem.getQuarter().intValue()) {
					list.remove(i);
					list.add(i, PcWorkPlanVo.fromPcWorkPlan(workPlanItem));
				}
			}
		}
		return list;
	}

	public void submitWorkPlan(PcWorkPlanVo workPlan) {
		workPlan.setStatusId(1);
		pcWorkPlanDaoImpl.updateWorkPlan(PcWorkPlanVo.toPcWorkPlan(workPlan));
	}

	public PcWorkPlanContentVo getContentByWorkPlanId(Integer workPlanId) {
		PcWorkPlanContent content = pcWorkPlanContentDaoImpl.getContentByWorkPlanIdAndType(workPlanId, 1);
		return PcWorkPlanContentVo.fromPcWorkPlanContent(content);
	}

	public PcWorkPlanContentVo getApprovalInfo(Integer workPlanId) {
		PcWorkPlanContent content = pcWorkPlanContentDaoImpl.getContentByWorkPlanIdAndType(workPlanId, 2);
		if (content == null) {
			return null;
		}
		return PcWorkPlanContentVo.fromPcWorkPlanContent(content);
	}

	public PcWorkPlanContentVo getEvaluateInfo(Integer workPlanId) {
		PcWorkPlanContent content = pcWorkPlanContentDaoImpl.getContentByWorkPlanIdAndType(workPlanId, 3);
		if (content == null) {
			return null;
		}
		return PcWorkPlanContentVo.fromPcWorkPlanContent(content);
	}

	public List<PcWorkPlanVo> getAlertInfo(Integer agencyId, Integer year,
			Integer quarter) {
		List<PcWorkPlanVo> list = new ArrayList<PcWorkPlanVo>();
		// Year work plan.
		PcWorkPlan yearWorkPlan = pcWorkPlanDaoImpl.getWorkPlanYearlyByAgencyId(agencyId, year);
		if (yearWorkPlan != null) {
			list.add(PcWorkPlanVo.fromPcWorkPlan(yearWorkPlan));
		} else {
			PcWorkPlanVo workPlanVo = new PcWorkPlanVo();
			workPlanVo.setTypeId(1);
			workPlanVo.setStatusId(0);
			list.add(workPlanVo);
		}
		// Quarter work plan.
		PcWorkPlan quarterWorkPlan = pcWorkPlanDaoImpl.getWorkPlanQuarterByAgencyId(agencyId, year, quarter);
		if (quarterWorkPlan != null) {
			list.add(PcWorkPlanVo.fromPcWorkPlan(quarterWorkPlan));
		} else {
			PcWorkPlanVo workPlanVo = new PcWorkPlanVo();
			workPlanVo.setTypeId(2);
			workPlanVo.setStatusId(0);
			list.add(workPlanVo);
		}
		// Quarter result.
		PcWorkPlan quarterResultWorkPlan = pcWorkPlanDaoImpl.getResultQuarterByAgencyId(agencyId, year, quarter);
		if (quarterResultWorkPlan != null) {
			list.add(PcWorkPlanVo.fromPcWorkPlan(quarterResultWorkPlan));
		} else {
			PcWorkPlanVo workPlanVo = new PcWorkPlanVo();
			workPlanVo.setTypeId(3);
			workPlanVo.setStatusId(0);
			list.add(workPlanVo);
		}
		// Year result.
		PcWorkPlan yearResultWorkPlan = pcWorkPlanDaoImpl.getWorkPlanYearlySummaryByAgencyId(agencyId, year);
		if (yearResultWorkPlan != null) {
			list.add(PcWorkPlanVo.fromPcWorkPlan(yearResultWorkPlan));
		} else {
			PcWorkPlanVo workPlanVo = new PcWorkPlanVo();
			workPlanVo.setTypeId(4);
			workPlanVo.setStatusId(0);
			list.add(workPlanVo);
		}
		return list;
	}

	public PcWorkPlanContentVo getWorkplanComment(PcWorkPlanVo workPlanVo) {
		PcWorkPlanContent content = null;
		if (workPlanVo == null) {
			return null;
		}
		if (workPlanVo.getTypeId() == 1 || workPlanVo.getTypeId() == 2) {
			content = pcWorkPlanContentDaoImpl.getContentByWorkPlanIdAndType(workPlanVo.getId(), 2);
		} else {
			content = pcWorkPlanContentDaoImpl.getContentByWorkPlanIdAndType(workPlanVo.getId(), 3);
		}
		if (content != null) {
			return PcWorkPlanContentVo.fromPcWorkPlanContent(content);
		}
		return null;
	}

}
