package com.kevlindev.mcpu.assembler;

/**
 * This class lists terminals used by the
 * grammar specified in "mcpu-assembler.grammar".
 */
public class Terminals {
	static public final short EOF = 0;
	static public final short IDENTIFIER = 1;
	static public final short NOR = 2;
	static public final short ADD = 3;
	static public final short STA = 4;
	static public final short JCC = 5;
	static public final short DB = 6;
	static public final short LABEL = 7;
	static public final short NUMBER = 8;
	static public final short HEX = 9;
}
