package com.partycommittee.remote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.partycommittee.remote.vo.PcMeetingContentVo;
import com.partycommittee.remote.vo.PcMeetingVo;
import com.partycommittee.service.PcMeetingService;

@Service("PcMeetingRo")
@RemotingDestination(channels={"my-amf"})
public class PcMeetingRemoteService {
	
	@Resource(name="PcMeetingService")
	private PcMeetingService pcMeetingService;
	public void setPcMeetingService(PcMeetingService pcMeetingService) {
		this.pcMeetingService = pcMeetingService;
	}
	
	@RemotingInclude
	public List<PcMeetingVo> getBranchCommitteeMeetingList(Integer agencyId, Integer year) {
		return pcMeetingService.getBranchCommitteeMeetingList(agencyId, year);
	}
	
	@RemotingInclude
	public List<PcMeetingVo> getBranchLifeMeetingList(Integer agencyId, Integer year) {
		return pcMeetingService.getBranchLifeMeetingList(agencyId, year);
	}
	
	@RemotingInclude
	public List<PcMeetingVo> getBranchMemberMeetingList(Integer agencyId, Integer year) {
		return pcMeetingService.getBranchMemberMeetingList(agencyId, year);
	}
	
	@RemotingInclude
	public List<PcMeetingVo> getClassMeetingList(Integer agencyId, Integer year) {
		return pcMeetingService.getClassMeetingList(agencyId, year);
	}
	
	@RemotingInclude
	public PcMeetingContentVo getMeetingContent(Integer meetingId) {
		return pcMeetingService.getMeetingContent(meetingId);
	}
	
	@RemotingInclude
	public List<PcMeetingVo> getOtherMeetingList(Integer agencyId, Integer year) {
		return pcMeetingService.getOtherMeetingList(agencyId, year);
	}
	
	@RemotingInclude
	public void submitMeeting(PcMeetingVo meeting) {
		pcMeetingService.submitMeeting(meeting);
	}
	
	@RemotingInclude
	public void createMeeting(PcMeetingVo meeting) {
		pcMeetingService.createMeeting(meeting);
	}
	
	@RemotingInclude
	public void updateMeeting(PcMeetingVo meeting) {
		pcMeetingService.updateMeeting(meeting);
	}
	
	@RemotingInclude
	public void updateMeetingStatus(Integer meetingId, Integer StatusId) {
		pcMeetingService.updateMeetingStatus(meetingId, StatusId);
	}		
	
	@RemotingInclude
	public void evaluateMeeting(Integer meetingId, PcMeetingContentVo contentVo) {
		pcMeetingService.evaluateMeeting(meetingId, contentVo);
	}
	
	@RemotingInclude
	public List<PcMeetingVo> getCommitChildrenMeeting(Integer agencyId, Integer year) {
		return pcMeetingService.getCommitChildrenMeeting(agencyId, year);
	}
	
	@RemotingInclude
	public PcMeetingContentVo getEvaluateInfo(Integer meetingId) {
		return pcMeetingService.getEvaluateInfo(meetingId);
	}
	
	@RemotingInclude
	public List<PcMeetingVo> getAlertInfo(Integer agencyId, Integer year, Integer quarter) {
		return pcMeetingService.getAlertInfo(agencyId, year, quarter);
	}
	
	@RemotingInclude
	public PcMeetingContentVo getMeetingComment(PcMeetingVo meetingVo) {
		return pcMeetingService.getMeetingComment(meetingVo);
	}
}
