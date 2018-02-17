package com.boxmanteam.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.boxmanteam.core.ID;

/**
 * This class is a blueprint for all the game objects.
 * 
 * @author Waleed Occidental
 *
 */
public abstract class GameObject {
	/**
	 * Default width of an object.
	 */
	final int width = 48;
	/**
	 * Default height of an object.
	 */
	final int height = 48;
	/**
	 * Position of the object.
	 */
	protected int x, y;
	/**
	 * Stores which type of object.
	 */
	protected ID id;
	/**
	 * Stores the previous location of the object.
	 */
	protected int prevX, prevY;

	/**
	 * This constructor sets the value for x, y and ID.
	 * 
	 * @param x
	 *            x position of the object
	 * @param y
	 *            y position of the object
	 * @param id
	 *            which type of object
	 */
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	/**
	 * updates the variables
	 */
	public abstract void tick();

	/**
	 * Renders the graphics
	 * 
	 * @param g
	 */
	public abstract void render(Graphics g);

	/**
	 * 
	 * @return returns the bounds of the object
	 */
	public abstract Rectangle getBounds();

	/**
	 * Limits the value within the range from min to max
	 * 
	 * @param var
	 *            variable to check
	 * @param min
	 *            minimum range
	 * @param max
	 *            maximum range
	 * @return
	 */
	public static int limits(int var, int min, int max) {
		if (var >= max) {
			return max;
		} else if (var <= min) {
			return min;
		} else
			return var;
	}

	/**
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * 
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	/**
	 * Sets x value
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}
	/**
	 * Sets y value
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * 
	 * @return id 
	 */
	public ID getId() {
		return id;
	}
	/**
	 * Sets id
	 * @param id
	 */
	public void setId(ID id) {
		this.id = id;
	}

}
