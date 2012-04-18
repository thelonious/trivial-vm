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
	public List<Integer> getByteCode() {
		int b = ((opcode.getOpcode() << 6) | 0);
		
		return CollectionUtils.createList(b);
	}

	public Opcode getOpcode() {
		return opcode;
	}

	@Override
	public int getSize() {
		return 1;
	}
}
