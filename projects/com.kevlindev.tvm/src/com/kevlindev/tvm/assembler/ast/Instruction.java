package com.kevlindev.tvm.assembler.ast;

import java.util.ArrayList;
import java.util.List;

import beaver.Symbol;

public class Instruction extends Symbol {
	public final Opcode opcode;
	public final Operand operand1;
	public final Operand operand2;
	private String label;

	public Instruction(Opcode opcode) {
		this(opcode, null, null);
	}

	public Instruction(Opcode opcode, Operand operand) {
		this(opcode, operand, null);
	}

	public Instruction(Opcode opcode, Operand operand1, Operand operand2) {
		this.opcode = opcode;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Byte> getBytes() {
		List<Byte> result = new ArrayList<Byte>();

		result.add((byte) opcode.getOpcode());

		return result;
	}
}
