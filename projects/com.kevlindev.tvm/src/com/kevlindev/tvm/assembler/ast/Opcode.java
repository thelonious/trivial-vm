package com.kevlindev.tvm.assembler.ast;

public enum Opcode {
	// @formatter:off
	BRK,
	SET,
	INC,
	JNE;
	// @formatter:on

	public int getOpcode() {
		return this.ordinal();
	}
}
