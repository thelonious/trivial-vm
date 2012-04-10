package com.kevlindev.tvm.io;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kevlindev.tvm.assembler.model.Program;

public class TVMFileWriter {
	private static final String TVM_EXTENSION = ".tvm";
	private static final String BIN_EXTENSION = ".tbin";
	private static final String SYM_EXTENSION = ".tsym";

	private TVMFileWriter() {

	}

	public static void writeSymFile(Program program, String file) throws IOException {
		// write binary file
		String symFileName;

		if (file.toLowerCase().endsWith(TVM_EXTENSION)) {
			symFileName = file.substring(0, file.length() - TVM_EXTENSION.length()) + SYM_EXTENSION;
		} else {
			symFileName = file + SYM_EXTENSION;
		}

		FileOutputStream fos = new FileOutputStream(symFileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataOutputStream dos = new DataOutputStream(bos);

		// write signature

		// write version number

		// write symbols
		for (String symbol : program.getSymbols()) {
			int offset = program.getSymbolAddress(symbol);

			dos.writeUTF(symbol);
			dos.writeShort(offset);
		}

		dos.close();
	}

	public static void writeBinFile(Program program, String file) throws IOException {
		// write binary file
		String binFileName;

		if (file.toLowerCase().endsWith(TVM_EXTENSION)) {
			binFileName = file.substring(0, file.length() - TVM_EXTENSION.length()) + BIN_EXTENSION;
		} else {
			binFileName = file + BIN_EXTENSION;
		}

		FileOutputStream fos = new FileOutputStream(binFileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataOutputStream dos = new DataOutputStream(bos);

		// write signature

		// write version number

		// write byteCode starting address and size

		// write byteCode
		for (Short byteCode : program.getByteCode()) {
			dos.writeShort(byteCode);
		}

		dos.close();
	}
}
