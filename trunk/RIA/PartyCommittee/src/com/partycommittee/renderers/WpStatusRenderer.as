package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	
	public class WpStatusRenderer extends Text  {
		
		public function WpStatusRenderer() {
			super();
		}
		
		override public function set text(txt:String):void {
			var datas:Object = data;
			var statusId:Number = parseFloat(datas.statusId);
			var model:ModelLocator = ModelLocator.getInstance();
			switch (statusId) {
				case 0:
					if (model.loginUser.enableReport != 1) {
						htmlText = "未填";
					} else {
						htmlText = '<u><font color="#0000ff"><a href="event:nowp">未填</a></font></u>';
					}
					break;
				case 1:
					if (model.loginUser.enableReport != 1) {
						htmlText = "未报";
					} else {
						htmlText = '<u><font color="#0000ff"><a href="event:nowp">未报</a></font></u>';	
					}
					break;
				case 2:
					if (model.loginUser.enableReport != 1) {
						htmlText = "已报";
					} else {
						htmlText = '<u><font color="#0000ff"><a href="event:nowp">已报</a></font></u>';	
					}
					break;				
				case 3:
					if (model.loginUser.enableReport != 1) {
						htmlText = "已审";
					} else {
						htmlText = '<u><font color="#0000ff"><a href="event:nowp">已审</a></font></u>';	
					}
					break;
				case 4:
					if (model.loginUser.enableReport != 1) {
						htmlText = "已评";
					} else {
						htmlText = '<u><font color="#0000ff"><a href="event:nowp">已评</a></font></u>';	
					}	
					break;
				case 8:
					htmlText = "已解锁";	
					break;
				case 9:
					htmlText = "已锁定";
					break;
			}
		}
	}
}