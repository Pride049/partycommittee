package com.partycommittee.renderers
{

	import mx.controls.Alert;
	import mx.controls.Text;
	
	public class DataGridPercentRenderer extends Text  {
		
		public function DataGridPercentRenderer() {
			super();
		}
		
		override public function set text(txt:String):void {
			if (!txt ) {
				super.text = ' ';
			} else {
				
				try {
					var percent:Number = Math.round(Number(txt) * 1000) / 10;
					htmlText = percent.toString() + '%'; 
				} catch(e:Error) {
					htmlText = txt;
				}
			}
		}
	}
}