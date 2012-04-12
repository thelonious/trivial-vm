package com.kevlindev.tvm.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TVMFileReader {
	public static List<Short> loadByteCode(File file) throws IOException {
		List<Short> result;

		if (file.canRead()) {
			result = new ArrayList<Short>();
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);

			try {
				short signature = dis.readShort();
				assert (signature == TVMFileWriter.SIGNATURE);

				short version = dis.readShort();
				assert (version == TVMFileWriter.BIN_VERSION);

				while (true) {
					short address = dis.readShort();
					short count = dis.readShort();

					for (int i = 0; i < count; i++) {
						result.add(dis.readShort());
					}
				}
			} catch (IOException e) {
			}

			dis.close();
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	public static Map<Short, String> loadSymbolTable(File file) throws IOException {
		Map<Short, String> result;

		if (file.canRead()) {
			result = new HashMap<Short, String>();

			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);

			try {
				short signature = dis.readShort();
				assert (signature == TVMFileWriter.SIGNATURE);

				short version = dis.readShort();
				assert (version == TVMFileWriter.BIN_VERSION);

				while (true) {
					String name = dis.readUTF();
					Short offset = dis.readShort();

					result.put(offset, name);
				}
			} catch (IOException e) {
			}

			dis.close();
		} else {
			result = Collections.emptyMap();
		}

		return result;
	}

	private TVMFileReader() {
	}
}
