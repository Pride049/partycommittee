package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcAgencyMappingDaoImpl;
import com.partycommittee.persistence.daoimpl.PcUserDaoImpl;
import com.partycommittee.persistence.po.PcAgencyMapping;
import com.partycommittee.persistence.po.PcUser;
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
				list.add(PcUserVo.fromPCUser(user));
			}
		}
		result.setList(list);
		return result;
	}
	
	public void createUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		user = pcUserDaoImpl.createUser(user);
		updateUserAgencyMapping(user.getId(), userVo.getPrivilege());
	}
	
	public void updateUser(PcUserVo userVo) {
		PcUser user = PcUserVo.toPCUser(userVo);
		pcUserDaoImpl.updateUser(user);
		updateUserAgencyMapping(user.getId(), userVo.getPrivilege());
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
