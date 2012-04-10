package com.kevlindev.tvm.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TVMFileReader {
	private TVMFileReader() {
	}

	public static List<Short> loadByteCode(String file) throws IOException {
		List<Short> result = new ArrayList<Short>();

		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);

		try {
			while (true) {
				result.add(dis.readShort());
			}
		} catch (IOException e) {
		}

		dis.close();

		return result;
	}

	public static Map<Short, String> loadSymbolTable(String file) throws IOException {
		Map<Short, String> result = new HashMap<Short, String>();

		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);

		try {
			while (true) {
				String name = dis.readUTF();
				Short offset = dis.readShort();

				result.put(offset, name);
			}
		} catch (IOException e) {
		}

		dis.close();

		return result;
	}
}
