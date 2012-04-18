/**
 * 
 */
package com.kevlindev.mcpu.assembler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Lindsey
 */
public class DataByte extends BaseNode {
	List<Integer> bytes;

	public DataByte() {
	}

	public void addByte(int data) {
		if (bytes == null) {
			bytes = new ArrayList<Integer>();
		}

		bytes.add(data);
	}

	@Override
	public List<Integer> getByteCode() {
		return bytes;
	}

	@Override
	public int getSize() {
		return bytes.size();
	}
}
