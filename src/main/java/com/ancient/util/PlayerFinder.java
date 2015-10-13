package com.ancient.util;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class PlayerFinder {
	private static HashMap<UUID, String> players = new HashMap<UUID, String>();
	
	public static void addPlayer(UUID uuid, String name) {
		if (players.containsKey(uuid)) return;
		
		players.put(uuid, name);
	}
	
	public static String getPlayerName(UUID uuid){
		if (!players.containsKey(uuid)) return "";
		
		return players.get(uuid);
	}
	
	public static void loadAllPlayers(File folder) {
		if (folder == null) return;
		if (!folder.exists()) return;
		
		for (File f : folder.listFiles()) {
			if (!f.isFile()) continue;
			
			if (!f.getName().endsWith(".yml")) continue;
			
			UUID uuid = UUID.fromString(f.getName().replaceAll(".yml", ""));
			addPlayer(uuid, UUIDConverter.getName(uuid));
		}
	}
}
