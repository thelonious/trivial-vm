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

			List<Integer> bytes = result.getByteCode();

			for (Integer byteCode : bytes) {
				System.out.println(String.format("%02X", byteCode));
			}
		} else {
			System.out.println("usage: test <mcpu-file>");
		}
	}
}
