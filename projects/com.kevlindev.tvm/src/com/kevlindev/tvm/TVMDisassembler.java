package com.kevlindev.tvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kevlindev.tvm.assembler.model.Opcode;
import com.kevlindev.tvm.assembler.model.OperandType;
import com.kevlindev.utils.StringUtils;

public class TVMDisassembler {
	private List<Short> memory;
	private Map<Short, String> symbols;
	private int programCounter;

	public TVMDisassembler(List<Short> memory, Map<Short, String> symbols) {
		this.memory = memory;
		this.symbols = symbols;
	}

	public void run() {
		while (programCounter < memory.size()) {
			System.out.print(String.format("%04X ", programCounter));

			String symbol = symbols.get((short) programCounter);
			if (!StringUtils.isEmpty(symbol)) {
				System.out.println(":" + symbol);
				System.out.print(String.format("%04X ", programCounter));
			}

			int current = memory.get(programCounter++);
			OperandType opandType1 = OperandType.getOperandType((current & 0xF000) >> 12);
			OperandType opandType2 = OperandType.getOperandType((current & 0x0F00) >> 8);
			Opcode opcode = Opcode.getOpcode(current & 0x00FF);
			
			System.out.print("    " + opcode + " ");
			printOperands(opandType1, opandType2);
		}
	}

	private void printOperands(OperandType op1, OperandType op2) {
		List<String> info = new ArrayList<String>();

		if (op1 == OperandType.REGISTER || op2 == OperandType.REGISTER) {
			int registerCode = memory.get(programCounter++);

			if (op1 == OperandType.REGISTER) {
				int register = ((registerCode) & 0xFF00) >> 8;

				info.add(String.format("r%d", register));
			}

			if (op2 == OperandType.REGISTER) {
				int register = ((registerCode) & 0x00FF) >> 8;

				info.add(String.format("r%d", register));
			}
		}

		addOperand(info, op1);
		addOperand(info, op2);

		System.out.println(StringUtils.join(", ", info));
	}

	private void addOperand(List<String> info, OperandType operand) {
		switch (operand) {
			case LABEL: {
				short offset = memory.get(programCounter++);
				String label = (symbols != null) ? symbols.get(offset) : null;

				if (!StringUtils.isEmpty(label)) {
					info.add(label);
				} else {
					info.add("$" + offset);
				}
				break;
			}

			case NUMBER: {
				int value = memory.get(programCounter++);

				info.add("#" + value);
				break;
			}

			case ADDRESS: {
				int value = memory.get(programCounter++);

				info.add("$" + value);
				break;
			}
		}
	}
}
