package com.partycommittee.commands
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.partycommittee.events.PcRemindEvent;
	import com.partycommittee.model.ModelLocator;
	import com.partycommittee.proxy.PcRemindProxy;

	
	import mx.rpc.IResponder;
	
	public class PcRemindCommand extends BaseCommand implements IResponder {
		public function PcRemindCommand() {
		}
		
		override public function execute(event:CairngormEvent):void {
			super.execute(event);
			var pcRemindEvt:PcRemindEvent = event as PcRemindEvent;
			var proxy:PcRemindProxy = getProxy();
			switch (pcRemindEvt.kind) {
				case PcRemindEvent.GET_REAL_REMIND_BY_ID :
					proxy.getRealRemindById(pcRemindEvt.agencyId, pcRemindEvt.year, pcRemindEvt.quarter);
					break;
				case PcRemindEvent.GET_REMIND_STAT_BY_PARENTID : 
					proxy.getListRemindStatByParentId(pcRemindEvt.agencyId, pcRemindEvt.year, pcRemindEvt.quarter);
					break;
				case PcRemindEvent.GET_REMIND_STAT_BY_PARENTID_FOR_ADMIN : 
					proxy.getListRemindStatByParentIdForAdmin(pcRemindEvt.agencyId, pcRemindEvt.year, pcRemindEvt.quarter);
					break;
				case PcRemindEvent.GET_REMIND_STAT_BY_PARENTID_FOR_OTHER : 
					proxy.getListRemindByParentIdForOther(pcRemindEvt.agencyId, pcRemindEvt.year, pcRemindEvt.quarter);
					break;						
				default :
					break;
			}
		}
		
		public function result(data:Object):void {
			var model:ModelLocator = ModelLocator.getInstance();
			onSuccess(data);
		}
		
		public function fault(info:Object):void {
			onFailure(info);
		}
		
		protected function getProxy():PcRemindProxy {
			return new PcRemindProxy(this);
		}
	}
}