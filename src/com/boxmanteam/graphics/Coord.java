package com.boxmanteam.graphics;

/**
 * This class is for storing the x and y coordinates of an object
 **/
public class Coord {
	int x, y;

	/**
	 * This constructor receives the x and y coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x coordinate of this class
	 **/
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate of this class
	 **/
	public int getY() {
		return y;
	}

	/**
	 * Prints the x and y coordinates
	 **/
	public void printCoord() {
		System.out.println("(" + x + ", " + y + ")");
	}

	/**
	 * Changes the x coordinate by adding 1 to the current x coordinate
	 **/
	public void moveRight() {
		x += 1;
	}

	/**
	 * Changes the x coordinate by subtracting 1 to the current x coordinate
	 **/
	public void moveLeft() {
		x -= 1;
	}

	/**
	 * Changes the y coordinate by subtracting 1 to the current y coordinate
	 **/
	public void moveUp() {
		y -= 1;
	}

	/**
	 * Changes the y coordinate by adding 1 to the current y coordinate
	 **/
	public void moveDown() {
		y += 1;
	}
}
