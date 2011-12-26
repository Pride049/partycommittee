package com.partycommittee.model 
{
	import com.partycommittee.control.tree.LoadingTree;
	import com.partycommittee.vo.PcAgencyVo;
	import com.partycommittee.vo.PcUserVo;
	
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	
    [Bindable]
	public final class ModelLocator extends EventDispatcher {
		private static var instance:ModelLocator;

		public function ModelLocator(access:Private) {
			if (access == null) {
			    throw new Error("ModelLocator is singleton!");
			}
			instance = this;
		}
		 
		public static function getInstance():ModelLocator {
			if (instance == null) {
				instance = new ModelLocator(new Private());
			}
			return instance;
		}
		
		private var _treeCollection:ArrayCollection;
		public function get treeCollection():ArrayCollection {
			return this._treeCollection;
		}
		public function set treeCollection(value:ArrayCollection):void {
			this._treeCollection = value;
		}
		
		private var _tree:LoadingTree;
		public function get tree():LoadingTree {
			return this._tree;
		}
		public function set tree(value:LoadingTree):void {
			this._tree = value;
		}
	 	
		public var loginUser:PcUserVo;
		public var focusAgencyVo:PcAgencyVo;
		
		public var LOGIN_PAGE:String;
		public var INDEX_PAGE:String;
		
		public var selectedMenu:Object;
	}
}

/**
 * Inner class which restricts constructor access to Private
 */
class Private {}