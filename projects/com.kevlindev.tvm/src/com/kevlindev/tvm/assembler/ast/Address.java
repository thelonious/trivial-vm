package com.kevlindev.tvm.assembler.ast;

public class Address extends BaseNode {
	private int address;

	public Address(int address) {
		this.address = address;
	}

	public int getAddress() {
		return address;
	}

	public String toString() {
		return "$" + address;
	}
}
