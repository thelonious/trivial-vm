package com.kevlindev.tvm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import beaver.Parser.Exception;

import com.kevlindev.text.Table;
import com.kevlindev.tvm.assembler.TVMLexer;
import com.kevlindev.tvm.assembler.TVMParser;
import com.kevlindev.tvm.assembler.ast.Instruction;
import com.kevlindev.utils.IOUtils;

public class Main {
	/**
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, Exception {
		if (args != null && args.length > 0) {
			String source = IOUtils.getString(new FileInputStream(args[0]));

			TVMParser parser = new TVMParser();
			TVMLexer lexer = new TVMLexer();

			lexer.setSource(source);
			Object result = parser.parse(lexer);

			printResult((List<Instruction>) result);
		} else {
			System.out.println("usage: tvm-assembler <tvm-file>");
		}
	}

	private static void printResult(List<Instruction> instructions) {
		Table table = new Table();

		for (Instruction instruction : instructions) {
			String label = instruction.getLabel();

			if (label != null && !label.isEmpty()) {
				table.addRow(label);
			}

			// get opcode
			String opcode = instruction.opcode.toString();

			// get operands
			String operands = "";

			if (instruction.operand1 != null) {
				operands = instruction.operand1.toString();
			}
			if (instruction.operand2 != null) {
				operands += ", " + instruction.operand2.toString();
			}

			// get bytes
			String byte1 = String.format("; %04X", instruction.opcode.ordinal());
			String byte2 = " ";
			String byte3 = " ";

			// add row
			table.addRow("", opcode, operands, byte1, byte2, byte3);
		}

		//table.setWidth(80, 20);

		System.out.println(table.toString());
	}
}
