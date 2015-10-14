package com.ancient.util.spell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ancient.util.spell.item.SpellItem;
import com.ancient.util.chat.ChatParser;

/** Class holding one spell, that may be executed<br>
 * Includes spell type, name etc.
 * 
 * @author FroznMine
 *
 */
public class Spell {
	/** A hash map containing all items in that spell.
	 * key is the items line
	 * value is the item
	 */
	private HashMap<Integer, SpellItem> items;
	
	/** A constructor for a spell.<br>
	 * Loaded from the passed file.
	 * 
	 * @param file The file containing the spell
	 */
	public Spell(File file) {
		ZipFile zip;
		try {
			zip = new ZipFile(file);
		} catch (ZipException ex) {
			ex.printStackTrace();
			return;
			//stop creation
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
			// stop creation
		}
		
		Enumeration<? extends ZipEntry> entries = zip.entries();
		InputStream configStream= null, spellStream = null;
		
		while (entries.hasMoreElements()) {
			ZipEntry entry  = entries.nextElement();
			try {
				if (entry.getName().equalsIgnoreCase("info.yml")) configStream = zip.getInputStream(entry);
				if (entry.getName().equalsIgnoreCase("spell.txt")) spellStream = zip.getInputStream(entry);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}	
		
		assert configStream != null && spellStream != null; // irregular file
		
		YamlConfiguration.loadConfiguration(configStream);
		
		// parse spell
		SpellItem[] items = SpellParser.parse(spellStream);
	}
	
	private void parseLine(String line) {
		line = line.trim(); // removes leading and trailing whitespace
		
//		if (line.equals("")) continue; // if empty line skip it
		
		if (Character.isLetter(line.charAt(0))); // throw exception line has wrong format
		
		// for every char of that line check special meanings
		for (char c : line.toCharArray()) {
			switch (c) {
			// if char is # following chars are comments and can be ignored. we can get to the next line
			case '#':
				continue;
			// if char not one with special meaning just append it
			default:
//				plain += c;
			}
		}
		// add new line after each line. equals command ending
//		plain += '\n';
	}
	
	public static void main(String[] args) {
		String arg = "   k hjkdkhjfkjhfkjs     ";
		System.out.println(arg);
		arg = arg.trim();
		System.out.println(arg);
	}
}
