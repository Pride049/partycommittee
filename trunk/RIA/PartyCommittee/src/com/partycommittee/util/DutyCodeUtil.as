package com.partycommittee.util
{
	public class DutyCodeUtil {
		public function DutyCodeUtil() {
		}
		
		public static function getDutyCodeDes(codeId:Number):String {
			switch (codeId) {
				case PCConst.DUTY_DEPUTY_SECRETARY:
					return "副书记";
				case PCConst.DUTY_DISCIPLINE_MEMBER:
					return "纪检委员";
				case PCConst.DUTY_MASSES_MEMBER:
					return "群众委员";
				case PCConst.DUTY_ORGNIZATION_MEMBER:
					return "组织委员";
				case PCConst.DUTY_PARTYCOMMITTEE_MEMBER:
					return "党委委员";
				case PCConst.DUTY_PROPAGANDA_MEMBER:
					return "宣传委员";
				case PCConst.DUTY_SECRECY_MEMBER:
					return "保密委员";
				case PCConst.DUTY_SECRETARY:
					return "书记";
				case PCConst.DUTY_TEAM_LEADER:
					return "组长";
				case PCConst.DUTY_TEAM_MEMBER:
					return "党组成员";
				case PCConst.DUTY_UNITEDFRONT_MEMBER:
					return "统战委员";
				case PCConst.DUTY_YOUTH_MEMBER:
					return "青年委员";
				default:
					return null;
			}
		}
	}
}