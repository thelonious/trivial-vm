package com.kevlindev.tvm.io;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.kevlindev.tvm.assembler.model.Program;

public class TVMFileWriter {
	public static final short SIGNATURE = (short) 0xABCD;
	public static final short BIN_VERSION = 1;
	public static final short SYM_VERSION = 1;

	public static void writeBinFile(Program program, File binFile) throws IOException {
		// write binary file
		FileOutputStream fos = new FileOutputStream(binFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataOutputStream dos = new DataOutputStream(bos);

		// write signature
		dos.writeShort(SIGNATURE);

		// write version number
		dos.writeShort(BIN_VERSION);

		// write byteCode starting address and size
		List<Short> byteCodes = program.getByteCode();
		dos.writeShort(0);
		dos.writeShort(byteCodes.size());

		// write byteCode
		for (Short byteCode : byteCodes) {
			dos.writeShort(byteCode);
		}

		dos.close();
	}

	public static void writeListing(Program program, File lstFile) throws IOException {
		FileWriter fw = new FileWriter(lstFile);

		fw.write(program.getListing());
		fw.close();
	}

	public static void writeSymFile(Program program, File symFile) throws IOException {
		// write binary file
		FileOutputStream fos = new FileOutputStream(symFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataOutputStream dos = new DataOutputStream(bos);

		// write signature
		dos.writeShort(SIGNATURE);

		// write version number
		dos.writeShort(BIN_VERSION);

		// write symbols
		for (String symbol : program.getSymbols()) {
			int offset = program.getSymbolAddress(symbol);

			dos.writeUTF(symbol);
			dos.writeShort(offset);
		}

		dos.close();
	}

	private TVMFileWriter() {
	}
}
