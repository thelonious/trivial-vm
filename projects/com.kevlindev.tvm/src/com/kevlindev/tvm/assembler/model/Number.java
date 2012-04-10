package com.kevlindev.tvm.assembler.model;

public class Number extends BaseNode {
	private int number;

	public Number(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public String toString() {
		return "#" + number;
	}
}
