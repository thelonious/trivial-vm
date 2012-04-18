/**
 * 
 */
package com.kevlindev.mcpu.assembler.model;

import java.util.ArrayList;
import java.util.List;

import com.kevlindev.utils.CollectionUtils;
import com.kevlindev.utils.StringUtils;

/**
 * @author Kevin Lindsey
 * 
 */
public class Program extends BaseNode {
	private List<BaseNode> children;

	public Program() {
	}

	public void addChild(BaseNode child) {
		if (child != null) {
			if (children == null) {
				children = new ArrayList<BaseNode>();
			}

			children.add(child);
		}
	}

	public void addChildren(List<BaseNode> children) {
		if (children != null) {
			for (BaseNode child : children) {
				addChild(child);
			}
		}
	}

	public List<Integer> getByteCode() {
		List<Integer> result = new ArrayList<Integer>();

		// assign label offset

		// generate bytecode
		for (BaseNode node : getChildren()) {
			List<Integer> bytes = node.getByteCode();

			if (!CollectionUtils.isEmpty(bytes)) {
				result.addAll(bytes);
			}
		}

		return result;
	}

	public List<BaseNode> getChildren() {
		return children;
	}

	public int getSize() {
		return 0;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (BaseNode child : children) {
			builder.append(child.toString()).append(StringUtils.EOL);
		}

		return builder.toString();
	}
}
