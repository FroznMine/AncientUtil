package com.ancient.util.spell;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;



//import com.ancient.rpg.spell.item.SpellItem;
import com.ancient.util.chat.ChatParser;
import com.ancient.util.spell.item.SpellItem;
import com.ancient.util.spell.parse.Tokenizer;

public class SpellParser {
//	public static HashMap<Integer, SpellItem> parseSpell(String plain) {
//		HashMap<Integer, SpellItem> items = new HashMap<Integer, SpellItem>();
//		String[] lines = plain.split("\n");
//		
//		for (int i = 0; i < lines.length; i++) {
//			String line = ChatParser.trimComplete(lines[i]);
//			List<String> tokens = Arrays.asList(StringUtils.splitPreserveAllTokens(line));
//			for (String token : tokens)
//				if (token.equals("")) tokens.remove(token);
//			
//			for (int j = 0; j < tokens.size(); j++) {
//				String token = tokens.get(j);
//				if (!isIdentifier(token)) {
//					// split to get identifier
//				}
//			}
//		}
//		String token = "";
//		for (char c : plain.toCharArray()) {
//			switch (c) {
//			case ' ':
//				
//				break;
//
//			default:
//				break;
//			}
//		}
//		return items;
//	}

	private static boolean isIdentifier(String token) {
		for (char c : token.toCharArray())
			if (!(c > 'a' && c < 'Z' || c == '_')) return false;
		return true;
	}

	/** Checks if the given char is a seperator<br>
	 * A seperator is every ascii char, which is not listed here:<br>
	 * - A-Z<br>
	 * - a-z<br>
	 * - 0-9<br>
	 * - '_'<br>
	 * @param c The char that has to be checked
	 * @return true if the char is a seperator, false otherwise
	 */
	private static boolean isSeperator(char c) {
		if (c < 48 || c > 57 && c < 65 || c > 90 && c < 97 && c != 95 || c > 122) return true;
		return false;
	}

	public static SpellItem[] parse(InputStream inStream) {
		return Tokenizer.tokenize(inStream);
	}
}
