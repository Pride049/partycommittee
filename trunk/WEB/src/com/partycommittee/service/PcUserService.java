package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcAgencyDaoImpl;
import com.partycommittee.persistence.daoimpl.PcAgencyMappingDaoImpl;
import com.partycommittee.persistence.daoimpl.PcUserDaoImpl;
import com.partycommittee.persistence.po.PcAgency;
import com.partycommittee.persistence.po.PcAgencyMapping;
import com.partycommittee.persistence.po.PcUser;
import com.partycommittee.remote.vo.PcAgencyVo;
import com.partycommittee.remote.vo.PcUserVo;
import com.partycommittee.remote.vo.helper.PageHelperVo;
import com.partycommittee.remote.vo.helper.PageResultVo;

@Transactional
@Service("PCUserService")
public class PcUserService {
	
	@Resource(name="PCUserDaoImpl")
	private PcUserDaoImpl pcUserDaoImpl;
	public void setPCUserDaoImpl(PcUserDaoImpl pcUserDaoImpl) {
		this.pcUserDaoImpl = pcUserDaoImpl;
	}
	
	@Resource(name="PcAgencyMappingDaoImpl")
	private PcAgencyMappingDaoImpl pcAgencyMappingDaoImpl;
	public void setPcAgencyMappingDaoImpl(PcAgencyMappingDaoImpl pcAgencyMappingDaoImpl) {
		this.pcAgencyMappingDaoImpl = pcAgencyMappingDaoImpl;
	}
	
	@Resource(name="PcAgencyDaoImpl")
	private PcAgencyDaoImpl pcAgencyDaoImpl;
	public void setPcAgencyDaoImpl(PcAgencyDaoImpl pcAgencyDaoImpl) {
		this.pcAgencyDaoImpl = pcAgencyDaoImpl;
	}
	
	public PcUserVo login(String username, String password) {
		PcUser user = pcUserDaoImpl.login(username, password);
		if (user == null || user.getStatus() != 1) {
			return null;
		}
		PcUserVo userVo = PcUserVo.fromPCUser(user);
		return userVo;
	}
	
	public List<PcUserVo> getUserList() {
		List<PcUserVo> list = new ArrayList<PcUserVo>();
		List<PcUser> userList = pcUserDaoImpl.getUserList();
		if (userList != null && userList.size() > 0) {
			for (PcUser user : userList) {
				list.add(PcUserVo.fromPCUser(user));
			}
		}
		return list;
	}
	
	public PageResultVo<PcUserVo> getUserListByPage(PageHelperVo page) {
		PageResultVo<PcUserVo> result = new PageResultVo<PcUserVo>();
		List<PcUserVo> list = new ArrayList<PcUserVo>();
		PageResultVo<PcUser> pageResult = pcUserDaoImpl.getUserListByPage(page);
		if (pageResult == null) {
			return null;
		}
		result.setPageHelper(pageResult.getPageHelper());
		if (pageResult.getList() != null && pageResult.getList().size() > 0) {
			for (PcUser user : pageResult.getList()) {
				PcUserVo userVo = PcUserVo.fromPCUser(user);
				getAgencyList(userVo);
				list.add(userVo);
			}
		}
		result.setList(list);
		return result;
	}
	
	public void getAgencyList(PcUserVo userVo) {
		String privilege = userVo.getPrivilege();
		if (privilege != null && !privilege.equals("")) {
			List<PcAgencyVo> agencyList = new ArrayList<PcAgencyVo>();
			List<PcAgency> list = pcAgencyDaoImpl.getAgencyListByIds(privilege);
			if (list != null && list.size() > 0) {
				for (PcAgency agencyItem : list) {
					agencyList.add(PcAgencyVo.fromPcAgency(agencyItem));
				}
			}
			userVo.setAgencyList(agencyList);
		}
//		List<PcAgencyVo> list = new ArrayList<PcAgencyVo>();
//		for (String privilegeItem : privilegeList) {
//			int agencyId = Integer.parseInt(privilegeItem);
//			PcAgency agencyItem = pcAgencyDaoImpl.getAgencyById(agencyId);
//			if (agencyItem != null) {
//				list.add(PcAgencyVo.fromPcAgency(agencyItem));
//			}
//		}
//		userVo.setAgencyList(list);
	}
	
	public void createUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		updateAgencyCodeId(user);
		user = pcUserDaoImpl.createUser(user);
		updateUserAgencyMapping(user.getId(), userVo.getPrivilege());
	}
	
	public void updateUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		updateAgencyCodeId(user);
		pcUserDaoImpl.updateUser(user);
		updateUserAgencyMapping(user.getId(), userVo.getPrivilege());
	}
	
	private void updateAgencyCodeId(PcUser user) {
		if (user.getPrivilege() != null && !user.getPrivilege().equals("")) {
			List<PcAgency> agencyList = pcAgencyDaoImpl.getAgencyListByIds(user.getPrivilege());
			if (agencyList == null || agencyList.size() == 0) {
				user.setAgencyCodeId(null);
			} else {
				Integer rootCodeId = null;
				for (PcAgency agency : agencyList) {
					if (rootCodeId == null) {
						rootCodeId = agency.getCodeId();
					} else {
						if (rootCodeId > agency.getCodeId()) {
							rootCodeId = agency.getCodeId();
						}
					}
				}
				user.setAgencyCodeId(rootCodeId);
			}
		} else {
			user.setAgencyCodeId(null);
		}
	}
	
	private void updateUserAgencyMapping(Long userId, String privilege) {
		if (privilege == null || privilege.equals("")) {
			return;
		}
		Integer rootAgencyId = getRootAgencyId(privilege.split(","));
		if (rootAgencyId != null) {
			List<PcAgencyMapping> mappingList = pcAgencyMappingDaoImpl.getAgencyMappingByUserId(userId.intValue());
			if (mappingList == null || mappingList.size() == 0) {
				PcAgencyMapping newMapping = new PcAgencyMapping();
				newMapping.setAgencyId(rootAgencyId);
				newMapping.setUserId(userId.intValue());
				pcAgencyMappingDaoImpl.createAgencyMapping(newMapping);
				return;
			}
			pcAgencyMappingDaoImpl.updateAgencyMappingByUser(userId, rootAgencyId);
		}
	}
	
	public void deleteUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		pcUserDaoImpl.deleteUser(user);
	}
	
	private Integer getRootAgencyId(String[] strings) {
		Integer temp = null;
		for (String str : strings) {
			Long id = Long.parseLong(str);
			if (id == null) {
				continue;
			}
			if (temp == null) {
				temp = id.intValue();
			} else {
				if (id.intValue() < temp) {
					temp = id.intValue();
				}
			}
		}
		return temp;
	}
}