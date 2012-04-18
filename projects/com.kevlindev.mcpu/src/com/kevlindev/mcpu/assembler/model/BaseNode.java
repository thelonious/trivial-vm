/**
 * 
 */
package com.kevlindev.mcpu.assembler.model;

import java.util.List;

import beaver.Symbol;

/**
 * @author Kevin Lindsey
 * 
 */
public abstract class BaseNode extends Symbol {
	private String label;
	private int baseAddress;

	public int getBaseAddress() {
		return baseAddress;
	}

	public abstract List<Integer> getByteCode(Program program);

	public String getLabel() {
		return label;
	}

	public abstract int getByteCount();

	public void setBaseAddress(int address) {
		baseAddress = address;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
