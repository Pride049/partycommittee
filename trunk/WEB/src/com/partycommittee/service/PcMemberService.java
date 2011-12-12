package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcMemberDaoImpl;
import com.partycommittee.persistence.po.PcMember;
import com.partycommittee.remote.vo.PcMemberVo;
import com.partycommittee.remote.vo.helper.PageHelperVo;
import com.partycommittee.remote.vo.helper.PageResultVo;

@Transactional
@Service("PcMemberService")
public class PcMemberService {

	@Resource(name="PcMemberDaoImpl")
	private PcMemberDaoImpl pcMemberDaoImpl;
	public void setPcMemberDaoImpl(PcMemberDaoImpl pcMemberDaoImpl) {
		this.pcMemberDaoImpl = pcMemberDaoImpl;
	}
	
	public List<PcMemberVo> getMemberListByAgencyId(int agencyId) {
		List<PcMemberVo> list = new ArrayList<PcMemberVo>();
		List<PcMember> memberList = pcMemberDaoImpl.getMemberListByAgencyId(agencyId);
		if (memberList != null && memberList.size() > 0) {
			for (PcMember member : memberList) {
				list.add(PcMemberVo.fromPcMember(member));
			}
		}
		return list;
	}
	
	public PageResultVo<PcMemberVo> getMemberListPageByAgencyId(int agencyId, PageHelperVo page) {
		PageResultVo<PcMemberVo> result = new PageResultVo<PcMemberVo>();
		List<PcMemberVo> list = new ArrayList<PcMemberVo>();
		PageResultVo<PcMember> pageResult = pcMemberDaoImpl.getMemberListPageByAgencyId(agencyId, page);
		if (pageResult == null) {
			return null;
		}
		result.setPageHelper(pageResult.getPageHelper());
		if (pageResult.getList() != null && pageResult.getList().size() > 0) {
			for (PcMember member : pageResult.getList()) {
				list.add(PcMemberVo.fromPcMember(member));
			}
		}
		result.setList(list);
		return result;
	}
	
	public void createMember(PcMemberVo memberVo) {
		PcMember member = PcMemberVo.toPcMember(memberVo);
		pcMemberDaoImpl.createPcMember(member);
	}
	
	public void deleteMember(PcMemberVo memberVo) {
		PcMember member = PcMemberVo.toPcMember(memberVo);
		pcMemberDaoImpl.deletePcMember(member);
	}
	
	public void updateMember(PcMemberVo memberVo) {
		PcMember member = PcMemberVo.toPcMember(memberVo);
		pcMemberDaoImpl.updatePcMember(member);
	}
}
