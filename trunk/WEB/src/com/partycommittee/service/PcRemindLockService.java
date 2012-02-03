package com.partycommittee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partycommittee.persistence.daoimpl.PcRemindLockDaoImpl;
import com.partycommittee.persistence.po.PcRemindLock;
import com.partycommittee.remote.vo.PcRemindLockVo;


@Transactional
@Service("PcRemindLockService")
public class PcRemindLockService {

	@Resource(name="PcRemindLockDaoImpl")
	private PcRemindLockDaoImpl pcRemindLockDaoImpl;
	public void setPcRemindLockDaoImpl(PcRemindLockDaoImpl pcRemindLockDaoImpl) {
		this.pcRemindLockDaoImpl = pcRemindLockDaoImpl;
	}

	public List<PcRemindLockVo> getRemindLockByFilters(List<Object> filters) {
		List<PcRemindLockVo> list = new ArrayList<PcRemindLockVo>();

		return list;
	}
	
	public void updateRemindLock(PcRemindLockVo pevo) {
		PcRemindLock vo = PcRemindLockVo.toPcRemindLock(pevo);
		pcRemindLockDaoImpl.updateRemindLock(vo);
	}
	

}
