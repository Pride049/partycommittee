package com.partycommittee.events
{
	import com.partycommittee.vo.PcRemindLockVo;
	import com.partycommittee.vo.page.PageHelperVo;
	
	import mx.collections.ArrayCollection;
	
	public class PcRemindLockEvent extends BaseEvent {
		public static const PCREMINDLOCK_EVENT:String = "com.partycommittee.events.PcRemindLockEvent";
		
		public static const GET_REMIND_LOCK_BY_FILTERS:String = "getRemindLockByFilters";
		public static const UPDATE_REMIND_LOCK:String = "updateRemindLock";
		
		private var _kind:String;
		
		private var _pcRemindLockVo:PcRemindLockVo;
		
		private var _filters:Array;
		
		private var _page:PageHelperVo;
		public function get page():PageHelperVo {
			return this._page;
		}
		public function set page(value:PageHelperVo):void {
			this._page = value;
		}		
		
		public function get filters():Array
		{
			return _filters;
		}

		public function set filters(value:Array):void
		{
			_filters = value;
		}

		public function get pcRemindLockVo():PcRemindLockVo
		{
			return _pcRemindLockVo;
		}

		public function set pcRemindLockVo(value:PcRemindLockVo):void
		{
			_pcRemindLockVo = value;
		}

		public function set kind(value:String):void {
			this._kind = value;
		}
		public function get kind():String {
			return this._kind;
		}
		
		public function PcRemindLockEvent(kind:String = "getRemindLockByFilters") {
			super(PCREMINDLOCK_EVENT);
			this.kind = kind;
		}
	}
}