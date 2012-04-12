package com.kevlindev.tvm.io;

import com.kevlindev.utils.StringUtils;

public class FileUtil {
	public static final String TVM_EXTENSION = "tvm";
	public static final String BIN_EXTENSION = "tbin";
	public static final String SYM_EXTENSION = "tsym";
	public static final String LST_EXTENSION = "lst";

	public static String setExtension(String filename, String extension) {
		// normalize extension
		String ext;

		if (StringUtils.isEmpty(extension)) {
			ext = StringUtils.EMPTY;
		} else if (extension.startsWith(".")) {
			ext = extension.substring(1);
		} else {
			ext = extension;
		}

		// replace extension
		String result;

		if (!StringUtils.isEmpty(filename)) {
			int index = filename.lastIndexOf('.');

			if (index == -1) {
				result = filename + "." + ext;
			} else {
				result = filename.substring(0, index + 1) + ext;
			}
		} else {
			result = StringUtils.EMPTY;
		}

		return result;
	}

	private FileUtil() {
	}
}
