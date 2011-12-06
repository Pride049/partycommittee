package com.partycommittee.remote.vo;

import java.io.Serializable;
import java.util.Date;

import com.partycommittee.persistence.po.PcMember;

public class PcMemberVo implements Serializable {
	private static final long serialVersionUID = -2191780744965268353L;

	private Integer id;
	private Integer agencyId;
	private Integer postId;
	private String name;
	private Integer sexId;
	private String nation;
	private Date birthday;
	private Date workday;
	private Date joinday;
	private Date postday;
	private Integer eduId;
	private String birthPlace;
	private String address;
	private Integer dutyId;
	private String ext;
	private Integer active;
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
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSexId() {
		return sexId;
	}
	public void setSexId(Integer sexId) {
		this.sexId = sexId;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getWorkday() {
		return workday;
	}
	public void setWorkday(Date workday) {
		this.workday = workday;
	}
	public Date getJoinday() {
		return joinday;
	}
	public void setJoinday(Date joinday) {
		this.joinday = joinday;
	}
	public Date getPostday() {
		return postday;
	}
	public void setPostday(Date postday) {
		this.postday = postday;
	}
	public Integer getEduId() {
		return eduId;
	}
	public void setEduId(Integer eduId) {
		this.eduId = eduId;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getDutyId() {
		return dutyId;
	}
	public void setDutyId(Integer dutyId) {
		this.dutyId = dutyId;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public static PcMember toPcMember(PcMemberVo memberVo) {
		PcMember member = new PcMember();
		member.setAddress(memberVo.getAddress());
		member.setAgencyId(memberVo.getAgencyId());
		member.setBirthday(memberVo.getBirthday());
		member.setDutyId(memberVo.getDutyId());
		member.setEduId(memberVo.getEduId());
		member.setExt(memberVo.getExt());
		member.setId(memberVo.getId());
		member.setJoinday(memberVo.getJoinday());
		member.setName(memberVo.getName());
		member.setNation(memberVo.getNation());
		member.setPostday(memberVo.getPostday());
		member.setPostId(memberVo.getPostId());
		member.setSexId(memberVo.getSexId());
		member.setActive(memberVo.getActive());
		member.setUpdatetime(memberVo.getUpdatetime());
		member.setWorkday(memberVo.getWorkday());
		return member;
	}
	
	public static PcMemberVo fromPcMember(PcMember member) {
		PcMemberVo memberVo = new PcMemberVo();
		memberVo.setAddress(member.getAddress());
		memberVo.setAgencyId(member.getAgencyId());
		memberVo.setBirthday(member.getBirthday());
		memberVo.setDutyId(member.getDutyId());
		memberVo.setEduId(member.getEduId());
		memberVo.setExt(member.getExt());
		memberVo.setId(member.getId());
		memberVo.setJoinday(member.getJoinday());
		memberVo.setName(member.getName());
		memberVo.setNation(member.getNation());
		memberVo.setPostday(member.getPostday());
		memberVo.setPostId(member.getPostId());
		memberVo.setSexId(member.getSexId());
		memberVo.setActive(member.getActive());
		memberVo.setUpdatetime(member.getUpdatetime());
		memberVo.setWorkday(member.getWorkday());
		return memberVo;
	}
}
