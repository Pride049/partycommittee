package com.partycommittee.proxy
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.partycommittee.vo.PcWorkPlanVo;
	
	import mx.rpc.IResponder;
	
	public class PcWorkPlanProxy extends BaseProxy {
		public static const SERVICE_NAME:String = "pcWorkPlan";
		
		public function PcWorkPlanProxy(responder:IResponder) {
			service = ServiceLocator.getInstance().getRemoteObject(SERVICE_NAME);
			this.responder = responder;
		}
		
		public function createWorkPlan(workPlan:PcWorkPlanVo):void {
			service.createWorkPlan(workPlan);
		}
		
		public function createWorkPlanAndSubmit(workPlan:PcWorkPlanVo):void {
			service.createWorkPlanAndSubmit(workPlan);
		}
		
		public function updateWorkPlan(workPlan:PcWorkPlanVo):void {
			service.updateWorkPlan(workPlan);
		}
		
		public function updateWorkPlanAndSubmit(workPlan:PcWorkPlanVo):void {
			service.updateWorkPlanAndSubmit(workPlan);
		}
		
		public function getWorkPlanYearly(agencyId:Number, year:Number):void {
			service.getWorkPlanYearly(agencyId, year);
		}
		
		public function getWorkPlanYearlySummary(agencyId:Number, year:Number):void {
			service.getWorkPlanYearlySummary(agencyId, year);
		}
		
		public function getWorkPlanQuarter(agencyId:Number, year:Number, quarter:Number):void {
			service.getWorkPlanQuarter(agencyId, year, quarter);
		}
		
		public function getWorkPlanListQuarter(agencyId:Number, year:Number):void {
			service.getWorkPlanListQuarter(agencyId, year);
		}
		
		public function getResultListQuarter(agencyId:Number, year:Number):void {
			service.getResultListQuarter(agencyId, year);
		}
		
		public function getContentByWorkPlanId(workPlanId:Number):void {
			service.getContentByWorkPlanId(workPlanId);
		}
		
		public function submitWorkPlan(workPlan:PcWorkPlanVo):void {
			service.submitWorkPlan(workPlan);
		}
		
		public function getCommitWorkplanListByParentId(parentId:Number):void {
			service.getCommitWorkplanListByParentId(parentId);
		}
		
		public function approvalWorkplan():void {
			service.approvalWorkplan();
		}
		
		public function evaluateWrokplan():void {
			service.evaluateWrokplan();
		}
		
	}
}