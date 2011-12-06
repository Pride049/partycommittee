package com.partycommittee.controller
{
	import com.adobe.cairngorm.control.FrontController;
	import com.partycommittee.commands.PcAgencyCommand;
	import com.partycommittee.commands.PcMemberCommand;
	import com.partycommittee.commands.PcUserCommand;
	import com.partycommittee.events.PcAgencyEvent;
	import com.partycommittee.events.PcMemberEvent;
	import com.partycommittee.events.PcUserEvent;

	public class Controller extends FrontController {
		public function Controller() {
			init();
		}
		
		private function init():void {
			addCommand(PcUserEvent.PCUSEREVENT, PcUserCommand);
			addCommand(PcMemberEvent.PCMEMBER_EVENT, PcMemberCommand);
			addCommand(PcAgencyEvent.PCAGENCY_EVENT, PcAgencyCommand);
		}
	}
   
}