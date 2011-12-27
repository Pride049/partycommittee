package com.partycommittee.proxy
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.partycommittee.vo.PcUserVo;
	import com.partycommittee.vo.page.PageHelperVo;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.remoting.RemoteObject;

	public class PcUserProxy extends BaseProxy {
		public static const SERVICE_NAME:String = "pcUser";
		
		public function PcUserProxy(responder:IResponder) {
			service = ServiceLocator.getInstance().getRemoteObject(SERVICE_NAME);
			this.responder = responder;
		}
		
		public function createPcUser(user:PcUserVo):void {
			service.createPcUser(user);
		}
		
		public function deletePcUsers(userList:ArrayCollection):void {
			service.deletePcUsers(userList);
		}
		
		public function updatePcUser(user:PcUserVo):void {
			service.updatePcUser(user);
		}
		
		public function getPcUserList():void {
			service.getPcUserList();
		}
		
		public function getPcUserListByPage(page:PageHelperVo):void {
			service.getPcUserListByPage(page);
		}
		
		public function login(username:String, password:String):void {
			service.login(username, password);
		}
		
		public function userLogOut():void {
			service.userLogOut();
		}
		
		public function getLoginUser():void {
			service.getLoginUser();
		}
		
	}
}