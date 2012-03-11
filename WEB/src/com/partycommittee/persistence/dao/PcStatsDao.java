package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcStats;


public interface PcStatsDao {

	public List<PcStats> getStatsById(Integer id, Integer year, List<Integer> qs, Integer typeId);

	public List<PcStats> getStatsByParentCode(Integer id, Integer year, List<Integer> qs, Integer typeId);

//	public List<PcStats> getMeetingStatsById(Integer id, Integer year, List<Integer> qs, Integer typeId);

//	public List<PcStats> getMeetingStatsByParentCode(Integer id, Integer year, List<Integer> qs, Integer typeId);
	
	public List<PcStats> getZwhStatsById(Integer id, Integer year,
			Integer startM, Integer endM);
	
	public List<PcStats> getZwhStatsByParentCode(Integer id, Integer year,
			Integer startM, Integer endM);

}
