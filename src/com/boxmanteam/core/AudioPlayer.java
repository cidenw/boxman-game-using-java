package com.boxmanteam.core;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * 
 * This class uses external libraries. Stores the audio in HashMaps.
 * 
 * @author Waleed Occidental
 * @see Music
 * @see SlickException
 * @see Sound
 */
public class AudioPlayer {
	/**
	 * Contains all the sound effects.
	 */
	public static Map<String, Sound> soundMap;
	/**
	 * Contains all the background music.
	 */
	public static Map<String, Music> musicMap;

	/**
	 * Loads all the music from the resource folder.
	 */
	public static void load() {

		soundMap = new HashMap<String, Sound>();
		musicMap = new HashMap<String, Music>();
		
		try {
			musicMap.put("bgm", new Music("res/sounds/bgm.ogg"));
			soundMap.put("push", new Sound("res/sounds/push.ogg"));
			soundMap.put("step", new Sound("res/sounds/step.ogg"));
			soundMap.put("notpush", new Sound("res/sounds/notpush.ogg"));
			soundMap.put("gameover", new Sound("res/sounds/gameover.ogg"));
			soundMap.put("youwin", new Sound("res/sounds/youwin.ogg"));
			//soundMap.put("click", new Sound("res/sounds/click.ogg"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param key	the key to search in the HashMap
	 * @return sound that matches the key
	 */
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	/**
	 * 
	 * @param key	the key to search in the HashMap
	 * @return sound that matches the key
	 */
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
