package com.kevlindev.mcpu.disassembler;

import java.util.List;
import java.util.Map;

import com.kevlindev.mcpu.Opcode;
import com.kevlindev.utils.StringUtils;

public class MCPUDisassembler {
	private List<Integer> memory;
	private Map<Integer, String> symbols;

	public MCPUDisassembler(List<Integer> memory, Map<Integer, String> symbols) {
		this.memory = memory;
		this.symbols = symbols;
	}

	public void run() {
		int programCounter = 0;

		while (programCounter < memory.size()) {
			System.out.print(String.format("%02X ", programCounter));

			String symbol = symbols.get(programCounter);
			if (!StringUtils.isEmpty(symbol)) {
				System.out.println(symbol + ":");
				System.out.print(String.format("%02X ", programCounter));
			}

			int current = memory.get(programCounter++);
			Opcode opcode = Opcode.getOpcode((current & 0xC0) >> 6);
			int address = current & 0x3F;
			String addressString = (symbols.containsKey(address)) ? symbols.get(address) : String.format("%02X", address);

			System.out.println("    " + opcode + " " + addressString);
		}
	}
}
