package com.partycommittee.commands
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.partycommittee.events.PcWorkPlanEvent;
	import com.partycommittee.model.ModelLocator;
	import com.partycommittee.proxy.PcWorkPlanProxy;
	import com.partycommittee.util.CRUDEventType;
	
	import mx.rpc.IResponder;
	
	public class PcWorkPlanCommand extends BaseCommand implements IResponder {
		public function PcWorkPlanCommand() {
		}
		
		override public function execute(event:CairngormEvent):void {
			super.execute(event);
			var pcWorkPlanEvt:PcWorkPlanEvent = event as PcWorkPlanEvent;
			var proxy:PcWorkPlanProxy = getProxy();
			switch (pcWorkPlanEvt.kind) {
				case CRUDEventType.UPDATE:
					proxy.updateWorkPlan(pcWorkPlanEvt.workPlan);
					break;
				case PcWorkPlanEvent.GET_WORKPLAN_YEARLY:
					proxy.getWorkPlayYearly(pcWorkPlanEvt.agency.id, pcWorkPlanEvt.year);
					break;
				case CRUDEventType.CREATE:
					proxy.createWorkPlan(pcWorkPlanEvt.workPlan);
					break;
				default :
					break;
			}
		}
		
		public function result(data:Object):void {
			var model:ModelLocator = ModelLocator.getInstance();
			var pcWorkPlanEvt:PcWorkPlanEvent = event as PcWorkPlanEvent;
			switch (pcWorkPlanEvt.kind) {
				case CRUDEventType.CREATE:
					break;
				case CRUDEventType.UPDATE:
					break;
				case PcWorkPlanEvent.GET_WORKPLAN_YEARLY:
					break;
				default:
					break;
			}
			onSuccess(data);
		}
		
		public function fault(info:Object):void {
			onFailure(info);
		}
		
		protected function getProxy():PcWorkPlanProxy {
			return new PcWorkPlanProxy(this);
		}
	}
}