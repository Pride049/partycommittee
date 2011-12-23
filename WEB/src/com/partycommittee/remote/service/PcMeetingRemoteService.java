package com.partycommittee.remote.service;


import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

@Service("PcMeeting")
@RemotingDestination(channels={"my-amf"})
public class PcMeetingRemoteService {
	
	
}
