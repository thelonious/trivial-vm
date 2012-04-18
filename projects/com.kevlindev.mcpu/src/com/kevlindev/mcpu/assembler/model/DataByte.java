/**
 * 
 */
package com.kevlindev.mcpu.assembler.model;

import java.util.ArrayList;
import java.util.Collections;
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
	public List<Integer> getByteCode(Program program) {
		if (bytes == null) {
			return Collections.emptyList();
		} else {
			return bytes;
		}
	}

	@Override
	public int getByteCount() {
		return (bytes != null) ? bytes.size() : 0;
	}
}
