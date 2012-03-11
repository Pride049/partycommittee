package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.partycommittee.persistence.daoimpl.PcAgencyStatsDaoImpl;
import com.partycommittee.persistence.daoimpl.PcStatsDaoImpl;
import com.partycommittee.persistence.po.PcAgencyStats;
import com.partycommittee.persistence.po.PcStats;
import com.partycommittee.remote.vo.PcAgencyStatsVo;
import com.partycommittee.remote.vo.PcStatsVo;



@Service("PcStatService")
public class PcStatService {

	@Resource(name="PcAgencyStatsDaoImpl")
	private PcAgencyStatsDaoImpl pcAgencyStatsDaoImpl;
	public void setPcStatDaoImpl(PcAgencyStatsDaoImpl pcAgencyStatsDaoImpl) {
		this.pcAgencyStatsDaoImpl = pcAgencyStatsDaoImpl;
	}
	
	@Resource(name="PcStatsDaoImpl")
	private PcStatsDaoImpl pcStatsDaoImpl;
	public void setPcStatsDaoImpl(PcStatsDaoImpl pcStatsDaoImpl) {
		this.pcStatsDaoImpl = pcStatsDaoImpl;
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
	
	
	public List<PcStatsVo> getWorkPlanStatsById(Integer id, Integer year, Integer startM, Integer endM) {
		List<PcStatsVo> list = new ArrayList<PcStatsVo>();
		try {
			List<Integer> qs = new ArrayList<Integer>() ;
			
			for(Integer i = startM; i <= endM; i++) {
				if (  qs.indexOf(getQuarter(i)) == -1 ){
					qs.add(getQuarter(i));
				}
			}
			
			Integer i = 1;
	
			for(i = 1; i < 5; i++) {
				List<Integer> qs_tmp = new ArrayList<Integer>() ;
				if (i == 1 || i == 4) {
					qs_tmp.add(0);
				} else {
					qs_tmp = qs;
				}
				List<PcStatsVo> list_admin = pcStatsDaoImpl.getStatsById(id, year, qs_tmp, i);
				for(PcStatsVo item : list_admin) {
					list.add(item);
				}
			}
				
			for(i = 1; i< 5; i++) {
				List<Integer> qs_tmp = new ArrayList<Integer>() ;
				if (i == 1 || i == 4) {
					qs_tmp.add(0);
				} else {
					qs_tmp = qs;
				}			
				List<PcStatsVo> list_admin = pcStatsDaoImpl.getStatsByParentId(id, year, qs_tmp, i);
				for(PcStatsVo item : list_admin) {
					list.add(item);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<PcStatsVo> getMeetingStatsById(Integer id, Integer year, Integer startM, Integer endM) {
		List<PcStatsVo> list = new ArrayList<PcStatsVo>();
		try {
			List<Integer> qs = new ArrayList<Integer>() ;
			
			for(Integer i = startM; i <= endM; i++) {
				if (  qs.indexOf(getQuarter(i)) == -1 ){
					qs.add(getQuarter(i));
				}
			}
			
			Integer i = 1;
	
			for(i = 5; i <= 9; i++) {
				
				if (i == 8) {
					List<PcStatsVo> list_zwh = pcStatsDaoImpl.getZwhStatsById(id, year, startM, endM);
					for(PcStatsVo item : list_zwh) {
						list.add(item);
					}
				} else {
					List<PcStatsVo> list_admin = pcStatsDaoImpl.getStatsById(id, year, qs, i);
					for(PcStatsVo item : list_admin) {
						list.add(item);
					}					
				}

			}
				
			for(i = 5; i<= 9; i++) {
				List<Integer> qs_tmp = new ArrayList<Integer>() ;
				if (i == 8) {
					List<PcStatsVo> list_zwh = pcStatsDaoImpl.getZwhStatsByParentId(id, year, startM, endM);
					for(PcStatsVo item : list_zwh) {
						list.add(item);
					}
				} else {
					List<PcStatsVo> list_admin = pcStatsDaoImpl.getStatsByParentId(id, year, qs, i);
					for(PcStatsVo item : list_admin) {
						list.add(item);
					}
				}
			}
						
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}	
	
	public List<PcStatsVo> getZbStatsById(Integer id, Integer year, Integer startM, Integer endM) {
		List<PcStatsVo> list = new ArrayList<PcStatsVo>();
		try {
			List<Integer> qs = new ArrayList<Integer>() ;
			
			for(Integer i = startM; i <= endM; i++) {
				if (  qs.indexOf(getQuarter(i)) == -1 ){
					qs.add(getQuarter(i));
				}
			}
			
			Integer i = 1;
	
			for(i = 1; i <= 9; i++) {
				List<Integer> qs_tmp = new ArrayList<Integer>() ;
				if (i == 1 || i == 4) {
					qs_tmp.add(0);
				} else {
					qs_tmp = qs;
				}
				
				if (i == 8) {
					List<PcStatsVo> list_zwh = pcStatsDaoImpl.getZwhStatsById(id, year, startM, endM);
					for(PcStatsVo item : list_zwh) {
						list.add(item);
					}
				} else {
					List<PcStatsVo> list_admin = pcStatsDaoImpl.getStatsById(id, year, qs_tmp, i);
					for(PcStatsVo item : list_admin) {
						list.add(item);
					}					
				}

			}
						
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}	
	
	
	
	
	public Integer getQuarter(Integer month) {
		
		if (month <= 3) {
			return 1;
		} else if (month <= 6) {
			return 2;
		} else if (month <= 9) {
			return 3;
		} else {
			return 4;
		}
	}
	
}
