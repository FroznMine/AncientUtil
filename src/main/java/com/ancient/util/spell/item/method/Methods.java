package com.ancient.util.spell.item.method;

import java.util.HashMap;
import java.util.Map;

import com.ancient.util.ClassFinder;
import com.ancient.util.spell.item.SpellItem;

public class Methods {
	private static Map<String, Class<? extends Method>> methods;
	
	public static SpellItem call(String text) {
		return null;
		
	}

	private static void loadMethods(String packageName) {
		if (methods == null) methods = new HashMap<String, Class<? extends Method>>();
		
		for (Class<?> c : ClassFinder.find(packageName)) {
			if (Method.class.isAssignableFrom(c)) {
				// ist methode
				String name;
				try {
					name = ((Method) c.newInstance()).getName();
				} catch (InstantiationException ex) {
					ex.printStackTrace();
					continue;
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
					continue;
				}
				if (methods.containsKey(name)) continue;
				methods.put(name, c.asSubclass(Method.class));
			}
		}
	}
	
}
