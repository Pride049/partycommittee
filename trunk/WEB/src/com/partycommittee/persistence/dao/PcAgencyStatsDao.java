package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcAgencyStats;

public interface PcAgencyStatsDao {

	public List<PcAgencyStats> getAgencyStatsByParentId(Integer id);
}
