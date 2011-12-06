package com.partycommittee.persistence.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcUserDao;
import com.partycommittee.persistence.po.PcUser;
import com.partycommittee.remote.vo.helper.PageHelper;

@Repository("PCUserDaoImpl")
public class PcUserDaoImpl extends JpaDaoBase implements PcUserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PcUser> getUserList() {
		try {
			return super.getJpaTemplate().find("from PcUser");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PcUser> getUserListByConditions(PcUser user,
			PageHelper pageHelper) {
		return null;
	}

	@Override
	public void createUser(PcUser user) {
		try {
			user.setId(null);
			super.persist(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void updateUser(PcUser user) {
		try {
			super.merge(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteUser(PcUser user) {
		try {
			super.removeFromKey(PcUser.class, user.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PcUser login(String username, String password) {
		try {
			List<PcUser> list = super.getJpaTemplate().find("from PcUser where username = '" 
					+ username + "' and password = '" + password + "'");
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
