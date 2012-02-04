package com.partycommittee.renderers
{
	import com.partycommittee.model.ModelLocator;
	
	import mx.controls.Alert;
	import mx.controls.Text;
	import mx.controls.dataGridClasses.DataGridListData;
	import mx.controls.listClasses.BaseListData;
	
	import spark.components.mediaClasses.VolumeBar;
	
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
				var datas:Object = value;
				
				if (datas.q == 1) {
				if(_listData.columnIndex == 0)	{
					//'<u><font color="#0000ff"><a href="event:getRemindList&typeId&statusId&'+datas.agencyId+'">'+datas.y1+'</a></font></u>';
					} else if(_listData.columnIndex == 1)	{
						if (datas.y1 > 0) {
							htmlText = '<u><font color="#0000ff"><a href="event:getRemindList&1&1&'+datas.agencyId+'">'+datas.y1+'</a></font></u>';
						} else {
							htmlText = datas.y1;
						}
					} else if(_listData.columnIndex == 2)	{
						if (datas.y2 > 0) {
							htmlText = '<u><font color="#0000ff"><a href="event:getRemindList&2&4&'+datas.agencyId+'">'+datas.y2+'</a></font></u>';
						} else {
							htmlText = datas.y2;
						}					
					} else if(_listData.columnIndex == 3)	{
						if (datas.lq1 > 0) {
							htmlText = '<u><font color="#0000ff"><a href="event:getRemindList&2&3&'+datas.agencyId+'">'+datas.lq1+'</a></font></u>';
						} else {
							htmlText = datas.lq1;
						}					
					}
				}
			}
		}		
		
		private function setValueQ1(value:Object):void {
			
		}
		
		private function setValueQ(value:Object):void {
			
		}
		
		
		
	}
}