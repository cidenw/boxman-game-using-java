package com.boxmanteam.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFileChooser;

/**
 * Stores all the scores of the player and highscores.
 * Includes all the methods for adding new scores.
 * @author Waleed Occidental
 *
 */
public class Scores {
	/**
	 * An ArrayList of HashTable containing the highscores of Time Attack Mode.
	 */
	public static ArrayList<Hashtable<String, String>> highscores_time = new ArrayList<Hashtable<String, String>>(16);
	/**
	 * An ArrayList of HashTable containing the highscores of Limited Moves Mode.
	 */
	public static ArrayList<Hashtable<String, String>> highscores_moves = new ArrayList<Hashtable<String, String>>(16);
	/**
	 * An ArrayList of HashTable containing the scores of the user Time Attack Mode.
	 */
	public static ArrayList<Hashtable<String, String>> currScores_time = new ArrayList<Hashtable<String, String>>(16);
	/**
	 * An ArrayList of HashTable containing the scores of the user Limited Moves Mode.
	 */
	public static ArrayList<Hashtable<String, String>> currScores_moves = new ArrayList<Hashtable<String, String>>(16);
	/**
	 * Path to the device's documents folder where the highscores will be stored.
	 */
	private static String filePath;
	/**
	 * Adds a new score of the player.
	 * @param name	name of the player
	 * @param level	current level
	 * @param time	time the player solved the puzzle
	 */
	public static void addTime(String name, int level, long time) {
		Hashtable<String, String> temp = new Hashtable<String, String>();
		temp.put("Name", name);
		temp.put("Level", Integer.toString(level));
		temp.put("Time", Long.toString(time));
		if (Long.parseLong(currScores_time.get(level).get("Time")) > Long.parseLong(temp.get("Time"))) {
			currScores_time.set(level, temp);
		}
	}
	/**
	 * Adds a new score of the player.
	 * @param name	name of the player
	 * @param level	current level
	 * @param moves	moves done by the user to solve the puzzle
	 */
	public static void addMoves(String name, int level, int moves) {
		Hashtable<String, String> temp = new Hashtable<String, String>();
		temp.put("Name", name);
		temp.put("Level", Integer.toString(level));
		temp.put("Moves", Integer.toString(moves));
		if (Integer.parseInt(currScores_moves.get(level).get("Moves")) > Integer.parseInt(temp.get("Moves"))) {
			currScores_moves.set(level, temp);
		}
	}
	/**
	 * Initializes the ArrayLists of all the highscores.
	 */
	public static void initialize() {
		filePath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Boxman/highscores.txt";
		for (int i = 0; i < 16; i++) {
			Hashtable<String, String> temp = new Hashtable<String, String>();
			if (i == 0) {
				temp.put("Name", "Name");
				temp.put("Level", "Level");
				temp.put("Moves", "Moves");
			} else {
				temp.put("Name", " ");
				temp.put("Level", Integer.toString(i));
				temp.put("Moves", Integer.toString(Integer.MAX_VALUE));
			}

			highscores_moves.add(temp);
			currScores_moves.add(temp);

			Hashtable<String, String> temp2 = new Hashtable<String, String>();
			if (i == 0) {
				temp2.put("Name", "Name");
				temp2.put("Level", "Level");
				temp2.put("Time", "Time");
			} else {
				temp2.put("Name", " ");
				temp2.put("Level", Integer.toString(i));
				temp2.put("Time", Long.toString(Long.MAX_VALUE));
			}

			highscores_time.add(temp2);
			currScores_time.add(temp2);
		}
		try {
			load_highscores();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Writes the new highscore to the file.
	 */
	public static void write_highscores() {
		for (int i = 1; i <= 15; i++) {
			if (Long.parseLong(highscores_time.get(i).get("Time")) > Long
					.parseLong(currScores_time.get(i).get("Time"))) {
				highscores_time.set(i, currScores_time.get(i));
			}

			if (Integer.parseInt(highscores_moves.get(i).get("Moves")) > Integer
					.parseInt(currScores_moves.get(i).get("Moves"))) {

				highscores_moves.set(i, currScores_moves.get(i));
			}
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i <= 15; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(highscores_time.get(i).get("Name"));
			sb.append(",");
			sb.append(highscores_time.get(i).get("Level"));
			sb.append(",");
			sb.append(highscores_time.get(i).get("Time"));
			sb.append("\n");
			try {
				bw.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i <= 15; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(highscores_moves.get(i).get("Name"));
			sb.append(",");
			sb.append(highscores_moves.get(i).get("Level"));
			sb.append(",");
			sb.append(highscores_moves.get(i).get("Moves"));
			sb.append("\n");
			try {
				bw.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Loads all the highscore from a file.
	 * @throws FileNotFoundException	if the file does not exist
	 * @throws IOException	if IO error occured
	 */
	private static void load_highscores() throws FileNotFoundException, IOException {
		if (!new File(filePath).exists()) {
			new File(new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Boxman/").mkdir();
			new File(filePath).createNewFile();
		}
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String line;
		String[] headerLine1 = new String[3];
		String[] headerLine2 = new String[3];
		int i = 0;
		while ((line = br.readLine()) != null) {
			String[] currLine = line.split(",");
			if (i < 16) {
				if (i == 0) {
					headerLine1[0] = currLine[0];
					headerLine1[1] = currLine[1];
					headerLine1[2] = currLine[2];
				} else {
					Hashtable<String, String> temp = new Hashtable<String, String>();
					temp.put(headerLine1[0], currLine[0]);
					temp.put(headerLine1[1], currLine[1]);
					temp.put(headerLine1[2], currLine[2]);
					highscores_time.set(i, temp);
				}
			} else {
				if (i == 16) {
					headerLine2[0] = currLine[0];
					headerLine2[1] = currLine[1];
					headerLine2[2] = currLine[2];
				} else {
					Hashtable<String, String> temp = new Hashtable<String, String>();
					temp.put(headerLine2[0], currLine[0]);
					temp.put(headerLine2[1], currLine[1]);
					temp.put(headerLine2[2], currLine[2]);
					highscores_moves.set(i - 16, temp);
				}
			}
			i++;
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
