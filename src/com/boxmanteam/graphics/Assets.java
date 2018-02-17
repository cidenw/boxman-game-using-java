package com.boxmanteam.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class stores all the assets used in the game.
 * 
 * @author Waleed Occidental
 *
 */
public class Assets {
	public static Character[] character = new Character[5];
	public static BufferedImage[] ruins = new BufferedImage[4];
	public static BufferedImage[] winter = new BufferedImage[4];
	public static BufferedImage[] desert = new BufferedImage[4];
	public static BufferedImage hud_main;
	public static BufferedImage hud_mode;
	public static BufferedImage hud_settings;
	public static BufferedImage hud_highscores;
	public static BufferedImage hud_help;
	public static BufferedImage hud_aboutTheGame;
	public static BufferedImage hud_instructions;
	public static BufferedImage hud_controls;
	public static BufferedImage hud_game;
	public static BufferedImage button_play;
	public static BufferedImage button_settings;
	public static BufferedImage button_quit;
	public static BufferedImage button_freePlay;
	public static BufferedImage button_timeAttack;
	public static BufferedImage button_limitedMoves;
	public static BufferedImage button_highScores;
	public static BufferedImage button_help;
	public static BufferedImage button_aboutTheGame;
	public static BufferedImage button_instructions;
	public static BufferedImage button_controls;
	public static BufferedImage button_playAgain;
	public static BufferedImage button_return;
	public static BufferedImage button_nextLevel;
	public static BufferedImage[] levelIcons = new BufferedImage[15];
	public static BufferedImage icon_back;
	public static BufferedImage icon_exit;
	public static BufferedImage icon_settings;
	public static BufferedImage icon_music;
	public static BufferedImage icon_undo;
	public static BufferedImage icon_redo;
	public static BufferedImage icon_reset;
	public static BufferedImage icon_play;
	public static BufferedImage icon_leftArrow;
	public static BufferedImage icon_rightArrow;
	public static BufferedImage icon_upArrow;
	public static BufferedImage icon_downArrow;
	public static BufferedImage timeBox;
	public static BufferedImage[] levelBox = new BufferedImage[3];
	public static BufferedImage[] avatar = new BufferedImage[5];
	public static BufferedImage[] charIcon = new BufferedImage[5];
	public static BufferedImage[] themeIcon = new BufferedImage[3];
	public static BufferedImage frame_end;
	public static BufferedImage frame_nextLevel;
	public static BufferedImage gameOver;
	public static BufferedImage youWin;
	public static BufferedImage puzzleSolved_timeAttack;
	public static BufferedImage puzzleSolved_limitedMoves;
	public static BufferedImage puzzleSolved_freePlay;
	public static BufferedImage[] num = new BufferedImage[11];
	public static BufferedImage limitedMoves_label;
	public static BufferedImage timeAttack_label;
	/**
	 * List of all the maps.
	 */
	public static ArrayList<World> maps = new ArrayList<World>();
	/**
	 * Stores the default width of a game object.
	 */
	private static final int width = 48;
	/**
	 * Stores the default height of a game object.
	 */
	private static final int height = 48;
	/**
	 * Loads all the images from the resource folder.
	 */
	public static void init() {
		
		addMaps();
		
		SpriteSheet avatars = new SpriteSheet(ImageLoader.loadImage("/textures/avatar.png"));
		avatar[0] = avatars.crop(0, 0, 226, 97);
		avatar[1] = avatars.crop(226, 0, 226, 97);
		avatar[2] = avatars.crop(226 * 2, 0, 226, 97);
		avatar[3] = avatars.crop(226 * 3, 0, 226, 97);
		avatar[4] = avatars.crop(226 * 4, 0, 226, 97);

		SpriteSheet charAnimation = new SpriteSheet(ImageLoader.loadImage("/textures/character.png"));
		character[0] = new Character(getMotionFrames(charAnimation, 0), getMotionFrames(charAnimation, 1),
				getMotionFrames(charAnimation, 2), getMotionFrames(charAnimation, 3),
				charAnimation.crop(width, height * 2, width, height));
		character[1] = new Character(getMotionFrames(charAnimation, 4), getMotionFrames(charAnimation, 5),
				getMotionFrames(charAnimation, 6), getMotionFrames(charAnimation, 7),
				charAnimation.crop(width, height * 6, width, height));
		character[2] = new Character(getMotionFrames(charAnimation, 8), getMotionFrames(charAnimation, 9),
				getMotionFrames(charAnimation, 10), getMotionFrames(charAnimation, 11),
				charAnimation.crop(width, height * 10, width, height));
		character[3] = new Character(getMotionFrames(charAnimation, 12), getMotionFrames(charAnimation, 13),
				getMotionFrames(charAnimation, 14), getMotionFrames(charAnimation, 15),
				charAnimation.crop(width, height * 14, width, height));
		character[4] = new Character(getMotionFrames(charAnimation, 16), getMotionFrames(charAnimation, 17),
				getMotionFrames(charAnimation, 18), getMotionFrames(charAnimation, 19),
				charAnimation.crop(width, height * 18, width, height));
		
		SpriteSheet hud = new SpriteSheet(ImageLoader.loadImage("/textures/hud.png"));
		hud_main = hud.crop(0, 0, 268, 960);
		hud_mode = hud.crop(268, 0, 268, 960);
		hud_settings = hud.crop(268 * 2, 0, 268, 960);
		hud_highscores = hud.crop(268 * 3, 0, 268, 960);
		hud_help = hud.crop(268 * 4, 0, 268, 960);
		hud_aboutTheGame = hud.crop(268 * 5, 0, 268, 960);
		hud_instructions = hud.crop(268 * 6, 0, 268, 960);
		hud_controls = hud.crop(268 * 7, 0, 268, 960);
		hud_game = hud.crop(268 * 8, 0, 268, 960);
		
		SpriteSheet option = new SpriteSheet(ImageLoader.loadImage("/textures/option.png"));
		for (int i = 0; i < 5; i++) {
			charIcon[i] = option.crop(i * 96, 0, 96, 144);
			if (i < 3) {
				themeIcon[i] = option.crop((i + 5) * 96, 0, 96, 144);
			}
		}

		SpriteSheet world = new SpriteSheet(ImageLoader.loadImage("/textures/world.png"));
		for (int i = 0; i < 4; i++) {
			ruins[i] = world.crop(i * 48, 0, 48, 48);
			winter[i] = world.crop(i * 48, 48, 48, 48);
			desert[i] = world.crop(i * 48, 48 * 2, 48, 48);
		}

		SpriteSheet buttons = new SpriteSheet(ImageLoader.loadImage("/textures/buttons.png"));
		button_play = buttons.crop(0, 0, 192, 64);
		button_settings = buttons.crop(192, 0, 192, 64);
		button_quit = buttons.crop(192 * 2, 0, 192, 64);
		button_freePlay = buttons.crop(192 * 3, 0, 192, 64);
		button_timeAttack = buttons.crop(192 * 4, 0, 192, 64);
		button_limitedMoves = buttons.crop(192 * 5, 0, 192, 64);
		button_highScores = buttons.crop(192 * 6, 0, 192, 64);
		button_help = buttons.crop(192 * 7, 0, 192, 64);
		button_aboutTheGame = buttons.crop(192 * 8, 0, 192, 64);
		button_instructions = buttons.crop(192 * 9, 0, 192, 64);
		button_controls = buttons.crop(192 * 10, 0, 192, 64);
		button_playAgain = buttons.crop(192 * 11, 0, 192, 64);
		button_return = buttons.crop(192 * 12, 0, 192, 64);
		button_nextLevel = buttons.crop(192 * 13, 0, 192, 64);
		for (int i = 0; i < 15; i++) {
			levelIcons[i] = buttons.crop(i * 48, 64, 48, 48);
		}
		icon_back = buttons.crop(15 * 48, 64, 48, 48);
		icon_exit = buttons.crop(16 * 48, 64, 48, 48);
		icon_settings = buttons.crop(17 * 48, 64, 48, 48);
		icon_music = buttons.crop(18 * 48, 64, 48, 48);
		icon_undo = buttons.crop(19 * 48, 64, 48, 48);
		icon_redo = buttons.crop(20 * 48, 64, 48, 48);
		icon_reset = buttons.crop(21 * 48, 64, 48, 48);
		icon_play = buttons.crop(22 * 48, 64, 48, 48);
		icon_leftArrow = buttons.crop(23 * 48, 64, 48, 48);
		icon_rightArrow = buttons.crop(24 * 48, 64, 48, 48);
		icon_upArrow = buttons.crop(25 * 48, 64, 48, 48);
		icon_downArrow = buttons.crop(26 * 48, 64, 48, 48);
		levelBox[0] = buttons.crop(0, 112, 180, 92);
		levelBox[1] = buttons.crop(180, 112, 180, 92);
		levelBox[2] = buttons.crop(180 * 2, 112, 180, 92);
		timeBox = buttons.crop(27 * 48, 64, 240, 100);
		
		SpriteSheet panel = new SpriteSheet(ImageLoader.loadImage("/textures/panel.png"));
		frame_nextLevel = panel.crop(0, 0, 816, 432);
		frame_end = panel.crop(816, 0, 816, 432);
		gameOver = panel.crop(816 + 816, 0, 370, 200);
		youWin = panel.crop(816 + 816, 200, 370, 200);
		puzzleSolved_timeAttack = panel.crop(816 + 816 + 370, 0, 564, 289);
		puzzleSolved_limitedMoves = panel.crop(816 + 816 + 370 + 564, 0, 564, 289);
		puzzleSolved_freePlay = panel.crop(816 + 816 + 370 + 564 + 564, 0, 485, 169);
		for (int i = 0; i < num.length; i++) {
			num[i] = panel.crop(816 + 816 + 370 + (i * 24) + 1, 289, 24, 23);
		}
		limitedMoves_label = panel.crop(816 + 816 + 370, 313, 189, 59);
		timeAttack_label = panel.crop(816 + 816 + 370 + 189 + 1, 313, 189, 59);
		
		

	}
	/**
	 * Returns the motion frames for the character animation.
	 * @param sheet 
	 * @param y
	 * @return
	 */
	private static BufferedImage[] getMotionFrames(SpriteSheet sheet, int y) {
		BufferedImage[] character = new BufferedImage[3];
		character[0] = sheet.crop(0, y * height, width, height);
		character[1] = sheet.crop(width, y * height, width, height);
		character[2] = sheet.crop(2 * width, y * height, width, height);
		return character;
	}
	/**
	 * Read all the maps from the resource folder.
	 */
	private static void addMaps() {
		//ClassLoader classLoader = Assets.class.getClassLoader();
		/**
		File folder = new File(classLoader.getResource("maps/").getFile());
		
		File[] listOfFiles = folder.listFiles();
		/**/
		ArrayList<String> fileNames = new ArrayList<String>();
		/**
		for (File file : listOfFiles) {
			if (file.isFile()) {
				fileNames.add(file.getName());
			}
		}
		/**/
		fileNames.add("0_title.map");
		fileNames.add("map01.map");
		fileNames.add("map02.map");
		fileNames.add("map03.map");
		fileNames.add("map04.map");
		fileNames.add("map05.map");
		fileNames.add("map06.map");
		fileNames.add("map07.map");
		fileNames.add("map08.map");
		fileNames.add("map09.map");
		fileNames.add("map10.map");
		fileNames.add("map11.map");
		fileNames.add("map12.map");
		fileNames.add("map13.map");
		fileNames.add("map14.map");
		fileNames.add("map15.map");
		
		for (int i = 0; i < fileNames.size(); i++) {
			if (fileNames.get(i).endsWith(".map")) {
				maps.add(new World("/maps/" + fileNames.get(i)));
			}
		}

		

	}

}
