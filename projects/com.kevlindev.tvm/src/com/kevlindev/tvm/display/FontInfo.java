package com.kevlindev.tvm.display;

public class FontInfo {
	public String name;
	public int columns;
	public int rows;
	public int defaultScale;
	public int offset;

	public FontInfo(String name, int columns, int rows) {
		this(name, columns, rows, 1, 0);
	}

	public FontInfo(String name, int columns, int rows, int defaultScale) {
		this(name, columns, rows, defaultScale, 0);
	}

	public FontInfo(String name, int columns, int rows, int defaultScale, int offset) {
		this.name = name;
		this.columns = columns;
		this.rows = rows;
		this.defaultScale = defaultScale;
		this.offset = offset;
	}
}
