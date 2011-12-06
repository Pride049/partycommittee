package com.partycommittee.persistence.daoimpl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcAgencyDao;
import com.partycommittee.persistence.po.PcAgency;

@Repository("PcAgencyDaoImpl")
public class PcAgencyDaoImpl extends JpaDaoBase implements PcAgencyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PcAgency> getAgencyList() {
		return super.getJpaTemplate().find("from PcAgency");
	}

	@SuppressWarnings("unchecked")
	@Override
	public PcAgency getAgencyById(int agencyId) {
		try {
			List<PcAgency> list = super.getJpaTemplate().find("from PcAgency where id = " + agencyId);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void createAgency(PcAgency agency) {
		try {
			agency.setId(null);
			agency.setMemberId(null);
			agency.setSetupDatetime(new Date());
			super.persist(agency);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void updateAgency(PcAgency agency) {
		try {
			if (agency.getMemberId() != null && agency.getMemberId() == 0) {
				agency.setMemberId(null);
			}
			super.merge(agency);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteAgency(PcAgency agency) {
		try {
			super.removeFromKey(PcAgency.class, agency.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
