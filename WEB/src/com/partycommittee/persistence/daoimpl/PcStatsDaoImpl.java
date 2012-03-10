package com.partycommittee.persistence.daoimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcStatsDao;
import com.partycommittee.persistence.po.PcRemind;
import com.partycommittee.persistence.po.PcStats;
import com.partycommittee.remote.vo.FilterVo;

@Repository("PcStatsDaoImpl")
public class PcStatsDaoImpl extends JpaDaoBase implements PcStatsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getWorkPlanStatsById(Integer id, Integer year,
			List<Integer> qs, Integer typeId) {
			
		try {
			// 跨季度
			String ids = "";
			for (Integer idItem : qs) {
				if (ids.equals("")) {
					ids = idItem + "";
				} else {
					ids += "," + idItem;
				}
			}
	
			Integer avg = qs.size();
	
			String where = " WHERE 1= 1";
			where = where + " AND agencyId=" + id;
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id = " + typeId;
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT agency_id, name, YEAR, type_id");
			sb.append("SUM(total) as total, ");
			sb.append("SUM(total_success) as total_success, ");
			sb.append("SUM(total_return) as total_return, ");
			sb.append("SUM(total_delay) as total_delay, ");
			sb.append("SUM(  reported ) as reported, ");
			sb.append("ROUND( SUM(reported_rate)/"+avg+", 4) as reported_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) return_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) delay_rate, ");
			sb.append("SUM(  attend ) as attend, ");
			sb.append("SUM(  asence ) as asence, ");
			sb.append("ROUND(SUM(attend_rate)/"+avg+", 4) as attend_rate, ");
			sb.append("SUM(eva) as eva,,");
			sb.append("ROUND(SUM(eva_rate)/"+avg+", 4) as eva_rate, ");
			sb.append("SUM(eva_1) as eva_1, ");
			sb.append("SUM(eva_2) as eva_2, ");
			sb.append("SUM(eva_3) as eva_3, ");
			sb.append("SUM(eva_4) as eva_4, ");
			sb.append("ROUND(SUM(eva_1_rate)/"+avg+", 4) as eva_1_rate, ");
			sb.append("ROUND(SUM(eva_2_rate)/"+avg+", 4) as eva_2_rate, ");
			sb.append("ROUND(SUM(eva_3_rate)/"+avg+", 4) as eva_3_rate, ");
			sb.append("ROUND(SUM(eva_4_rate)/"+avg+", 4) as eva_4_rate, ");
			sb.append("SUM(agency_goodjob) as agency_goodjob ");
			sb.append("FROM  pc_zzsh_stat " + where);
			sb.append(" GROUP BY agency_id, name, YEAR, type_id");
			
			EntityManager em = super.getEntityManager();
			Query query=em.createQuery(sb.toString());
			List<PcStats> list =  query.getResultList();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getWorkPlanStatsByParentCode(Integer id, Integer year,
			List<Integer> qs, Integer typeId) {

		try {
			EntityManager em = super.getEntityManager();
			Query sql1 = em.createQuery("select code from PcAgency where id = " + id);
			List<String> rs = sql1.getResultList();
			String code = rs.get(0);			
			
			// 跨季度
			String ids = "";
			for (Integer idItem : qs) {
				if (ids.equals("")) {
					ids = idItem + "";
				} else {
					ids += "," + idItem;
				}
			}
	
			Integer avg = qs.size();
	
			String where = " WHERE 1= 1";
			where = where + " AND code like '"+code+"%'";
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id = " + typeId;
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT agency_id, name, YEAR, type_id");
			sb.append("SUM(total) as total, ");
			sb.append("SUM(total_success) as total_success, ");
			sb.append("SUM(total_return) as total_return, ");
			sb.append("SUM(total_delay) as total_delay, ");
			sb.append("SUM(  reported ) as reported, ");
			sb.append("ROUND( SUM(reported_rate)/"+avg+", 4) as reported_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) return_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) delay_rate, ");
			sb.append("SUM(  attend ) as attend, ");
			sb.append("SUM(  asence ) as asence, ");
			sb.append("ROUND(SUM(attend_rate)/"+avg+", 4) as attend_rate, ");
			sb.append("SUM(eva) as eva,,");
			sb.append("ROUND(SUM(eva_rate)/"+avg+", 4) as eva_rate, ");
			sb.append("SUM(eva_1) as eva_1, ");
			sb.append("SUM(eva_2) as eva_2, ");
			sb.append("SUM(eva_3) as eva_3, ");
			sb.append("SUM(eva_4) as eva_4, ");
			sb.append("ROUND(SUM(eva_1_rate)/"+avg+", 4) as eva_1_rate, ");
			sb.append("ROUND(SUM(eva_2_rate)/"+avg+", 4) as eva_2_rate, ");
			sb.append("ROUND(SUM(eva_3_rate)/"+avg+", 4) as eva_3_rate, ");
			sb.append("ROUND(SUM(eva_4_rate)/"+avg+", 4) as eva_4_rate, ");
			sb.append("SUM(agency_goodjob) as agency_goodjob ");
			sb.append("FROM  pc_zzsh_stat " + where);
			sb.append(" GROUP BY agency_id, name, YEAR, type_id");
			
			
			Query query=em.createQuery(sb.toString());
			List<PcStats> list =  query.getResultList();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return null;		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getMeetingStatsById(Integer id, Integer year,
			List<Integer> qs, Integer typeId) {

		try {
			// 跨季度
			String ids = "";
			for (Integer idItem : qs) {
				if (ids.equals("")) {
					ids = idItem + "";
				} else {
					ids += "," + idItem;
				}
			}
	
			Integer avg = qs.size();
	
			String where = " WHERE 1= 1";
			where = where + " AND agencyId=" + id;
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id =" + typeId;
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT agency_id, name, YEAR, type_id, ");
			sb.append("SUM(total) as total, ");
			sb.append("SUM(total_success) as total_success, ");
			sb.append("SUM(total_return) as total_return, ");
			sb.append("SUM(total_delay) as total_delay, ");
			sb.append("SUM(  reported ) as reported, ");
			sb.append("ROUND( SUM(reported_rate)/"+avg+", 4) as reported_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) return_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) delay_rate, ");
			sb.append("SUM(  attend ) as attend, ");
			sb.append("SUM(  asence ) as asence, ");
			sb.append("ROUND(SUM(attend_rate)/"+avg+", 4) as attend_rate, ");
			sb.append("SUM(eva) as eva,,");
			sb.append("ROUND(SUM(eva_rate)/"+avg+", 4) as eva_rate, ");
			sb.append("SUM(eva_1) as eva_1, ");
			sb.append("SUM(eva_2) as eva_2, ");
			sb.append("SUM(eva_3) as eva_3, ");
			sb.append("SUM(eva_4) as eva_4, ");
			sb.append("ROUND(SUM(eva_1_rate)/"+avg+", 4) as eva_1_rate, ");
			sb.append("ROUND(SUM(eva_2_rate)/"+avg+", 4) as eva_2_rate, ");
			sb.append("ROUND(SUM(eva_3_rate)/"+avg+", 4) as eva_3_rate, ");
			sb.append("ROUND(SUM(eva_4_rate)/"+avg+", 4) as eva_4_rate, ");
			sb.append("SUM(agency_goodjob) as agency_goodjob ");
			sb.append("FROM  pc_zzsh_stat " + where);
			sb.append(" GROUP BY agency_id, name, YEAR, type_id");
			
			EntityManager em = super.getEntityManager();
			Query query=em.createQuery(sb.toString());
			List<PcStats> list =  query.getResultList();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getMeetingStatsByParentCode(Integer id, Integer year,
			List<Integer> qs, Integer typeId) {

		try {
			EntityManager em = super.getEntityManager();
			Query sql1 = em.createQuery("select code from PcAgency where id = " + id);
			List<String> rs = sql1.getResultList();
			String code = rs.get(0);			
			
			// 跨季度
			String ids = "";
			for (Integer idItem : qs) {
				if (ids.equals("")) {
					ids = idItem + "";
				} else {
					ids += "," + idItem;
				}
			}
	
			Integer avg = qs.size();
	
			String where = " WHERE 1= 1";
			where = where + " AND code like '"+code+"%'";
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id =" + typeId;
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT agency_id, name, YEAR, type_id, ");
			sb.append("SUM(total) as total, ");
			sb.append("SUM(total_success) as total_success, ");
			sb.append("SUM(total_return) as total_return, ");
			sb.append("SUM(total_delay) as total_delay, ");
			sb.append("SUM(  reported ) as reported, ");
			sb.append("ROUND( SUM(reported_rate)/"+avg+", 4) as reported_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) return_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) delay_rate, ");
			sb.append("SUM(  attend ) as attend, ");
			sb.append("SUM(  asence ) as asence, ");
			sb.append("ROUND(SUM(attend_rate)/"+avg+", 4) as attend_rate, ");
			sb.append("SUM(eva) as eva,,");
			sb.append("ROUND(SUM(eva_rate)/"+avg+", 4) as eva_rate, ");
			sb.append("SUM(eva_1) as eva_1, ");
			sb.append("SUM(eva_2) as eva_2, ");
			sb.append("SUM(eva_3) as eva_3, ");
			sb.append("SUM(eva_4) as eva_4, ");
			sb.append("ROUND(SUM(eva_1_rate)/"+avg+", 4) as eva_1_rate, ");
			sb.append("ROUND(SUM(eva_2_rate)/"+avg+", 4) as eva_2_rate, ");
			sb.append("ROUND(SUM(eva_3_rate)/"+avg+", 4) as eva_3_rate, ");
			sb.append("ROUND(SUM(eva_4_rate)/"+avg+", 4) as eva_4_rate, ");
			sb.append("SUM(agency_goodjob) as agency_goodjob ");
			sb.append("FROM  pc_zzsh_stat " + where);
			sb.append(" GROUP BY agency_id, name, YEAR, type_id");
			
			
			Query query=em.createQuery(sb.toString());
			List<PcStats> list =  query.getResultList();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return null;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getZwhStatsById(Integer id, Integer year,
			Integer startM, Integer endM) {

		try {

			Integer avg = endM - startM + 1;
	
			String where = " WHERE 1= 1";
			where = where + " AND agencyId=" + id;
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = 8";
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT agency_id, name, YEAR, type_id, ");
			sb.append("SUM(total) as total, ");
			sb.append("SUM(total_success) as total_success, ");
			sb.append("SUM(total_return) as total_return, ");
			sb.append("SUM(total_delay) as total_delay, ");
			sb.append("SUM(  reported ) as reported, ");
			sb.append("ROUND( SUM(reported_rate)/"+avg+", 4) as reported_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) return_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) delay_rate, ");
			sb.append("SUM(  attend ) as attend, ");
			sb.append("SUM(  asence ) as asence, ");
			sb.append("ROUND(SUM(attend_rate)/"+avg+", 4) as attend_rate, ");
			sb.append("SUM(eva) as eva,,");
			sb.append("ROUND(SUM(eva_rate)/"+avg+", 4) as eva_rate, ");
			sb.append("SUM(eva_1) as eva_1, ");
			sb.append("SUM(eva_2) as eva_2, ");
			sb.append("SUM(eva_3) as eva_3, ");
			sb.append("SUM(eva_4) as eva_4, ");
			sb.append("ROUND(SUM(eva_1_rate)/"+avg+", 4) as eva_1_rate, ");
			sb.append("ROUND(SUM(eva_2_rate)/"+avg+", 4) as eva_2_rate, ");
			sb.append("ROUND(SUM(eva_3_rate)/"+avg+", 4) as eva_3_rate, ");
			sb.append("ROUND(SUM(eva_4_rate)/"+avg+", 4) as eva_4_rate, ");
			sb.append("SUM(agency_goodjob) as agency_goodjob ");
			sb.append("FROM  pc_zzsh_stat " + where);
			sb.append(" GROUP BY agency_id, name, YEAR, type_id");
			
			EntityManager em = super.getEntityManager();
			Query query=em.createQuery(sb.toString());
			List<PcStats> list =  query.getResultList();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getZwhStatsByParentCode(Integer id, Integer year,
			Integer startM, Integer endM) {

		try {
			EntityManager em = super.getEntityManager();
			Query sql1 = em.createQuery("select code from PcAgency where id = " + id);
			List<String> rs = sql1.getResultList();
			String code = rs.get(0);			
			
			Integer avg = endM - startM + 1;
	
			String where = " WHERE 1= 1";
			where = where + " AND code like '"+code+"%'";
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = 8";
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT agency_id, name, YEAR, type_id, ");
			sb.append("SUM(total) as total, ");
			sb.append("SUM(total_success) as total_success, ");
			sb.append("SUM(total_return) as total_return, ");
			sb.append("SUM(total_delay) as total_delay, ");
			sb.append("SUM(  reported ) as reported, ");
			sb.append("ROUND( SUM(reported_rate)/"+avg+", 4) as reported_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) return_rate, ");
			sb.append("ROUND( SUM(return_rate)"+avg+", 4) delay_rate, ");
			sb.append("SUM(  attend ) as attend, ");
			sb.append("SUM(  asence ) as asence, ");
			sb.append("ROUND(SUM(attend_rate)/"+avg+", 4) as attend_rate, ");
			sb.append("SUM(eva) as eva,,");
			sb.append("ROUND(SUM(eva_rate)/"+avg+", 4) as eva_rate, ");
			sb.append("SUM(eva_1) as eva_1, ");
			sb.append("SUM(eva_2) as eva_2, ");
			sb.append("SUM(eva_3) as eva_3, ");
			sb.append("SUM(eva_4) as eva_4, ");
			sb.append("ROUND(SUM(eva_1_rate)/"+avg+", 4) as eva_1_rate, ");
			sb.append("ROUND(SUM(eva_2_rate)/"+avg+", 4) as eva_2_rate, ");
			sb.append("ROUND(SUM(eva_3_rate)/"+avg+", 4) as eva_3_rate, ");
			sb.append("ROUND(SUM(eva_4_rate)/"+avg+", 4) as eva_4_rate, ");
			sb.append("SUM(agency_goodjob) as agency_goodjob ");
			sb.append("FROM  pc_zzsh_stat " + where);
			sb.append(" GROUP BY agency_id, name, YEAR, type_id");
			
			
			Query query=em.createQuery(sb.toString());
			List<PcStats> list =  query.getResultList();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return null;	
	}
	
	

}
