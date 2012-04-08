package com.kevlindev.tvm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import beaver.Parser.Exception;

import com.kevlindev.tvm.ast.Instruction;
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
		for (Instruction instruction : instructions) {
			String label = instruction.getLabel();

			if (label != null && !label.isEmpty()) {
				System.out.println(label);
			}
			
			String opcode = instruction.opcode.toString();

			String line = "\t" + opcode;
			
			if (instruction.operand1 != null) {
				line += "\t" + instruction.operand1.toString();
			}
			
			if (instruction.operand2 != null) {
				line += ", " + instruction.operand2.toString();
			}
			
			System.out.println(line);
		}
	}
}
