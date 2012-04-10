package com.kevlindev.tvm.assembler;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TVMTokenType {
	// @formatter:off
	ERROR((short) -2),
	UNDEFINED((short) -1),
	EOF(Terminals.EOF),
	
	LBRACKET(Terminals.LBRACKET),
	RBRACKET(Terminals.RBRACKET),
	PLUS(Terminals.PLUS),
	COMMA(Terminals.COMMA),
	
	ADDRESS(Terminals.ADDRESS),
	REGISTER(Terminals.REGISTER),
	LABEL(Terminals.LABEL),
	NUMBER(Terminals.NUMBER),
	IDENTIFIER(Terminals.IDENTIFIER),
	
	BRK(Terminals.BRK),
	SET(Terminals.SET),
	INC(Terminals.INC),
	JNE(Terminals.JNE);
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
