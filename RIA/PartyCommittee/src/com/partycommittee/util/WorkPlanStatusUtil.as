package com.partycommittee.util
{
	public class WorkPlanStatusUtil {
		public function WorkPlanStatusUtil() {
		}
		
		// 1=已上报， 2=未上报, 3=已审批, 4=已评价
		public static function getWorkPlanStatusDes(statusId:Number):String {
			switch (statusId) {
				case 0:
					return "";
				case 1:
					return "未上报";
				case 2:
					return "已上报";
				case 3:
					return "已审批";
				case 4:
					return "已评价";
			}
			return "";
		}
	}
}