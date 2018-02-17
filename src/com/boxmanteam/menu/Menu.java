package com.boxmanteam.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.boxmanteam.core.AudioPlayer;
import com.boxmanteam.core.Game;
import com.boxmanteam.core.Handler;
import com.boxmanteam.core.Scores;
import com.boxmanteam.graphics.Assets;

/**
 * This class prints the specific menus
 *
 */
public class Menu extends MouseAdapter {
	public enum STATE {
		Play, Menu
	}

	public enum GAMEMODE {
		TimeAttack, LeastMoves, FreePlay
	}

	public static STATE gameState = STATE.Menu;

	/**
	 * Gets the gameState*
	 * 
	 * @return
	 */
	public STATE getGameState() {
		return gameState;
	}

	private boolean BGM = false;
	public static int moves = 0;
	public static int maxMoves;
	public static int currentMoves;
	private int selectedChar = 0;
	private int selectedTheme = 0;
	public static boolean isPaused = false;
	public static char[] name;
	public static int timer;
	public static int startTime;
	public static long now, lastTime = 0;
	private boolean[] mainClicked = new boolean[5];
	private boolean[] modeClicked = new boolean[5];
	private boolean[] helpClicked = new boolean[5];
	private boolean[] aboutClicked = new boolean[2];
	private boolean[] instructionsClicked = new boolean[2];
	private boolean[] controlsClicked = new boolean[2];
	private boolean[] settingsClicked = new boolean[17];
	private boolean[] freePlayClicked = new boolean[9];
	private boolean[] timeAttackClicked = new boolean[9];
	private boolean[] limitedMovesClicked = new boolean[7];
	private boolean[] highscoreClicked = new boolean[4];
	private boolean[] levelPromptClicked = new boolean[2];
	private int highscore_current = 0;
	// private HUDSTATE previousHudState;
	public static boolean freePlay_LevelPrompt = false;
	public static boolean timeAttack_levelPrompt = false;
	public static boolean limitedMoves_LevelPrompt = false;
	public static boolean isGameOver = false;
	Game game;
	Handler handler;
	PlayerStats stats;

	/**
	 * Makes the enums for the HUDSTATE
	 * 
	 * @author walee
	 *
	 */
	public enum HUDSTATE {
		Main, Settings, Mode, FreePlayHUD, TimeAttackHUD, LimitedMovesHUD, Help, AboutTheGame, Highscore, Instructions, Controls
	}

	public static HUDSTATE hudState = HUDSTATE.Main;
	private HUDSTATE prevState;

	/**
	 * This constructor constructs the menu and sets the game object and the
	 * handler and sets default name to blank
	 * 
	 * @param handler
	 *            - handler that handles the game object
	 */
	public Menu(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		stats = new PlayerStats();
		name = new char[5];
		name[0] = 32;
		name[1] = 32;
		name[2] = 32;
		name[3] = 32;
		name[4] = 32;
	}

	/**
	 * This method accepts the mousevent and changes the state depending on what
	 * is pressed
	 */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (hudState == HUDSTATE.Main) {
			mainMenuState_pressed(mx, my);
		}

		if (hudState == HUDSTATE.Mode) {
			modeMenuState_pressed(mx, my);
		}
		if (hudState == HUDSTATE.Help) {
			helpMenuState_pressed(mx, my);
		}
		if (hudState == HUDSTATE.AboutTheGame) {
			aboutTheGameState_pressed(mx, my);
		}
		if (hudState == HUDSTATE.Instructions) {
			instructionsState_pressed(mx, my);
		}

		if (hudState == HUDSTATE.Controls) {
			controlsState_pressed(mx, my);
		}
		if (hudState == HUDSTATE.Settings) {
			settingsState_pressed(mx, my);
		}

		if (hudState == HUDSTATE.FreePlayHUD) {
			freePlayState_pressed(mx, my);
			if (freePlay_LevelPrompt) {
				freePlay_levelPrompt_pressed(mx, my);
			}
		}

		if (hudState == HUDSTATE.TimeAttackHUD) {
			timeAttackState_pressed(mx, my);
		}
		if (hudState == HUDSTATE.LimitedMovesHUD) {
			limitedMovesState_pressed(mx, my);
		}

		if (hudState == HUDSTATE.Highscore) {
			highscoreState_pressed(mx, my);
		}
	}

	/**
	 * Receives the mouse event when it is released and changes the state
	 * respectively
	 */
	public void mouseReleased(MouseEvent e) {
		if (hudState == HUDSTATE.Main) {
			mainMenuState_released();
		}
		if (hudState == HUDSTATE.Mode) {
			modeMenuState_released();
		}
		if (hudState == HUDSTATE.Settings) {
			settingsState_released();
		}
		if (hudState == HUDSTATE.Help) {
			helpMenuState_released();
		}
		if (hudState == HUDSTATE.Instructions) {
			instructionsState_released();
		}
		if (hudState == HUDSTATE.FreePlayHUD) {
			freePlayState_released();
			if (freePlay_LevelPrompt) {
				freePlay_levelPrompt_released();
			}
		}
		if (hudState == HUDSTATE.AboutTheGame) {
			aboutTheGameState_released();
		}
		if (hudState == HUDSTATE.Controls) {
			controlsState_released();
		}
		if (hudState == HUDSTATE.LimitedMovesHUD) {
			limitedMovesState_released();
		}
		if (hudState == HUDSTATE.TimeAttackHUD) {
			timeAttackState_released();

		}
		if (hudState == HUDSTATE.Highscore) {
			highscoreState_released();
		}
	}

	/**
	 * This method clamps the alphabet so they wont go out of bounds of the 26
	 * letters
	 * 
	 * @param x
	 *            - char value
	 * @return char value
	 */
	private char clampAlphabet(char x) {
		if (x > 90) {
			return 32;
		} else if (x < 65) {
			if (x < 32) {
				return 90;
			}
			if (x == 33) {
				return 65;
			}
			return 32;
		} else
			return x;
	}

	/**
	 * Checks if the mouse coordinates is within the bounds
	 * 
	 * @param mx
	 * @param my
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * Resets the move to 0
	 */
	public static void reset() {
		moves = 0;
	}

	/**
	 * This method sets the boolean for the graphics buttons
	 * 
	 * @param mx
	 * @param my
	 */
	private void mainMenuState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 237, 284, 192, 64)) { // PLAY BUTTON
			mainClicked[0] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 237, 382, 192, 64)) { // HELP BUTTON
			mainClicked[1] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 237, 480, 192, 64)) { // HIGHSCORES
																	// BUTTON
			mainClicked[2] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 237, 578, 192, 64)) { // SETTINGS
																	// BUTTON
			mainClicked[3] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 237, 676, 192, 64)) { // QUIT
			mainClicked[4] = true;
		}
	}

	/**
	 * This uses the booleans from the pressed method and changes the state and
	 * graphics respectively
	 */
	private void mainMenuState_released() {
		if (mainClicked[0]) {
			hudState = HUDSTATE.Mode;
			mainClicked[0] = false;
		}
		if (mainClicked[1]) {
			hudState = HUDSTATE.Help;
			mainClicked[1] = false;
		}
		if (mainClicked[2]) {
			// hudState = HUDSTATE.Settings;
			hudState = HUDSTATE.Highscore;
			mainClicked[2] = false;
		}
		if (mainClicked[3]) {
			hudState = HUDSTATE.Settings;
			mainClicked[3] = false;
		}
		if (mainClicked[4]) {
			exitGame();
			mainClicked[4] = false;
		}
	}

	/**
	 * Renders the main menu graphics
	 * 
	 * @param g
	 *            - graphics object
	 */
	private void mainMenu_render(Graphics g) {
		g.drawImage(Assets.hud_main, Game.WIDTH - 274, 0, 268, 960, null);

		// Play BUTTOn
		if (mainClicked[0]) {
			g.drawImage(Assets.button_play, Game.WIDTH - 237, 284, null);
		}

		// HELP BUTTON
		if (mainClicked[1]) {
			g.drawImage(Assets.button_help, Game.WIDTH - 237, 382, null);
		}

		// HIGH SCORES
		if (mainClicked[2]) {
			g.drawImage(Assets.button_highScores, Game.WIDTH - 237, 480, null);
		}

		// SETTINGS
		if (mainClicked[3]) {
			g.drawImage(Assets.button_settings, Game.WIDTH - 237, 578, null);
		}

		// QUIT
		if (mainClicked[4]) {
			g.drawImage(Assets.button_quit, Game.WIDTH - 237, 676, null);
		}
	}

	/**
	 * This method sets the boolean for the graphics buttons
	 * 
	 * @param mx
	 * @param my
	 */
	private void modeMenuState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 236, 305, 192, 64)) {
			modeClicked[0] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 236, 415, 192, 64)) { // TimeAttack
			modeClicked[1] = true;
		}
		if (mouseOver(mx, my, Game.WIDTH - 236, 525, 192, 64)) {
			modeClicked[2] = true;
		}
		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			modeClicked[3] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 91, 870, 48, 48)) {
			// exit
			modeClicked[4] = true;
		}
	}

	/*
	 * This uses the booleans from the pressed method and changes the state and
	 * graphics respectively
	 */
	private void modeMenuState_released() {
		if (modeClicked[0]) {
			hudState = HUDSTATE.FreePlayHUD;
			gameState = STATE.Play;
			handler.reset();
			handler.setCurrentTheme(selectedTheme);
			handler.setCurrentChar(selectedChar);
			handler.setCurrentLevel(1);
			handler.loadWorld();
			modeClicked[0] = false;
		}
		if (modeClicked[1]) {
			hudState = HUDSTATE.TimeAttackHUD;
			gameState = STATE.Play;
			handler.setCurrentTheme(selectedTheme);
			handler.setCurrentChar(selectedChar);
			handler.setCurrentLevel(1);
			handler.loadWorld();
			modeClicked[1] = false;
		}
		if (modeClicked[2]) {

			hudState = HUDSTATE.LimitedMovesHUD;
			gameState = STATE.Play;
			handler.setCurrentTheme(selectedTheme);
			handler.setCurrentChar(selectedChar);
			handler.setCurrentLevel(1);
			handler.loadWorld();
			modeClicked[2] = false;
		}
		if (modeClicked[3]) {
			hudState = HUDSTATE.Main;
			modeClicked[3] = false;
		}
		if (modeClicked[4]) {
			exitGame();
			modeClicked[0] = false;
		}
	}

	/**
	 * This renders the mode menu graphics
	 * 
	 * @param g
	 */
	private void modeMenu_render(Graphics g) {
		g.setColor(Color.white);

		g.drawImage(Assets.hud_mode, Game.WIDTH - 274, 0, 268, 960, null);
		if (modeClicked[0])
			g.drawImage(Assets.button_freePlay, Game.WIDTH - 236, 305, null);
		if (modeClicked[1])
			g.drawImage(Assets.button_timeAttack, Game.WIDTH - 236, 415, null);
		if (modeClicked[2])
			g.drawImage(Assets.button_limitedMoves, Game.WIDTH - 236, 525, null);
		if (modeClicked[3])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (modeClicked[4])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 870, null);
	}

	/**
	 * This method changes the booleans for the graphic buttons
	 * 
	 * @param mx
	 * @param my
	 */
	private void freePlayState_pressed(int mx, int my) {
		if (!isPaused) {
			// UNDO

			if (mouseOver(mx, my, Game.WIDTH - 233, 700, 48, 48)) {
				freePlayClicked[0] = true;
			}
			// REDO
			if (mouseOver(mx, my, Game.WIDTH - 97, 700, 48, 48)) {
				freePlayClicked[1] = true;

			}

			// PREVIOUS LEVEL
			if (mouseOver(mx, my, Game.WIDTH - 238, 424, 48, 48)) {
				freePlayClicked[2] = true;
			}
			// NEXT LEVEL
			if (mouseOver(mx, my, Game.WIDTH - 104, 424, 48, 48)) {
				freePlayClicked[3] = true;
			}

			// RESET
			if (mouseOver(mx, my, Game.WIDTH - 91, 810, 48, 48)) {
				freePlayClicked[5] = true;
			}
		}

		// PAUSE
		if (mouseOver(mx, my, Game.WIDTH - 162, 810, 48, 48)) {
			freePlayClicked[4] = true;
		}

		// BACK
		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			freePlayClicked[6] = true;
		}
		// Settings
		if (mouseOver(mx, my, Game.WIDTH - 162, 869, 48, 48)) {
			freePlayClicked[7] = true;
		}
		// exit
		if (mouseOver(mx, my, Game.WIDTH - 91, 869, 48, 48)) {
			freePlayClicked[8] = true;
		}
	}

	/**
	 * This method changes the graphic buttons and states respectively
	 */
	private void freePlayState_released() {
		if (freePlayClicked[0]) {
			handler.undo();
			freePlayClicked[0] = false;
		}
		if (freePlayClicked[1]) {
			handler.redo();
			freePlayClicked[1] = false;
		}
		// prev lev
		if (freePlayClicked[2]) {
			if (handler.getCurrentLevel() == 1) {
				freePlayClicked[2] = false;
				return;
			}
			handler.setCurrentLevel(handler.getCurrentLevel() - 1);
			handler.reset();
			freePlayClicked[2] = false;
		}
		// next lev
		if (freePlayClicked[3]) {
			handler.setCurrentLevel(handler.getCurrentLevel() + 1);
			handler.reset();
			freePlayClicked[3] = false;
		}
		if (freePlayClicked[4]) {
			if (!freePlay_LevelPrompt) {
				if (isPaused) {
					isPaused = false;
				} else {
					isPaused = true;
				}
				freePlayClicked[4] = false;
			}

		}
		if (freePlayClicked[5]) {
			handler.reset();
			handler.loadWorld();
			freePlayClicked[5] = false;
		}
		if (freePlayClicked[6]) {
			freePlay_LevelPrompt = false;
			setPaused(false);
			gameState = STATE.Menu;
			hudState = HUDSTATE.Mode;
			handler.setCurrentLevel(0);
			freePlayClicked[6] = false;
		}

		if (freePlayClicked[7]) {
			setPaused(true);
			prevState = hudState;
			hudState = HUDSTATE.Settings;
			freePlayClicked[7] = false;
		}
		if (freePlayClicked[8]) {
			freePlayClicked[8] = false;
			exitGame();
		}
	}

	/**
	 * This method renders the freeplay menu
	 * 
	 * @param g
	 */
	private void freePlay_render(Graphics g) {
		if (freePlay_LevelPrompt) {

			freePlay_levelPrompt_render(g);
		}
		g.setColor(Color.white);
		Font font = new Font("Jokerman", 1, 14);
		g.setFont(font);
		g.drawImage(Assets.hud_game, Game.WIDTH - 274, 0, 268, 960, null);
		// AVATAR
		g.drawImage(Assets.avatar[handler.getCurrentChar()], 1030, 200, null);
		// NAME
		g.drawString(
				Character.toString(Menu.name[0]) + Character.toString(Menu.name[1]) + Character.toString(Menu.name[2])
						+ Character.toString(Menu.name[3]) + Character.toString(Menu.name[4]),
				1170, 260);

		font = new Font("Arial", 1, 25);
		g.setFont(font);
		// moves
		g.drawString(Integer.toString(Menu.moves), Game.WIDTH - 165, 733);
		// LEVEL
		g.drawImage(Assets.levelBox[0], 1045, 400, null);
		if (handler.getCurrentLevel() > 0) {
			g.drawImage(Assets.levelIcons[handler.getCurrentLevel() - 1], 1111, 422, null);
		}
		if (freePlayClicked[0])
			g.drawImage(Assets.icon_undo, Game.WIDTH - 233, 700, null);
		if (freePlayClicked[1])
			g.drawImage(Assets.icon_redo, Game.WIDTH - 97, 700, null);
		if (freePlayClicked[2])
			g.drawImage(Assets.icon_leftArrow, Game.WIDTH - 238, 424, null);
		if (freePlayClicked[3])
			g.drawImage(Assets.icon_rightArrow, Game.WIDTH - 104, 424, null);
		if (isPaused)
			g.drawImage(Assets.icon_play, Game.WIDTH - 162, 810, null);
		if (freePlayClicked[5])
			g.drawImage(Assets.icon_reset, Game.WIDTH - 91, 810, null);
		if (freePlayClicked[6])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (freePlayClicked[7])
			g.drawImage(Assets.icon_settings, Game.WIDTH - 162, 869, null);
		if (freePlayClicked[8])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 869, null);

	}

	/**
	 * This method changes the booleans for the graphic buttons
	 * 
	 * @param mx
	 * @param my
	 */
	private void freePlay_levelPrompt_pressed(int mx, int my) {
		if (mouseOver(mx, my, 225, 535, 192, 64)) {
			levelPromptClicked[0] = true;
		}
		if (mouseOver(mx, my, 585, 535, 192, 64)) {
			levelPromptClicked[1] = true;
		}
	}

	/**
	 * This method changes the buttons and the states respectively
	 */
	private void freePlay_levelPrompt_released() {
		if (levelPromptClicked[0]) {
			levelPromptClicked[0] = false;
			Menu.moves = 0;
			handler.clearStates();
			if (handler.getCurrentLevel() == 15) {
				handler.setCurrentLevel(1);
			} else {
				handler.setCurrentLevel(handler.getCurrentLevel() + 1);
			}
			handler.loadWorld();
			freePlay_LevelPrompt = false;
			isPaused = false;
		}
		if (levelPromptClicked[1]) {
			levelPromptClicked[1] = false;
			Menu.moves = 0;
			handler.clearStates();
			handler.setCurrentLevel(0);
			hudState = HUDSTATE.Main;
			freePlay_LevelPrompt = false;
			isPaused = false;
		}
	}

	/**
	 * Renders the freeplay menu
	 * 
	 * @param g
	 */
	private void freePlay_levelPrompt_render(Graphics g) {
		if (handler.getCurrentLevel() == 15) {
			g.drawImage(Assets.youWin, 320, 300, null);
		} else {
			g.drawImage(Assets.puzzleSolved_freePlay, 260, 250, null);
		}

		if (handler.getCurrentLevel() == 15) {
			g.drawImage(Assets.frame_end, 100, 200, null);
		} else {
			g.drawImage(Assets.frame_nextLevel, 100, 200, null);
		}

		g.setColor(Color.white);
		if (levelPromptClicked[0]) {
			if (handler.getCurrentLevel() == 15) {
				g.drawImage(Assets.button_playAgain, 225, 535, null);
			} else {
				g.drawImage(Assets.button_nextLevel, 225, 535, null);
			}

		}
		if (levelPromptClicked[1]) {
			g.drawImage(Assets.button_return, 585, 535, null);
		}
	}

	/**
	 * This method changes the booleans for the graphic buttons
	 * 
	 * @param mx
	 * @param my
	 */
	private void timeAttackState_pressed(int mx, int my) {
		if (timeAttack_levelPrompt) {
			timeAttack_levelPrompt_pressed(mx, my);
		}
		if (!isPaused) {
			// UNDO

			if (mouseOver(mx, my, Game.WIDTH - 233, 700, 48, 48)) {
				timeAttackClicked[0] = true;
			}
			// REDO
			if (mouseOver(mx, my, Game.WIDTH - 97, 700, 48, 48)) {
				timeAttackClicked[1] = true;

			}

			// PREVIOUS LEVEL
			if (mouseOver(mx, my, Game.WIDTH - 238, 524, 48, 48)) {
				timeAttackClicked[2] = true;
			}
			// NEXT LEVEL
			if (mouseOver(mx, my, Game.WIDTH - 104, 524, 48, 48)) {
				timeAttackClicked[3] = true;
			}

			// RESET
			if (mouseOver(mx, my, Game.WIDTH - 91, 810, 48, 48)) {
				timeAttackClicked[5] = true;
			}
		}

		/**/
		if (mouseOver(mx, my, Game.WIDTH - 162, 810, 48, 48)) {
			if (!timeAttack_levelPrompt) {
				timeAttackClicked[4] = true;
			}
		}

		/**/

		// BACK
		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			timeAttackClicked[6] = true;
		}
		// Settings
		if (mouseOver(mx, my, Game.WIDTH - 162, 869, 48, 48)) {
			timeAttackClicked[7] = true;
		}
		// exit
		if (mouseOver(mx, my, Game.WIDTH - 91, 869, 48, 48)) {
			timeAttackClicked[8] = true;
		}
	}

	/**
	 * This method changes the graphics and states respectively
	 */
	private void timeAttackState_released() {
		if (timeAttack_levelPrompt) {
			timeAttack_levelPrompt_released();
		}
		if (timeAttackClicked[0]) {
			handler.undo();
			timeAttackClicked[0] = false;
		}
		if (timeAttackClicked[1]) {
			handler.redo();
			timeAttackClicked[1] = false;
		}
		if (timeAttackClicked[4]) {
			if (isPaused) {
				setPaused(false);
			} else {
				setPaused(true);
			}
			timeAttackClicked[4] = false;

		}
		if (timeAttackClicked[5]) {
			handler.reset();
			handler.loadWorld();
			timeAttackClicked[5] = false;
		}
		if (timeAttackClicked[6]) {
			Scores.write_highscores();
			gameState = Menu.STATE.Menu;
			hudState = HUDSTATE.Mode;
			isPaused = false;
			handler.setCurrentLevel(0);
			timeAttackClicked[6] = false;
		}

		if (timeAttackClicked[7]) {
			setPaused(true);
			prevState = hudState;
			hudState = HUDSTATE.Settings;
			timeAttackClicked[7] = false;
		}
		if (timeAttackClicked[8]) {
			Scores.write_highscores();
			timeAttackClicked[8] = false;
			exitGame();
		}
	}

	/**
	 * Renders the tiem attack menu
	 * 
	 * @param g
	 */
	private void timeAttack_render(Graphics g) {
		if (timeAttack_levelPrompt) {
			timeAttack_levelPrompt_render(g);
		}
		g.setColor(Color.white);
		Font font = new Font("Jokerman", 1, 14);
		g.setFont(font);
		// HUD
		g.drawImage(Assets.hud_game, Game.WIDTH - 274, 0, 268, 960, null);

		// AVATAR
		g.drawImage(Assets.avatar[handler.getCurrentChar()], 1030, 200, null);
		// NAME
		g.drawString(
				Character.toString(Menu.name[0]) + Character.toString(Menu.name[1]) + Character.toString(Menu.name[2])
						+ Character.toString(Menu.name[3]) + Character.toString(Menu.name[4]),
				1170, 260);
		// undo
	
		font = new Font("Arial", 1, 25);
		g.setFont(font);
		// moves
		g.drawString(Integer.toString(Menu.moves), Game.WIDTH - 165, 733);
		g.drawImage(Assets.levelIcons[handler.getCurrentLevel() - 1], 1111, 422, null);

		// redo
		
		if (timeAttackClicked[0])
			g.drawImage(Assets.icon_undo, Game.WIDTH - 233, 700, null);
		if (timeAttackClicked[1])
			g.drawImage(Assets.icon_redo, Game.WIDTH - 97, 700, null);
		if (isPaused)
			g.drawImage(Assets.icon_play, Game.WIDTH - 162, 810, null);
		if (timeAttackClicked[5])
			g.drawImage(Assets.icon_reset, Game.WIDTH - 91, 810, null);
		if (timeAttackClicked[6])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (timeAttackClicked[7])
			g.drawImage(Assets.icon_settings, Game.WIDTH - 162, 869, null);
		if (timeAttackClicked[8])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 869, null);

		// timebox
		g.drawImage(Assets.timeBox, 1022, 300, null);
		g.drawString(timeToString(), 1108, 380);

	}

	/**
	 * This method changes the booleans for the graphic buttons
	 * 
	 * @param mx
	 * @param my
	 */
	private void timeAttack_levelPrompt_pressed(int mx, int my) {
		if (mouseOver(mx, my, 225, 535, 192, 64)) {
			levelPromptClicked[0] = true;
		}
		if (mouseOver(mx, my, 585, 535, 192, 64)) {
			levelPromptClicked[1] = true;
		}
	}

	/**
	 * This method changes the graphics and states respectively
	 */
	private void timeAttack_levelPrompt_released() {
		if (levelPromptClicked[0]) {
			Scores.write_highscores();
			levelPromptClicked[0] = false;
			Menu.moves = 0;
			handler.clearStates();
			if (handler.getCurrentLevel() == 15 || isGameOver) {
				handler.setCurrentLevel(1);
			} else {
				handler.setCurrentLevel(handler.getCurrentLevel() + 1);
			}
			handler.loadWorld();
			timeAttack_levelPrompt = false;
			isPaused = false;
			isGameOver = false;
		}
		if (levelPromptClicked[1]) {
			Scores.write_highscores();
			levelPromptClicked[1] = false;
			Menu.moves = 0;
			handler.clearStates();
			handler.setCurrentLevel(0);
			hudState = HUDSTATE.Main;
			timeAttack_levelPrompt = false;
			isPaused = false;
			isGameOver = false;
		}
	}

	/**
	 * Renders the time attack menu
	 * 
	 * @param g
	 */
	private void timeAttack_levelPrompt_render(Graphics g) {
		if (isGameOver) {
			g.drawImage(Assets.gameOver, 320, 300, null);
		} else if (handler.getCurrentLevel() == 15) {
			g.drawImage(Assets.youWin, 320, 300, null);
		} else {
			g.setColor(Color.white);
			g.drawImage(Assets.puzzleSolved_timeAttack, 220, 200, null);
			Long time = Long.parseLong(Scores.currScores_time.get(handler.getCurrentLevel()).get("Time"));
			drawTime(g, time);
		}

		if (handler.getCurrentLevel() == 15 || isGameOver) {
			g.drawImage(Assets.frame_end, 100, 200, null);
		} else {
			g.drawImage(Assets.frame_nextLevel, 100, 200, null);
		}

		g.setColor(Color.white);
		if (levelPromptClicked[0]) {
			if (handler.getCurrentLevel() == 15 || isGameOver) {
				g.drawImage(Assets.button_playAgain, 225, 535, null);
			} else {
				g.drawImage(Assets.button_nextLevel, 225, 535, null);
			}

		}
		if (levelPromptClicked[1]) {
			g.drawImage(Assets.button_return, 585, 535, null);
		}
	}

	/**
	 * Draws the time left for the time menu
	 * 
	 * @param g
	 * @param time
	 */
	private void drawTime(Graphics g, long time) {
		int min = (int) time / 60000;
		int sec = (int) time % 60000 / 1000;
		if (min < 10) {
			g.drawImage(Assets.num[9], 472, 440, null);
			g.drawImage(Assets.num[getIndexNum(min)], 496, 440, null);
		} else {
			int tens = min / 10;
			int ones = min % 10;
			g.drawImage(Assets.num[getIndexNum(tens)], 472, 440, null);
			g.drawImage(Assets.num[getIndexNum(ones)], 496, 440, null);
		}
		if (sec < 10) {
			System.out.println(getIndexNum(sec));
			g.drawImage(Assets.num[9], 544, 440, null);
			g.drawImage(Assets.num[getIndexNum(sec)], 568, 440, null);
		} else {
			int tens = sec / 10;
			int ones = sec % 10;

			g.drawImage(Assets.num[getIndexNum(tens)], 544, 440, null);
			g.drawImage(Assets.num[getIndexNum(ones)], 568, 440, null);
		}

		g.drawImage(Assets.num[10], 520, 444, null);
	}

	/**
	 * Gets the index number
	 * 
	 * @param x
	 * @return
	 */
	private int getIndexNum(int x) {
		if (x > 0) {
			return x - 1;
		} else {
			return 9;
		}
	}

	/**
	 * This changes the booleans for the graphics button
	 * 
	 * @param mx
	 * @param my
	 */
	private void limitedMovesState_pressed(int mx, int my) {
		if (limitedMoves_LevelPrompt) {
			limitedMoves_levelPrompt_pressed(mx, my);
		}
		if (!isPaused) {
			// UNDO

			if (mouseOver(mx, my, Game.WIDTH - 233, 700, 48, 48)) {
				limitedMovesClicked[0] = true;
			}
			// REDO
			if (mouseOver(mx, my, Game.WIDTH - 97, 700, 48, 48)) {
				limitedMovesClicked[1] = true;

			}

			// RESET
			if (mouseOver(mx, my, Game.WIDTH - 91, 810, 48, 48)) {
				limitedMovesClicked[3] = true;
			}
		}

		/**/
		if (mouseOver(mx, my, Game.WIDTH - 162, 810, 48, 48)) {
			if (!limitedMoves_LevelPrompt) {
				limitedMovesClicked[2] = true;
			}

		}

		/**/

		// BACK
		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			limitedMovesClicked[4] = true;
		}
		// Settings
		if (mouseOver(mx, my, Game.WIDTH - 162, 869, 48, 48)) {
			limitedMovesClicked[5] = true;
		}
		// exit
		if (mouseOver(mx, my, Game.WIDTH - 91, 869, 48, 48)) {
			limitedMovesClicked[6] = true;
		}
	}

	/**
	 * This method changes the states and graphics respectively
	 */
	private void limitedMovesState_released() {
		if (limitedMoves_LevelPrompt) {
			limitedMoves_levelPrompt_released();
		}
		if (limitedMovesClicked[0]) {
			handler.undo();
			limitedMovesClicked[0] = false;
		}
		if (limitedMovesClicked[1]) {
			handler.redo();
			limitedMovesClicked[1] = false;
		}
		if (limitedMovesClicked[2]) {
			if (isPaused) {
				setPaused(false);
			} else {
				setPaused(true);
			}
			limitedMovesClicked[2] = false;

		}
		if (limitedMovesClicked[3]) {
			handler.reset();
			handler.loadWorld();
			limitedMovesClicked[3] = false;
		}
		if (limitedMovesClicked[4]) {
			Scores.write_highscores();
			hudState = HUDSTATE.Mode;
			gameState = Menu.STATE.Menu;

			isPaused = false;
			reset();
			handler.setCurrentLevel(0);
			limitedMovesClicked[4] = false;
		}

		if (limitedMovesClicked[5]) {
			setPaused(true);
			prevState = hudState;
			hudState = HUDSTATE.Settings;
			limitedMovesClicked[5] = false;
		}
		if (limitedMovesClicked[6]) {
			Scores.write_highscores();
			limitedMovesClicked[6] = false;
			exitGame();
		}
	}

	/**
	 * This method renders the limited moves menu
	 * 
	 * @param g
	 */
	private void limitedMoves_render(Graphics g) {
		if (limitedMoves_LevelPrompt) {
			limitedMoves_levelPrompt_render(g);
		}
		g.setColor(Color.white);
		Font font = new Font("Jokerman", 1, 14);
		g.setFont(font);
		// HUD
		g.drawImage(Assets.hud_game, Game.WIDTH - 274, 0, 268, 960, null);

		// AVATAR
		g.drawImage(Assets.avatar[handler.getCurrentChar()], 1030, 200, null);
		// NAME
		g.drawString(
				Character.toString(Menu.name[0]) + Character.toString(Menu.name[1]) + Character.toString(Menu.name[2])
						+ Character.toString(Menu.name[3]) + Character.toString(Menu.name[4]),
				1170, 260);
		font = new Font("Arial", 1, 25);
		g.setFont(font);
		// moves
		currentMoves = Menu.maxMoves - Menu.moves;
		if (!isGameOver) {
			if (currentMoves <= 0) {
				isGameOver = true;
				AudioPlayer.getSound("gameover").play();
				limitedMoves_LevelPrompt = true;
				isPaused = true;
			}
		}

		g.drawString(Integer.toString(currentMoves), Game.WIDTH - 165, 733);
		if (handler.getCurrentLevel() != 0) {
			g.drawImage(Assets.levelIcons[handler.getCurrentLevel() - 1], 1111, 322, null);
		}

		// redo
		// g.drawRect(Game.WIDTH - 236+136, 330, 48, 48);
		if (limitedMovesClicked[0])
			g.drawImage(Assets.icon_undo, Game.WIDTH - 233, 700, null);
		if (limitedMovesClicked[1])
			g.drawImage(Assets.icon_redo, Game.WIDTH - 97, 700, null);
		if (isPaused)
			g.drawImage(Assets.icon_play, Game.WIDTH - 162, 810, null);
		if (limitedMovesClicked[3])
			g.drawImage(Assets.icon_reset, Game.WIDTH - 91, 810, null);
		if (limitedMovesClicked[4])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (limitedMovesClicked[5])
			g.drawImage(Assets.icon_settings, Game.WIDTH - 162, 869, null);
		if (limitedMovesClicked[6])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 869, null);


	}

	/**
	 * This method changes the booleans for the graphic buttons
	 * 
	 * @param mx
	 * @param my
	 */
	private void limitedMoves_levelPrompt_pressed(int mx, int my) {
		if (mouseOver(mx, my, 225, 535, 192, 64)) {
			levelPromptClicked[0] = true;
		}
		if (mouseOver(mx, my, 585, 535, 192, 64)) {
			levelPromptClicked[1] = true;
		}

	}

	/**
	 * This method changes the graphics and states respectively
	 */
	private void limitedMoves_levelPrompt_released() {
		if (levelPromptClicked[0]) {
			Scores.write_highscores();
			levelPromptClicked[0] = false;
			Menu.moves = 0;
			handler.clearStates();
			if (handler.getCurrentLevel() == 15 || isGameOver) {
				handler.setCurrentLevel(1);
			} else {
				handler.setCurrentLevel(handler.getCurrentLevel() + 1);
			}
			handler.loadWorld();
			limitedMoves_LevelPrompt = false;
			isPaused = false;
			isGameOver = false;
		}
		if (levelPromptClicked[1]) {
			Scores.write_highscores();
			levelPromptClicked[1] = false;
			Menu.moves = 0;
			handler.clearStates();
			handler.setCurrentLevel(0);
			hudState = HUDSTATE.Main;
			limitedMoves_LevelPrompt = false;
			isPaused = false;
			isGameOver = false;
		}

	}

	/**
	 * This method renders the limitedMoves menu
	 * 
	 * @param g
	 */
	private void limitedMoves_levelPrompt_render(Graphics g) {
		if (isGameOver) {
			g.drawImage(Assets.gameOver, 320, 300, null);
		} else if (handler.getCurrentLevel() == 15) {
			g.drawImage(Assets.youWin, 320, 300, null);
		} else {
			g.setColor(Color.white);
			g.drawImage(Assets.puzzleSolved_limitedMoves, 220, 200, null);
			int moves = Integer.parseInt(Scores.currScores_moves.get(handler.getCurrentLevel()).get("Moves"));
			if (moves > 100) {
				int hundreds = (moves % 1000) / 100;
				int tens = ((moves - (moves / 100)) % 100) / 10;
				int ones = moves % 10;
				System.out.println(hundreds + "," + tens + "-" + ones);
				g.drawImage(Assets.num[getIndexNum(hundreds)], 440, 440, null);
				g.drawImage(Assets.num[getIndexNum(tens)], 465, 440, null);
				g.drawImage(Assets.num[getIndexNum(ones)], 490, 440, null);
			} else if (moves > 10) {
				int tens = moves / 10;
				int ones = moves % 10;
				g.drawImage(Assets.num[getIndexNum(tens)], 465, 440, null);
				g.drawImage(Assets.num[getIndexNum(ones)], 490, 440, null);
			} else {
				int ones = moves % 10;
				g.drawImage(Assets.num[getIndexNum(ones)], 490, 440, null);
			}

		}

		if (handler.getCurrentLevel() == 15 || isGameOver) {
			g.drawImage(Assets.frame_end, 100, 200, null);
		} else {
			g.drawImage(Assets.frame_nextLevel, 100, 200, null);
		}

		g.setColor(Color.white);
		if (levelPromptClicked[0]) {
			if (handler.getCurrentLevel() == 15 || isGameOver) {
				g.drawImage(Assets.button_playAgain, 225, 535, null);
			} else {
				g.drawImage(Assets.button_nextLevel, 225, 535, null);
			}

		}
		if (levelPromptClicked[1]) {
			g.drawImage(Assets.button_return, 585, 535, null);
		}

	}

	/**
	 * This changes the booleans for the graphics
	 * 
	 * @param mx
	 * @param my
	 */
	private void settingsState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 258, 167, 48, 48)) {
			settingsClicked[0] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 210, 167, 48, 48)) {
			settingsClicked[1] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 162, 167, 48, 48)) {

			settingsClicked[2] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 114, 167, 48, 48)) {
			settingsClicked[3] = true;
		}
		if (mouseOver(mx, my, Game.WIDTH - 66, 167, 48, 48)) {
			settingsClicked[4] = true;
		}
		// DOWN ARROW
		if (mouseOver(mx, my, Game.WIDTH - 258, 277, 48, 48)) {
			settingsClicked[5] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 210, 277, 48, 48)) {
			settingsClicked[6] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 162, 277, 48, 48)) {
			settingsClicked[7] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 114, 277, 48, 48)) {
			settingsClicked[8] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 66, 277, 48, 48)) {
			settingsClicked[9] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 212, 432, 48, 48)) {
			settingsClicked[10] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 114, 432, 48, 48)) {
			settingsClicked[11] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 208, 622, 48, 48)) {
			settingsClicked[12] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 114, 622, 48, 48)) {
			settingsClicked[13] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 236, 863, 48, 48)) {
			settingsClicked[14] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 91 - 72, 870, 48, 48)) {
			settingsClicked[15] = true;
		}
		if (mouseOver(mx, my, Game.WIDTH - 91, 863, 48, 48)) {
			settingsClicked[16] = true;
		}
	}

	/**
	 * This changes the graphics and states respectively
	 */
	private void settingsState_released() {
		if (settingsClicked[0]) {
			name[0] = clampAlphabet((char) (name[0] + 1));
			settingsClicked[0] = false;
		}
		if (settingsClicked[1]) {
			name[1] = clampAlphabet((char) (name[1] + 1));
			settingsClicked[1] = false;
		}
		if (settingsClicked[2]) {
			name[2] = clampAlphabet((char) (name[2] + 1));
			settingsClicked[2] = false;
		}
		if (settingsClicked[3]) {
			name[3] = clampAlphabet((char) (name[3] + 1));
			settingsClicked[3] = false;
		}
		if (settingsClicked[4]) {
			name[4] = clampAlphabet((char) (name[4] + 1));
			settingsClicked[4] = false;
		}
		if (settingsClicked[5]) {
			name[0] = clampAlphabet((char) (name[0] - 1));
			settingsClicked[5] = false;
		}
		if (settingsClicked[6]) {
			name[1] = clampAlphabet((char) (name[1] - 1));
			settingsClicked[6] = false;
		}
		if (settingsClicked[7]) {
			name[2] = clampAlphabet((char) (name[2] - 1));
			settingsClicked[7] = false;
		}
		if (settingsClicked[8]) {
			name[3] = clampAlphabet((char) (name[3] - 1));
			settingsClicked[8] = false;
		}
		if (settingsClicked[9]) {
			name[4] = clampAlphabet((char) (name[4] - 1));
			settingsClicked[9] = false;
		}
		if (settingsClicked[10]) {
			selectedChar += -1;
			if (selectedChar < 0)
				selectedChar = 4;
			handler.setCurrentChar(selectedChar);
			settingsClicked[10] = false;
		}
		if (settingsClicked[11]) {
			selectedChar += 1;
			if (selectedChar > 4)
				selectedChar = 0;
			handler.setCurrentChar(selectedChar);
			settingsClicked[11] = false;
		}
		if (settingsClicked[12]) {
			selectedTheme += -1;
			if (selectedTheme < 0)
				selectedTheme = 2;
			handler.setCurrentTheme(selectedTheme);
			settingsClicked[12] = false;
		}
		if (settingsClicked[13]) {
			selectedTheme += 1;
			if (selectedTheme > 2)
				selectedTheme = 0;
			handler.setCurrentTheme(selectedTheme);
			settingsClicked[13] = false;
		}
		if (settingsClicked[14]) {
			if (gameState == STATE.Play) {
				hudState = prevState;
				settingsClicked[14] = false;
				return;
			} else {
				hudState = HUDSTATE.Main;
				settingsClicked[14] = false;
			}
			settingsClicked[14] = false;
		}
		if (settingsClicked[15]) {
			if (BGM == false) {
				AudioPlayer.musicMap.get("bgm").loop();
				BGM = true;
			} else {
				AudioPlayer.musicMap.get("bgm").stop();
				BGM = false;
			}

			settingsClicked[15] = false;
		}
		if (settingsClicked[16]) {
			exitGame();
			settingsClicked[16] = false;
		}
		stats.setName(name);
	}

	/**
	 * Renders the settings menu
	 * 
	 * @param g
	 */
	private void settings_render(Graphics g) {
		g.setColor(Color.white);
		Font font = new Font("Jokerman", 1, 34);
		g.setFont(font);
		g.drawImage(Assets.hud_settings, Game.WIDTH - 274, 0, 268, 960, null);

		g.drawString(Character.toString(name[0]), Game.WIDTH - 292 + 40, 200 + 60);
		g.drawString(Character.toString(name[1]), Game.WIDTH - 292 + 40 + 50, 200 + 60);
		g.drawString(Character.toString(name[2]), Game.WIDTH - 292 + 40 + 100, 200 + 60);
		g.drawString(Character.toString(name[3]), Game.WIDTH - 292 + 40 + 150, 200 + 60);
		g.drawString(Character.toString(name[4]), Game.WIDTH - 292 + 40 + 200, 200 + 60);
		if (settingsClicked[0])
			g.drawImage(Assets.icon_upArrow, Game.WIDTH - 256, 166, null);
		if (settingsClicked[1])
			g.drawImage(Assets.icon_upArrow, Game.WIDTH - 208, 166, null);
		if (settingsClicked[2])
			g.drawImage(Assets.icon_upArrow, Game.WIDTH - 160, 166, null);
		if (settingsClicked[3])
			g.drawImage(Assets.icon_upArrow, Game.WIDTH - 112, 166, null);
		if (settingsClicked[4])
			g.drawImage(Assets.icon_upArrow, Game.WIDTH - 64, 166, null);
		if (settingsClicked[5])
			g.drawImage(Assets.icon_downArrow, Game.WIDTH - 256, 280, null);
		if (settingsClicked[6])
			g.drawImage(Assets.icon_downArrow, Game.WIDTH - 208, 280, null);
		if (settingsClicked[7])
			g.drawImage(Assets.icon_downArrow, Game.WIDTH - 160, 280, null);
		if (settingsClicked[8])
			g.drawImage(Assets.icon_downArrow, Game.WIDTH - 112, 280, null);
		if (settingsClicked[9])
			g.drawImage(Assets.icon_downArrow, Game.WIDTH - 64, 280, null);
		if (settingsClicked[10])
			g.drawImage(Assets.icon_leftArrow, Game.WIDTH - 200 - 12, 380 + 52, null);
		if (settingsClicked[11])
			g.drawImage(Assets.icon_rightArrow, Game.WIDTH - 140 + 26, 380 + 52, null);
		if (settingsClicked[12])
			g.drawImage(Assets.icon_leftArrow, Game.WIDTH - 211, 380 + 52 + 190 + 1, null);
		if (settingsClicked[13])
			g.drawImage(Assets.icon_rightArrow, Game.WIDTH - 113, 380 + 52 + 190 + 1, null);
		if (settingsClicked[14])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (BGM) {
			g.drawImage(Assets.icon_music, Game.WIDTH - 91 - 72, 870, null);
		}
		if (settingsClicked[16])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 870, null);
		/*
		 * g.drawRect(Game.WIDTH - 258 , 165, 48, 48); g.drawRect(Game.WIDTH -
		 * 210 , 165, 48, 48); g.drawRect(Game.WIDTH - 162 , 165, 48, 48);
		 * g.drawRect(Game.WIDTH - 114, 165, 48, 48); g.drawRect(Game.WIDTH -
		 * 66, 165, 48, 48);
		 */
		/*
		 * g.drawRect(Game.WIDTH - 258 , 280, 48, 48); g.drawRect(Game.WIDTH -
		 * 210, 280, 48, 48); g.drawRect(Game.WIDTH - 162, 280, 48, 48);
		 * g.drawRect(Game.WIDTH - 114, 280, 48, 48); g.drawRect(Game.WIDTH -
		 * 66, 280, 48, 48);
		 */

		g.drawImage(Assets.charIcon[selectedChar], Game.WIDTH - 188, 420, null);

		g.drawImage(Assets.themeIcon[selectedTheme], Game.WIDTH - 188, 610, null);
	}

	/**
	 * This method changes the boolean for the graphics
	 * 
	 * @param mx
	 * @param my
	 */
	private void helpMenuState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 236, 170, 192, 64)) {
			helpClicked[0] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 236, 275, 192, 64)) {
			helpClicked[1] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 236, 382, 192, 64)) {
			helpClicked[2] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			helpClicked[3] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 91, 870, 48, 48)) {
			helpClicked[4] = true;// exit

		}
	}

	/**
	 * This method changes the graphics and states respectively
	 */
	private void helpMenuState_released() {
		if (helpClicked[0]) {
			hudState = com.boxmanteam.menu.Menu.HUDSTATE.AboutTheGame;
			helpClicked[0] = false;
		}
		if (helpClicked[1]) {
			hudState = com.boxmanteam.menu.Menu.HUDSTATE.Instructions;
			helpClicked[1] = false;
		}
		if (helpClicked[2]) {
			hudState = com.boxmanteam.menu.Menu.HUDSTATE.Controls;
			helpClicked[2] = false;
		}
		if (helpClicked[3]) {
			hudState = HUDSTATE.Main;
			helpClicked[3] = false;
		}
		if (helpClicked[4]) {
			exitGame();
			helpClicked[4] = false;
		}
	}

	/**
	 * Renders the help menu
	 * 
	 * @param g
	 */
	private void helpMenu_render(Graphics g) {
		g.setColor(Color.white);
		g.drawImage(Assets.hud_help, Game.WIDTH - 274, 0, null);
		if (helpClicked[0])
			g.drawImage(Assets.button_aboutTheGame, Game.WIDTH - 236, 170, null);
		if (helpClicked[1])
			g.drawImage(Assets.button_instructions, Game.WIDTH - 236, 275, null);
		if (helpClicked[2])
			g.drawImage(Assets.button_controls, Game.WIDTH - 236, 382, null);
		if (helpClicked[3])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (helpClicked[4])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 870, null);
	}

	/**
	 * This method changes the booleans for the graphics
	 * 
	 * @param mx
	 * @param my
	 */
	private void aboutTheGameState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			aboutClicked[0] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 91, 870, 48, 48)) { // exit
			aboutClicked[1] = true;
		}
	}

	/**
	 * This changes the graphics and states respectively
	 */
	private void aboutTheGameState_released() {
		if (aboutClicked[0]) {
			hudState = HUDSTATE.Help;
			aboutClicked[0] = false;
		}
		if (aboutClicked[1]) {
			exitGame();
			aboutClicked[1] = false;

		}
	}

	/**
	 * Renders the about the game menu
	 * 
	 * @param g
	 */
	private void aboutTheGame_render(Graphics g) {
		g.drawImage(Assets.hud_aboutTheGame, Game.WIDTH - 274, 0, null);
		if (aboutClicked[0])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (aboutClicked[1])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 870, null);
	}

	/**
	 * This method changes the booleans for the graphics
	 * 
	 * @param mx
	 * @param my
	 */
	private void instructionsState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			instructionsClicked[0] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 91, 870, 48, 48)) { // exit
			instructionsClicked[1] = true;
		}
	}

	/**
	 * This method changes the graphics and states respectively
	 */
	private void instructionsState_released() {
		if (instructionsClicked[0]) {
			hudState = HUDSTATE.Help;
			instructionsClicked[0] = false;
		}
		if (instructionsClicked[1]) {
			exitGame();
			instructionsClicked[1] = false;
		}
	}

	/**
	 * Renders the instructions menu
	 * 
	 * @param g
	 */
	private void instructions_render(Graphics g) {
		g.drawImage(Assets.hud_instructions, Game.WIDTH - 274, 0, null);
		if (instructionsClicked[0])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (instructionsClicked[1])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 870, null);
	}

	/**
	 * This method changes the boolean for the graphics
	 * 
	 * @param mx
	 * @param my
	 */
	private void controlsState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 236, 870, 48, 48)) { // BACK BUTTON
			controlsClicked[0] = true;
		}

		if (mouseOver(mx, my, Game.WIDTH - 91, 870, 48, 48)) { // exit
			controlsClicked[1] = true;
		}
	}

	/**
	 * This method changes the graphics and states respectively
	 */
	private void controlsState_released() {
		if (controlsClicked[0]) {
			hudState = HUDSTATE.Help;
			controlsClicked[0] = false;
		}
		if (controlsClicked[1]) {
			exitGame();
			controlsClicked[1] = false;
		}
	}

	/**
	 * This method renders the controls menu
	 * 
	 * @param g
	 */
	private void controls_render(Graphics g) {
		g.drawImage(Assets.hud_controls, Game.WIDTH - 274, 0, null);
		if (controlsClicked[0])
			g.drawImage(Assets.icon_back, Game.WIDTH - 236, 870, null);
		if (controlsClicked[1])
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 870, null);
	}

	/**
	 * This method changes the booleans for the graphics
	 * 
	 * @param mx
	 * @param my
	 */
	private void highscoreState_pressed(int mx, int my) {
		if (mouseOver(mx, my, Game.WIDTH - 218, 750, 48, 48)) {
			highscoreClicked[0] = true;
		}
		if (mouseOver(mx, my, Game.WIDTH - 115, 750, 48, 48)) {
			highscoreClicked[1] = true;
		}
		if (mouseOver(mx, my, Game.WIDTH - 238, 860, 48, 48)) {
			highscoreClicked[2] = true;
		}
		if (mouseOver(mx, my, Game.WIDTH - 91, 860, 48, 48)) {
			highscoreClicked[3] = true;
		}

	}

	/**
	 * This method changes the sates and graphics respectively
	 */
	private void highscoreState_released() {
		if (highscoreClicked[0]) {
			if (highscore_current == 1) {
				highscore_current--;
			} else {
				highscore_current++;
			}
			highscoreClicked[0] = false;
		}
		if (highscoreClicked[1]) {
			if (highscore_current == 0) {
				highscore_current++;
			} else {
				highscore_current--;
			}
			highscoreClicked[1] = false;
		}
		if (highscoreClicked[2]) {
			Menu.hudState = Menu.HUDSTATE.Main;
			highscoreClicked[2] = false;
		}
		if (highscoreClicked[3]) {
			exitGame();
		}

	}

	/**
	 * Renders the highscores menu
	 * 
	 * @param g
	 */
	private void highscore_render(Graphics g) {
		g.drawImage(Assets.hud_highscores, Game.WIDTH - 274, 0, null);
		g.setColor(Color.white);
		if (highscore_current == 0) {
			g.drawImage(Assets.limitedMoves_label, Game.WIDTH - 228, 200, null);
		} else {
			g.drawImage(Assets.timeAttack_label, Game.WIDTH - 228, 200, null);
		}
		printScores(g);

		if (highscoreClicked[0]) {
			g.drawImage(Assets.icon_leftArrow, Game.WIDTH - 218, 750, null);
		}
		if (highscoreClicked[1]) {
			g.drawImage(Assets.icon_rightArrow, Game.WIDTH - 115, 750, null);
		}
		if (highscoreClicked[2]) {
			g.drawImage(Assets.icon_back, Game.WIDTH - 238, 860, null);
		}
		if (highscoreClicked[3]) {
			g.drawImage(Assets.icon_exit, Game.WIDTH - 91, 860, null);
		}
	}

	/**
	 * This methods prints the scores in the highscores menu
	 * 
	 * @param g
	 */
	private void printScores(Graphics g) {
		Font font;
		if (highscore_current == 0) {
			for (int i = 0; i < 16; i++) {
				String name = Scores.highscores_moves.get(i).get("Name");
				String level = Scores.highscores_moves.get(i).get("Level");
				String moves = Scores.highscores_moves.get(i).get("Moves");

				if (i == 0) {
					font = new Font("Tahoma", 3, 16);
					g.setFont(font);
					g.drawString(name, 1122, 300);
					g.drawString(level, 1045, 300);
					g.drawString(moves, 1190, 300);
				} else {
					font = new Font("Tahoma", 0, 15);
					g.setFont(font);
					if (Integer.parseInt(moves) == Integer.MAX_VALUE) {
						moves = "  -  ";
					}
					if (name.equals(" ")) {
						name = "    -  ";
					}
					g.drawString(name, 1122, 300 + 20 * i);
					if (Integer.parseInt(level) < 10) {
						g.drawString(level, 1060, 300 + 20 * i);
					} else {
						g.drawString(level, 1055, 300 + 20 * i);
					}

					if (!moves.equals("  -  ")) {
						if (Integer.parseInt(moves) < 100) {
							g.drawString(moves, 1203, 300 + 20 * i);

						} else {
							g.drawString(moves, 1200, 300 + 20 * i);
						}
					} else {
						g.drawString(moves, 1200, 300 + 20 * i);
					}

				}

			}
		} else if (highscore_current == 1) {
			for (int i = 0; i < 16; i++) {
				// System.out.println(Scores.highscores_moves.get(i).get("Time"));
				String name = Scores.highscores_time.get(i).get("Name");
				String level = Scores.highscores_time.get(i).get("Level");
				String time = Scores.highscores_time.get(i).get("Time");

				if (i == 0) {
					font = new Font("Tahoma", 3, 16);
					g.setFont(font);
					g.drawString(name, 1122, 300);
					g.drawString(level, 1045, 300);
					g.drawString(time, 1190, 300);
				} else {

					font = new Font("Tahoma", 0, 15);
					g.setFont(font);
					if (Long.parseLong(time) == Long.MAX_VALUE) {
						time = "  -  ";
					} else {

						time = timeToString2(Long.parseLong(time));
					}
					if (name.equals(" ") || name.equals("     ")) {
						name = "    -  ";
					}

					g.drawString(name, 1122, 300 + 20 * i);
					if (Integer.parseInt(level) < 10) {
						g.drawString(level, 1060, 300 + 20 * i);
					} else {
						g.drawString(level, 1055, 300 + 20 * i);
					}

					if (!time.equals("  -  ")) {

						g.drawString(time, 1195, 300 + 20 * i);

					} else {
						g.drawString(time, 1200, 300 + 20 * i);
					}

				}

			}
		}
	}

	/**
	 * Checks if its paused
	 * 
	 * @return
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * Pauses the game
	 * 
	 * @param isPaused
	 */
	public void setPaused(boolean isPaused) {
		Menu.isPaused = isPaused;
	}

	/**
	 * Tick for the menu that updates it
	 */
	public void tick() {
		if (hudState == HUDSTATE.TimeAttackHUD) {
			if (!isGameOver) {
				Menu.now = System.currentTimeMillis();
				if (isPaused == false) {
					Menu.timer -= (Menu.now - Menu.lastTime);
				}
				Menu.lastTime = System.currentTimeMillis();
				if (timer <= 0) {
					isGameOver = true;
					AudioPlayer.getSound("gameover").play();
					isPaused = true;
					timeAttack_levelPrompt = true;
				}
			}
		}
	}

	/**
	 * This renders everything depending on the state
	 * 
	 * @param g
	 */
	public void render(Graphics g) {
		if (isPaused) {
			g.setColor(new Color(0, 10, 0, 100));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}
		if (hudState == HUDSTATE.Main) {
			mainMenu_render(g);
		}
		if (hudState == HUDSTATE.Mode) {
			modeMenu_render(g);
		}

		if (hudState == HUDSTATE.FreePlayHUD) {
			freePlay_render(g);
		}
		if (hudState == HUDSTATE.TimeAttackHUD) {
			timeAttack_render(g);
		}

		if (hudState == HUDSTATE.AboutTheGame) {
			aboutTheGame_render(g);
		}

		if (hudState == HUDSTATE.Instructions) {
			instructions_render(g);
		}

		if (hudState == HUDSTATE.Controls) {
			controls_render(g);
		}

		if (hudState == HUDSTATE.LimitedMovesHUD) {
			limitedMoves_render(g);
		}

		if (hudState == HUDSTATE.Help) {
			helpMenu_render(g);
		}

		if (hudState == HUDSTATE.Settings) {
			settings_render(g);
		}

		if (hudState == HUDSTATE.Highscore) {
			highscore_render(g);
		}

	}

	/**
	 * Exits the game and saves the highscore
	 */
	private void exitGame() {
		Scores.write_highscores();
		System.exit(1);
	}

	/**
	 * Gets the time and sets that to a string for the time attack
	 * 
	 */
	private String timeToString() {
		int min = timer / 60000;
		int sec = timer % 60000 / 1000;
		String minutes, seconds;
		minutes = Integer.toString(min);
		seconds = Integer.toString(sec);
		if (min < 10) {
			minutes = "0" + minutes;
		}
		if (sec < 10) {
			seconds = "0" + seconds;
		}
		return minutes + ":" + seconds;
	}

	/**
	 * Gets the tiem and sets that to a string for the time attack highscores
	 * 
	 * @param time
	 * @return
	 */
	private String timeToString2(long time) {

		int min = (int) time / 60000;
		int sec = (int) time % 60000 / 1000;
		if (time < 60000) {
			min = 0;
		}
		String minutes, seconds;
		minutes = Integer.toString(min);
		seconds = Integer.toString(sec);
		if (min < 10) {
			minutes = "0" + minutes;
		}
		if (sec < 10) {
			seconds = "0" + seconds;
		}
		return minutes + ":" + seconds;
	}

	/**
	 * Shows the level prompt menu if you want to return to the main menu or
	 * play again or next level
	 */
	public static void showLevelPrompt() {
		if (hudState == HUDSTATE.FreePlayHUD) {
			freePlay_LevelPrompt = true;
		}
		if (hudState == HUDSTATE.TimeAttackHUD) {
			timeAttack_levelPrompt = true;
		}
		if (hudState == HUDSTATE.LimitedMovesHUD) {
			limitedMoves_LevelPrompt = true;
		}
		isPaused = true;
	}
}
