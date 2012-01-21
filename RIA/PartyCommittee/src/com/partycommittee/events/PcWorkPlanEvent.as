package com.partycommittee.events
{
	import com.partycommittee.vo.PcAgencyVo;
	import com.partycommittee.vo.PcWorkPlanContentVo;
	import com.partycommittee.vo.PcWorkPlanVo;
	
	public class PcWorkPlanEvent extends BaseEvent {
		public static const PCWORKPLAN_EVENT:String = "com.partycommittee.events.PcWorkPlanEvent";
		
		public static const GET_WORKPLAN_YEARLY:String = "getWorkPlanYearly";
		public static const GET_WORKPLAN_YEARLY_SUMMARY:String = "getWorkPlanYearlySummary";
		public static const GET_WORKPLAN_QUARTER:String = "getWorkPlanQuarter";
		public static const GET_WORKPLAN_QUARTER_LIST:String = "getWorkPlanQuarterList";
		public static const GET_WORKPLAN_QUARTER_RESULT_LIST:String = "getWorkPlanQuarterResultList";
		public static const GET_COMMIT_CHILDREN_WORKPLAN:String = "getCommitChildrenWorkplan";
		public static const GET_WORKPLAN_CONTENT_BY_WORKPLANID:String = "getWorkPlanContentByWorkPlanId";
		public static const SUBMIT_WORKPLAN:String = "submitWorkPlan";
		
		public static const GET_APPROVAL_INFO:String = "getApprovalInfo";
		public static const GET_EVALUATE_INFO:String = "getEvaluateInfo";
		public static const APPROVAL_WORKPLAN:String = "approvalWorkPlan";
		public static const EVALUATE_WORKPLAN:String = "valuateWorkPlan";
		public static const RETURN_WORKPLAN:String = "returnWorkPlan";
		
		public static const GET_WORKPLAN_COMMENT:String = "getWorkPlanComment";
		public static const GET_ALERT_INFO:String = "getAlertInfo";
		
		private var _agency:PcAgencyVo;
		public function set agency(value:PcAgencyVo):void {
			this._agency = value;
		}
		public function get agency():PcAgencyVo {
			return this._agency;
		}
		
		private var _workPlan:PcWorkPlanVo;
		public function set workPlan(value:PcWorkPlanVo):void {
			this._workPlan = value;
		}
		public function get workPlan():PcWorkPlanVo {
			return this._workPlan;
		}
		
		private var _year:Number;
		public function get year():Number {
			return this._year;
		}
		public function set year(value:Number):void {
			this._year = value;
		}
		
		private var _quarter:Number;
		public function get quarter():Number {
			return this._quarter;
		}
		public function set quarter(value:Number):void {
			this._quarter = value;
		}
		
		private var _workPlanContent:PcWorkPlanContentVo;
		public function get workPlanContent():PcWorkPlanContentVo {
			return this._workPlanContent;
		}
		public function set workPlanContent(value:PcWorkPlanContentVo):void {
			this._workPlanContent = value;
		}
		
		private var _kind:String;
		public function set kind(value:String):void {
			this._kind = value;
		}
		public function get kind():String {
			return this._kind;
		}
		
		public function PcWorkPlanEvent(kind:String = "READ", agency:PcAgencyVo = null) {
			super(PCWORKPLAN_EVENT);
			this.agency = agency;
			this.kind = kind;
		}
	}
}