package com.partycommittee.proxy
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.partycommittee.vo.PcMeetingContentVo;
	import com.partycommittee.vo.PcMeetingVo;
	
	import mx.rpc.IResponder;
	
	public class PcMeetingProxy extends BaseProxy {
		public static const SERVICE_NAME:String = "pcMeeting";
		
		public function PcMeetingProxy(responder:IResponder) {
			service = ServiceLocator.getInstance().getRemoteObject(SERVICE_NAME);
			this.responder = responder;
		}
		
		public function getBranchCommitteeMeetingList(agencyId:Number, year:Number):void {
			service.getBranchCommitteeMeetingList(agencyId, year);
		}
		
		public function getBranchLifeMeetingList(agencyId:Number, year:Number):void {
			service.getBranchLifeMeetingList(agencyId, year);
		}
		
		public function getBranchMemberMeetingList(agencyId:Number, year:Number):void {
			service.getBranchMemberMeetingList(agencyId, year);
		}
		
		public function getClassMeetingList(agencyId:Number, year:Number):void {
			service.getClassMeetingList(agencyId, year);
		}
		
		public function getMeetingContent(meetingId:Number):void {
			service.getMeetingContent(meetingId);
		}
		
		public function getOtherMeetingList(agencyId:Number, year:Number):void {
			service.getOtherMeetingList(agencyId, year);
		}
		
		public function submitMeeting(meeting:PcMeetingVo):void {
			service.submitMeeting(meeting);
		}
		
		public function createMeeting(meeting:PcMeetingVo):void {
			service.createMeeting(meeting);
		}
		
		public function updateMeeting(meeting:PcMeetingVo):void {
			service.updateMeeting(meeting);
		}
		
		public function getCommitChildrenMeeting(agencyId:Number, year:Number):void {
			service.getCommitChildrenMeeting(agencyId, year);
		}
		
		public function getEvaluateInfo(meetingId:Number):void {
			service.getEvaluateInfo(meetingId);
		}
		
		public function evaluateMeeting(meetingId:Number, content:PcMeetingContentVo):void {
			service.evaluateMeeting(meetingId, content);
		}
		
		public function getAlertInfo(agencyId:Number, year:Number, quarter:Number):void {
			service.getAlertInfo(agencyId, year, quarter);
		}
		
		public function getMeetingComment(meeting:PcMeetingVo):void {
			service.getMeetingComment(meeting);
		}
		
		public function updateMeetingStatus(meetingId:Number, statusId:Number):void {
			service.updateMeetingStatus(meetingId, statusId);
		}		
		
	}
}