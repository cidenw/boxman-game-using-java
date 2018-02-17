package com.boxmanteam.graphics;

import java.awt.image.BufferedImage;

/*
 * This class is the character class that keeps all the movement frames*
 * */
public class Character {

	private BufferedImage[] player_down;
	private BufferedImage[] player_up;
	private BufferedImage[] player_left;
	private BufferedImage[] player_right;
	private BufferedImage player_stand;

	/**
	 * This is constructor receives the motion frames of a character
	 * 
	 * @param player_up
	 * @param player_right
	 * @param player_down
	 * @param player_left
	 * @param player_stand
	 */
	public Character(BufferedImage[] player_up, BufferedImage[] player_right, BufferedImage[] player_down,
			BufferedImage[] player_left, BufferedImage player_stand) {
		this.player_down = player_down;
		this.player_up = player_up;
		this.player_left = player_left;
		this.player_right = player_right;
		this.player_stand = player_stand;
	}

	/**
	 * Gets down motion frame
	 **/
	public BufferedImage[] getPlayer_down() {
		return player_down;
	}

	/**
	 * Gets up motion frame
	 **/
	public BufferedImage[] getPlayer_up() {
		return player_up;
	}

	/**
	 * Gets left motion frame
	 **/
	public BufferedImage[] getPlayer_left() {
		return player_left;
	}

	/**
	 * Gets right motion frame
	 **/
	public BufferedImage[] getPlayer_right() {
		return player_right;
	}

	/**
	 * Gets standing frame
	 **/
	public BufferedImage getPlayer_stand() {
		return player_stand;
	}
}
