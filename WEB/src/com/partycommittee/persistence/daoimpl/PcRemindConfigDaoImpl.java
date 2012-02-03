package com.partycommittee.persistence.daoimpl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcRemindConfigDao;
import com.partycommittee.persistence.po.PcRemindConfig;

@Repository("PcRemindConfigDaoImpl")
public class PcRemindConfigDaoImpl extends JpaDaoBase implements PcRemindConfigDao {

	@Override
	public void updateRemindConfig(PcRemindConfig vo) {
		try {
			super.merge(vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcRemindConfig> getRemindConfigLists() {
		try {
			return super.find("from PcRemindConfig");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
