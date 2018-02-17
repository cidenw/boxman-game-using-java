package com.boxmanteam.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.boxmanteam.core.AudioPlayer;
//import com.boxmanteam.core.AudioPlayer;
import com.boxmanteam.core.Handler;
import com.boxmanteam.core.ID;
import com.boxmanteam.graphics.Assets;
import com.boxmanteam.graphics.Character;
import com.boxmanteam.graphics.Coord;
import com.boxmanteam.menu.Menu;

public class Player extends GameObject {
	/**
	 * Enumeration of directions where the character is facing.
	 * @author Waleed Occidental
	 *
	 */
	private enum DIRECTION {
		right, left, up, down;
	}
	/**
	 * Instance of handler class.
	 * @see Handler
	 */
	Handler handler;
	/**
	 * Instance of character class.
	 * @see Character
	 */
	Character chosenChar;
	/**
	 * Coordinates of this player.
	 */
	Coord coord;
	/**
	 * Current frame of the character animation.
	 */
	private int currentFrame;
	/**
	 * Instance of direction class.
	 * Stores the current direction.
	 */
	private DIRECTION direction;
	/**
	 * Walking down frames.
	 */
	private BufferedImage[] player_down;
	/**
	 * Walking up frames.
	 */
	private BufferedImage[] player_up;
	/**
	 * Walking left frames.
	 */
	private BufferedImage[] player_left;
	/**
	 * Walking right frames.
	 */
	private BufferedImage[] player_right;
	/**
	 * Destination of the player.
	 */
	private int destX, destY;

	/**
	 * This constructor sets the position, id and the default character of this player.
	 * @param x x position
	 * @param y y position
	 * @param id id of this game object
	 * @param chosenChar chosen character of this player.
	 * @param handler instance of the handler object
	 */
	public Player(int x, int y, ID id, Character chosenChar, Handler handler) {
		super(x, y, id);
		this.destX = x;
		this.destY = y;
		this.handler = handler;
		this.chosenChar = chosenChar;
		player_down = chosenChar.getPlayer_down();
		player_up = chosenChar.getPlayer_up();
		player_left = chosenChar.getPlayer_left();
		player_right = chosenChar.getPlayer_right();
		currentFrame = 0;
		direction = DIRECTION.down;
	}
	/**
	 * @return character chosen by this player
	 */
	public Character getChosenChar() {
		return chosenChar;
	}
	
	@Override
	public void tick() {
	}
	/**
	 * Change the current frame.
	 */
	public void nextFrame() {
		if (currentFrame == 2) {
			currentFrame = 0;
		} else {
			currentFrame += 1;
		}
		System.out.println(currentFrame);
	}
	/**
	 * Move the x position.
	 */
	public void moveX() {
		AudioPlayer.getSound("step").play();
		nextFrame();
		if (destX > this.x) {

			direction = DIRECTION.right;
		}
		if (destX < this.x) {
			direction = DIRECTION.left;
		}

		handler.addState();
		prevX = this.x;
		this.x = destX;
		if (collisionWithWall()) {
			this.x = prevX;
		}
		Box collidingBox = collisionWithBox();
		if (collidingBox != null) {
			if (destX > prevX) {
				if (!collidingBox.moveX(collidingBox.getX() + 48)) {
					this.x = prevX;
				}
			} else if (destX < prevX) {
				if (!collidingBox.moveX(collidingBox.getX() - 48)) {
					this.x = prevX;
				}
			}
		}
		if (this.x != prevX) {
			handler.emptyRedo();
			Menu.moves += 1;
			handler.checkMap();
		} else {
			handler.mapStatePop();
		}
	}
	/**
	 * Move the y position.
	 */
	public void moveY() {
		AudioPlayer.getSound("step").play();
		nextFrame();
		if (destY > this.y) {

			direction = DIRECTION.down;
		}
		if (destY < this.y) {
			direction = DIRECTION.up;
		}
		handler.addState();
		prevY = this.y;
		this.y = destY;
		if (collisionWithWall()) {
			this.y = prevY;
		}
		Box collidingBox = collisionWithBox();
		if (collidingBox != null) {
			if (destY > prevY) {
				if (!collidingBox.moveY(collidingBox.getY() + 48)) {
					this.y = prevY;
				}
			} else if (destY < prevY) {
				if (!collidingBox.moveY(collidingBox.getY() - 48)) {
					this.y = prevY;
				}
			}
		}
		if (this.y != prevY) {
			handler.emptyRedo();
			Menu.moves += 1;
			handler.checkMap();
		} else {
			handler.mapStatePop();
		}
	}
	/**
	 * Render the player's character on the screen.
	 */
	@Override
	public void render(Graphics g) {
		if (direction == DIRECTION.up) {
			g.drawImage(player_up[currentFrame], x, y, width, height, null);
		} else if (direction == DIRECTION.down) {
			g.drawImage(player_down[currentFrame], x, y, width, height, null);
		} else if (direction == DIRECTION.left) {
			g.drawImage(player_left[currentFrame], x, y, width, height, null);
		} else if (direction == DIRECTION.right) {
			g.drawImage(player_right[currentFrame], x, y, width, height, null);
		}
		// g.drawImage(player_stand, x, y, width, height, null);
	}
	/**
	 * Returns the bounds of this character.
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	/**
	 * Checks if this player is in collision with a wall.
	 * @return true if this player collides with a wall
	 */
	private boolean collisionWithWall() {
		for (int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			if (tempObject.getId() == ID.Wall) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Checks all the box that may collide with this player.
	 * @return box that collides with this player
	 */
	private Box collisionWithBox() {
		for (int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			if (tempObject.getId() == ID.Box) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					return (Box) tempObject;
				}
			}
		}
		return null;
	}
	/**
	 * Sets the current position of this player.
	 * @param playerPosition position of the player
	 */
	public void setPlayerPosition(Coord playerPosition) {
		this.coord = playerPosition;
	}
	/**
	 * Sets the current character of this player
	 * @param selected
	 */
	public void setChar(int selected) {
		this.player_down = Assets.character[selected].getPlayer_down();
		this.player_up = Assets.character[selected].getPlayer_up();
		this.player_left = Assets.character[selected].getPlayer_left();
		this.player_right = Assets.character[selected].getPlayer_right();
	}
	/**
	 * 
	 * @return destX x destination of this player
	 */
	public int getDestX() {
		return destX;
	}
	/**
	 * Sets the x destination of this player.
	 * @param destX
	 */
	public void setDestX(int destX) {
		this.destX = destX;
	}
	/**
	 * 
	 * @return destY
	 */
	public int getDestY() {
		return destY;
	}
	/**
	 * Sets the y destination of this player.
	 * @param velY
	 */
	public void setDestY(int destY) {
		this.destY = destY;
	}

}
