package com.boxmanteam.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.boxmanteam.core.ID;
import com.boxmanteam.graphics.Assets;
import com.boxmanteam.graphics.World;
/**
 * This class represents a target where a box must coincide with.
 * This class extends GameObject.
 * @author Waleed Occidental
 *
 */
public class Target extends GameObject {
	/**
	 * This constructor sets the coordinates and id of this target game object.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param id id of this object
	 */
	public Target(int x, int y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
	/**
	 * Renders the target to the screen.
	 */
	@Override
	public void render(Graphics g) {
		if (World.theme == 0) {
			g.drawImage(Assets.ruins[3], x, y, width, height, null);
		} else if (World.theme == 1) {
			g.drawImage(Assets.winter[3], x, y, width, height, null);
		} else if (World.theme == 2) {
			g.drawImage(Assets.desert[3], x, y, width, height, null);
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
