package com.partycommittee.remote.vo;

import java.io.Serializable;
import java.util.Date;

import com.partycommittee.persistence.po.PcAgency;

public class PcAgencyVo implements Serializable {
	private static final long serialVersionUID = 389989221452220832L;
	
	private Integer id;
	private Integer parentId;
	private String name;
	private Integer codeId;
	private Integer number;
	private Integer memberId;
	private PcMemberVo member;
	private String tel;
	private String ext;
	private Date setupDatetime;
	private String comment;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCodeId() {
		return this.codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public PcMemberVo getMember() {
		return this.member;
	}
	public void setMember(PcMemberVo member) {
		this.member = member;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public Date getSetupDatetime() {
		return setupDatetime;
	}
	public void setSetupDatetime(Date setupDatetime) {
		this.setupDatetime = setupDatetime;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public static PcAgency toPcAgency(PcAgencyVo agencyVo) {
		PcAgency agency = new PcAgency();
		agency.setExt(agencyVo.getExt());
		agency.setId(agencyVo.getId());
		agency.setMemberId(agencyVo.getMemberId());
		agency.setName(agencyVo.getName());
		agency.setCodeId(agencyVo.getCodeId());
		agency.setNumber(agencyVo.getNumber());
		agency.setSetupDatetime(agencyVo.getSetupDatetime());
		agency.setTel(agencyVo.getTel());
		agency.setComment(agencyVo.getComment());
		return agency;
	}
	
	public static PcAgencyVo fromPcAgency(PcAgency agency) {
		PcAgencyVo agencyVo = new PcAgencyVo();
		agencyVo.setExt(agency.getExt());
		agencyVo.setId(agency.getId());
		agencyVo.setMemberId(agency.getMemberId());
		agencyVo.setName(agency.getName());
		agencyVo.setCodeId(agency.getCodeId());
		agencyVo.setNumber(agency.getNumber());
		agencyVo.setSetupDatetime(agency.getSetupDatetime());
		agencyVo.setTel(agency.getTel());
		agencyVo.setComment(agency.getComment());
		return agencyVo;
	}
}
