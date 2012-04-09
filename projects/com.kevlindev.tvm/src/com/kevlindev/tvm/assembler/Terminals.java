package com.kevlindev.tvm.assembler;

/**
 * This class lists terminals used by the
 * grammar specified in "tvm-assembler.grammar".
 */
public class Terminals {
	static public final short EOF = 0;
	static public final short REGISTER = 1;
	static public final short ADDRESS = 2;
	static public final short LBRACKET = 3;
	static public final short PLUS = 4;
	static public final short LOAD = 5;
	static public final short INC = 6;
	static public final short BNE = 7;
	static public final short BRK = 8;
	static public final short RBRACKET = 9;
	static public final short LABEL = 10;
	static public final short COMMA = 11;
	static public final short NUMBER = 12;
	static public final short IDENTIFIER = 13;
}
