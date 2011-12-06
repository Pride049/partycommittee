package com.partycommittee.persistence.po;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pc_agency")
public class PcAgency implements java.io.Serializable {
	private static final long serialVersionUID = 2648939911474179327L;

	/** default constructor */
	public PcAgency() {
	}
	
	private Integer id;
	private String name;
	private Integer codeId;
	private Integer number;
	private Integer memberId;
	private String tel;
	private String ext;
	private Date setupDatetime;
	private String comment;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "code_id")
	public Integer getCodeId() {
		return this.codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@Column(name = "member_id")
	public Integer getMemberId() {
		return this.memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@Column(name = "tel")
	public String getTel() {
		return this.tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Column(name = "ext")
	public String getExt() {
		return this.ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "setup_datetime", length = 23)
	public Date getSetupDatetime() {
		return this.setupDatetime;
	}
	public void setSetupDatetime(Date setupDatetime) {
		this.setupDatetime = setupDatetime;
	}
	
	@Column(name = "comment")
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}