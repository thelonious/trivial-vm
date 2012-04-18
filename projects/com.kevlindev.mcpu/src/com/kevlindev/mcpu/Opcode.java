/**
 * 
 */
package com.kevlindev.mcpu;

/**
 * @author Kevin Lindsey
 * 
 */
public enum Opcode {
	// @formatter:off
	NOR(0),
	ADD(1),
	STA(2),
	JCC(3);
	// @formatter:on

	private int opcode;

	private Opcode(int opcode) {
		this.opcode = opcode;
	}

	public static Opcode getOpcode(int value) {
		switch (value) {
			case 0:
				return NOR;
			case 1:
				return ADD;
			case 2:
				return STA;
			case 3:
				return JCC;
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getOpcode() {
		return opcode;
	}
}
