package com.kevlindev.template.parser;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TemplateTokenType {
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

	private static final Map<Short, TemplateTokenType> SYMBOL_ID_MAP;
	private static final Map<Short, TemplateTokenType> TREE_SYMBOL_ID_MAP;

	static {
		SYMBOL_ID_MAP = new HashMap<Short, TemplateTokenType>();

		for (TemplateTokenType type : EnumSet.allOf(TemplateTokenType.class)) {
			SYMBOL_ID_MAP.put(type.getIndex(), type);
		}

		TREE_SYMBOL_ID_MAP = new HashMap<Short, TemplateTokenType>();

		for (TemplateTokenType type : EnumSet.allOf(TemplateTokenType.class)) {
			TREE_SYMBOL_ID_MAP.put(type.getTreeIndex(), type);
		}
	}

	public static TemplateTokenType getTreeTokenType(short id) {
		TemplateTokenType result = UNDEFINED;

		if (TREE_SYMBOL_ID_MAP.containsKey(id)) {
			result = TREE_SYMBOL_ID_MAP.get(id);
		}

		return result;
	}

	public static TemplateTokenType getTokenType(short id) {
		TemplateTokenType result = UNDEFINED;

		if (SYMBOL_ID_MAP.containsKey(id)) {
			result = SYMBOL_ID_MAP.get(id);
		}

		return result;
	}

	private short index;
	private short treeIndex;

	private TemplateTokenType(short index) {
		this.index = index;
		this.treeIndex = -1;
	}

	private TemplateTokenType(short index, short treeIndex) {
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
