package com.kevlindev.tvm.assembler.ast;

public enum OperandType {
	// @formatter:off
	NONE(0),
	
	ADDRESS(1),
	ADDRESS_INDEXED(2),
	ADDRESS_INDIRECT(3),
	ADDRESS_INDIRECT_PRE_INDEXED(4),
	ADDRESS_INDIRECT_POST_INDEXED(5),
	ADDRESS_INDIRECT_PRE_AND_POST_INDEXED(6),
	
	NUMBER(7),
	LABEL(8),
	
	REGISTER(9),
	// REGISTER_INDEXED(10), // doesn't make sense as an addressing mode
	REGISTER_INDIRECT(11),
	REGISTER_INDIRECT_PRE_INDEXED(12),
	REGISTER_INDIRECT_POST_INDEXED(13),
	REGISTER_INDIRECT_PRE_AND_POST_INDEXED(14);
	
	// @formatter:on

	private int index;

	private OperandType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
