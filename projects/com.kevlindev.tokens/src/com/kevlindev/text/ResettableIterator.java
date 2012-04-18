/**
 * 
 */
package com.kevlindev.text;

import java.util.Iterator;

/**
 * @author Kevin Lindsey
 * 
 */
public interface ResettableIterator<E> extends Iterator<E> {
	E peek();

	void reset();
}
