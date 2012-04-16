package com.kevlindev.tokens;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TokenType {
	// @formatter:off
	// token types
	EQUAL(Terminals.EQUAL),
	LBRACKET(Terminals.LBRACKET),
	RBRACKET(Terminals.RBRACKET),
	EOF(Terminals.EOF, TreeTerminals.EOF),
	
	// token and node types
	PACKAGE(Terminals.PACKAGE, TreeTerminals.PACKAGE),
	LANGUAGE(Terminals.LANGUAGE, TreeTerminals.LANGUAGE),
	KEYWORDS(Terminals.KEYWORDS, TreeTerminals.KEYWORDS),
	OPERATORS(Terminals.OPERATORS, TreeTerminals.OPERATORS),
	STRING(Terminals.STRING, TreeTerminals.STRING),
	IDENTIFIER(Terminals.IDENTIFIER, TreeTerminals.IDENTIFIER),
	
	// custom node types
	ERROR((short) -2),
	UNDEFINED((short) -1),
	ROOT((short) 4094, TreeTerminals.ROOT),
	DOWN((short) 4095, TreeTerminals.DOWN),
	UP((short) 4096, TreeTerminals.UP);
	// @formatter:on

	private static final Map<Short, TokenType> SYMBOL_ID_MAP;
	private static final Map<Short, TokenType> TREE_SYMBOL_ID_MAP;

	static {
		SYMBOL_ID_MAP = new HashMap<Short, TokenType>();

		for (TokenType type : EnumSet.allOf(TokenType.class)) {
			SYMBOL_ID_MAP.put(type.getIndex(), type);
		}

		TREE_SYMBOL_ID_MAP = new HashMap<Short, TokenType>();

		for (TokenType type : EnumSet.allOf(TokenType.class)) {
			TREE_SYMBOL_ID_MAP.put(type.getTreeIndex(), type);
		}
	}

	public static TokenType getTreeTokenType(short id) {
		TokenType result = UNDEFINED;

		if (TREE_SYMBOL_ID_MAP.containsKey(id)) {
			result = TREE_SYMBOL_ID_MAP.get(id);
		}

		return result;
	}

	public static TokenType getTokenType(short id) {
		TokenType result = UNDEFINED;

		if (SYMBOL_ID_MAP.containsKey(id)) {
			result = SYMBOL_ID_MAP.get(id);
		}

		return result;
	}

	private short index;
	private short treeIndex;

	private TokenType(short index) {
		this.index = index;
		this.treeIndex = -1;
	}

	private TokenType(short index, short treeIndex) {
		this.index = index;
		this.treeIndex = treeIndex;
	}

	public short getIndex() {
		return index;
	}

	public short getTreeIndex() {
		return treeIndex;
	}
}
