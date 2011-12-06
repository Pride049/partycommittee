package com.partycommittee.persistence.dao;

import java.util.List;

import com.partycommittee.persistence.po.PcUser;
import com.partycommittee.remote.vo.helper.PageHelper;

public interface PcUserDao {
	/**
	 * User Login.
	 * @param username
	 * @param password
	 * @return PCUser
	 */
	public PcUser login(String username, String password);
	
	/**
	 * Get user list.
	 * @return List<PCUser>
	 */
	public List<PcUser> getUserList();
	
	/**
	 * Get user list by conditions and page info.
	 * @param user
	 * @param pageHelper
	 * @return List<PCUser>
	 */
	public List<PcUser> getUserListByConditions(PcUser user, PageHelper pageHelper);
	
	/**
	 * Create new user.
	 * @param user
	 */
	public void createUser(PcUser user);
	
	/**
	 * Update user.
	 * @param user
	 */
	public void updateUser(PcUser user);
	
	/**
	 * Delete user.
	 * @param user
	 */
	public void deleteUser(PcUser user);
}
