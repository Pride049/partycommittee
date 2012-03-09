package com.partycommittee.remote.vo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;

import com.partycommittee.persistence.po.PcAgencyStats;

public class PcAgencyStatsVo implements Serializable {
	private static final long serialVersionUID = -1190776206070964606L;

	private Integer id;

	private Integer agencyId;

	private Integer codeId;

	private String name;
	
	private String code;

	private Integer parentId;

	private Integer ejdwNum;
	private Integer dzjNum;
	private Integer dzbNum;
	private Integer more2yearNum;
	private Integer less7Num;
	private Integer noFsjZbwyNum;
	private Integer dxzNum;
	private Integer dyNum;
	private Integer zbsjNum;
	private Integer zbfsjNum;
	private Integer zzwyNum;
	private Integer xcwyNum;
	private Integer jjwyNum;
	private Integer qnwyNum;
	private Integer ghwyNum;
	private Integer fnwyNum;
	private Integer bmwyNum;
	private Date updatetime;	

	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	
	@Column(name = "ejdw_num")
	public Integer getEjdwNum() {
		return ejdwNum;
	}

	public void setEjdwNum(Integer ejdwNum) {
		this.ejdwNum = ejdwNum;
	}

	@Column(name = "dzj_num")
	public Integer getDzjNum() {
		return dzjNum;
	}

	public void setDzjNum(Integer dzjNum) {
		this.dzjNum = dzjNum;
	}

	@Column(name = "dzb_num")
	public Integer getDzbNum() {
		return dzbNum;
	}

	public void setDzbNum(Integer dzbNum) {
		this.dzbNum = dzbNum;
	}

	@Column(name = "more2year_num")
	public Integer getMore2yearNum() {
		return more2yearNum;
	}

	public void setMore2yearNum(Integer more2yearNum) {
		this.more2yearNum = more2yearNum;
	}

	@Column(name = "less7_num")
	public Integer getLess7Num() {
		return less7Num;
	}

	public void setLess7Num(Integer less7Num) {
		this.less7Num = less7Num;
	}

	@Column(name = "no_fsj_zbwy_num")
	public Integer getNoFsjZbwyNum() {
		return noFsjZbwyNum;
	}

	public void setNoFsjZbwyNum(Integer noFsjZbwyNum) {
		noFsjZbwyNum = noFsjZbwyNum;
	}
	
	@Column(name = "dxz_num")
	public Integer getDxzNum() {
		return dxzNum;
	}

	public void setDxzNum(Integer dxzNum) {
		this.dxzNum = dxzNum;
	}

	@Column(name = "dy_num")
	public Integer getDyNum() {
		return dyNum;
	}

	public void setDyNum(Integer dyNum) {
		this.dyNum = dyNum;
	}

	@Column(name = "zbsj_num")
	public Integer getZbsjNum() {
		return zbsjNum;
	}

	public void setZbsjNum(Integer zbsjNum) {
		this.zbsjNum = zbsjNum;
	}

	@Column(name = "zbfsj_num")
	public Integer getZbfsjNum() {
		return zbfsjNum;
	}

	public void setZbfsjNum(Integer zbfsjNum) {
		this.zbfsjNum = zbfsjNum;
	}

	@Column(name = "zzwy_num")
	public Integer getZzwyNum() {
		return zzwyNum;
	}

	public void setZzwyNum(Integer zzwyNum) {
		this.zzwyNum = zzwyNum;
	}

	@Column(name = "xcwy_num")
	public Integer getXcwyNum() {
		return xcwyNum;
	}

	
	public void setXcwyNum(Integer xcwyNum) {
		this.xcwyNum = xcwyNum;
	}

	@Column(name = "jjwy_num")
	public Integer getJjwyNum() {
		return jjwyNum;
	}

	
	public void setJjwyNum(Integer jjwyNum) {
		this.jjwyNum = jjwyNum;
	}
	
	@Column(name = "qnwy_num")
	public Integer getQnwyNum() {
		return qnwyNum;
	}

	
	public void setQnwyNum(Integer qnwyNum) {
		this.qnwyNum = qnwyNum;
	}

	@Column(name = "ghwy_num")
	public Integer getGhwyNum() {
		return ghwyNum;
	}

	
	public void setGhwyNum(Integer ghwyNum) {
		this.ghwyNum = ghwyNum;
	}

	@Column(name = "fnwy_num")
	public Integer getFnwyNum() {
		return fnwyNum;
	}

	
	public void setFnwyNum(Integer fnwyNum) {
		this.fnwyNum = fnwyNum;
	}

	@Column(name = "bmwy_num")
	public Integer getBmwyNum() {
		return bmwyNum;
	}

	public void setBmwyNum(Integer bmwyNum) {
		this.bmwyNum = bmwyNum;
	}

	@Column(name = "updatetime")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}		
	
	
	public static PcAgencyStatsVo fromPcAgencyStats(PcAgencyStats pevo) {
		PcAgencyStatsVo vo = new PcAgencyStatsVo();
		vo.setAgencyId(pevo.getAgencyId());
		vo.setCodeId(pevo.getCodeId());
		vo.setCode(pevo.getCode());
		vo.setId(pevo.getId());
		vo.setName(pevo.getName());
		vo.setParentId(pevo.getParentId());
		vo.setEjdwNum(pevo.getEjdwNum());
		vo.setDzjNum(pevo.getDzjNum());
		vo.setDzbNum(pevo.getDzbNum());
		vo.setMore2yearNum(pevo.getMore2yearNum());
		vo.setLess7Num(pevo.getLess7Num());
		vo.setNoFsjZbwyNum(pevo.getNoFsjZbwyNum());
		vo.setDxzNum(pevo.getDxzNum());
		vo.setDyNum(pevo.getDyNum());
		vo.setZbsjNum(pevo.getZbsjNum());
		vo.setZbfsjNum(pevo.getZbfsjNum());
		vo.setZzwyNum(pevo.getZzwyNum());
		vo.setXcwyNum(pevo.getXcwyNum());
		vo.setJjwyNum(pevo.getJjwyNum());
		vo.setQnwyNum(pevo.getQnwyNum());
		vo.setGhwyNum(pevo.getGhwyNum());
		vo.setFnwyNum(pevo.getFnwyNum());
		vo.setBmwyNum(pevo.getBmwyNum());
		vo.setUpdatetime(pevo.getUpdatetime());
		return vo;
	}
	
	public static PcAgencyStats toPcAgencyStats(PcAgencyStatsVo pevo) {
		PcAgencyStats vo = new PcAgencyStats();
		vo.setCodeId(pevo.getCodeId());
		vo.setCode(pevo.getCode());
		vo.setId(pevo.getId());
		vo.setName(pevo.getName());
		vo.setParentId(pevo.getParentId());
		vo.setEjdwNum(pevo.getEjdwNum());
		vo.setDzjNum(pevo.getDzjNum());
		vo.setDzbNum(pevo.getDzbNum());
		vo.setMore2yearNum(pevo.getMore2yearNum());
		vo.setLess7Num(pevo.getLess7Num());
		vo.setNoFsjZbwyNum(pevo.getNoFsjZbwyNum());
		vo.setDxzNum(pevo.getDxzNum());
		vo.setDyNum(pevo.getDyNum());
		vo.setZbsjNum(pevo.getZbsjNum());
		vo.setZbfsjNum(pevo.getZbfsjNum());
		vo.setZzwyNum(pevo.getZzwyNum());
		vo.setXcwyNum(pevo.getXcwyNum());
		vo.setJjwyNum(pevo.getJjwyNum());
		vo.setQnwyNum(pevo.getQnwyNum());
		vo.setGhwyNum(pevo.getGhwyNum());
		vo.setFnwyNum(pevo.getFnwyNum());
		vo.setBmwyNum(pevo.getBmwyNum());
		vo.setUpdatetime(pevo.getUpdatetime());
		return vo;
	}
}
