package com.kevlindev.tvm.assembler;

/**
 * This class lists terminals used by the
 * grammar specified in "tvm-assembler.grammar".
 */
public class Terminals {
	static public final short EOF = 0;
	static public final short REGISTER = 1;
	static public final short ADDRESS = 2;
	static public final short IDENTIFIER = 3;
	static public final short PLUS = 4;
	static public final short LBRACKET = 5;
	static public final short SET = 6;
	static public final short INC = 7;
	static public final short BNE = 8;
	static public final short BRK = 9;
	static public final short RBRACKET = 10;
	static public final short LABEL = 11;
	static public final short COMMA = 12;
	static public final short NUMBER = 13;
}
