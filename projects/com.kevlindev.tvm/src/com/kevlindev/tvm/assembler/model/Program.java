package com.kevlindev.tvm.assembler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kevlindev.text.Table;
import com.kevlindev.utils.CollectionUtils;
import com.kevlindev.utils.StringUtils;

public class Program extends BaseNode {
	private List<Instruction> instructions;
	private Map<String, Instruction> symbolInstructions;

	/**
	 * Program
	 * 
	 * @param instructions
	 */
	public Program(List<Instruction> instructions) {
		this.instructions = instructions;

		initSymbolTable();
	}

	/**
	 * getByteCode
	 * 
	 * @return
	 */
	public List<Short> getByteCode() {
		List<Short> byteCode = new ArrayList<Short>();

		if (instructions != null) {
			for (Instruction instruction : instructions) {
				byteCode.addAll(instruction.getByteCode(this));
			}
		}

		return byteCode;
	}

	/**
	 * getInstructions
	 * 
	 * @return
	 */
	public List<Instruction> getInstructions() {
		return instructions;
	}

	/**
	 * getListing
	 * 
	 * @return
	 */
	public String getListing() {
		Table table = new Table();

		for (Instruction instruction : getInstructions()) {
			String address = String.format("%04X", instruction.getAddress());
			String label = instruction.getLabel();

			if (!StringUtils.isEmpty(label)) {
				table.addRow(address, ":" + label);
			}

			// get opcode
			String opcode = instruction.getOpcode().toString();

			// get operands
			String operands = StringUtils.EMPTY;
			Operand operand1 = instruction.getOperand(0);
			Operand operand2 = instruction.getOperand(1);

			if (operand1 != null) {
				operands = operand1.toString();

				if (operand2 != null) {
					operands += ", " + operand2;
				}
			}

			// get bytes
			List<Short> byteCodes = instruction.getByteCode(this);
			String byteString = StringUtils.EMPTY;

			if (!CollectionUtils.isEmpty(byteCodes)) {
				List<String> byteStrings = new ArrayList<String>();

				for (Short byteCode : byteCodes) {
					byteStrings.add(String.format("%04X", byteCode));
				}

				byteString = "; ".concat(StringUtils.join(" ", byteStrings));
			}

			// add row
			table.addRow(address, StringUtils.EMPTY, opcode, operands, byteString);
		}

		table.setAlignment(4, Table.Alignment.COLLAPSE);
		// table.setWidth(80, 20);

		return table.toString();
	}

	/**
	 * getSymbols
	 * 
	 * @return
	 */
	public Set<String> getSymbols() {
		return symbolInstructions.keySet();
	}

	/**
	 * getSymbolAddress
	 * 
	 * @param symbol
	 * @return
	 */
	public int getSymbolAddress(String symbol) {
		Instruction instruction = symbolInstructions.get(symbol);

		return (instruction != null) ? instruction.getAddress() : 0;
	}

	/**
	 * getSymbolListing
	 * 
	 * @return
	 */
	public String getSymbolListing() {
		StringBuilder buffer = new StringBuilder();

		for (Map.Entry<String, Instruction> entry : symbolInstructions.entrySet()) {
			String label = entry.getKey();
			Instruction instruction = entry.getValue();
			int offset = instruction.getAddress();

			buffer.append(label).append(" = ").append(offset).append(StringUtils.EOL);
		}

		return buffer.toString();
	}

	/**
	 * initSymbolTable
	 */
	private void initSymbolTable() {
		// Calculate instruction address and build symbol cross reference along the way
		if (this.instructions != null) {
			symbolInstructions = new HashMap<String, Instruction>();

			// TODO: origin should be set by instructions as well
			int address = 0;

			for (Instruction instruction : this.instructions) {
				// save reference to instruction if it has a symbol attached to it
				String label = instruction.getLabel();

				if (!StringUtils.isEmpty(label)) {
					symbolInstructions.put(label, instruction);
				}

				// apply current address
				instruction.setAddress(address);

				// advance address by current instruction's byte count
				address += instruction.getByteCodeCount();
			}
		} else {
			symbolInstructions = Collections.emptyMap();
		}
	}
}
