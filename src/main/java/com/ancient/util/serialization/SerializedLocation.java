package com.ancient.util.serialization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class SerializedLocation implements Serializable, ConfigurationSerializable {
	private static final long serialVersionUID = -4024229540724198112L;
	private final String WORLD;
	private final double X;
	private final double Y;
	private final double Z;
	
	public SerializedLocation(String world, double x, double y, double z) {
		this.WORLD = world;
		this.X = x;
		this.Y = y;
		this.Z = z;
	}
	
	public SerializedLocation(Map<String, Object> map) {
		this.WORLD = (String) map.get("world");;
		this.X = (Double) map.get("x");
		this.Y = (Double) map.get("y");
		this.Z = (Double) map.get("z");
	}
	
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> serialized = new HashMap<String, Object>();
		
		serialized.put("world", this.WORLD);
		serialized.put("x", this.X);
		serialized.put("y", this.Y);
		serialized.put("z", this.Z);
		
		return serialized;
	}
}
