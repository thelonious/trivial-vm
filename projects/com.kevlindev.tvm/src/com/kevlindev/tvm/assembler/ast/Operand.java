package com.kevlindev.tvm.assembler.ast;

public class Operand {
	public final OperandType type;
	public final Object value;

	public Operand(OperandType type, Object value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
