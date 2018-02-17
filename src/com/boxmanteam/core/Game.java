package com.boxmanteam.core;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.boxmanteam.graphics.Assets;
import com.boxmanteam.menu.Menu;

/**
 * This class contains the game loop.
 * 
 * @author Waleed
 *
 */
public class Game extends Canvas implements Runnable {
	/**
	 * Main class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Game();
	}

	/**
	 * Generated serial versionUID.
	 */
	private static final long serialVersionUID = -8921419424614180143L;
	/**
	 * Width of the window.
	 */
	public static final int WIDTH = 1282;
	/**
	 * Height of the window.
	 */
	public static final int HEIGHT = 990;
	/**
	 * Main thread of the game.
	 */
	private Thread thread;
	/**
	 * 
	 */
	private boolean running = false;
	/**
	 * Instance of the handler class which contains all the game objects.
	 */
	private Handler handler;
	/**
	 * Instance of the menu class which handles the menu and mouse input.
	 */
	private Menu menu;

	/**
	 * This constructor initializes all of the components of the game. This is
	 * where the game will start. Initializes all the Assets. Initializes the
	 * AudioPlayer class. Initializes the Scores class. Adds a key and mouse
	 * listener. Creates a new instance of the Window class.
	 * 
	 * @see Assets
	 * @see AudioPlayer
	 * @see Scores
	 * @see KeyInput
	 * @see Menu
	 * @see Window
	 */
	public Game() {
		Assets.init();
		AudioPlayer.load();
		Scores.initialize();
		handler = new Handler();
		menu = new Menu(this, handler);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		handler.loadWorld(); // starts the game
		new Window(WIDTH, HEIGHT, "Boxman by the BoxmanTeam", this);
	}

	/**
	 * Updates all the variables of the menu and game objects.
	 */
	private void tick() {
		menu.tick();
		handler.tick();
	}

	/**
	 * Renders all the graphics to the window.
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);
		menu.render(g);
		
		g.dispose();
		bs.show();
	}

	/**
	 * Game loop. Calls the tick and render method once every 1/60 seconds.
	 */
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double fps = 60.0;
		double timePerTick = 1000000000 / fps; // fps in nanoseconds
		double delta = 0;
		long timer = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			timer += now - lastTime;
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}

			frames++;

			if (timer >= 1000000000) {
				System.out.println("FPS : " + frames);
				frames = 0;
				timer = 0;
			}
		}
		stop();
	}

	/**
	 * Starts the game thread.
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	/**
	 * Stops the game thread.
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
