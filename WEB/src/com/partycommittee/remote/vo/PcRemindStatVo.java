package com.partycommittee.remote.vo;

import java.io.Serializable;
import java.sql.Date;

import com.partycommittee.persistence.po.PcRemindStat;

public class PcRemindStatVo implements Serializable {
	private static final long serialVersionUID = -1190776206070964606L;

	private int id;

	private int agency_id;

	private int c;

	private int code_id;

	private String ext;

	private String name;

	private int parent_id;

	private byte quarter;

	private int status;

	private int type_id;

	private Integer year;	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAgency_id() {
		return agency_id;
	}

	public void setAgency_id(int agencyId) {
		agency_id = agencyId;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getCode_id() {
		return code_id;
	}

	public void setCode_id(int codeId) {
		code_id = codeId;
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

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parentId) {
		parent_id = parentId;
	}

	public byte getQuarter() {
		return quarter;
	}

	public void setQuarter(byte quarter) {
		this.quarter = quarter;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int typeId) {
		type_id = typeId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public static PcRemindStatVo fromPcRemind(PcRemindStat pevo) {
		PcRemindStatVo vo = new PcRemindStatVo();
		vo.setAgency_id(pevo.getAgency_id());
		vo.setC(pevo.getC());
		vo.setCode_id(pevo.getCode_id());
		vo.setExt(pevo.getExt());
		vo.setId(pevo.getId());
		vo.setName(pevo.getName());
		vo.setParent_id(pevo.getParent_id());
		vo.setQuarter(pevo.getQuarter());
		vo.setStatus(pevo.getStatus());
		vo.setType_id(pevo.getType_id());
		vo.setYear(pevo.getYear());

		return vo;
	}
	
	public static PcRemindStat toPcRemind(PcRemindStatVo pevo) {
		PcRemindStat vo = new PcRemindStat();
		vo.setAgency_id(pevo.getAgency_id());
		vo.setC(pevo.getC());
		vo.setCode_id(pevo.getCode_id());
		vo.setExt(pevo.getExt());
		vo.setId(pevo.getId());
		vo.setName(pevo.getName());
		vo.setParent_id(pevo.getParent_id());
		vo.setQuarter(pevo.getQuarter());
		vo.setStatus(pevo.getStatus());
		vo.setType_id(pevo.getType_id());
		vo.setYear(pevo.getYear());
		return vo;
	}
}
