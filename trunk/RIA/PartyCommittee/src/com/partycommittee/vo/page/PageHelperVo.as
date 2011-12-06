package com.partycommittee.vo
{
	[RemoteClass(alias="com.partycommittee.remote.vo.PageHelperVo")]
	public class PageHelperVo {
		public function PageHelperVo() {
		}
		
		public var recordCount:Number;
		public var pageCount:Number;
		public var pageIndex:Number;
		public var pageSize:Number;
	}
}