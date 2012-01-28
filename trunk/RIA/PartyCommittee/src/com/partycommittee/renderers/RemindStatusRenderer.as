package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	
	public class RemindStatusRenderer extends Text  {
		
		public function RemindStatusRenderer() {
			super();
		}
		
		override public function set text(txt:String):void {

			var datas:Object = data;
			var statusId:Number;
			switch(datas.typeId) {
				case 1 : 
					statusId= datas.y;
					break;
				case 2 : 
					statusId= datas.q;
					break;
				case 3 : 
					statusId= datas.lq;
					break;
				case 4 : 
					statusId= datas.ly;
					break;	
				case 5 : 
					statusId= datas.dk;
					break;
				case 6 : 
					statusId= datas.dydh;
					break;
				case 7 : 
					statusId= datas.mzshh;
					break;
				case 8 : 
					statusId= datas.zbwyh;
					break;
				case 9 : 
					statusId= datas.qt;
					break;				
			}
			switch (statusId) {
				case 0:
					htmlText = "未报";
					break;
				case 1:
					htmlText = "未报";
					break;
				case 2:
					htmlText  = "未审";
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