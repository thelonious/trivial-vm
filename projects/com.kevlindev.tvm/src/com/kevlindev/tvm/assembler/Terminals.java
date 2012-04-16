package com.kevlindev.tvm.assembler;

/**
 * This class lists terminals used by the
 * grammar specified in "tvm-assembler.grammar".
 */
public class Terminals {
	static public final short EOF = 0;
	static public final short CLR = 1;
	static public final short INC = 2;
	static public final short BEQ = 3;
	static public final short BNE = 4;
	static public final short BRK = 5;
	static public final short REGISTER = 6;
	static public final short LABEL = 7;
	static public final short ADDRESS = 8;
	static public final short IDENTIFIER = 9;
}
