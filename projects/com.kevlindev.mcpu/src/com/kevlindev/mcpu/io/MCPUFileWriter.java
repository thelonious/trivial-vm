package com.kevlindev.mcpu.io;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.kevlindev.mcpu.Constants;
import com.kevlindev.mcpu.assembler.model.Program;

public class MCPUFileWriter {
	public static void writeBinFile(Program program, File binFile) throws IOException {
		// write binary file
		FileOutputStream fos = new FileOutputStream(binFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataOutputStream dos = new DataOutputStream(bos);

		// write signature
		dos.writeShort(Constants.SIGNATURE);

		// write version number
		dos.writeShort(Constants.BIN_VERSION);

		// write byteCode starting address and size
		List<Integer> byteCodes = program.getByteCode(null);
		dos.writeShort(0);
		dos.writeShort(byteCodes.size());

		// write byteCode
		for (int byteCode : byteCodes) {
			dos.writeShort((short) byteCode);
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
		dos.writeShort(Constants.SIGNATURE);

		// write version number
		dos.writeShort(Constants.BIN_VERSION);

		// write symbols
		for (String symbol : program.getSymbols()) {
			int offset = program.getSymbolAddress(symbol);

			dos.writeUTF(symbol);
			dos.writeShort(offset);
		}

		dos.close();
	}

	private MCPUFileWriter() {
	}
}
