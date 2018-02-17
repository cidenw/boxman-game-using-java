package com.boxmanteam.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class loads the image depending on the path given
 **/
public class ImageLoader {
	/**
	 * This method receives the path of the image from the resource folder and
	 * returns it as a bufferimage
	 * 
	 * @param path
	 * @return
	 */
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
