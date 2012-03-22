package com.partycommittee.persistence.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.stereotype.Repository;

import com.partycommittee.persistence.dao.PcStatsDao;
import com.partycommittee.persistence.po.PcRemind;
import com.partycommittee.persistence.po.PcStats;
import com.partycommittee.remote.vo.FilterVo;
import com.partycommittee.remote.vo.PcStatsVo;

@Repository("PcStatsDaoImpl")
public class PcStatsDaoImpl extends JpaDaoBase implements PcStatsDao {

	
	
	public List<PcStatsVo> getStatsByIdForAdmin(Integer id, Integer year,
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
			where = where + " AND code_id= 10";
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id = " + typeId;
						
			String sql = createSql(id, where, avg);
			System.out.print(sql);
			List<PcStatsVo> list = getResult(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public List<PcStatsVo> getStatsById(Integer id, Integer year,
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
			EntityManager em = super.getEntityManager();
			Query sql1 = em.createQuery("select code from PcAgency where id = " + id);
			List<String> rs = sql1.getResultList();
			String code = rs.get(0);
			where = where + " AND code like '" + code + "%'";
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id = " + typeId;
			String sql = createSql(id, where, avg);
			System.out.print(sql);
			List<PcStatsVo> list = getResult(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	
	public List<PcStatsVo> getZwhStatsById(Integer id, Integer year, Integer typeId,
			Integer startM, Integer endM) {
		try {

			Integer avg = endM - startM + 1;

			String where = " WHERE 1= 1";

			EntityManager em = super.getEntityManager();
			Query sql1 = em.createQuery("select code from PcAgency where id = " + id);
			List<String> rs = sql1.getResultList();
			String code = rs.get(0);
			where = where + " AND code like '" + code + "%'";			
			
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = " + typeId;
			String sql = createSql(id, where, avg);
			System.out.print(sql);
			List<PcStatsVo> list = getResult(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	
	public List<PcStatsVo> getZwhStatsByIdForAdmin(Integer id, Integer year, Integer typeId,
			Integer startM, Integer endM) {
		try {

			Integer avg = endM - startM + 1;

			String where = " WHERE 1= 1";
			where = where + " AND code_id= 10";
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = " + typeId;
			String sql = createSql(id, where, avg);
			System.out.print(sql);
			List<PcStatsVo> list = getResult(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}			
	
	
	
	public String createSql(Integer id, String where, Integer avg) {
		String sql = "SELECT * FROM"
			+ " (SELECT T1.agency_id, T2.name, T2.code_id, T2.code,  0 as parent_id, T1.year, T1.type_id, T1.total, T1.total_success, T1.total_return, T1.total_delay, T1.reported,"
			+ " (CASE WHEN T1.reported_rate is NULL THEN 0 ELSE T1.reported_rate END) as reported_rate, "
			+ " (CASE WHEN T1.return_rate is NULL THEN 0 ELSE T1.return_rate END) as return_rate, "
			+ " (CASE WHEN T1.delay_rate is NULL THEN 0 ELSE T1.delay_rate END) as delay_rate,   "
			+ " T1.attend, T1.asence, "
			+ " (CASE WHEN T1.attend_rate is NULL THEN 0 ELSE T1.attend_rate END) as attend_rate," 
			+ " T1.eva, "
			+ " (CASE WHEN T1.eva_rate is NULL THEN 0 ELSE T1.eva_rate END) as eva_rate," 
			+ " T1.eva_1, T1.eva_2, T1.eva_3, T1.eva_4,"
			+ " (CASE WHEN T1.eva_1_rate is NULL THEN 0 ELSE T1.eva_1_rate END) as eva_1_rate," 
			+ " (CASE WHEN T1.eva_2_rate is NULL THEN 0 ELSE T1.eva_2_rate END) as eva_2_rate, "
			+ " (CASE WHEN T1.eva_3_rate is NULL THEN 0 ELSE T1.eva_3_rate END) as eva_3_rate, "
			+ " (CASE WHEN T1.eva_4_rate is NULL THEN 0 ELSE T1.eva_4_rate END) as eva_4_rate,  " 
			+ " T1.agency_goodjob FROM "
			+ " (SELECT "+ id +" as agency_id, YEAR, type_id," 
			+ " SUM(total) as total, "
			+ " SUM(total_success) as total_success," 
			+ " SUM(total_return) as total_return," 
			+ " SUM(total_delay) as total_delay," 
			+ " SUM(  reported ) as reported ," 
			+ " ROUND( COUNT(CASE WHEN reported_rate = 1 THEN agency_id END)/COUNT(*), 4) reported_rate,"
			+ " ROUND( COUNT(CASE WHEN return_rate = 1 THEN agency_id END)/COUNT(*), 4) return_rate,"
			+ " ROUND( COUNT(CASE WHEN delay_rate = 1 THEN agency_id END)/COUNT(*), 4) delay_rate,"
			+ " SUM(  attend ) as attend , "
			+ " SUM(  asence ) as asence , "
			+ " ROUND(SUM(attend_rate)/COUNT(CASE WHEN total > 0 THEN total END), 4) as attend_rate,"
			+ " SUM(eva) as eva,"
			+ " ROUND(SUM(eva)/SUM(total), 4) as eva_rate ," 
			+ " SUM(eva_1) as eva_1,"
			+ " SUM(eva_2) as eva_2,"
			+ " SUM(eva_3) as eva_3,"
			+ " SUM(eva_4) as eva_4,"
			+ " ROUND(SUM(eva_1)/SUM(eva), 4) as eva_1_rate," 
			+ " ROUND(SUM(eva_2)/SUM(eva), 4) as eva_2_rate, "
			+ " ROUND(SUM(eva_3)/SUM(eva), 4) as eva_3_rate, "
			+ " ROUND(SUM(eva_4)/SUM(eva), 4) as eva_4_rate, "
			+ " COUNT(CASE WHEN reported_rate = 1 THEN agency_id END) as agency_goodjob"
			+ " FROM  pc_zzsh_stat " + where
			+ " GROUP BY YEAR, type_id )  as T1"
			+ " LEFT JOIN pc_agency as T2 ON T1.agency_id = T2.id ) as T3";		
		
		
		return sql;
	}
	
	public List<PcStatsVo> getResult(String sql) throws SQLException {
		java.sql.Connection conn = null;
		try {

			 List<PcStatsVo> list = new ArrayList<PcStatsVo>();
			 EntityManager em = super.getEntityManager();
			 em.getTransaction().begin();

			 HibernateEntityManager hem = (HibernateEntityManager)em;
			 conn = hem.getSession().connection();
			 
			 java.sql.Statement statement = conn.createStatement();
			    ResultSet rows = statement.executeQuery(sql);  
			    while (rows.next()) {
//			    	System.out.println(rows.getString("name"));
			    	PcStatsVo pevo = new PcStatsVo();
			    	pevo.setAgencyId(rows.getInt("agency_id"));
			    	pevo.setName(rows.getString("name"));
			    	pevo.setYear(rows.getInt("YEAR"));
			    	pevo.setTypeId(rows.getInt("type_id"));
			    	pevo.setTotal(rows.getInt("total"));
			    	pevo.setTotalSuccess(rows.getInt("total_success"));
			    	pevo.setTotalReturn(rows.getInt("total_return"));
			    	pevo.setTotalDelay(rows.getInt("total_delay"));
			    	pevo.setReported(rows.getInt("reported"));
			    	pevo.setReportedRate(rows.getDouble("reported_rate"));
			    	pevo.setReturnRate(rows.getDouble("return_rate"));
			    	pevo.setDelayRate(rows.getDouble("delay_rate"));
			    	pevo.setAttend(rows.getInt("attend"));
			    	pevo.setAsence(rows.getInt("asence"));
			    	pevo.setAttendRate(rows.getDouble("attend_rate"));
			    	pevo.setEva(rows.getInt("eva"));
			    	pevo.setEvaRate(rows.getDouble("eva_rate"));
			    	pevo.setEva1(rows.getInt("eva_1"));
			    	pevo.setEva2(rows.getInt("eva_2"));
			    	pevo.setEva3(rows.getInt("eva_3"));
			    	pevo.setEva4(rows.getInt("eva_4"));
			    	pevo.setEva1Rate(rows.getDouble("eva_1_rate"));
			    	pevo.setEva2Rate(rows.getDouble("eva_2_rate"));
			    	pevo.setEva3Rate(rows.getDouble("eva_3_rate"));
			    	pevo.setEva4Rate(rows.getDouble("eva_4_rate"));
			    	pevo.setAgencyGoodjob(rows.getInt("agency_goodjob"));
			    	
			    	list.add(pevo);
			    }
			    em.getTransaction().commit();
//			 statement.close();
//			 conn.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		

	public List<PcStatsVo> getStatsByParentId(Integer id, Integer year,
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
			where = where + " AND parent_id=" + id;
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id = " + typeId;

			List<PcStatsVo> list = getResult(where);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStatsVo> getStatsByParentCode(Integer id, Integer year,
			List<Integer> qs, Integer typeId) {

		try {
			EntityManager em = super.getEntityManager();
			Query sql1 = em.createQuery("select code from PcAgency where id = "
					+ id);
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
			where = where + " AND code like '" + code + "%'";
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id = " + typeId;

			List<PcStatsVo> list = getResult(where);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStatsVo> getZwhStatsById(Integer id, Integer year,
			Integer startM, Integer endM) {

		try {

			Integer avg = endM - startM + 1;

			String where = " WHERE 1= 1";
			where = where + " AND agency_id=" + id;
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = 8";

			List<PcStatsVo> list = getResult(where);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PcStatsVo> getZwhStatsByParentId(Integer id, Integer year,
			Integer startM, Integer endM) {

		try {

			Integer avg = endM - startM + 1;

			String where = " WHERE 1= 1";
			where = where + " AND parent_id=" + id;
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = 8";

			List<PcStatsVo> list = getResult(where);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStatsVo> getZwhStatsByParentCode(Integer id, Integer year,
			Integer startM, Integer endM) {

		try {
			EntityManager em = super.getEntityManager();
			Query sql1 = em.createQuery("select code from PcAgency where id = "
					+ id);
			List<String> rs = sql1.getResultList();
			String code = rs.get(0);

			Integer avg = endM - startM + 1;

			String where = " WHERE 1= 1";
			where = where + " AND code like '" + code + "%'";
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = 8";

			List<PcStatsVo> list = getResult(where);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	

}
