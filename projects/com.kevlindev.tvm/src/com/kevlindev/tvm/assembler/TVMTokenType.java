package com.kevlindev.tvm.assembler;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TVMTokenType {
	// @formatter:off
	ERROR((short) -2),
	UNDEFINED((short) -1),
	EOF(Terminals.EOF),
	COMMA(Terminals.COMMA),
	BRK(Terminals.BRK),
	LOAD(Terminals.LOAD),
	INC(Terminals.INC),
	BNE(Terminals.BNE),
	REGISTER(Terminals.REGISTER),
	NUMBER(Terminals.NUMBER),
	LABEL(Terminals.LABEL),
	IDENTIFIER(Terminals.IDENTIFIER);
	// @formatter:on

	private static final Map<Short, TVMTokenType> SYMBOL_ID_MAP;
	static {
		SYMBOL_ID_MAP = new HashMap<Short, TVMTokenType>();

		for (TVMTokenType type : EnumSet.allOf(TVMTokenType.class)) {
			SYMBOL_ID_MAP.put(type.getIndex(), type);
		}
	}

	private short index;

	private TVMTokenType(short index) {
		this.index = index;
	}

	public short getIndex() {
		return index;
	}
}
