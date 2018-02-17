package com.boxmanteam.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import com.boxmanteam.gameobjects.Box;
import com.boxmanteam.gameobjects.GameObject;
import com.boxmanteam.gameobjects.MapState;
import com.boxmanteam.gameobjects.Player;
import com.boxmanteam.gameobjects.Target;
import com.boxmanteam.gameobjects.Wall;
import com.boxmanteam.graphics.Assets;
import com.boxmanteam.graphics.Coord;
import com.boxmanteam.graphics.World;
import com.boxmanteam.menu.Menu;

public class Handler {
	/**
	 * Stores the current level.
	 */
	private int currentLevel;
	/**
	 * Stores the current integer representation of the character selected by
	 * the user. 0 - Kenny, 1 - Cartman, 2 - Red, 3 - Goku, 4 - Deadpool
	 */
	private int currentChar;
	/**
	 * Timer used in the tick method. Animates the varying background of the
	 * title.
	 */
	private long timer;
	/**
	 * Timer used in the tick method. Animates the varying background of the
	 * title.
	 */
	private long lastTime;
	/**
	 * ArrayList of all the game objects. Includes the boxes, walls, player,
	 * target.
	 */
	ArrayList<GameObject> object;
	/**
	 * List of all the World objects/maps.
	 * 
	 * @see ArrayList
	 */
	private ArrayList<World> maps;
	/**
	 * Stack of all the undo states
	 * 
	 * @see ArrayList
	 */
	private Stack<MapState> undoStates;
	/**
	 * Stack of all the redo states.
	 * 
	 * @see Stack
	 */
	private Stack<MapState> redoStates;

	/**
	 * This construction initializes all the fields.
	 * 
	 * @see Stack
	 */
	public Handler() {
		this.maps = Assets.maps;
		currentLevel = 0;
		currentChar = 0;
		timer = 0;
		lastTime = 0;
		object = new ArrayList<GameObject>();
		undoStates = new Stack<MapState>();
		redoStates = new Stack<MapState>();
	}

	/**
	 * Updates all the variables of all game objects.
	 */
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
		if (currentLevel == 0) {
			timer += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			if (creatorCurrentX >= 840) {
				creatorCurrentX = 48;
			} else {
				creatorCurrentX += 3;
			}

			if (timer >= 750) {
				if (World.theme == 2) {
					setCurrentTheme(0);
				} else {
					setCurrentTheme(World.theme + 1);
				}
				timer = 0;
			}
		}
	}

	int creatorCurrentX = 48;

	/**
	 * Render the world and all the game objects excluding the menu.
	 * 
	 * @param g
	 *            This graphics g will be used to draw on the window.
	 */
	public void render(Graphics g) {
		maps.get(currentLevel).render(g);
		if (currentLevel == 0) {
			timer += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			if (timer >= 750) {
				if (World.theme == 2) {
					setCurrentTheme(0);
				} else {
					setCurrentTheme(World.theme + 1);
				}
				timer = 0;
			}
			Font font = new Font("Arial", 1, 15);
			g.setFont(font);

			g.setColor(new Color(0, 10, 0, 100));
			g.fillRect(48, 910, 915, 40);
			g.setColor(Color.yellow);
			g.drawString("Waleed and Sean", creatorCurrentX, 935);

			// g.drawRect(48, 910, 915, 40);

		}
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}

	/**
	 * Checks if all the Box objects are all intersecting with all the Target
	 * objects. If solved it will call the solved() method.
	 * 
	 * @see void com.boxmanteam.core.Handler.solved()
	 */
	public void checkMap() {
		ArrayList<GameObject> boxes = new ArrayList<GameObject>();
		ArrayList<GameObject> targets = new ArrayList<GameObject>();

		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject.getId() == ID.Box) {
				boxes.add(tempObject);
			} else if (tempObject.getId() == ID.Target) {
				targets.add(tempObject);
			}
		}
		boolean isSolved = true;
		for (int i = 0; i < boxes.size(); i++) {
			if (!collisionWithTarget(boxes.get(i), targets)) {
				isSolved = false;
				break;
			}
		}

		if (isSolved) {
			solved();
		}
	}

	// cheat
	// delete after defense
	public void solve() {
		solved();
	}

	/**
	 * Calls the prompt method from the Menu class. Calls the saveSolveTime()
	 * and saveSolveMoves() methods.
	 */
	private void solved() {
		if (Menu.hudState == Menu.HUDSTATE.FreePlayHUD) {
			if (currentLevel == 15) {
				AudioPlayer.getSound("youwin").play();
			}
			Menu.showLevelPrompt();
		}
		if (Menu.hudState == Menu.HUDSTATE.TimeAttackHUD) {
			if (currentLevel == 15) {
				AudioPlayer.getSound("youwin").play();
			}
			saveSolveTime(Menu.startTime - Menu.timer);
			Menu.showLevelPrompt();

		}
		if (Menu.hudState == Menu.HUDSTATE.LimitedMovesHUD)
			if (currentLevel == 15) {
				AudioPlayer.getSound("youwin").play();
			}
		{
			saveSolveMoves(Menu.moves);
			Menu.showLevelPrompt();

		}

	}

	/**
	 * Calls the addMoves method from the Scores class.
	 * 
	 * @param moves
	 *            the number of moves done by the player for a particular level
	 * @see Scores
	 */
	private void saveSolveMoves(int moves) {
		String name = Character.toString(Menu.name[0]) + Character.toString(Menu.name[1])
				+ Character.toString(Menu.name[2]) + Character.toString(Menu.name[3])
				+ Character.toString(Menu.name[4]);
		Scores.addMoves(name, currentLevel, moves);
	}

	/**
	 * Calls the addTime method from the Scores class.
	 * 
	 * @param time
	 *            the total time in milliseconds the player solved the current
	 *            puzzle.
	 * @see Scores
	 */
	private void saveSolveTime(long time) {
		String name = Character.toString(Menu.name[0]) + Character.toString(Menu.name[1])
				+ Character.toString(Menu.name[2]) + Character.toString(Menu.name[3])
				+ Character.toString(Menu.name[4]);
		Scores.addTime(name, currentLevel, time);
	}

	/**
	 * Checks if a box intersects with any of the targets.
	 * 
	 * @param box
	 *            the box to be checked with the target location/goal
	 * @param targets
	 *            the targets which the box is checked if they intersect.
	 * @return true a box intersects with a target/goal
	 */
	private boolean collisionWithTarget(GameObject box, ArrayList<GameObject> targets) {
		for (int i = 0; i < targets.size(); i++) {
			if (box.getBounds().intersects(targets.get(i).getBounds())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Clears the Stack of undo and redo States. Calls the reset method from
	 * Menu class.
	 * 
	 * @see Menu
	 */
	public void reset() {
		undoStates.clear();
		redoStates.clear();
		Menu.reset();
	}

	/**
	 * Create objects for the current level. Adds all the newly created objects
	 * to the List object.
	 * 
	 * @param level
	 *            the current level
	 */
	public void loadWorldObjects(int level) {
		ArrayList<Coord> wall = maps.get(level).getWall();
		ArrayList<Coord> box = maps.get(level).getBox();
		ArrayList<Coord> target = maps.get(level).getTarget();
		for (int i = 0; i < target.size(); i++) {
			int x = target.get(i).getX();
			int y = target.get(i).getY();
			object.add(new Target(x * 48, y * 48, ID.Target));
		}
		for (int i = 0; i < wall.size(); i++) {
			int x = wall.get(i).getX();
			int y = wall.get(i).getY();
			object.add(new Wall(x * 48, y * 48, ID.Wall));
		}
		for (int i = 0; i < box.size(); i++) {
			int x = box.get(i).getX();
			int y = box.get(i).getY();
			object.add(new Box(x * 48, y * 48, ID.Box, this));
		}

	}

	/**
	 * Loads all the objects and initializes the timer and move counter of the
	 * Menu class. Clears all the objects currently loaded before loading a new.
	 * 
	 * @see Menu
	 */
	public void loadWorld() {
		reset();
		object.clear();
		this.loadWorldObjects(currentLevel);
		object.add(new Player(Assets.maps.get(currentLevel).getPlayerPos().getX() * 48,
				Assets.maps.get(currentLevel).getPlayerPos().getY() * 48, ID.Player, Assets.character[currentChar],
				this));
		Menu.timer = maps.get(currentLevel).getTimer() + 1000;
		Menu.startTime = Menu.timer;
		Menu.lastTime = System.currentTimeMillis();
		Menu.maxMoves = maps.get(currentLevel).getMaxMoves();
	}

	/**
	 * Adds the current state to the undo Stack.
	 */
	public void addState() {
		ArrayList<Coord> boxes = new ArrayList<Coord>();
		Coord player = new Coord(0, 0);

		for (int i = 0; i < object.size(); i++) {
			GameObject temp = object.get(i);
			if (temp.getId() == ID.Player) {
				int x = temp.getX();
				int y = temp.getY();
				player = new Coord(x, y);
			}
			if (temp.getId() == ID.Box) {
				int x = temp.getX();
				int y = temp.getY();
				boxes.add(new Coord(x, y));
			}
		}
		MapState mapState = new MapState(boxes, player);
		undoStates.push(mapState);
	}

	/**
	 * Adds the current state to the redo Stack.
	 */
	public void addRedoState() {
		ArrayList<Coord> boxes = new ArrayList<Coord>();
		Coord player = new Coord(0, 0);

		for (int i = 0; i < object.size(); i++) {
			GameObject temp = object.get(i);
			if (temp.getId() == ID.Player) {
				int x = temp.getX();
				int y = temp.getY();
				player = new Coord(x, y);
			}
			if (temp.getId() == ID.Box) {
				int x = temp.getX();
				int y = temp.getY();
				boxes.add(new Coord(x, y));
			}
		}
		MapState mapState = new MapState(boxes, player);
		redoStates.push(mapState);
	}

	/**
	 * Clears the redo Stack. Used by the moveX and moveY method of the Player
	 * class.
	 */
	public void emptyRedo() {
		redoStates.clear();
	}

	/**
	 * Pops the previous state from the undoStates Stack. Restores previous
	 * state of the player and boxes objects.
	 */
	public void undo() {
		if (undoStates.isEmpty()) {
			return;
		}
		Menu.moves--;
		addRedoState();
		MapState mapState = undoStates.pop();
		ArrayList<Coord> boxesCoords = mapState.getBoxes();
		Coord player = mapState.getPlayer();
		ArrayList<GameObject> boxes = new ArrayList<GameObject>();
		for (int i = 0; i < object.size(); i++) {
			GameObject temp = object.get(i);
			if (temp.getId() == ID.Player) {
				temp.setX(player.getX());
				temp.setY(player.getY());
			}
			if (temp.getId() == ID.Box) {
				boxes.add(temp);
			}
		}

		for (int i = 0; i < boxes.size(); i++) {
			GameObject temp = boxes.get(i);
			temp.setX(boxesCoords.get(i).getX());
			temp.setY(boxesCoords.get(i).getY());
		}

	}

	/**
	 * Pops the previous state from the redoStates Stack. Restores the previous
	 * state of the player and boxes objects.
	 */
	public void redo() {
		if (redoStates.isEmpty()) {
			return;
		}
		Menu.moves++;
		addState();
		MapState mapState = redoStates.pop();
		ArrayList<Coord> boxesCoords = mapState.getBoxes();
		Coord player = mapState.getPlayer();
		ArrayList<GameObject> boxes = new ArrayList<GameObject>();
		for (int i = 0; i < object.size(); i++) {
			GameObject temp = object.get(i);
			if (temp.getId() == ID.Player) {
				temp.setX(player.getX());
				temp.setY(player.getY());
			}
			if (temp.getId() == ID.Box) {
				boxes.add(temp);
			}
		}

		for (int i = 0; i < boxes.size(); i++) {
			GameObject temp = boxes.get(i);
			temp.setX(boxesCoords.get(i).getX());
			temp.setY(boxesCoords.get(i).getY());
		}

	}

	/**
	 * Pops undoState when the player move against a wall.
	 */
	public void mapStatePop() {
		undoStates.pop();
	}

	/**
	 * Empties the undo and redo Stacks.
	 */
	public void clearStates() {
		undoStates.clear();
		redoStates.clear();
	}

	/**
	 * @return object list of all the game objects
	 */
	public ArrayList<GameObject> getObject() {
		return object;
	}

	/**
	 * @return world map for the current level
	 */
	public World getCurrentWorld() {
		return maps.get(currentLevel);
	}

	/**
	 * sets the current theme
	 * 
	 * @param currentTheme
	 */
	public void setCurrentTheme(int currentTheme) {
		if (currentTheme > 2 || currentTheme < 0) {
			return;
		}
		World.theme = currentTheme;
	}

	/**
	 * 
	 * @return currentChar int representation of character
	 */
	public int getCurrentChar() {
		return currentChar;
	}

	/**
	 * sets the current character
	 * 
	 * @param currentChar
	 */
	public void setCurrentChar(int currentChar) {
		if (currentChar > 4 || currentChar < 0) {
			return;
		}
		this.currentChar = currentChar;
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject.getId() == ID.Player) {
				Player playerObject = (Player) tempObject;
				playerObject.setChar(currentChar);

			}
		}
	}

	/**
	 * 
	 * @return currentLevel current level
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * sets the current level
	 * 
	 * @param currentLevel
	 */
	public void setCurrentLevel(int currentLevel) {
		if (currentLevel > maps.size() - 1 || currentLevel < 0) {
			return;
		}
		this.currentLevel = currentLevel;
		loadWorld();
	}

}
