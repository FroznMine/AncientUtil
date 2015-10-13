package com.ancient.util.spell.parse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.ancient.util.chat.ChatParser;
import com.ancient.util.spell.item.SpellItem;
import com.ancient.util.spell.item.method.Methods;
import com.ancient.util.spell.item.variable.Variable;
import com.ancient.util.spell.operations.Mathematic;

public class Tokenizer {
	
	public static SpellItem[] tokenize(InputStream inStream) {	
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		
			String line;
			String text = "";
			
			while ((line = br.readLine()) != null) text += line + "\n";
			
			return tokenize(text);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
//		System.out.println(TokenType.getByRegex("var a = bs lkdkljdsfd dlkfsdlkf fflskjd; "));
//		System.out.println(TokenType.getByRegex("sdfa = bs lkdkljdsfd dlkfsdlkf fflskjd; "));
//		System.out.println(TokenType.getByRegex("var (djfdkfd, fsdjf ,fd, 494, fds49 );"));
//		System.out.println(TokenType.getByRegex("5 + 5"));
//		String pattern = "(\\s*)(\\w)(\\s*)([\\.,])";
//		System.out.println("hallo    .    du.  f .d".replaceAll(pattern, "$2$4")); 
	}
	
	public static SpellItem[] tokenize(String text) {	
		try {
			LinkedList<SpellItem> itemList = new LinkedList<SpellItem>();
			while (!text.equals("")) {
				String textOld = text;
				for (TokenType t : TokenType.values()) {
					Pattern pattern = Pattern.compile(t.getRegex());
					Matcher matcher = pattern.matcher(text);
					
					String found = null;
					if (matcher.find()) found = matcher.group(); // was übereinstimmt
			
					if (found != null) {
						if (text.indexOf(found) == 0) {
							// uebereinstimmung am beginn des textes
							// spezielle regex chars escapen.
							found = ChatParser.escapeAll(found);
							
							itemList.add((SpellItem) t.clazz.getMethod(t.method, String.class).invoke(null, found));
						
							text = text.replaceFirst(found, "").trim();
							break;
						}
					}
				}
				if (textOld.equals(text)) throw new IllegalArgumentException("Fehler in Zeile: " + text.split("\n")[0]); // wenn nix geaendert scheint was ungueltiges da gewesen zu sein
			}
			return itemList.toArray(new SpellItem[itemList.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
//		
	}
	/* Der komplette Spell wird eingelesen und anschließend 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static void main(String[] args) {
		String text = " 5 - 5 * (5 - 5)";
		// bracer angucken
		Stack<String> stack = new Stack<String>();
//		StringTokenizer tokenizer = new StringTokenizer();
				
		// schlecht
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String foo = "5+5,33.9-5-f55.5";
		try {
			System.out.println(engine.eval(foo));
		} catch (ScriptException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		    
		for (TokenType t : TokenType.values()) {
			Pattern pattern = Pattern.compile(t.getRegex());
			Matcher matcher = pattern.matcher(text);
			
			String found = null;
			if (matcher.find()) found = matcher.group(); // was übereinstimmt
	
			if (found != null) {
				if (text.indexOf(found) == 0) {
					// uebereinstimmung am beginn des textes
					// spezielle regex chars escapen.
					found = ChatParser.escapeAll(found);
					
					text = text.replaceFirst(found, "").trim();
					System.out.println(t);
					break;
				}
			}
		}
	}
	
	private enum TokenType {
		VARIABLE_DECLERATION("\\s*var\\s*\\w+\\s*=\\s*.+\\s*;+\\s*", Variable.class, "declare"),
		VARIABLE_CHANGE("\\s*\\w+\\s*=\\s*.+\\s*;\\s*", Variable.class, "change"),
		METHOD_CALL("\\s*\\w+\\s*\\([[a-zA-Z0-9_\\s]*\\,?]*\\)\\s*;+\\s*", Methods.class, "call"),
		OPERATION("\\s*\\w+\\s*[[\\+|-|\\*|/\\^]\\s*\\w+\\s*]", Mathematic.class, "operation");
//		TEST("(?s).*\\w+\\s*;(?s).*");
		
		private String regex;
		private Class<?> clazz;
		private String method;
		
		private TokenType(String regex, Class<?> clazz, String method) {
			this.regex = regex;
			this.clazz = clazz;
			this.method = method;
		}
		
		public static TokenType getByRegex(String text) {
			for (TokenType t : TokenType.values())
				if (text.contains(t.regex)) return t;
			return null;
		}
		
		public String getRegex() {
			return this.regex;
		}
	}
}
