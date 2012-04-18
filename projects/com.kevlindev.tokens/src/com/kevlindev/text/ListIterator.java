/**
 * 
 */
package com.kevlindev.text;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Kevin Lindsey
 * 
 */
public class ListIterator implements ResettableIterator<String> {
	private List<String> list;
	private int index = 0;

	public ListIterator(List<String> list) {
		this.list = list;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		return (list != null && index < list.size());
	}

	@Override
	public String next() {
		if (list != null && index < list.size()) {
			return list.get(index++);
		}

		throw new NoSuchElementException();
	}

	@Override
	public String peek() {
		if (list != null && index < list.size()) {
			return list.get(index);
		}

		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset() {
		index = 0;
	}
}
