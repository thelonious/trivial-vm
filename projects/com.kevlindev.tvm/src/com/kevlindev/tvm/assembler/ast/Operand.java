package com.kevlindev.tvm.assembler.ast;

public class Operand extends BaseNode {
	public final OperandType type;
	public final Object value1;
	public final Object value2;
	public final Object value3;

	public Operand(OperandType type, Object value) {
		this(type, value, null, null);
	}

	public Operand(OperandType type, Object value1, Object value2) {
		this(type, value1, value2, null);
	}

	public Operand(OperandType type, Object value1, Object value2, Object value3) {
		this.type = type;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		switch (type) {
			case REGISTER:
			case ADDRESS:
			case NUMBER:
			case LABEL:
				buffer.append(value1.toString());
				break;

			case ADDRESS_INDEXED:
				buffer.append(value1).append("+").append(value2);
				break;

			case ADDRESS_INDIRECT:
				buffer.append("[").append(value1).append("]");
				break;

			case ADDRESS_INDIRECT_PRE_INDEXED:
				buffer.append("[").append(value1).append("+").append(value2).append("]");
				break;

			case ADDRESS_INDIRECT_POST_INDEXED:
				buffer.append("[").append(value1).append("]").append("+").append(value2);
				break;

			case ADDRESS_INDIRECT_PRE_AND_POST_INDEXED:
				buffer.append("[").append(value1).append("+").append(value2).append("]+").append(value3);
				break;
		}

		return buffer.toString();
	}
}
