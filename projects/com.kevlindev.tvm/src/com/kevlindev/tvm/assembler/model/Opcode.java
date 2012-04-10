package com.kevlindev.tvm.assembler.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Opcode {
	// @formatter:off
	BRK,
	SET,
	INC,
	JEQ,
	JNE;
	// @formatter:on

	private static final Map<Integer, Opcode> opcodeMap;
	static {
		opcodeMap = new HashMap<Integer, Opcode>();

		for (Opcode opcode : EnumSet.allOf(Opcode.class)) {
			opcodeMap.put(opcode.getOpcode(), opcode);
		}
	}

	public static Opcode getOpcode(int value) {
		Opcode result = BRK;

		if (opcodeMap.containsKey(value)) {
			result = opcodeMap.get(value);
		}

		return result;
	}

	public int getOpcode() {
		return this.ordinal();
	}
}
