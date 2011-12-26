package com.partycommittee.events
{
	import com.partycommittee.vo.PcAgencyVo;
	import com.partycommittee.vo.PcMeetingContentVo;
	import com.partycommittee.vo.PcMeetingVo;
	
	public class PcMeetingEvent extends BaseEvent {
		public static const PCMEETING_EVENT:String = "com.partycommittee.events.PcMeetingEvent";
		
		public static const GET_CLASS_LIST:String = "getClassList";
		public static const GET_BRANCH_MEMBER_LIST:String = "getBranchMemberList";
		public static const GET_BRANCH_LIFE_LIST:String = "getBranchLifeList";
		public static const GET_BRANCH_COMMITTEE_LIST:String = "getBranchCommitteeList";
		public static const GET_TEAM_LIST:String = "getTeamList";
		public static const GET_OTHER_LIST:String = "getOtherList";
		public static const GET_EVALUATE_INFO:String = "getEvaluateInfo";
		
		public static const GET_COMMIT_CHILDREN_MEETING:String = "getCommitChildrenMeeting";
		
		public static const GET_MEETING_CONTENT:String = "getMeetingContent";
		public static const SUBMIT_MEETING:String = "submitMeeting";
		
		public static const APPROVAL_MEETING:String = "approvalMeeting";
		public static const EVALUATE_MEETING:String = "evaluateMeeting";
		
		public static const GET_ALERT_INFO:String = "getAlertInfo";
		
		private var _agency:PcAgencyVo;
		public function set agency(value:PcAgencyVo):void {
			this._agency = value;
		}
		public function get agency():PcAgencyVo {
			return this._agency;
		}
		
		private var _meeting:PcMeetingVo;
		public function set meeting(value:PcMeetingVo):void {
			this._meeting = value;
		}
		public function get meeting():PcMeetingVo {
			return this._meeting;
		}
		
		private var _year:Number;
		public function set year(value:Number):void {
			this._year = value;
		}
		public function get year():Number {
			return this._year;
		}
		
		private var _quarter:Number;
		public function set quarter(value:Number):void {
			this._quarter = value;
		}
		public function get quarter():Number {
			return this._quarter;
		}
		
		private var _meetingContent:PcMeetingContentVo;
		public function set meetingContent(value:PcMeetingContentVo):void {
			this._meetingContent = value;
		}
		public function get meetingContent():PcMeetingContentVo {
			return this._meetingContent;
		}
		
		private var _kind:String;
		public function set kind(value:String):void {
			this._kind = value;
		}
		public function get kind():String {
			return this._kind;
		}
		
		public function PcMeetingEvent(kind:String = "READ", agency:PcAgencyVo = null) {
			super(PCMEETING_EVENT);
			this.agency = agency;
			this.kind = kind;
		}
	}
}