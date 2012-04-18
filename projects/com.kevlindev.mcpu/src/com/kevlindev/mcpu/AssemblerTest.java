/**
 * 
 */
package com.kevlindev.mcpu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.kevlindev.mcpu.assembler.MCPUParser;
import com.kevlindev.mcpu.assembler.model.Program;
import com.kevlindev.utils.ArrayUtils;
import com.kevlindev.utils.IOUtils;

/**
 * @author Kevin Lindsey
 */
public class AssemblerTest {
	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if (!ArrayUtils.isEmpty(args)) {
			String source = IOUtils.getString(new FileInputStream(args[0]));
			Program result = new MCPUParser().parse(source);

			List<Integer> bytes = result.getByteCode(null);
			MCPUVM vm = new MCPUVM();
			int[] memory = vm.getMemory();

			for (int i = 0; i < bytes.size(); i++) {
				int b = bytes.get(i);

				memory[i] = b;
				System.out.println(String.format("%02X: %02X", i, b));
			}

			vm.run();

			System.out.println();
			System.out.println("Result:");
			System.out.println(memory[17]);
			System.out.println(memory[18]);
		} else {
			System.out.println("usage: test <mcpu-file>");
		}
	}
}
