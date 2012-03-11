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

@Repository("PcStatsDaoImpl")
public class PcStatsDaoImpl extends JpaDaoBase implements PcStatsDao {


	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getStatsById(Integer id, Integer year,
			List<Integer> qs, Integer typeId) {
		java.sql.Connection conn = null;
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
			where = where + " AND agency_id=" + id;
			where = where + " AND year =" + year;
			where = where + " AND quarter in (" + ids + ")";
			where = where + " AND type_id = " + typeId;
			List<PcStats> list = getResult(where, avg);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PcStats> getStatsByParentId(Integer id, Integer year,
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

			List<PcStats> list = getResult(where, avg);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PcStats> getStatsByParentCode(Integer id, Integer year,
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

			List<PcStats> list = getResult(where, avg);
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
			where = where + " AND agency_id=" + id;
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = 8";

			List<PcStats> list = getResult(where, avg);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PcStats> getZwhStatsByParentId(Integer id, Integer year,
			Integer startM, Integer endM) {

		try {

			Integer avg = endM - startM + 1;

			String where = " WHERE 1= 1";
			where = where + " AND parent_id=" + id;
			where = where + " AND year =" + year;
			where = where + " AND month >= " + startM + " AND month <=" + endM;
			where = where + " AND type_id = 8";

			List<PcStats> list = getResult(where, avg);
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

			List<PcStats> list = getResult(where, avg);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<PcStats> getResult(String where, Integer avg) throws SQLException {
		java.sql.Connection conn = null;
		try {

			String sql = "SELECT agency_id, name, YEAR, type_id,"
					+ "SUM(total) as total,SUM(total_success) as total_success,SUM(total_return) as total_return,SUM(total_delay) as total_delay,SUM(  reported ) as reported,"
					+ "ROUND( SUM(reported_rate)/"+ avg + ", 4) as reported_rate,"
					+ "ROUND( SUM(return_rate)/" + avg + ", 4) as  return_rate,"
					+ "ROUND( SUM(delay_rate)/"	+ avg + ", 4) as  delay_rate,"
					+ "SUM(  attend ) as attend,SUM(  asence ) as asence,"
					+ "ROUND(SUM(attend_rate)/"	+ avg + ", 4) as attend_rate,SUM(eva) as eva,"
					+ "ROUND(SUM(eva_rate)/" + avg + ", 4) as eva_rate,"
					+ "SUM(eva_1) as eva_1,SUM(eva_2) as eva_2,SUM(eva_3) as eva_3,SUM(eva_4) as eva_4,"
					+ "ROUND(SUM(eva_1_rate)/" + avg + ", 4) as eva_1_rate,"
					+ "ROUND(SUM(eva_2_rate)/" + avg + ", 4) as eva_2_rate,"
					+ "ROUND(SUM(eva_3_rate)/" + avg + ", 4) as eva_3_rate,"
					+ "ROUND(SUM(eva_4_rate)/" + avg + ", 4) as eva_4_rate,"
					+ "SUM(agency_goodjob) as agency_goodjob FROM pc_zzsh_stat "
					+ where + " GROUP BY agency_id, name, YEAR, type_id";

			 List<PcStats> list = new ArrayList<PcStats>();
			 EntityManager em = super.getEntityManager();
			 HibernateEntityManager hem = (HibernateEntityManager)em;
			 conn = hem.getSession().connection();
			 
			 java.sql.Statement statement = conn.createStatement();
			    ResultSet rows = statement.executeQuery(sql);  
			    while (rows.next()) {
//			    	System.out.println(rows.getString("name"));
			    	PcStats pevo = new PcStats();
			    	pevo.setAgencyId(rows.getInt("agency_id"));
			    	pevo.setName(rows.getString("name"));
			    	pevo.setYear(rows.getInt("YEAR"));
			    	pevo.setTypeId(rows.getInt("type_id"));
			    	pevo.setTotal(rows.getInt("total"));
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
			    
//			 statement.close();
//			 conn.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	

}
