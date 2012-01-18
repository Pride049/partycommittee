package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcRemindDaoImpl;
import com.partycommittee.remote.vo.PcRemindVo;
import com.partycommittee.persistence.po.PcRemind;


@Transactional
@Service("PcRemindService")
public class PcRemindService {

	@Resource(name="PcRemindService")
	private PcRemindDaoImpl PcRemindDaoImpl;
	public void setPcRemindDaoImpl(PcRemindDaoImpl PcRemindDaoImpl) {
		this.PcRemindDaoImpl = PcRemindDaoImpl;
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
}
