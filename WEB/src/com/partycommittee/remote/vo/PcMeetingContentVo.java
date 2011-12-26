package com.partycommittee.remote.vo;

import java.io.Serializable;
import java.util.Date;

import com.partycommittee.persistence.po.PcMeetingContent;

public class PcMeetingContentVo implements Serializable {
	private static final long serialVersionUID = 3297647266353084328L;
	
	private Integer id;
	private Integer type;
	private Integer meetingId;
	private Integer memberId;
	private String memberName;
	private String content;
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public static PcMeetingContent toPcMeetingContent(PcMeetingContentVo contentVo) {
		PcMeetingContent content = new PcMeetingContent();
		content.setContent(contentVo.getContent());
		content.setId(contentVo.getId());
		content.setMeetingId(contentVo.getMeetingId());
		content.setMemberId(contentVo.getMemberId());
		content.setMemberName(contentVo.getMemberName());
		content.setType(contentVo.getType());
		content.setUpdateTime(contentVo.getUpdateTime());
		return content;
	}

	public static PcMeetingContentVo fromPcMeetingContent(PcMeetingContent content) {
		PcMeetingContentVo contentVo = new PcMeetingContentVo();
		contentVo.setContent(content.getContent());
		contentVo.setId(content.getId());
		contentVo.setMeetingId(content.getMeetingId());
		contentVo.setMemberId(content.getMemberId());
		contentVo.setMemberName(content.getMemberName());
		contentVo.setType(content.getType());
		contentVo.setUpdateTime(content.getUpdateTime());
		return contentVo;
	}
}
