package com.partycommittee.persistence.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcMemberDao;
import com.partycommittee.persistence.po.PcMember;

@Repository("PcMemberDaoImpl")
public class PcMemberDaoImpl extends JpaDaoBase implements PcMemberDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PcMember> getMemberList() {
		try {
			return super.getJpaTemplate().find("from PcMember");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcMember> getMemberListByAgencyId(int agencyId) {
		try {
			return super.getJpaTemplate().find("from PcMember where agencyId = " + agencyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void createPcMember(PcMember member) {
		try {
			member.setId(null);
			super.persist(member);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void updatePcMember(PcMember member) {
		try {
			super.merge(member);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deletePcMember(PcMember member) {
		try {
			super.removeFromKey(PcMember.class, member.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PcMember getMemberById(int id) {
		try {
			List<PcMember> list = super.getJpaTemplate().find("from PcMember where id = " + id);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
