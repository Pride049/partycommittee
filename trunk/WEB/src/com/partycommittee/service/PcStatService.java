package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.partycommittee.persistence.daoimpl.PcAgencyRelationDaoImpl;
import com.partycommittee.persistence.daoimpl.PcAgencyStatsDaoImpl;
import com.partycommittee.persistence.daoimpl.PcStatsDaoImpl;
import com.partycommittee.persistence.po.PcAgencyRelation;
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
	
	@Resource(name="PcAgencyRelationDaoImpl")
	private PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl;
	public void setPcAgencyRelationDaoImpl(PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl) {
		this.pcAgencyRelationDaoImpl = pcAgencyRelationDaoImpl;
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
				List<PcStatsVo> list_admin = new ArrayList<PcStatsVo>();
				if (id == 1) {
					list_admin = pcStatsDaoImpl.getStatsByIdForAdmin(id, year, qs_tmp, i);
				} else {
					list_admin = pcStatsDaoImpl.getStatsById(id, year, qs_tmp, i);
				}
				for(PcStatsVo item : list_admin) {
					list.add(item);
				}
			}
			
			
			List<PcAgencyRelation> agencyRelationList = pcAgencyRelationDaoImpl.getChildrenByParentId(id);
			if (agencyRelationList == null || agencyRelationList.size() == 0) {
				return null;
			}
			
			for (PcAgencyRelation agencyRelation : agencyRelationList) {
				for(i = 1; i< 5; i++) {
					List<Integer> qs_tmp = new ArrayList<Integer>() ;
					if (i == 1 || i == 4) {
						qs_tmp.add(0);
					} else {
						qs_tmp = qs;
					}
					List<PcStatsVo> list_admin = pcStatsDaoImpl.getStatsById(agencyRelation.getAgencyId(), year, qs_tmp, i);
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
				
				if (i >= 8) {
					List<PcStatsVo> list_zwh = new ArrayList<PcStatsVo>();
					if (id == 1) {
						list_zwh = pcStatsDaoImpl.getZwhStatsByIdForAdmin(id, year, i, startM, endM);
					} else {
						list_zwh = pcStatsDaoImpl.getZwhStatsById(id, year, i, startM, endM);
					}
					
					for(PcStatsVo item : list_zwh) {
						list.add(item);
					}
				} else {
					List<PcStatsVo> list_admin = new ArrayList<PcStatsVo>();
					if (id == 1) {
						list_admin = pcStatsDaoImpl.getStatsByIdForAdmin(id, year, qs, i);
					} else {
						list_admin = pcStatsDaoImpl.getStatsById(id, year, qs, i);
					}
					
					for(PcStatsVo item : list_admin) {
						list.add(item);
					}					
				}

			}

			List<PcAgencyRelation> agencyRelationList = pcAgencyRelationDaoImpl.getChildrenByParentId(id);
			if (agencyRelationList == null || agencyRelationList.size() == 0) {
				return null;
			}
			
			for (PcAgencyRelation agencyRelation : agencyRelationList) {
				for(i = 5; i<= 9; i++) {
					List<Integer> qs_tmp = new ArrayList<Integer>() ;
					if (i >= 8) {
						List<PcStatsVo> list_zwh = pcStatsDaoImpl.getZwhStatsById(agencyRelation.getAgencyId(), year, i, startM, endM);
						for(PcStatsVo item : list_zwh) {
							list.add(item);
						}
					} else {
						List<PcStatsVo> list_admin = pcStatsDaoImpl.getStatsById(agencyRelation.getAgencyId(), year, qs, i);
						for(PcStatsVo item : list_admin) {
							list.add(item);
						}
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
				
				if (i >= 8) {
					List<PcStatsVo> list_zwh = pcStatsDaoImpl.getZwhStatsById(id, year, i, startM, endM);
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
