package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	
	public class RemindDKStatusRenderer extends Text  {
		
		public function RemindDKStatusRenderer() {
			super();
		}

		override public function set text(txt:String):void {

			var datas:Object = data;
			var statusId:Number = datas.dydh;
			switch (statusId) {
				case 0:
					htmlText = "未报";
					break;
				case 1:
					htmlText = "未报";
					break;
				case 2:
					htmlText  = "未评";
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