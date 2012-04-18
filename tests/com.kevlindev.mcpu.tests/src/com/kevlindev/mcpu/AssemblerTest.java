/**
 * 
 */
package com.kevlindev.mcpu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.kevlindev.mcpu.assembler.MCPUParser;
import com.kevlindev.mcpu.assembler.model.Program;
import com.kevlindev.mcpu.io.MCPUFileWriter;
import com.kevlindev.utils.ArrayUtils;
import com.kevlindev.utils.FileUtils;
import com.kevlindev.utils.IOUtils;

/**
 * @author Kevin Lindsey
 */
public class AssemblerTest {
	public AssemblerTest() {
	}

	public Program assemble(String file) throws FileNotFoundException {
		System.out.println();
		System.out.println("Processing " + file);
		System.out.println();

		// grab source
		String source = IOUtils.getString(new FileInputStream(file));

		System.out.println("Source");
		System.out.println("======");
		System.out.println(source);

		return new MCPUParser().parse(source);
	}

	public void showListing(Program program) {
		if (program != null) {
			// print listing from AST
			System.out.println("Listing");
			System.out.println("=======");
			System.out.println(program.getListing());
		}
	}

	public void showSymbolListing(Program program) {
		if (program != null) {
			// print symbols
			System.out.println("Symbols");
			System.out.println("=======");
			System.out.println(program.getSymbolListing());
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (!ArrayUtils.isEmpty(args)) {
			AssemblerTest tester = new AssemblerTest();

			for (String file : args) {
				Program program = tester.assemble(file);

				if (program != null) {
					// get bytecode
					// FIXME: need to get byte code before showing lists
					List<Integer> bytes = program.getByteCode(null);
					
					tester.showListing(program);
					tester.showSymbolListing(program);
					
					// write bin file
					File binFile = new File(FileUtils.setExtension(file, FileUtils.BIN_EXTENSION));
					MCPUFileWriter.writeBinFile(program, binFile);

					// write sym file
					File symFile = new File(FileUtils.setExtension(file, FileUtils.SYM_EXTENSION));
					MCPUFileWriter.writeSymFile(program, symFile);

					// write lst file
					File lstFile = new File(FileUtils.setExtension(file, FileUtils.LST_EXTENSION));
					MCPUFileWriter.writeListing(program, lstFile);

					// transfer to VM's memory
					MCPUVM vm = new MCPUVM();
					int[] memory = vm.getMemory();

					for (int i = 0; i < bytes.size(); i++) {
						int b = bytes.get(i);

						memory[i] = b;
					}
					
					vm.run();

					// show code's result
					System.out.println("Result");
					System.out.println("======");
					System.out.println(memory[17]);
					System.out.println(memory[18]);
				}
			}
		} else {
			System.out.println("usage: test <mcpu-file>");
		}
	}
}
