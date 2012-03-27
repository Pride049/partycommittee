package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcAgencyDaoImpl;
import com.partycommittee.persistence.daoimpl.PcAgencyMappingDaoImpl;
import com.partycommittee.persistence.daoimpl.PcAgencyRelationDaoImpl;
import com.partycommittee.persistence.daoimpl.PcMemberDaoImpl;
import com.partycommittee.persistence.po.PcAgency;
import com.partycommittee.persistence.po.PcAgencyRelation;
import com.partycommittee.persistence.po.PcMember;
import com.partycommittee.remote.vo.PcAgencyInfoVo;
import com.partycommittee.remote.vo.PcAgencyVo;
import com.partycommittee.remote.vo.PcMemberVo;

@Transactional
@Service("PcAgencyService")
public class PcAgencyService {

	@Resource(name="PcAgencyDaoImpl")
	private PcAgencyDaoImpl pcAgencyDaoImpl;
	public void setPcAgencyDaoImpl(PcAgencyDaoImpl pcAgencyDaoImpl) {
		this.pcAgencyDaoImpl = pcAgencyDaoImpl;
	}
	
	@Resource(name="PcAgencyRelationDaoImpl")
	private PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl;
	public void setPcAgencyRelationDaoImpl(PcAgencyRelationDaoImpl pcAgencyRelationDaoImpl) {
		this.pcAgencyRelationDaoImpl = pcAgencyRelationDaoImpl;
	}
	
	@Resource(name="PcAgencyMappingDaoImpl")
	private PcAgencyMappingDaoImpl pcAgencyMappingDaoImpl;
	public void setPcAgencyMappingDaoImpl(PcAgencyMappingDaoImpl pcAgencyMappingDaoImpl) {
		this.pcAgencyMappingDaoImpl = pcAgencyMappingDaoImpl;
	}
	
	@Resource(name="PcMemberDaoImpl")
	private PcMemberDaoImpl pcMemberDaoImpl;
	public void setPcMemberDaoImpl(PcMemberDaoImpl pcMemberDaoImpl) {
		this.pcMemberDaoImpl = pcMemberDaoImpl;
	}
	
	public List<PcAgencyVo> getAgencyList() {
		List<PcAgencyVo> list = new ArrayList<PcAgencyVo>();
		List<PcAgency> agencyList = pcAgencyDaoImpl.getAgencyList();
		if (agencyList != null && agencyList.size() > 0) {
			for (PcAgency agency : agencyList) {
				list.add(PcAgencyVo.fromPcAgency(agency));
			}
		}
		return list;
	}
	
	public List<PcAgencyVo> getChildrenAgencyListByCode(int agencyId) {
		List<PcAgencyVo> list = new ArrayList<PcAgencyVo>();
		PcAgency agency =  pcAgencyDaoImpl.getAgencyById(agencyId);
		List<PcAgency> agencyList = pcAgencyDaoImpl.getChildrenAgencyByCode(agency.getCode());
		for (PcAgency item : agencyList) {
			PcAgencyVo vo = PcAgencyVo.fromPcAgency(item);
			vo.setZbsj(" ");
			List<PcMember> list_mem = pcMemberDaoImpl.getMemberByDutyId(item.getId(), 1);
			if (list_mem != null && list_mem.size() > 0) {
				String zbsj = "";
				for(PcMember m: list_mem) {
					zbsj+= m.getName() + ' ';
				}
				vo.setZbsj(zbsj);
			}
			
			
			list.add(vo);
		}
		return list;
	}	
	
	public List<PcAgencyVo> getChildrenAgencyListByCodeOnlyParent(int agencyId) {
		List<PcAgencyVo> list = new ArrayList<PcAgencyVo>();
		PcAgency agency =  pcAgencyDaoImpl.getAgencyById(agencyId);
		List<PcAgency> agencyList = pcAgencyDaoImpl.getChildrenAgencyByCodeOnly(agency.getCode());
		for (PcAgency item : agencyList) {
			PcAgencyVo vo = PcAgencyVo.fromPcAgency(item);
			list.add(vo);
		}
		return list;
	}		
	
	
	
	public PcAgencyVo getAgencyById(int agencyId) {
		PcAgency agency = pcAgencyDaoImpl.getAgencyById(agencyId);
		if (agency == null) {
			return null;
		}
		PcAgencyVo agencyVo = PcAgencyVo.fromPcAgency(agency);
		if (agency.getMemberId() != null) {
			PcMember member = pcMemberDaoImpl.getMemberById(agency.getMemberId());
			if (member != null) {
				agencyVo.setMember(PcMemberVo.fromPcMember(member));
			}
		}
		return agencyVo;
	}
	
	public void createAgency(PcAgencyVo agencyVo) {
		
		// Create agency.
		
		// get MaxCode From parent_id;
		String maxcode = pcAgencyDaoImpl.getMaxCodeByParentId(agencyVo.getParentId());
		agencyVo.setCode(maxcode);
		PcAgency agency = PcAgencyVo.toPcAgency(agencyVo);
		pcAgencyDaoImpl.createAgency(agency);
		// Create agency relation.
		PcAgencyRelation agencyRelation = new PcAgencyRelation();
		agencyRelation.setAgencyId(agency.getId());
		agencyRelation.setParentId(agencyVo.getParentId());
		pcAgencyRelationDaoImpl.createAgencyRelation(agencyRelation);
	}
	
	public void updateAgency(PcAgencyVo agencyVo) {
		PcAgency agency = PcAgencyVo.toPcAgency(agencyVo);
		pcAgencyDaoImpl.updateAgency(agency);
	}
	
	public void deleteAgency(PcAgencyVo agencyVo) {
		PcAgency agency = PcAgencyVo.toPcAgency(agencyVo);
		// Delete agency mapping.
		pcAgencyMappingDaoImpl.deleteAgencyMappingByAgencyId(agency.getId());
		
		// Get related child agency.
		List<PcAgencyRelation> relations = pcAgencyRelationDaoImpl.getChildrenByParentId(agency.getId());
		if (relations == null || relations.size() == 0) {
			for (PcAgencyRelation relation : relations) {
				PcAgency deleteItem = new PcAgency();
				deleteItem.setId(relation.getAgencyId());
				pcAgencyDaoImpl.deleteAgency(deleteItem);
			}
		}
		
		// Delete agency relation.
		pcAgencyRelationDaoImpl.deleteAgencyRelationByAgencyId(agency.getId());
		
		// Delete agency.
		pcAgencyDaoImpl.deleteAgency(agency);
		
	}

	public PcAgencyInfoVo getAgencyInfo(Integer agencyId) {
		PcAgencyInfoVo agencyInfo = new PcAgencyInfoVo();
		// get team number.
		List<PcAgencyRelation> relationList = pcAgencyRelationDaoImpl.getChildrenByParentId(agencyId);
		if (relationList != null) {
			agencyInfo.setTeamNumber(relationList.size());
		} else {
			agencyInfo.setTeamNumber(0);
		}
		// get member number.
		List<PcMember> memberList = pcMemberDaoImpl.getMemberListByAgencyId(agencyId);
		if (memberList != null) {
			agencyInfo.setMemberNumber(memberList.size());
		} else {
			agencyInfo.setMemberNumber(0);
		}
		// get duty member list.
		List<PcMemberVo> list = new ArrayList<PcMemberVo>();
		List<PcMember> dutyList = pcMemberDaoImpl.getDutyMemberListByAgencyId(agencyId);
		if (dutyList != null && dutyList.size() > 0) {
			for (PcMember memberItem : dutyList) {
				list.add(PcMemberVo.fromPcMember(memberItem));
			}
		}
		agencyInfo.setDutyMemberList(list);
		return agencyInfo;
	}

	public PcAgencyVo getParentAgency(Integer agencyId) {
		PcAgencyRelation relation = pcAgencyRelationDaoImpl.getParentByAgencyId(agencyId);
		if (relation == null || relation.getParentId() == null) {
			return null;
		}
		PcAgency agency = pcAgencyDaoImpl.getAgencyById(relation.getParentId());
		if (agency == null) {
			return null;
		}
		return PcAgencyVo.fromPcAgency(agency);
	}
}
