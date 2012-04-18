/**
 * 
 */
package com.kevlindev.mcpu.assembler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kevlindev.text.Table;
import com.kevlindev.utils.CollectionUtils;
import com.kevlindev.utils.StringUtils;

/**
 * @author Kevin Lindsey
 * 
 */
public class Program extends BaseNode {
	private List<BaseNode> children;
	private Map<String, BaseNode> symbols;

	/**
	 * Program
	 * 
	 * @param symbols
	 */
	public Program(Map<String, BaseNode> symbols) {
		this.symbols = symbols;
	}

	/**
	 * addChild
	 * 
	 * @param child
	 */
	public void addChild(BaseNode child) {
		if (child != null) {
			if (children == null) {
				children = new ArrayList<BaseNode>();
			}

			children.add(child);
		}
	}

	/**
	 * addChildren
	 * 
	 * @param children
	 */
	public void addChildren(List<BaseNode> children) {
		if (children != null) {
			for (BaseNode child : children) {
				addChild(child);
			}
		}
	}

	public List<Integer> getByteCode(Program program) {
		List<Integer> result = new ArrayList<Integer>();

		initSymbolTable();

		// generate bytecode
		for (BaseNode node : getChildren()) {
			List<Integer> bytes = node.getByteCode(this);

			if (!CollectionUtils.isEmpty(bytes)) {
				result.addAll(bytes);
			}
		}

		return result;
	}

	public int getByteCount() {
		return 0;
	}

	/**
	 * getChildren
	 * 
	 * @return
	 */
	public List<BaseNode> getChildren() {
		return children;
	}

	public String getListing() {
		Table table = new Table();

		for (BaseNode node : children) {
			String address = String.format("%02X", node.getBaseAddress());
			String label = node.getLabel();

			if (!StringUtils.isEmpty(label)) {
				table.addRow(address, label + ":");
			}

			if (node instanceof Instruction) {
				Instruction instruction = (Instruction) node;

				String opcode = instruction.getOpcode().toString();
				String operand = instruction.getAddress();

				List<Integer> byteCodes = instruction.getByteCode(this);
				String byteString = StringUtils.EMPTY;

				if (!CollectionUtils.isEmpty(byteCodes)) {
					List<String> byteStrings = new ArrayList<String>();

					for (int byteCode : byteCodes) {
						byteStrings.add(String.format("%02X", byteCode));
					}

					byteString = "; ".concat(StringUtils.join(" ", byteStrings));
				}

				// add row
				table.addRow(address, StringUtils.EMPTY, opcode, operand, byteString);
			} else if (node instanceof DataByte) {
				DataByte data = (DataByte) node;

				String opcode = ".DB";
				List<Integer> byteCodes = data.getByteCode(this);
				String byteString1 = StringUtils.EMPTY;
				String byteString2 = StringUtils.EMPTY;

				if (!CollectionUtils.isEmpty(byteCodes)) {
					List<String> byteStrings = new ArrayList<String>();

					for (int byteCode : byteCodes) {
						byteStrings.add(String.format("%02X", byteCode));
					}

					byteString1 = StringUtils.join(" ", byteStrings);
					byteString2 = "; ".concat(byteString1);
				}

				// add row
				table.addRow(address, StringUtils.EMPTY, opcode, byteString1, byteString2);
			}
		}

		return table.toString();
	}

	/**
	 * getSymbolAddress
	 * 
	 * @param symbol
	 * @return
	 */
	public int getSymbolAddress(String symbol) {
		int result = 0;

		if (symbols != null && symbols.containsKey(symbol)) {
			BaseNode node = symbols.get(symbol);

			result = node.getBaseAddress();
		}

		return result;
	}

	/**
	 * getSymbolListing
	 * 
	 * @return
	 */
	public String getSymbolListing() {
		StringBuilder buffer = new StringBuilder();

		for (Map.Entry<String, BaseNode> entry : symbols.entrySet()) {
			String label = entry.getKey();
			BaseNode node = entry.getValue();
			int offset = node.getBaseAddress();

			buffer.append(label).append(" = ").append(offset).append(StringUtils.EOL);
		}

		return buffer.toString();
	}

	/**
	 * getSymbols
	 * 
	 * @return
	 */
	public Set<String> getSymbols() {
		return symbols.keySet();
	}

	private void initSymbolTable() {
		// assign addresses
		int address = 0;

		for (BaseNode node : getChildren()) {
			node.setBaseAddress(address);
			address += node.getByteCount();
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (BaseNode child : children) {
			builder.append(child.toString()).append(StringUtils.EOL);
		}

		return builder.toString();
	}
}
