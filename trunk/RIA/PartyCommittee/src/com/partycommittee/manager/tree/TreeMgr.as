package com.partycommittee.manager.tree
{
	import com.partycommittee.control.tree.classes.LoadingTreeNodeFactory;
	import com.partycommittee.control.tree.classes.Node;
	import com.partycommittee.model.ModelLocator;
	import com.partycommittee.vo.PcAgencyVo;
	
	import mx.collections.ArrayCollection;

	public class TreeMgr {
		private static var instance:TreeMgr;
		public function TreeMgr() {
			if (instance) {
				throw new Error("TreeMgr is singleton!");
			}
			instance = this;
		}
		
		public static function getInstance():TreeMgr {
			if (instance == null) {
				instance = new TreeMgr();
			}
			return instance;
		}
		
		[Bindable]
		private var model:ModelLocator = ModelLocator.getInstance();
		
		public function initRoot(root:PcAgencyVo):void {
			if (!model.treeCollection) {
				model.treeCollection = new ArrayCollection();
			}
			model.treeCollection.removeAll();
			var rootItem:Node = createItem(root);
			model.treeCollection.addItem(rootItem);
		}
		
		public function createItem(agencyVo:PcAgencyVo):Node {
			if (!agencyVo) {
				return null;
			}
			var node:Node = new Node();
			node.entity = agencyVo;
			node.labelField = "name";
			node.nodeType = LoadingTreeNodeFactory.NODETYPE_INVENTORY;
			node.children = new ArrayCollection();
			return node;
		}
	}
}