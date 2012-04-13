package com.kevlindev.tvm.display;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kevlindev.utils.ArrayUtils;
import com.kevlindev.utils.ImageUtils;

/**
 * This class demonstrates how to load an Image from an external file
 */
public class TVMDisplay extends Component {
	private class WhiteToGreenFilter extends RGBImageFilter {
		@Override
		public int filterRGB(int x, int y, int rgb) {
			int alpha = 0xFF000000 & rgb;

			if ((rgb & 0x00FFFFFF) != 0xFFFFFF) {
				return alpha | 0x0060AA;
			} else {
				return rgb;
			}
		}
	}

	private static final long serialVersionUID = 8705442778453346381L;

	private BufferedImage[] slices;
	private int offset;
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
	public TVMDisplay() {
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
					BufferedImage image = slices[(32 <= c && c <= 127) ? c - offset : 32 - offset];

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

	public void setFont(FontInfo info) {
		if (info != null) {
			try {
				BufferedImage font = ImageIO.read(new File("image/" + info.name + ".png"));
				ImageFilter colorfilter = new WhiteToGreenFilter();
				Image greenFontImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(font.getSource(), colorfilter));
				BufferedImage greenFont = ImageUtils.getBufferedImage(greenFontImage);

				setFont(greenFont, info.columns, info.rows, info.offset);
				setScale(info.defaultScale);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setFont(BufferedImage font, int columns, int rows) {
		setFont(font, columns, rows, 0);
	}

	/**
	 * setFont
	 * 
	 * @param image
	 * @param columns
	 * @param rows
	 */
	public void setFont(BufferedImage font, int columns, int rows, int offset) {
		slices = ImageUtils.sliceImage(font, columns, rows);
		this.offset = offset;

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