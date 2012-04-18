/**
 * 
 */
package com.kevlindev.template.parser;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import com.kevlindev.template.parser.model.TemplateNode;

import beaver.Scanner;
import beaver.Symbol;

/**
 * @author kevin
 * 
 */
public class TreeScanner extends Scanner {
	Queue<Symbol> tokens = new ArrayDeque<Symbol>();

	public void setNode(TemplateNode node) {
		tokens.offer(getMappedSymbol(node));

		if (node.hasChildren()) {
			tokens.offer(new Symbol(TemplateTokenType.DOWN.getTreeIndex()));

			for (TemplateNode child : node.getChildren()) {
				setNode(child);
			}

			tokens.offer(new Symbol(TemplateTokenType.UP.getTreeIndex()));
		}
	}
	
	protected Symbol getMappedSymbol(TemplateNode node) {
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
