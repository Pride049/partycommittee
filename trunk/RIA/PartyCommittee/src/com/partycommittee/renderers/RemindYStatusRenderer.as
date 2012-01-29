package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	
	public class RemindYStatusRenderer extends Text  {
		
		public function RemindYStatusRenderer() {
			super();
		}

		override public function set text(txt:String):void {

			var datas:Object = data;
			var statusId:Number = datas.y;
			var model:ModelLocator = ModelLocator.getInstance();
			switch (statusId) {
				case 0:
					htmlText = "未报";
					break;
				case 1:
					htmlText = "未报";
					break;
				case 2:
//					if (datas.parentId == model.loginUser.privilege) {
//						htmlText = '<u><font color="#0000ff"><a href="event:approval&yearplan&'+datas.agencyId+'">未审</a></font></u>';
//					} else {
						htmlText = '未审';
//					}
					break;				
				case 3:
					htmlText  = "已审";
					break;
				case 4:
					htmlText  = "已评";		
					break;
			}
		}
	}
}