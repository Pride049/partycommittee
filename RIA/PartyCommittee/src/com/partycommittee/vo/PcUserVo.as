package com.partycommittee.vo 
{
	[Bindable]
	[RemoteClass(alias="com.partycommittee.remote.vo.PcUserVo")]
	public class PcUserVo {
      
		public function PcUserVo() {
		}
      
		public var id:int;
		public var username:String;
		public var password:String;
		public var email:String;
        public var phone:String;
		public var status:Number;
		public var lastlogintime:Date;
		public var description:String;
		public var privilege:String;
	}
}