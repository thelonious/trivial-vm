/**
 * 
 */
package com.kevlindev.template.parser;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import beaver.Scanner;
import beaver.Symbol;

import com.kevlindev.parsing.model.ParseNode;

/**
 * @author kevin
 * 
 */
public class TreeScanner<T> extends Scanner {
	Queue<Symbol> tokens = new ArrayDeque<Symbol>();

	public void setNode(ParseNode<T> node) {
		tokens.offer(getMappedSymbol(node));

		if (node.hasChildren()) {
			tokens.offer(new Symbol(TemplateTokenType.DOWN.getTreeIndex()));

			for (ParseNode<T> child : node.getChildren()) {
				setNode(child);
			}

			tokens.offer(new Symbol(TemplateTokenType.UP.getTreeIndex()));
		}
	}

	protected Symbol getMappedSymbol(ParseNode<T> node) {
		Symbol s = node.getSymbol();
		TemplateTokenType t = TemplateTokenType.getTokenType(s.getId());

		return new Symbol(t.getTreeIndex(), s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see beaver.Scanner#nextToken()
	 */
	@Override
	public Symbol nextToken() throws IOException, Exception {
		if (!tokens.isEmpty()) {
			return tokens.poll();
		} else {
			return new Symbol(TemplateTokenType.EOF);
		}
	}
}
