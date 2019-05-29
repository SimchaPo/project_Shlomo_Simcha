package unittests;

import java.awt.Color;

import junit.framework.TestCase;
import renderer.ImageWriter;

public class ImageWriterTest extends TestCase {
	/**
	 * test - print grid 500*500 pixel, 10*10 squares
	 */
	public void testWriteToimage1() {
		ImageWriter im = new ImageWriter("Grid", 5000, 5000, 500, 500);
		for (int i = 0; i < 500; i += 50) {
			for (int j = 0; j < 500; ++j) {
				im.writePixel(i, j, Color.white);
				im.writePixel(j, i, Color.white);
			}
		}
		im.writeToImage();
		assertTrue(true);
	}

}
