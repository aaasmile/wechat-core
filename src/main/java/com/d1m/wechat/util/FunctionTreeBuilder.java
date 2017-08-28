package com.d1m.wechat.util;

import java.util.ArrayList;
import java.util.List;

import com.d1m.wechat.dto.FunctionDto;

public class FunctionTreeBuilder {
	List<FunctionDto> nodes = new ArrayList<FunctionDto>();

	public FunctionTreeBuilder(List<FunctionDto> nodes) {
		this.nodes = nodes;
	}

	/**
	 * 构建JSON树形结构
	 * @return
	 */
	public List<FunctionDto> buildJSONTree() {
		List<FunctionDto> treeNodes = new ArrayList<FunctionDto>();
		List<FunctionDto> rootNodes = getRootNodes();
		for (FunctionDto rootNode : rootNodes) {
			buildChildNodes(rootNode);
			treeNodes.add(rootNode);
		}
		return treeNodes;
	}

	/**
	 * 递归子节点
	 * @param node
	 */
	public void buildChildNodes(FunctionDto node) {
		List<FunctionDto> children = getChildNodes(node);
		if (!children.isEmpty()) {
			for (FunctionDto child : children) {
				buildChildNodes(child);
			}
			node.setChildren(children);
		}
	}

	/**
	 * 获取父节点下所有的子节点
	 * @param pnode
	 * @return
	 */
	public List<FunctionDto> getChildNodes(FunctionDto pnode) {
		List<FunctionDto> childNodes = new ArrayList<FunctionDto>();
		for (FunctionDto n : nodes) {
			if (pnode.getId().equals(n.getParentId())) {
				childNodes.add(n);
			}
		}
		return childNodes;
	}

	/**
	 * 判断是否为根节点
	 * @param nodes
	 * @return
	 */
	public boolean rootNode(FunctionDto node) {
		boolean isRootNode = true;
		if (node.getParentId() != null) {
			isRootNode = false;
		}
		return isRootNode;
	}

	/**
	 * 获取集合中所有的根节点
	 * @param nodes
	 * @return
	 */
	public List<FunctionDto> getRootNodes() {
		List<FunctionDto> rootNodes = new ArrayList<FunctionDto>();
		for (FunctionDto n : nodes) {
			if (rootNode(n)) {
				rootNodes.add(n);
			}
		}
		return rootNodes;
	}

}
