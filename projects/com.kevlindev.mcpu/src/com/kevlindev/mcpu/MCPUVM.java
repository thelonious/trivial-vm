/**
 * 
 */
package com.kevlindev.mcpu;

/**
 * @author Kevin Lindsey
 * 
 */
public class MCPUVM {
	private int[] memory = new int[64];
	private int pc = 0;
	private int a = 0;
	private boolean carry = false;

	public MCPUVM() {
	}

	public int[] getMemory() {
		return memory;
	}

	public void run() {
		int lastPC;

		do {
			lastPC = pc;

			int b = memory[pc];
			int opcodeValue = (b & 0xC0) >> 6;
			int address = b & 0x3F;
			Opcode opcode = Opcode.getOpcode(opcodeValue);

			/*
			 * String debugString = String.format("%04X: %3s %02X", pc, opcode, address);
			 * System.out.println(debugString);
			 */

			switch (opcode) {
				case NOR:
					a = (~(a | memory[address]) & 0xFF);
					pc++;
					break;

				case ADD:
					a += memory[address];
					if (a > 255) {
						carry = true;
						a = a & 0xFF;
					} else {
						carry = false;
					}
					pc++;
					break;

				case STA:
					memory[address] = a;
					pc++;
					break;

				case JCC:
					if (!carry) {
						pc = address;
					} else {
						carry = false;
						pc++;
					}
					break;

				default:
					throw new IllegalArgumentException();
			}

			while (pc >= 64) {
				pc -= 64;
			}
		} while (lastPC != pc);
	}
}
