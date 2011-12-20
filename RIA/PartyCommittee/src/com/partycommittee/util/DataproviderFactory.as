package com.partycommittee.util
{
	import mx.collections.ArrayCollection;

	public class DataproviderFactory {
		public function DataproviderFactory() {
		}
		
		public static const WORKPLAN_YEAR_LENGTH:Number = 20;
		
		public static function getYearDp():ArrayCollection {
			var yearCollection:ArrayCollection = new ArrayCollection();
			var currentYear:Number = new Date().getFullYear();
			for (var i:int = 0; i < WORKPLAN_YEAR_LENGTH; i++) {
				yearCollection.addItem(currentYear - i);
			}
			return yearCollection;
		}
	}
}