/**
 * 
 */
package com.kevlindev.tvm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.kevlindev.tvm.display.FontInfo;
import com.kevlindev.tvm.display.TVMDisplay;

/**
 * @author kevin
 * 
 */
public class DisplayTest implements ActionListener {
	private static FontInfo[] FONTS;
	private static Map<String, FontInfo> NAMES_TO_INFO;

	static {
		// @formatter:off
		FONTS = new FontInfo[] {
			new FontInfo("atari", 16, 8),
			new FontInfo("atari-retouched", 16, 8),
			new FontInfo("atari-st-hi-res", 32, 4, 1, 1),
			new FontInfo("chicago", 16, 8),
			new FontInfo("dcpu", 32, 4, 2),
			new FontInfo("ibm-bios", 16, 16),
			new FontInfo("tom-thumb", 32, 4, 2)
		};
		// @formatter:on

		NAMES_TO_INFO = new HashMap<String, FontInfo>();

		for (FontInfo info : FONTS) {
			NAMES_TO_INFO.put(info.name, info);
		}
	};

	private TVMDisplay display;
	private JFrame frame;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		DisplayTest test = new DisplayTest();

		test.init();
	}

	/**
	 * DisplayTest
	 */
	public DisplayTest() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) source;

			FontInfo info = NAMES_TO_INFO.get(item.getText());

			if (info != null) {
				display.setFont(info);
				frame.pack();
				display.repaint();
			}
		}
	}

	/**
	 * createFrame
	 * 
	 * @return
	 */
	protected JFrame createFrame() {
		JFrame f = new JFrame("TVM Display");

		// setup exit-on-close behavior
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		return f;
	}

	/**
	 * createMemory
	 * 
	 * @return
	 */
	protected short[] createMemory() {
		// create some memory sample memory
		short[] memory = new short[0x10000];

		// @formatter:off
		FontInfo activeFont = FONTS[2];
		String[] lines = {
			"01 This is line one",
			"02 And this is line two",
			"03 This is a really long line that hopefully should get truncated to 80 columns if the logic is correct here.",
			"04 Line 4",
			"05 compare letters and numbers that are often confused: oO0 1lL",
			"06 this font is \"" + activeFont.name + "\" at " + activeFont.defaultScale + "x"
		};
		// @formatter:on

		// put some text into the video buffer
		int index = 0x8000;

		// don't exceed 24 lines of text
		int lineCount = Math.min(lines.length, 24);

		for (int i = 0; i < lineCount; i++, index += 80) {
			char[] chars = lines[i].toCharArray();

			// don't exceed 80 columns of text
			int charCount = Math.min(chars.length, 80);

			for (int j = 0; j < charCount; j++) {
				memory[index + j] = (short) chars[j];
			}
		}

		return memory;
	}

	/**
	 * createMenuBar
	 * 
	 * @return
	 */
	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fontMenu = new JMenu("Fonts");
		menuBar.add(fontMenu);

		for (FontInfo fontInfo : FONTS) {
			JMenuItem item = new JMenuItem(fontInfo.name);

			item.addActionListener(this);
			fontMenu.add(item);
		}

		return menuBar;
	}

	/**
	 * init
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		frame = createFrame();
		frame.setJMenuBar(createMenuBar());

		short[] memory = createMemory();
		display = new TVMDisplay();
		display.setMemory(memory, 0x8000);
		display.setFont(FONTS[2]);

		// display it
		frame.add(display);
		frame.pack();
		frame.setVisible(true);
	}
}
