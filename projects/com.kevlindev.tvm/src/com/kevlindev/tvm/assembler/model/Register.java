package com.kevlindev.tvm.assembler.model;

public class Register extends BaseNode {
	private int index;

	public Register(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public String toString() {
		return "r" + index;
	}
}
