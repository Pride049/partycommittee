package com.partycommittee.persistence.daoimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcAgencyStatsDao;
import com.partycommittee.persistence.po.PcAgencyStats;;

@Repository("PcAgencyStatsDaoImpl")
public class PcAgencyStatsDaoImpl extends JpaDaoBase implements PcAgencyStatsDao {
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PcAgencyStats> getAgencyStatsByParentId(Integer id) {
		try {
			if (id == null) {
				return null;
			}
			EntityManager em = super.getEntityManager();
			

			// 获取直属党支部
			String query_sql;
			if (id == 1) {
				query_sql = "from  PcAgencyStats where parent_id = 1 Order by agency_id ASC ";
			} else {
				Query sql = em.createQuery("select code from PcAgency where id = " + id);
				List<String> rs = sql.getResultList();
				String code = rs.get(0);	
				query_sql = "from PcAgencyStats where code like '"+code+"%' Order by agency_id ASC ";
			}
			
			Query query=em.createQuery(query_sql);
			List<PcAgencyStats> list =  query.getResultList();
	
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	

}
