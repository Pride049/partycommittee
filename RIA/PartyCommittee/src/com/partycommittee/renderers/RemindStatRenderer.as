package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	import mx.controls.dataGridClasses.DataGridListData;
	import mx.controls.listClasses.BaseListData;
	
	public class RemindStatRenderer extends Text  {
		
		protected var _listData:DataGridListData;	
		protected var _snum:Number;
		
		public function RemindStatRenderer() {
			super();
		}

		override public function set data(value:Object):void {
			setIt(value);
		}
		
		override public function get listData():BaseListData	{
			return _listData;
		}
		
		override public function set listData(value:BaseListData):void	{
			_listData = DataGridListData(value);
			invalidateProperties();
		}
		
		private function setIt(value:Object):void	{
			if(value && _listData)	{
				if(_listData.columnIndex == 0)	{
					
				} else if(_listData.columnIndex == 1)	{
					
				} else	{
					
				}
			}
		}		
		
		override public function set text(txt:String):void {

		}
	}
}