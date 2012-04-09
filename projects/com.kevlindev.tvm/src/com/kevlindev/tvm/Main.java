package com.kevlindev.tvm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import beaver.Parser.Exception;

import com.kevlindev.text.Table;
import com.kevlindev.tvm.assembler.TVMLexer;
import com.kevlindev.tvm.assembler.TVMParser;
import com.kevlindev.tvm.assembler.ast.Instruction;
import com.kevlindev.tvm.assembler.ast.Operand;
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
			for (String file : args) {
				System.out.println("Processing " + file);

				String source = IOUtils.getString(new FileInputStream(file));

				TVMParser parser = new TVMParser();
				TVMLexer lexer = new TVMLexer();

				lexer.setSource(source);
				Object result = parser.parse(lexer);

				printResult((List<Instruction>) result);
			}
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
			String opcode = instruction.getOpcode().toString();

			// get operands
			String operands = "";
			String operand1 = getOperand(instruction, 0);
			String operand2 = getOperand(instruction, 1);

			if (operand1 != null) {
				operands = operand1;

				if (operand2 != null) {
					operands += ", " + operand2;
				}
			}

			// get bytes
			List<Short> byteCode = instruction.getByteCode();
			String byte1 = "; " + getByte(byteCode, 0);
			String byte2 = getByte(byteCode, 1);
			String byte3 = getByte(byteCode, 2);

			// add row
			table.addRow(" ", opcode, operands, byte1, byte2, byte3);
		}

		// table.setWidth(80, 20);

		System.out.println(table.toString());
	}

	private static String getOperand(Instruction instruction, int index) {
		Operand operand = instruction.getOperand(index);
		String result = null;

		if (operand != null) {
			result = operand.toString();
		}

		return result;
	}

	private static String getByte(List<Short> byteCode, int index) {
		String result = " ";

		if (byteCode != null && 0 <= index && index < byteCode.size()) {
			result = String.format("%04X", byteCode.get(index));
		}

		return result;
	}
}
