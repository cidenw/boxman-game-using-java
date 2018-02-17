package com.boxmanteam.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.boxmanteam.core.Handler;
import com.boxmanteam.core.ID;
import com.boxmanteam.graphics.Assets;
import com.boxmanteam.graphics.World;

/**
 * Represents a box (GameObject)used in the game.
 * @author Waleed Occidental
 *
 */
public class Box extends GameObject {
	/**
	 * Instance of the handler class
	 * @see Handler
	 */
	Handler handler;
	/**
	 * This constructor sets the values for the position and id of this object.
	 * @param x	the initial x position of this object
	 * @param y the initial y position of this object
	 * @param id determines what kind of object it is
	 * @param handler instance of the handler class
	 * @see Handler
	 */
	public Box(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
	/**
	 * Renders the object to the screen.
	 */
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (World.theme == 0) {
			g.drawImage(Assets.ruins[2], x, y, width, height, null);
		} else if (World.theme == 1) {
			g.drawImage(Assets.winter[2], x, y, width, height, null);
		} else if (World.theme == 2) {
			g.drawImage(Assets.desert[2], x, y, width, height, null);
		}
	}
	/**
	 * Changes the x position of the Box.
	 * @param x
	 * @return
	 */
	public boolean moveX(int x) {
		prevX = this.x;
		this.x = x;
		if (collisionWithWall()) {
			this.x = prevX;
			return false;
		}
		return true;
	}
	/**
	 * Changes the y position of the Box.
	 * @param x
	 * @return
	 */
	public boolean moveY(int y) {
		prevY = this.y;
		this.y = y;
		if (collisionWithWall()) {
			this.y = prevY;
			return false;
		}
		return true;
	}
	/**
	 * Checks whether this box intersects with a wall object.
	 * @return true if it collides with a wall
	 * @see Wall
	 */
	private boolean collisionWithWall() {
		for (int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			if (tempObject.getId() == ID.Wall || tempObject.getId() == ID.Box && this != tempObject) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * returns a new Rectangle with the dimension and position of this box.
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, width, height);
	}

}
