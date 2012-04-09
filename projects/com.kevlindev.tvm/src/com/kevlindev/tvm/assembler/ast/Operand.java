package com.kevlindev.tvm.assembler.ast;

import beaver.Symbol;

public class Operand extends Symbol {
	public final OperandType type;
	public final Object value1;
	public final Object value2;

	public Operand(OperandType type, Object value) {
		this(type, value, null);
	}

	public Operand(OperandType type, Object value1, Object value2) {
		this.type = type;
		this.value1 = value1;
		this.value2 = value2;
	}

	@Override
	public String toString() {
		return value1.toString();
	}
}
