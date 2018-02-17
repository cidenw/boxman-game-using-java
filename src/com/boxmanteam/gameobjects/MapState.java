package com.boxmanteam.gameobjects;

import java.util.ArrayList;

import com.boxmanteam.graphics.Coord;
/**
 * This class represents a map state.
 * @author Waleed Occidental
 *
 */
public class MapState {
	/**
	 * List of all the coordinates of box object.
	 */
	ArrayList<Coord> boxes;
	/**
	 * Stores the coordinates of the player.
	 */
	Coord player;
	/**
	 * 
	 * @return list of boxes
	 */
	public ArrayList<Coord> getBoxes() {
		return boxes;
	}
	/**
	 * 
	 * @return the coordinates of the player
	 */
	public Coord getPlayer() {
		return player;
	}
	/**
	 * This constructor sets the boxes' and player's coordinates.
	 * @param boxes
	 * @param player
	 */
	public MapState(ArrayList<Coord> boxes, Coord player) {
		this.boxes = new ArrayList<Coord>(boxes);
		this.player = player;
	}
}
