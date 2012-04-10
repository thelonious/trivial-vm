package com.kevlindev.tvm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kevlindev.tvm.io.TVMFileReader;
import com.kevlindev.utils.ArrayUtils;

public class TVMTest {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (!ArrayUtils.isEmpty(args)) {
			List<Short> byteCode = TVMFileReader.loadByteCode(args[0]);
			Map<Short, String> symbols = TVMFileReader.loadSymbolTable(args[1]);
			TrivialVirtualMachine vm = new TrivialVirtualMachine(byteCode, symbols);

			vm.run();
		} else {
			System.out.println("usage: tvm <tbin-filename>");
		}
	}

}
