package com.partycommittee.util
{
	import com.partycommittee.vo.PcAgencyVo;
	import com.partycommittee.vo.PcMemberVo;
	import com.partycommittee.vo.PcUserVo;
	
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;

	public class LblFunctionHelper {
		public function LblFunctionHelper() {
		}
		
		public static function dateLblFunc(item:Object, col:*):String {
			var date:Date = item[col.dataField] as Date;
			if (date) {
				return DateUtil.toISOString(date, false);
			}
			return "";
		}
		
		public static function agencyCodeLblFunc(item:Object, col:*):String {
			if (item as PcAgencyVo) {
				return AgencyCodeUtil.getAgencyCodeDes((item as PcAgencyVo).codeId);
			}
			return "";
		}
		
		public static function memberSexLblFunc(item:Object, col:*):String {
			if (item as PcMemberVo) {
				return SexCodeUtil.getSexCodeDes((item as PcMemberVo).sexId);
			}
			return "";
		}
		
		public static function memberDutyLblFunc(item:Object, col:*):String {
			if (item as PcMemberVo) {
				return DutyCodeUtil.getDutyCodeDes((item as PcMemberVo).dutyId);
			}
			return "";
		}
		
		public static function memberEduLblFunc(item:Object, col:*):String {
			if (item as PcMemberVo) {
				return EduCodeUtil.getEduCodeDes((item as PcMemberVo).eduId);
			}
			return "";
		}
		
		public static function userStatusLblFunc(item:Object, col:*):String {
			if (item as PcUserVo) {
				return (item as PcUserVo).status == 1 ? "启用" : "禁用";
			}
			return "";
		}
		
		public static function userAgencyLblFunc(item:Object, col:*):String {
			if (item as PcUserVo) {
				var userVo:PcUserVo = item as PcUserVo;
				if (userVo.agencyList && userVo.agencyList.length) {
					var agencyNameArry:Array = new Array();
					for each (var agency:PcAgencyVo in userVo.agencyList) {
						agencyNameArry.push(agency.name);
					}
					return agencyNameArry.join(", ");
				}
			}
			return "";
		}
	}
}