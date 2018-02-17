package com.boxmanteam.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.boxmanteam.gameobjects.GameObject;
import com.boxmanteam.gameobjects.Player;
import com.boxmanteam.menu.Menu;

/**
 * Handles all the keyboard inputs.
 * 
 * @author Waleed Occidental
 *
 */
public class KeyInput extends KeyAdapter {

	private Handler handler;
	/**
	 * This constructor links the handler.
	 * @param handler handler of all the game objects
	 */
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	/**
	 * Invoked when a key is pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (Menu.isPaused) {
			return;
		}
		if (Menu.gameState == Menu.STATE.Menu) {
			return;
		}
		if (key == KeyEvent.VK_F1) {
			handler.solve();
		}
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.Player) {
				// key events for player
				Player p = (Player) tempObject;
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
					p.setDestY(GameObject.limits(tempObject.getY() - 48, 48, Game.HEIGHT - 128));
					p.moveY();
				}
				if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
					// tempObject.setDestY(48);
					p.setDestY(GameObject.limits(tempObject.getY() + 48, 48, Game.HEIGHT - 128));
					p.moveY();
				}
				if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
					p.setDestX(GameObject.limits(tempObject.getX() + 48, 48, Game.WIDTH - 272 - 48 - 48));
					p.moveX();

				}
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
					p.setDestX(GameObject.limits(tempObject.getX() - 48, 48, Game.WIDTH - 272 - 48 - 48));
					p.moveX();
				}

			}
		}
	}
	
}
