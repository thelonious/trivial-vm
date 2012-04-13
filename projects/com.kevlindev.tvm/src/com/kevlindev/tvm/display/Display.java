package com.kevlindev.tvm.display;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kevlindev.utils.ArrayUtils;
import com.kevlindev.utils.ImageUtils;

/**
 * This class demonstrates how to load an Image from an external file
 */
public class Display extends Component {
	private static final long serialVersionUID = 8705442778453346381L;

	private BufferedImage[] slices;
	private int characterWidth;
	private int characterHeight;

	private int columnCount = 80;
	private int rowCount = 24;
	private int scale = 1;

	private short[] memory;
	private int baseAddress;

	/**
	 * Display
	 */
	public Display() {
	}

	/**
	 * getBaseAddress
	 * 
	 * @return
	 */
	public int getBaseAddress() {
		return baseAddress;
	}

	/**
	 * getColumnCount
	 * 
	 * @return
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * getMemory
	 * 
	 * @return
	 */
	public short[] getMemory() {
		return memory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		if (ArrayUtils.isEmpty(slices)) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(characterWidth * columnCount * scale, characterHeight * rowCount * scale);
		}
	}

	/**
	 * getRowCount
	 * 
	 * @return
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * getScale
	 * 
	 * @return
	 */
	public int getScale() {
		return scale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		if (!ArrayUtils.isEmpty(slices) && memory != null) {
			int index = 0;

			for (int row = 0, y1 = 0; row < rowCount; row++, y1 += characterHeight * scale) {
				for (int col = 0, x1 = 0; col < columnCount; col++, x1 += characterWidth * scale, index++) {
					short c = memory[index + baseAddress];
					BufferedImage image = slices[(32 <= c && c <= 127) ? c : 32];

					g.drawImage(image, x1, y1, characterWidth * scale, characterHeight * scale, null);
				}
			}
		}
	}

	/**
	 * setBaseAddress
	 * 
	 * @param baseAddress
	 */
	public void setBaseAddress(int baseAddress) {
		this.baseAddress = baseAddress;
	}

	/**
	 * setColumnCount
	 * 
	 * @param columnCount
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	/**
	 * setFont
	 * 
	 * @param image
	 */
	public void setFont(BufferedImage font) {
		slices = ImageUtils.sliceImage(font, 32, 4);

		if (!ArrayUtils.isEmpty(slices)) {
			characterWidth = slices[0].getWidth();
			characterHeight = slices[0].getHeight();
		}
	}

	/**
	 * setMemory
	 * 
	 * @param memory
	 */
	public void setMemory(short[] memory) {
		this.memory = memory;
	}

	/**
	 * setMemory
	 * 
	 * @param memory
	 */
	public void setMemory(short[] memory, int baseAddress) {
		setMemory(memory);
		setBaseAddress(baseAddress);
	}

	/**
	 * setRowCount
	 * 
	 * @param rowCount
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * setScale
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}
}