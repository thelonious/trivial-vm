package com.kevlindev.tvm.assembler.model;

public class Operand extends BaseNode {
	public final OperandType type;
	public final BaseNode value1;
	public final BaseNode value2;
	public final BaseNode value3;

	public Operand(OperandType type, BaseNode value) {
		this(type, value, null, null);
	}

	public Operand(OperandType type, BaseNode value1, BaseNode value2) {
		this(type, value1, value2, null);
	}

	public Operand(OperandType type, BaseNode value1, BaseNode value2, BaseNode value3) {
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
			case REGISTER_INDIRECT:
				buffer.append("[").append(value1).append("]");
				break;

			case ADDRESS_INDIRECT_PRE_INDEXED:
			case REGISTER_INDIRECT_PRE_INDEXED:
				buffer.append("[").append(value1).append("+").append(value2).append("]");
				break;

			case ADDRESS_INDIRECT_POST_INDEXED:
			case REGISTER_INDIRECT_POST_INDEXED:
				buffer.append("[").append(value1).append("]").append("+").append(value2);
				break;

			case ADDRESS_INDIRECT_PRE_AND_POST_INDEXED:
			case REGISTER_INDIRECT_PRE_AND_POST_INDEXED:
				buffer.append("[").append(value1).append("+").append(value2).append("]+").append(value3);
				break;
		}

		return buffer.toString();
	}
}
