package com.partycommittee.proxy
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.IResponder;
	
	public class PcMeetingProxy extends BaseProxy {
		public static const SERVICE_NAME:String = "pcMeeting";
		
		public function PcMeetingProxy(responder:IResponder) {
			service = ServiceLocator.getInstance().getRemoteObject(SERVICE_NAME);
			this.responder = responder;
		}
		
	}
}