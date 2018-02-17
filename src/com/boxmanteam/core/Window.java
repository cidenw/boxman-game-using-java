package com.boxmanteam.core;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.boxmanteam.graphics.ImageLoader;

/**
 * This serves as the window for the game. The thread will start here.
 * 
 * @author Waleed Occidental
 *
 */
public class Window extends Canvas {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -8255319694373975038L;
	/**
	 * Creates the window where the graphics will be rendered.
	 * @param width	width of the window
	 * @param height	height of the window
	 * @param title	title of the window
	 * @param game instance of the game
	 */
	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);

		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(ImageLoader.loadImage("/textures/icon.png"));
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}
