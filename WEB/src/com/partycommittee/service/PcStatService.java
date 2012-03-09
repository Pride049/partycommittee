package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.partycommittee.persistence.daoimpl.PcAgencyStatsDaoImpl;
import com.partycommittee.persistence.po.PcAgencyStats;
import com.partycommittee.remote.vo.PcAgencyStatsVo;



@Service("PcStatService")
public class PcStatService {

	@Resource(name="PcAgencyStatsDaoImpl")
	private PcAgencyStatsDaoImpl pcAgencyStatsDaoImpl;
	public void setPcStatDaoImpl(PcAgencyStatsDaoImpl pcAgencyStatsDaoImpl) {
		this.pcAgencyStatsDaoImpl = pcAgencyStatsDaoImpl;
	}
	
	public List<PcAgencyStatsVo> getAgencyStatsByParentId(Integer id) {
		List<PcAgencyStatsVo> list = new ArrayList<PcAgencyStatsVo>();
		
		List<PcAgencyStats> y = pcAgencyStatsDaoImpl.getAgencyStatsByParentId(id);
		for (PcAgencyStats item : y) {
			list.add(PcAgencyStatsVo.fromPcAgencyStats(item));
		}
		return list;
	}		
}
