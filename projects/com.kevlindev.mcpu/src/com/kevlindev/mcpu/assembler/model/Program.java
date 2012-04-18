/**
 * 
 */
package com.kevlindev.mcpu.assembler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kevlindev.utils.CollectionUtils;
import com.kevlindev.utils.StringUtils;

/**
 * @author Kevin Lindsey
 * 
 */
public class Program extends BaseNode {
	private List<BaseNode> children;
	private Map<String, BaseNode> symbols;

	public Program(Map<String, BaseNode> symbols) {
		this.symbols = symbols;
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

	public List<Integer> getByteCode(Program program) {
		List<Integer> result = new ArrayList<Integer>();

		// assign addresses
		int address = 0;

		for (BaseNode node : getChildren()) {
			node.setBaseAddress(address);
			address += node.getByteCount();
		}

		// generate bytecode
		for (BaseNode node : getChildren()) {
			List<Integer> bytes = node.getByteCode(this);

			if (!CollectionUtils.isEmpty(bytes)) {
				result.addAll(bytes);
			}
		}

		return result;
	}

	public List<BaseNode> getChildren() {
		return children;
	}

	public int getByteCount() {
		return 0;
	}

	public int getSymbolAddress(String symbol) {
		int result = 0;

		if (symbols != null && symbols.containsKey(symbol)) {
			BaseNode node = symbols.get(symbol);

			result = node.getBaseAddress();
		}

		return result;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (BaseNode child : children) {
			builder.append(child.toString()).append(StringUtils.EOL);
		}

		return builder.toString();
	}
}
