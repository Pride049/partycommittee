package com.partycommittee.events
{
	import com.partycommittee.vo.PcRemindLockVo;
	
	import mx.collections.ArrayCollection;
	
	public class PcRemindLockEvent extends BaseEvent {
		public static const PCREMINDLOCK_EVENT:String = "com.partycommittee.events.PcRemindLockEvent";
		
		public static const GET_REMIND_LOCK_BY_FILTERS:String = "getRemindLockByFilters";
		public static const UPDATE_REMIND_LOCK:String = "updateRemindLock";
		
		private var _kind:String;
		
		private var _pcRemindLockVo:PcRemindLockVo;
		
		private var _filters:ArrayCollection;
		
		public function get filters():ArrayCollection
		{
			return _filters;
		}

		public function set filters(value:ArrayCollection):void
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