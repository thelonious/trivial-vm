package com.kevlindev.mcpu;

public class VMTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MCPUVM vm = new MCPUVM();
		int[] memory = vm.getMemory();
		// @formatter:off
		int[] bytecode = {
			(0 << 6) | 14,
			(0 << 6) | 18,
			(1 << 6) | 16,
			(1 << 6) | 17,
			(3 << 6) | 9,
			(2 << 6) | 17,
			(1 << 6) | 14,
			(3 << 6) | 13,
			(3 << 6) | 0,
			(0 << 6) | 15,
			(1 << 6) | 16,
			(2 << 6) | 18,
			(3 << 6) | 0,
			(3 << 6) | 13,
			0xFF,
			0,
			1,
			26,
			22
		};
		// @formatter:on

		for (int i = 0; i < bytecode.length; i++) {
			memory[i] = bytecode[i];
		}

		vm.run();

		System.out.println(memory[17]);
		System.out.println(memory[18]);
	}
}
