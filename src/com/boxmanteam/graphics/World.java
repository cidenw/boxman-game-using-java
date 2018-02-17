package com.boxmanteam.graphics;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class reads the world or map from the resource folder and renders it
 */
public class World {
	int height = 48, width = 48;
	public static int theme;
	Coord playerPos;
	int timer;
	int maxMoves;

	ArrayList<Coord> wall = new ArrayList<Coord>();
	ArrayList<Coord> box = new ArrayList<Coord>();
	ArrayList<Coord> target = new ArrayList<Coord>();

	/**
	 * This constructor loads the map file from the resource folder using the
	 * loadMap method
	 * 
	 * @param path
	 *            - path of the file to be read
	 */
	public World(String path) {
		try {
			loadMap(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the coordinate of the player
	 * 
	 * @return player position x and y
	 */
	public Coord getPlayerPos() {
		return playerPos;
	}

	/**
	 * This class renders the map itself
	 * 
	 * @param g
	 *            - graphics object
	 */
	public void render(Graphics g) {
		if (theme == 0) {
			for (int x = 0; x <= 20; x++) {
				for (int y = 0; y <= 19; y++) {
					g.drawImage(Assets.ruins[0], x * width, y * height, width, height, null);
				}
			}
			for (int x = 0; x <= 20; x++) {
				g.drawImage(Assets.ruins[1], x * width, 0, width, height, null);
				g.drawImage(Assets.ruins[1], x * width, height * 19, width, height, null);
			}
			for (int y = 0; y <= 19; y++) {
				g.drawImage(Assets.ruins[1], 0, height * y, width, height, null);
				g.drawImage(Assets.ruins[1], width * 20, height * y, width, height, null);
			}
		}
		if (theme == 1) {
			for (int x = 0; x <= 20; x++) {
				for (int y = 0; y <= 19; y++) {
					g.drawImage(Assets.winter[0], x * width, y * height, width, height, null);
				}
			}
			for (int x = 0; x <= 20; x++) {
				g.drawImage(Assets.winter[1], x * width, 0, width, height, null);
				g.drawImage(Assets.winter[1], x * width, height * 19, width, height, null);
			}
			for (int y = 0; y <= 19; y++) {
				g.drawImage(Assets.winter[1], 0, height * y, width, height, null);
				g.drawImage(Assets.winter[1], width * 20, height * y, width, height, null);
			}
		}
		if (theme == 2) {
			for (int x = 0; x <= 20; x++) {
				for (int y = 0; y <= 19; y++) {
					g.drawImage(Assets.desert[0], x * width, y * height, width, height, null);
				}
			}
			for (int x = 0; x <= 20; x++) {
				g.drawImage(Assets.desert[1], x * width, 0, width, height, null);
				g.drawImage(Assets.desert[1], x * width, height * 19, width, height, null);
			}
			for (int y = 0; y <= 19; y++) {
				g.drawImage(Assets.desert[1], 0, height * y, width, height, null);
				g.drawImage(Assets.desert[1], width * 20, height * y, width, height, null);
			}
		}

	}

	/**
	 * This method loads the map and stored them separately for the render
	 * method
	 * 
	 * @param path
	 *            - path of the file
	 * @throws FileNotFoundException
	 *             - exception if the file is not found
	 * @throws IOException
	 *             - exception if the input is incorrect
	 */
	private void loadMap(String path) throws FileNotFoundException, IOException {
		BufferedReader br;
		System.out.println(new File(path).getAbsolutePath());
		InputStream in = getClass().getResourceAsStream(path);
		br = new BufferedReader(new InputStreamReader(in));
		playerPos = new Coord(-1, -1);
		// default moves = 300
		// default time = 300s
		maxMoves = 300;
		timer = 300 * 1000;
		String temp;
		String what = "Player";
		while ((temp = br.readLine()) != null) {
			temp = temp.trim();
			if (temp.equals("Time") || temp.equals("Moves") || temp.equals("Player") || temp.equals("Wall")
					|| temp.equals("Box") || temp.equals("Target")) {
				what = temp;
				continue;
			}

			if (what.equals("Time")) {
				timer = Integer.parseInt(temp) * 1000;
			}
			if (what.equals("Moves")) {
				maxMoves = Integer.parseInt(temp);
			}
			if (what.equals("Player")) {
				String[] xy = temp.split(",");
				playerPos = new Coord(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
			}
			if (what.equals("Wall")) {
				String[] xy = temp.split(",");
				wall.add(new Coord(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
			}
			if (what.equals("Box")) {
				String[] xy = temp.split(",");
				box.add(new Coord(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
			}
			if (what.equals("Target")) {
				String[] xy = temp.split(",");
				target.add(new Coord(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
			}

		}
		br.close();
	}

	/**
	 * Gets an arraylist of coord of the wall
	 * 
	 * @return
	 */
	public ArrayList<Coord> getWall() {
		return wall;
	}
	/**
	 * Gets an arraylist of coord of the box
	 * @return
	 */
	public ArrayList<Coord> getBox() {
		return box;
	}
	/**
	 * Gets an arraylist of coord of the targets
	 * @return
	 */
	public ArrayList<Coord> getTarget() {
		return target;
	}
	/**
	 * Gets the maximum number of moves 
	 * @return
	 */
	public int getMaxMoves() {
		return maxMoves;
	}
	/**
	 * Gets the time per map
	 * @return
	 */
	public int getTimer() {
		return timer;
	}
}
