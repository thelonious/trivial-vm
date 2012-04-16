/**
 * 
 */
package com.kevlindev.tokens;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import beaver.Scanner;
import beaver.Symbol;

/**
 * @author kevin
 * 
 */
public class TreeScanner extends Scanner {
	Queue<Symbol> tokens = new ArrayDeque<Symbol>();

	public void setNode(TokenNode node) {
		tokens.offer(getMappedSymbol(node));

		if (node.hasChildren()) {
			tokens.offer(new Symbol(TokenType.DOWN.getTreeIndex()));

			for (TokenNode child : node.getChildren()) {
				setNode(child);
			}

			tokens.offer(new Symbol(TokenType.UP.getTreeIndex()));
		}
	}
	
	protected Symbol getMappedSymbol(TokenNode node) {
		Symbol s = node.getSymbol();
		TokenType t = TokenType.getTokenType(s.getId());
		
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
			return new Symbol(TokenType.EOF);
		}
	}
}
