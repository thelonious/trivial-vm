package com.kevlindev.mcpu.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kevlindev.mcpu.Constants;

public class MCPUFileReader {
	public static List<Integer> loadByteCode(File file) throws IOException {
		List<Integer> result;

		if (file.canRead()) {
			result = new ArrayList<Integer>();
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);

			try {
				short signature = dis.readShort();
				assert (signature == Constants.SIGNATURE);

				short version = dis.readShort();
				assert (version == Constants.BIN_VERSION);

				while (true) {
					short address = dis.readShort();
					short count = dis.readShort();

					for (int i = 0; i < count; i++) {
						result.add((int) dis.readShort());
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

	public static Map<Integer, String> loadSymbolTable(File file) throws IOException {
		Map<Integer, String> result;

		if (file.canRead()) {
			result = new HashMap<Integer, String>();

			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);

			try {
				short signature = dis.readShort();
				assert (signature == Constants.SIGNATURE);

				short version = dis.readShort();
				assert (version == Constants.BIN_VERSION);

				while (true) {
					String name = dis.readUTF();
					Short offset = dis.readShort();

					result.put((int) offset, name);
				}
			} catch (IOException e) {
			}

			dis.close();
		} else {
			result = Collections.emptyMap();
		}

		return result;
	}

	private MCPUFileReader() {
	}
}
