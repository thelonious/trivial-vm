/**
 * 
 */
package com.kevlindev.pipistrello;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kevin Lindsey
 */
public class SerialReader implements Runnable {
	private InputStream in;
	private DataInputStream dis;
	
	public SerialReader(InputStream in) {
		this.in = in;
		this.dis = new DataInputStream(this.in);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		boolean run = true;
		
		try
		{
			while (run) {
				if (this.dis.available() > 0) {
					byte command = this.dis.readByte();
					
					switch (command) {
					case 'd':
					{
						int byteCount = dis.readInt();
						int[] pixels = new int[byteCount / 4];
						int i = 0;
						
						try {
							for (i = 0; i < byteCount / 4; i++) {
								if (i != 0 && (i % 640) == 0) System.out.print(".");
								if (i != 0 && (i % (640*40)) == 0) System.out.println();
								//while (this.dis.available() < 4);
								pixels[i] = dis.readInt();
							}
							System.out.println();
						}
						catch (IOException e) {
							// ignore
						}
						
						System.out.println("Read " + (i*4) + " bytes out of " + byteCount);
						
						DataOutputStream dos = null;

						try {
							dos = new DataOutputStream(
								new BufferedOutputStream(
									new FileOutputStream(
										new File("C:\\Users\\Kevin Lindsey\\Desktop\\screen.raw")
									)
								)
							);
							for (int pixel : pixels) {
								dos.writeInt(pixel);
							}
						} catch (IOException e) {
							throw new RuntimeException(e);
						} finally {
							try {
								if (dos != null) {
									dos.close();
								}
							} catch (IOException e) {
								// do nothing
							}
						}
						
						/*
						DataBufferInt buffer = new DataBufferInt(pixels, 640 * 480);
						int bitMask[] = new int[] { 0xff0000, 0xff00, 0xff, 0xff000000 };
						SinglePixelPackedSampleModel sampleModel = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, 640, 480, bitMask);
						WritableRaster raster = Raster.createWritableRaster(sampleModel, buffer, null);
						BufferedImage image = new BufferedImage(ColorModel.getRGBdefault(), raster, false, null);
						ImageIO.write(image, "png", new File("C:\\Users\\Kevin Lindsey\\Desktop\\screen.png"));
						*/
						
						// exit runnable until we make input block properly
						run = false;
						
						break;
					}
						
					default:
						System.out.print((char) command);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
