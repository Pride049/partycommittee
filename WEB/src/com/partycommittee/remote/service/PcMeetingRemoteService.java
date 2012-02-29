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
	public List<PcMeetingVo> getMeetingList(Integer agencyId, Integer year, Integer meetingType) {
		return pcMeetingService.getMeetingList(agencyId, year, meetingType);
	}	
		
	@RemotingInclude
	public PcMeetingContentVo getMeetingContent(Integer meetingId) {
		return pcMeetingService.getMeetingContent(meetingId);
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
	public void evaluateMeeting(Integer meetingId, Integer statusId, PcMeetingContentVo contentVo) {
		pcMeetingService.evaluateMeeting(meetingId, statusId, contentVo);
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
