/**
 * 
 */
package com.kevlindev.mcpu.assembler;

/**
 * @author Kevin Lindsey
 * 
 */
public enum MCPUTokenType {
	// @formatter:off
	ERROR((short) -1),
	EOF((short) 0),
	
	NUMBER(Terminals.NUMBER),
	HEX(Terminals.HEX),
	
	DB(Terminals.DB),
	
	NOR(Terminals.NOR),
	ADD(Terminals.ADD),
	STA(Terminals.STA),
	JCC(Terminals.JCC),
	
	IDENTIFIER(Terminals.IDENTIFIER),
	LABEL(Terminals.LABEL);
	// @formatter:on

	private short id;

	private MCPUTokenType(short id) {
		this.id = id;
	}

	public short getIndex() {
		return id;
	}
}
