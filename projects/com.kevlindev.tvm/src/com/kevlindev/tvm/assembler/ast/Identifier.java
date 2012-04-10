package com.kevlindev.tvm.assembler.ast;

public class Identifier extends BaseNode {
	private String identifier;

	public Identifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String toString() {
		return identifier;
	}
}
