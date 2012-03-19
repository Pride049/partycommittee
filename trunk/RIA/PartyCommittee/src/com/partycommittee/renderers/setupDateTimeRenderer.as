package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	import com.partycommittee.util.DateUtil;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	
	public class setupDateTimeRenderer extends Text  {
		
		public function setupDateTimeRenderer() {
			super();
		}
		
		override public function set text(txt:String):void {
			var datas:Object = data;
			if (datas.hasOwnProperty('setupDatetime')) {
				htmlText = DateUtil.toISOString(data.setupDatetime as Date);
			} else {
				htmlText = '';
			} 
				
		}
	}
}