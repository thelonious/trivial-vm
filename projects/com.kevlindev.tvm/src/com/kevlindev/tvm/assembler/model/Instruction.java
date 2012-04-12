package com.kevlindev.tvm.assembler.model;

import java.util.ArrayList;
import java.util.List;

import com.kevlindev.tvm.Opcode;
import com.kevlindev.utils.ArrayUtils;
import com.kevlindev.utils.CollectionUtils;

public class Instruction extends BaseNode {
	private Opcode opcode;
	private List<Operand> operands;
	private String label;
	private int address;
	private List<Short> byteCode;

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

	public int getAddress() {
		return address;
	}

	/**
	 * getByteCode
	 * 
	 * @return
	 */
	public List<Short> getByteCode(Program program) {
		if (byteCode == null) {
			List<Short> result = new ArrayList<Short>();

			// get opcode
			int word1 = opcode.getOpcode();

			// get operand codes
			int opand1 = getOpand(0);
			int opand2 = getOpand(1);

			// munge in operand types
			word1 = ((opand1 & 0x0F) << 12) | ((opand2 & 0x0F) << 8) | (word1 & 0xFF);

			// emit word1
			result.add((short) word1);

			// emit register code word
			int regCode1 = getRegisterCode(0);
			int regCode2 = getRegisterCode(1);

			if (regCode1 != -1 || regCode2 != -1) {
				if (regCode1 == -1) {
					regCode1 = 0;
				}
				if (regCode2 == -1) {
					regCode2 = 0;
				}

				int regCodeWord = ((regCode1 & 0xFF) << 8) | (regCode2 & 0xFF);

				result.add((short) regCodeWord);
			}

			// emit operand words
			int opandData1 = getData(program, 0);
			int opandData2 = getData(program, 1);

			if (opandData1 != -1) {
				result.add((short) opandData1);
			}
			if (opandData2 != -1) {
				result.add((short) opandData2);
			}

			byteCode = result;
		}

		return byteCode;
	}

	/**
	 * getByteCodeCount
	 * 
	 * @return
	 */
	public int getByteCodeCount() {
		int count = 1; // opcode and operand types

		int regCode1 = getRegisterCode(0);
		int regCode2 = getRegisterCode(1);

		if (regCode1 != -1 || regCode2 != -1) {
			count++; // add register code word
		}

		// add in words for operand data
		count += getDataCount(0);
		count += getDataCount(1);

		return count;
	}

	/**
	 * getData
	 * 
	 * @param index
	 * @return
	 */
	private int getData(Program program, int index) {
		Operand operand = getOperand(index);
		int result = -1;

		if (operand != null) {
			BaseNode value = operand.value1;

			if (value instanceof Number) {
				result = ((Number) value).getNumber();
			} else if (value instanceof Address) {
				result = ((Address) value).getAddress();
			} else if (value instanceof Identifier) {
				result = program.getSymbolAddress(((Identifier) value).getIdentifier());
			}
		}

		return result;
	}

	private int getDataCount(int index) {
		Operand operand = getOperand(index);
		int result = 0;

		if (operand != null) {
			BaseNode value = operand.value1;

			if (value instanceof Number || value instanceof Address || value instanceof Identifier) {
				result = 1;
			}
		}

		return result;
	}

	/**
	 * getLabel
	 * 
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * getOpand
	 * 
	 * @param index
	 * @return
	 */
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

	/**
	 * getOperand
	 * 
	 * @param index
	 * @return
	 */
	public Operand getOperand(int index) {
		Operand result = null;

		if (!CollectionUtils.isEmpty(operands) && 0 <= index && index < operands.size()) {
			result = operands.get(index);
		}

		return result;
	}

	/**
	 * getRegisterCode
	 * 
	 * @param index
	 * @return
	 */
	private int getRegisterCode(int index) {
		Operand operand = getOperand(index);
		int result = -1;

		if (operand != null) {
			// TODO: handle value2, and value3
			if (operand.value1 instanceof Register) {
				Register register = (Register) operand.value1;

				result = register.getIndex();
			}
		}

		return result;
	}

	/**
	 * setAddress
	 * 
	 * @param address
	 */
	public void setAddress(int address) {
		this.address = address;
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
