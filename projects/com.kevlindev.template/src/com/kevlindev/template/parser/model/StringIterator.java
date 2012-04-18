/**
 * 
 */
package com.kevlindev.template.parser.model;

import java.util.NoSuchElementException;

/**
 * @author Kevin Lindsey
 * 
 */
public class StringIterator implements ResettableIterator<String> {
	private String value;
	private boolean hasNext = true;

	public StringIterator(String value) {
		this.value = value;
		hasNext = true;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public String next() {
		if (hasNext) {
			hasNext = false;
			return value;
		}

		throw new NoSuchElementException();
	}

	@Override
	public String peek() {
		if (hasNext) {
			return value;
		}

		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset() {
		hasNext = true;
	}
}
