package com.kevlindev.tvm.assembler.ast;

import java.util.ArrayList;
import java.util.List;

import com.kevlindev.utils.ArrayUtils;
import com.kevlindev.utils.CollectionUtils;

public class Instruction extends BaseNode {
	private Opcode opcode;
	private List<Operand> operands;
	private String label;

	/**
	 * Instruction
	 * 
	 * @param opcode
	 * @param operands
	 */
	public Instruction(Opcode opcode, Operand... operands) {
		this.opcode = opcode;

		if (!ArrayUtils.isEmpty(operands)) {
			this.operands = CollectionUtils.createList(operands);
		}
	}

	/**
	 * getLabel
	 * 
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	private int getOpand(int index) {
		Operand operand = getOperand(index);

		return (operand != null) ? operand.type.getIndex() : 0;
	}

	/**
	 * getOpcode
	 * 
	 * @return
	 */
	public Opcode getOpcode() {
		return opcode;
	}

	public Operand getOperand(int index) {
		Operand result = null;

		if (!CollectionUtils.isEmpty(operands) && 0 <= index && index < operands.size()) {
			result = operands.get(index);
		}

		return result;
	}

	/**
	 * getByteCode
	 * 
	 * @return
	 */
	public List<Short> getByteCode() {
		List<Short> result = new ArrayList<Short>();

		// get opcode
		int word1 = opcode.getOpcode();

		// get operand codes
		int opand1 = getOpand(0);
		int opand2 = getOpand(1);

		// munge in operand types
		word1 = ((opand1 & 0x0F) << 12) | ((opand2 & 0x0F) << 8) | ((word1 & 0xFF));

		// emit word1
		result.add((short) word1);

		// emit operand words
		if (opand1 != 0) {
			result.add((short) 0); // TODO: emit actual value

			if (opand2 != 0) {
				result.add((short) 0); // TODO: emit actual value
			}
		}

		return result;
	}

	/**
	 * setLabel
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
