package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	
	public class RemindLQStatusRenderer extends Text  {
		
		public function RemindLQStatusRenderer() {
			super();
		}

		override public function set text(txt:String):void {

			var datas:Object = data;
			var statusId:Number = datas.lq;
			var model:ModelLocator = ModelLocator.getInstance();
			switch (statusId) {
				case 0:
					htmlText = "未报";
					break;
				case 1:
					htmlText = "未报";
					break;
				case 2:
					if (datas.parentId == model.loginUser.privilege) {
						htmlText = '<u><font color="#0000ff"><a href="event:evaluate&lquarterplan&'+datas.agencyId+'">未评</a></font></u>';
					} else {
						htmlText = '未评';
					}	
					break;
				case 3:
					htmlText  = "已审";
					break;
				case 4:
					//htmlText  = "已评";	
					htmlText = '<u><font color="#0000ff"><a href="event:evaluate&lquarterplan&'+datas.agencyId+'">已评</a></font></u>';
					break;
			}
		}
	}
}