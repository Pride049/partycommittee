package com.partycommittee.proxy
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.partycommittee.vo.PcRemindLockVo;
	import com.partycommittee.vo.page.PageHelperVo;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class PcStatProxy extends BaseProxy {
		public static const SERVICE_NAME:String = "pcStat";
		
		public function PcStatProxy(responder:IResponder) {
			service = ServiceLocator.getInstance().getRemoteObject(SERVICE_NAME);
			this.responder = responder;
		}
		
		public function getAgencyStatById(agencyId:Number):void {
			service.getAgencyStatById(agencyId);
		}
	}
}