package com.kevlindev.tvm.assembler.ast;

public enum Opcode {
	// @formatter:off
	BRK(0),
	LOAD(2),
	INC(1),
	BNE(1);
	// @formatter:on

	private int operandCount;

	private Opcode(int operandCount) {
		this.operandCount = operandCount;
	}

	public int getOpcode() {
		return this.ordinal();
	}

	public int getOperandCount() {
		return operandCount;
	}
}
