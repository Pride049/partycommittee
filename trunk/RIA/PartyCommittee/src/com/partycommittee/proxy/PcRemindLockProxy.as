package com.partycommittee.proxy
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.partycommittee.vo.PcRemindLockVo;

	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class PcRemindLockProxy extends BaseProxy {
		public static const SERVICE_NAME:String = "pcRemindLock";
		
		public function PcRemindLockProxy(responder:IResponder) {
			service = ServiceLocator.getInstance().getRemoteObject(SERVICE_NAME);
			this.responder = responder;
		}

		public function getRemindLockByFilters(filters:ArrayCollection):void {
			service.getRemindLockByFilters();
		}
		
		public function updateRemindLock(item:PcRemindLockVo):void {
			service.updateItems(item);
		}		

	}
}