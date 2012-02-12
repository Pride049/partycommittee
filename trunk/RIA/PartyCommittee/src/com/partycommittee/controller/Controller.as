package com.partycommittee.controller
{
	import com.adobe.cairngorm.control.FrontController;
	import com.partycommittee.commands.PcAgencyCommand;
	import com.partycommittee.commands.PcMeetingCommand;
	import com.partycommittee.commands.PcMemberCommand;
	import com.partycommittee.commands.PcParentStatCommand;
	import com.partycommittee.commands.PcRemindCommand;
	import com.partycommittee.commands.PcRemindConfigCommand;
	import com.partycommittee.commands.PcRemindLockCommand;
	import com.partycommittee.commands.PcUserCommand;
	import com.partycommittee.commands.PcWorkPlanCommand;
	import com.partycommittee.events.PcAgencyEvent;
	import com.partycommittee.events.PcMeetingEvent;
	import com.partycommittee.events.PcMemberEvent;
	import com.partycommittee.events.PcParentStatEvent;
	import com.partycommittee.events.PcRemindConfigEvent;
	import com.partycommittee.events.PcRemindEvent;
	import com.partycommittee.events.PcRemindLockEvent;
	import com.partycommittee.events.PcUserEvent;
	import com.partycommittee.events.PcWorkPlanEvent;

	public class Controller extends FrontController {
		public function Controller() {
			init();
		}
		
		private function init():void {
			addCommand(PcUserEvent.PCUSEREVENT, PcUserCommand);
			addCommand(PcMemberEvent.PCMEMBER_EVENT, PcMemberCommand);
			addCommand(PcAgencyEvent.PCAGENCY_EVENT, PcAgencyCommand);
			addCommand(PcWorkPlanEvent.PCWORKPLAN_EVENT, PcWorkPlanCommand);
			addCommand(PcMeetingEvent.PCMEETING_EVENT, PcMeetingCommand);
			addCommand(PcRemindEvent.PCREMIND_EVENT, PcRemindCommand);
			addCommand(PcRemindConfigEvent.PCREMINDCONFIG_EVENT, PcRemindConfigCommand);
			addCommand(PcRemindLockEvent.PCREMINDLOCK_EVENT, PcRemindLockCommand);
			addCommand(PcParentStatEvent.PCPARENT_EVENT, PcParentStatCommand);
		}
	}
   
}