package com.boxmanteam.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.boxmanteam.core.ID;
import com.boxmanteam.graphics.Assets;
import com.boxmanteam.graphics.World;
/**
 * This class represents the wall object.
 * This class extends GameObject.
 * @author Waleed Occidental
 *
 */
public class Wall extends GameObject {
	/**
	 * This constructor sets the initial position of this object and id.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param id id of this object
	 */
	public Wall(int x, int y, ID id) {
		super(x, y, id);
	}

	@Override
	public void tick() {

	}
	/**
	 * Renders the wall object to the screen.
	 */
	@Override
	public void render(Graphics g) {
		if (World.theme == 0) {
			g.drawImage(Assets.ruins[1], x, y, width, height, null);
		} else if (World.theme == 1) {
			g.drawImage(Assets.winter[1], x, y, width, height, null);
		} else if (World.theme == 2) {
			g.drawImage(Assets.desert[1], x, y, width, height, null);
		}

	}
	/**
	 * Returns the bounds of this object.
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, width, height);
	}

}
