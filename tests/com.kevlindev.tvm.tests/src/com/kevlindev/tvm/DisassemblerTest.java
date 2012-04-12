package com.kevlindev.tvm;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kevlindev.tvm.disassembler.TVMDisassembler;
import com.kevlindev.tvm.io.FileUtil;
import com.kevlindev.tvm.io.TVMFileReader;
import com.kevlindev.utils.ArrayUtils;

public class DisassemblerTest {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (!ArrayUtils.isEmpty(args)) {
			String file = args[0];
			File tvmFile = new File(file);
			File symFile = new File(FileUtil.setExtension(file, FileUtil.SYM_EXTENSION));
			List<Short> byteCode = TVMFileReader.loadByteCode(tvmFile);
			Map<Short, String> symbols = TVMFileReader.loadSymbolTable(symFile);
			TVMDisassembler vm = new TVMDisassembler(byteCode, symbols);

			vm.run();
		} else {
			System.out.println("usage: tvm <tbin-filename>");
		}
	}

}
