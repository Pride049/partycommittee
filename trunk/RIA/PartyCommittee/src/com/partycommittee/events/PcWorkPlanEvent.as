package com.partycommittee.events
{
	import com.partycommittee.vo.PcAgencyVo;
	import com.partycommittee.vo.PcWorkPlanVo;
	
	public class PcWorkPlanEvent extends BaseEvent {
		public static const PCWORKPLAN_EVENT:String = "com.partycommittee.events.PcWorkPlanEvent";
		
		public static const GET_WORKPLAN_YEARLY:String = "getWorkPlanYearly";
		
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