package com.kevlindev.tvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import beaver.Parser.Exception;

import com.kevlindev.tvm.assembler.TVMParser;
import com.kevlindev.tvm.assembler.model.Program;
import com.kevlindev.tvm.io.TVMFileWriter;
import com.kevlindev.utils.FileUtils;
import com.kevlindev.utils.IOUtils;

public class AssemblerTest {
	public AssemblerTest() {
	}

	public Program assemble(String file) throws IOException {
		System.out.println();
		System.out.println("Processing " + file);
		System.out.println();

		// grab source
		String source = IOUtils.getString(new FileInputStream(file));

		System.out.println("Source");
		System.out.println("======");
		System.out.println(source);

		return new TVMParser().parse(source);
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
	 * main
	 * 
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, Exception {
		if (args != null && args.length > 0) {
			AssemblerTest tester = new AssemblerTest();

			for (String file : args) {
				Program program = tester.assemble(file);

				if (program != null) {
					tester.showListing(program);
					tester.showSymbolListing(program);

					// write bin file
					File binFile = new File(FileUtils.setExtension(file, FileUtils.BIN_EXTENSION));
					TVMFileWriter.writeBinFile(program, binFile);

					// write sym file
					File symFile = new File(FileUtils.setExtension(file, FileUtils.SYM_EXTENSION));
					TVMFileWriter.writeSymFile(program, symFile);

					// write lst file
					File lstFile = new File(FileUtils.setExtension(file, FileUtils.LST_EXTENSION));
					TVMFileWriter.writeListing(program, lstFile);
				}
			}
		} else {
			System.out.println("usage: tvm-assembler <tvm-file>");
		}
	}
}
