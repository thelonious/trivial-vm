/**
 * 
 */
package com.kevlindev.tvm;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.kevlindev.utils.ImageUtils;

/**
 * @author kevin
 * 
 */
public class DisplayTest {
	static class WhiteToGreenFilter extends RGBImageFilter {
		@Override
		public int filterRGB(int x, int y, int rgb) {
			if ((rgb & 0x00FFFFFF) == 0xFFFFFF) {
				return ((rgb & 0xFF000000) | 0xC000);
			} else {
				return rgb;
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame("TVM Display");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// create some memory sample memory
		short[] memory = new short[0x10000];

		// put some text into it
		// @formatter:off
		String[] lines = {
			"01 This is line one",
			"02 And this is line two",
			"03 This is a really long line that hopefully should get truncated to 80 columns if the logic is correct here.",
			"04 Line 4"
		};
		// @formatter:on

		int index = 0x8000;
		int lineCount = Math.min(lines.length, 24);

		for (int i = 0; i < lineCount; i++, index += 80) {
			char[] chars = lines[i].toCharArray();
			int charCount = Math.min(chars.length, 80);

			for (int j = 0; j < charCount; j++) {
				memory[index + j] = (short) chars[j];
			}
		}

		// create display and set state
		Display display = new Display();
		BufferedImage font = ImageIO.read(new File("image/font.png"));
		ImageFilter colorfilter = new WhiteToGreenFilter();
		Image greenFontImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(font.getSource(), colorfilter));
		BufferedImage greenFont = ImageUtils.getBufferedImage(greenFontImage);
		display.setFont(greenFont);
		display.setScale(2);
		display.setMemory(memory, 0x8000);

		f.add(display);
		f.pack();
		f.setVisible(true);
	}
}
