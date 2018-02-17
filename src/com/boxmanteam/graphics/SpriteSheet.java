package com.boxmanteam.graphics;

import java.awt.image.BufferedImage;

/*
 * This class makes a sprite sheet from an image by cropping it and storing it in an arraylist or array depending on the usage
 * **/
public class SpriteSheet {
	private BufferedImage sheet;

	/**
	 * This constructor receives the buffered image to be cropped
	 * 
	 * @param sheet
	 */
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}

	/**
	 * This method crops the image by using the subimage class by receiving the
	 * x and y coordinates of the starting crop and the width and height of the
	 * cropped image
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
}
