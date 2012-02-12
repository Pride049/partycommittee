package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcParentStatDaoImpl;
import com.partycommittee.persistence.po.PcParentStat;
import com.partycommittee.remote.vo.PcParentStatVo;

@Transactional
@Service("PcParentStatService")
public class PcParentStatService {

	@Resource(name="PcParentStatDaoImpl")
	private PcParentStatDaoImpl PcParentStatDaoImpl;
	public void setPcParentStatDaoImpl(PcParentStatDaoImpl PcParentStatDaoImpl) {
		this.PcParentStatDaoImpl = PcParentStatDaoImpl;
	}

	public List<PcParentStatVo> getListStatByParentId(Integer id, Integer year, Integer q) {
		List<PcParentStatVo> list = new ArrayList<PcParentStatVo>();
		
		//年度计划
		List<PcParentStat> y = PcParentStatDaoImpl.getListStatByParentId(id, year, q);
		for (PcParentStat item : y) {
			list.add(PcParentStatVo.fromPcParentStat(item));
		}
		return list;
	}
	
	
}
