package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcMeetingDaoImpl;
import com.partycommittee.persistence.daoimpl.PcRemindDaoImpl;
import com.partycommittee.persistence.daoimpl.PcWorkPlanDaoImpl;
import com.partycommittee.remote.vo.PcMeetingVo;
import com.partycommittee.remote.vo.PcRemindVo;
import com.partycommittee.remote.vo.PcWorkPlanVo;
import com.partycommittee.persistence.po.PcMeeting;
import com.partycommittee.persistence.po.PcRemind;
import com.partycommittee.persistence.po.PcWorkPlan;


@Transactional
@Service("PcRemindService")
public class PcRemindService {

	@Resource(name="PcRemindDaoImpl")
	private PcRemindDaoImpl PcRemindDaoImpl;
	public void setPcRemindDaoImpl(PcRemindDaoImpl PcRemindDaoImpl) {
		this.PcRemindDaoImpl = PcRemindDaoImpl;
	}
	
	@Resource(name="PcWorkPlanDaoImpl")
	private PcWorkPlanDaoImpl pcWorkPlanDaoImpl;
	public void setPcWorkPlanDaoImpl(PcWorkPlanDaoImpl pcWorkPlanDaoImpl) {
		this.pcWorkPlanDaoImpl = pcWorkPlanDaoImpl;
	}
	
	@Resource(name="PcMeetingDaoImpl")
	private PcMeetingDaoImpl pcMeetingDaoImpl;
	public void setPcMeetingDaoImpl(PcMeetingDaoImpl pcMeetingDaoImpl) {
		this.pcMeetingDaoImpl = pcMeetingDaoImpl;
	}	
	
	public List<PcRemindVo> getListRemindById(Integer id, Integer year, Integer q) {
		List<PcRemindVo> list = new ArrayList<PcRemindVo>();
		List<PcRemind> workplan = PcRemindDaoImpl.getWorkPlanById(id, year, q);
		List<PcRemind> meeting = PcRemindDaoImpl.getWorkPlanById(id, year, q);
		for (PcRemind item : workplan) {
			list.add(PcRemindVo.fromPcRemind(item));
		}
		
		for (PcRemind item : meeting) {
			list.add(PcRemindVo.fromPcRemind(item));
		}
		return list;
	}

	public List<PcRemindVo> getListRemindByParentId(Integer id, Integer year, Integer q) {
		List<PcRemindVo> list = new ArrayList<PcRemindVo>();
		List<PcRemind> workplan = PcRemindDaoImpl.getListWorkPlanByParentId(id, year, q);
		List<PcRemind> meeting = PcRemindDaoImpl.getListMeetingByParentId(id, year, q);

		for (PcRemind item : workplan) {
			list.add(PcRemindVo.fromPcRemind(item));
		}
		
		for (PcRemind item : meeting) {
			list.add(PcRemindVo.fromPcRemind(item));
		}
		return list;
	}
	
	public List<PcRemindVo> getRealRemindById(Integer agencyId, Integer year,
			Integer quarter) {
		List<PcRemindVo> list = new ArrayList<PcRemindVo>();
		// Year work plan.
		
		
		// 计划类
		PcWorkPlan yearWorkPlan = pcWorkPlanDaoImpl.getWorkPlanYearlyByAgencyId(agencyId, year);
		
		if (yearWorkPlan != null) {
			
			PcWorkPlanVo vo = PcWorkPlanVo.fromPcWorkPlan(yearWorkPlan);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);
		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(1);			
			list.add(remindvo);
		}
		// Quarter work plan.
		PcWorkPlan quarterWorkPlan = pcWorkPlanDaoImpl.getWorkPlanQuarterByAgencyId(agencyId, year, quarter);
		if (quarterWorkPlan != null) {
			PcWorkPlanVo vo = PcWorkPlanVo.fromPcWorkPlan(quarterWorkPlan);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);
		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(2);			
			list.add(remindvo);
		}
		// Quarter result.
		PcWorkPlan quarterResultWorkPlan = pcWorkPlanDaoImpl.getResultQuarterByAgencyId(agencyId, year, quarter);
		if (quarterResultWorkPlan != null) {
			PcWorkPlanVo vo = PcWorkPlanVo.fromPcWorkPlan(quarterResultWorkPlan);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);			

		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(3);			
			list.add(remindvo);
		}
		// Year result.
		PcWorkPlan yearResultWorkPlan = pcWorkPlanDaoImpl.getWorkPlanYearlySummaryByAgencyId(agencyId, year);
		if (yearResultWorkPlan != null) {
			
			PcWorkPlanVo vo = PcWorkPlanVo.fromPcWorkPlan(yearResultWorkPlan);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);	

		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(4);			
			list.add(remindvo);
		}
		
		// 会议类
		PcMeeting classMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 5);
		if (classMeeting != null) {
			
			PcMeetingVo vo = PcMeetingVo.fromPcMeeting(classMeeting);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);				

		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(5);			
			list.add(remindvo);
		}
		// branch members.
		PcMeeting branchMemberMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 6);
		if (branchMemberMeeting != null) {
			PcMeetingVo vo = PcMeetingVo.fromPcMeeting(branchMemberMeeting);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);		
		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(6);			
			list.add(remindvo);
		}
		
		// branch life.
		PcMeeting branchLifeMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 7);
		if (branchLifeMeeting != null) {
			PcMeetingVo vo = PcMeetingVo.fromPcMeeting(branchLifeMeeting);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);		
		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(7);			
			list.add(remindvo);
		}				
		
		// branch committee.
		PcMeeting branchCommitteeMeeting = pcMeetingDaoImpl.getMeeting(agencyId, year, quarter, 8);
		if (branchCommitteeMeeting != null) {
			PcMeetingVo vo = PcMeetingVo.fromPcMeeting(branchCommitteeMeeting);
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setAgencyId(vo.getAgencyId());
			remindvo.setStatusId(vo.getStatusId());
			remindvo.setTypeId(vo.getTypeId());
			list.add(remindvo);		
		} else {
			PcRemindVo remindvo = new PcRemindVo();
			remindvo.setStatusId(0);
			remindvo.setTypeId(8);			
			list.add(remindvo);
		}
		return list;
	}	
	
}
