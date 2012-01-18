package com.partycommittee.persistence.po;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the pc_remind_stat database table.
 * 
 */
@Entity
@Table(name="pc_remind_stat")
public class PcRemindStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

    public PcRemindStat() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAgency_id() {
		return this.agency_id;
	}

	public void setAgency_id(int agency_id) {
		this.agency_id = agency_id;
	}

	public int getC() {
		return this.c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getCode_id() {
		return this.code_id;
	}

	public void setCode_id(int code_id) {
		this.code_id = code_id;
	}

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public byte getQuarter() {
		return this.quarter;
	}

	public void setQuarter(byte quarter) {
		this.quarter = quarter;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType_id() {
		return this.type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}