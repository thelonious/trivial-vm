/**
 * 
 */
package com.kevlindev.mcpu.assembler.model;

import java.util.List;

import com.kevlindev.mcpu.Opcode;
import com.kevlindev.utils.CollectionUtils;

/**
 * @author Kevin Lindsey
 * 
 */
public class Instruction extends BaseNode {
	private Opcode opcode;
	private String address;

	public Instruction(Opcode opcode, String address) {
		this.opcode = opcode;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public List<Integer> getByteCode(Program program) {
		int addr = program.getSymbolAddress(address);
		int b = ((opcode.getOpcode() << 6) | addr);

		return CollectionUtils.createList(b);
	}

	public Opcode getOpcode() {
		return opcode;
	}

	@Override
	public int getByteCount() {
		return 1;
	}
}
