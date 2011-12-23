package com.partycommittee.commands
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.partycommittee.events.PcMeetingEvent;
	import com.partycommittee.model.ModelLocator;
	import com.partycommittee.proxy.PcMeetingProxy;
	
	import mx.rpc.IResponder;
	
	public class PcMeetingCommand extends BaseCommand implements IResponder {
		public function PcMeetingCommand() {
		}
		
		override public function execute(event:CairngormEvent):void {
			super.execute(event);
			var pcMeetingEvt:PcMeetingEvent = event as PcMeetingEvent;
			var proxy:PcMeetingProxy = getProxy();
			switch (pcMeetingEvt.kind) {
				default :
					break;
			}
		}
		
		public function result(data:Object):void {
			var model:ModelLocator = ModelLocator.getInstance();
			var pcMeetingEvt:PcMeetingEvent = event as PcMeetingEvent;
			switch (pcMeetingEvt.kind) {
				default:
					break;
			}
			onSuccess(data);
		}
		
		public function fault(info:Object):void {
			onFailure(info);
		}
		
		protected function getProxy():PcMeetingProxy {
			return new PcMeetingProxy(this);
		}
	}
}