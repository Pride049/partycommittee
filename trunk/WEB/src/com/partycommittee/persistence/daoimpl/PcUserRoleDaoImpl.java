package com.partycommittee.persistence.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcUserRoleDao;
import com.partycommittee.persistence.po.PcUserRole;

@Repository("PcUserRoleDaoImpl")
public class PcUserRoleDaoImpl extends JpaDaoBase implements PcUserRoleDao {


	@SuppressWarnings("unchecked")
	@Override
	public List<PcUserRole> getRolesByUserId(int userId) {
		try {
			return super.getJpaTemplate().find("from PcUserRole where user_id = " + userId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
