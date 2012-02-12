package com.partycommittee.remote.vo;

import java.io.Serializable;
import java.sql.Date;

import com.partycommittee.persistence.po.PcParentStat;;

public class PcParentStatVo implements Serializable {
	private static final long serialVersionUID = -1190776206070964606L;

	private Integer id;

	private Integer agencyId;

	private Integer codeId;

	private String ext;

	private String name;

	private Integer parentId;

	private Integer quarter;

	private Integer statusId;

	private Integer typeId;

	private Integer year;	

	private Integer total;
	
	private Integer reported;
	
	private Integer delay;

	private Double reportedRate;
	
	private Integer eva;
	
	private Double evaRate;
	
	private Integer attend;
	
	private Integer asence;
	
	private Double  attendRate;
	
	private Integer pCount;
	
	private Integer zbNum;	
	
	private Integer agencyNum;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getReported() {
		return reported;
	}

	public void setReported(Integer reported) {
		this.reported = reported;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Double getReportedRate() {
		return reportedRate;
	}

	public void setReportedRate(Double reportedRate) {
		this.reportedRate = reportedRate;
	}

	public Integer getEva() {
		return eva;
	}

	public void setEva(Integer eva) {
		this.eva = eva;
	}

	public Double getEvaRate() {
		return evaRate;
	}

	public void setEvaRate(Double evaRate) {
		this.evaRate = evaRate;
	}

	public Integer getAttend() {
		return attend;
	}

	public void setAttend(Integer attend) {
		this.attend = attend;
	}

	public Integer getAsence() {
		return asence;
	}

	public void setAsence(Integer asence) {
		this.asence = asence;
	}

	public Double getAttendRate() {
		return attendRate;
	}

	public void setAttendRate(Double attendRate) {
		this.attendRate = attendRate;
	}

	public Integer getpCount() {
		return pCount;
	}

	public void setpCount(Integer pCount) {
		this.pCount = pCount;
	}

	public Integer getZbNum() {
		return zbNum;
	}

	public void setZbNum(Integer zbNum) {
		this.zbNum = zbNum;
	}
	
	public Integer getAgencyNum() {
		return agencyNum;
	}

	public void setAgencyNum(Integer agencyNum) {
		this.agencyNum = agencyNum;
	}
	
	
	
	public static PcParentStatVo fromPcParentStat(PcParentStat pevo) {
		PcParentStatVo vo = new PcParentStatVo();
		vo.setAgencyId(pevo.getAgencyId());
		vo.setCodeId(pevo.getCodeId());
		vo.setExt(pevo.getExt());
		vo.setId(pevo.getId());
		vo.setName(pevo.getName());
		vo.setParentId(pevo.getParentId());
		vo.setQuarter(pevo.getQuarter());
		vo.setStatusId(pevo.getStatusId());
		vo.setTypeId(pevo.getTypeId());
		vo.setYear(pevo.getYear());
		vo.setTotal(pevo.getTotal());
		vo.setReported(pevo.getReported());
		vo.setDelay(pevo.getDelay());
		vo.setReportedRate(pevo.getReportedRate());
		vo.setEva(pevo.getEva());
		vo.setEvaRate(pevo.getEvaRate());
		vo.setAttend(pevo.getAttend());
		vo.setAsence(pevo.getAsence());
		vo.setAttendRate(pevo.getAttendRate());
		vo.setpCount(pevo.getpCount());
		vo.setZbNum(pevo.getZbNum());
		vo.setAgencyNum(pevo.getAgencyNum());
		return vo;
	}
	
	public static PcParentStat toPcParentStat(PcParentStatVo pevo) {
		PcParentStat vo = new PcParentStat();
		vo.setCodeId(pevo.getCodeId());
		vo.setExt(pevo.getExt());
		vo.setId(pevo.getId());
		vo.setName(pevo.getName());
		vo.setParentId(pevo.getParentId());
		vo.setQuarter(pevo.getQuarter());
		vo.setStatusId(pevo.getStatusId());
		vo.setTypeId(pevo.getTypeId());
		vo.setYear(pevo.getYear());
		vo.setTotal(pevo.getTotal());
		vo.setReported(pevo.getReported());
		vo.setDelay(pevo.getDelay());
		vo.setReportedRate(pevo.getReportedRate());
		vo.setEva(pevo.getEva());
		vo.setEvaRate(pevo.getEvaRate());
		vo.setAttend(pevo.getAttend());
		vo.setAsence(pevo.getAsence());
		vo.setAttendRate(pevo.getAttendRate());
		vo.setpCount(pevo.getpCount());
		vo.setZbNum(pevo.getZbNum());
		vo.setAgencyNum(pevo.getAgencyNum());
		return vo;
	}
}
