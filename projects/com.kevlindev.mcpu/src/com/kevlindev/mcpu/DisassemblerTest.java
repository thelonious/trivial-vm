package com.kevlindev.mcpu;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kevlindev.mcpu.disassembler.MCPUDisassembler;
import com.kevlindev.mcpu.io.MCPUFileReader;
import com.kevlindev.utils.ArrayUtils;
import com.kevlindev.utils.FileUtils;

public class DisassemblerTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (!ArrayUtils.isEmpty(args)) {
			String file = args[0];
			File tvmFile = new File(file);
			File symFile = new File(FileUtils.setExtension(file, FileUtils.SYM_EXTENSION));
			List<Integer> byteCode = MCPUFileReader.loadByteCode(tvmFile);
			Map<Integer, String> symbols = MCPUFileReader.loadSymbolTable(symFile);
			MCPUDisassembler vm = new MCPUDisassembler(byteCode, symbols);

			vm.run();
		} else {
			System.out.println("usage: tvm <tbin-filename>");
		}
	}
}
