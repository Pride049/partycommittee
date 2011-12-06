package com.partycommittee.events
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.partycommittee.util.CRUDEventType;
	import com.partycommittee.vo.PcUserVo;
	
	import mx.collections.ArrayCollection;
	
	public class PcUserEvent extends BaseEvent {
		public static const PCUSEREVENT:String = "com.partycommittee.events.PCUserEvent";
		
		public static const LOGIN:String = "login";
		
		private var _user:PcUserVo;
		public function set user(value:PcUserVo):void {
			this._user = value;
		}
		public function get user():PcUserVo {
			return this._user;
		}
		
		private var _kind:String;
		public function set kind(value:String):void {
			this._kind = value;
		}
		public function get kind():String {
			return this._kind;
		}
		
		private var _userList:ArrayCollection;
		public function set userList(value:ArrayCollection):void {
			this._userList = value;
		}
		public function get userList():ArrayCollection {
			return this._userList;
		}
		
		public function PcUserEvent(kind:String = "READ", user:PcUserVo = null, userList:ArrayCollection = null) {
			super(PCUSEREVENT);
			this.user = user;
			this.userList = userList;
			this.kind = kind;
		}
	}
}