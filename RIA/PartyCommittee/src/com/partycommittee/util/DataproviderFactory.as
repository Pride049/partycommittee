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
				yearCollection.addItem(currentYear + i);
			}
			return yearCollection;
		}
		
		public static function createMonthDp():ArrayCollection {
			var monthCollection:ArrayCollection = new ArrayCollection();
			for (var i:Number = 1; i <= 12; i++) {
				monthCollection.addItem(i);
			}
			return monthCollection;
		}
		
		public static function createQuarterDp():ArrayCollection {
			var quarterCollection:ArrayCollection = new ArrayCollection();
			for (var i:Number = 1; i <= 4; i++) {
				quarterCollection.addItem(i);
			}
			return quarterCollection;
		}
		
		public static function createDayDp():ArrayCollection {
			var dateCollection:ArrayCollection = new ArrayCollection();
			for (var i:Number = 1; i <= 31; i++) {
				dateCollection.addItem(i);
			}
			return dateCollection;
		}
		
		public static function getRangeYearDp():ArrayCollection {
			var yearCollection:ArrayCollection = new ArrayCollection();
			var currentYear:Number = new Date().getFullYear();
			for (var i:int = 0; i < WORKPLAN_YEAR_LENGTH + 1; i++) {
				yearCollection.addItem(currentYear - i);
			}
			return yearCollection;
		}
	}
}