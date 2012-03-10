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
		
		if (id == 1) {
			List<PcAgencyStats> list_admin = pcAgencyStatsDaoImpl.getAgencyStatsById(id);
			for (PcAgencyStats item : list_admin) {
				list.add(PcAgencyStatsVo.fromPcAgencyStats(item));
			}		
			
			List<PcAgencyStats> list_child = pcAgencyStatsDaoImpl.getAgencyStatsByParentId(id);
			for (PcAgencyStats item : list_child) {
				list.add(PcAgencyStatsVo.fromPcAgencyStats(item));
			}			
		} else {
			List<PcAgencyStats> y = pcAgencyStatsDaoImpl.getAgencyStatsByParentCode(id);
			for (PcAgencyStats item : y) {
				list.add(PcAgencyStatsVo.fromPcAgencyStats(item));
			}
		}
		return list;
	}		
}
