package com.boxmanteam.menu;

/**
 * This class gets the player name
 *
 */
public class PlayerStats {

	String name;

	/**
	 * This sets the player name
	 * 
	 * @param name
	 */
	public void setName(char[] name) {
		for (int i = 0; i < name.length; i++) {
			this.name += Character.toString(name[i]);
		}
	}
}
