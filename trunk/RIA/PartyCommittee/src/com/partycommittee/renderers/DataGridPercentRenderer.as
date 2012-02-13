package com.partycommittee.renderers
{

	import mx.controls.Alert;
	import mx.controls.Text;
	import mx.controls.dataGridClasses.DataGridItemRenderer;
	
	public class DataGridPercentRenderer extends DataGridItemRenderer  {
		
		public function DataGridPercentRenderer() {
			super();
		}
		
		override public function set text(value:String):void {
			super.text = value + "%";
		}
	}
}