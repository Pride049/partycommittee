package com.partycommittee.vo
{
	[RemoteClass(alias="com.partycommittee.remote.vo.PcAgencyVo")]
	public class PcAgencyVo {
		public function PcAgencyVo() {
		}
		
		public var id:Number;
		public var parentId:Number;
		public var name:String;
		public var codeId:Number;
		public var number:Number;
		public var memberId:Number;
		public var member:PcMemberVo;
		public var tel:String;
		public var comment:String;
		public var setupDatetime:Date;
		
		public var ext:String;
	}
}