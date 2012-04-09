package com.kevlindev.tvm.assembler.ast;

public enum OperandType {
	// @formatter:off
	REGISTER,
	NUMBER,
	LABEL,
	ADDRESS,
	ADDRESS_INDEXED,
	ADDRESS_INDIRECT,
	ADDRESS_INDIRECT_PRE_INDEXED,
	ADDRESS_INDIRECT_POST_INDEXED;
	// @formatter:on
}
