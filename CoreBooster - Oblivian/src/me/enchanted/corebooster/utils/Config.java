package me.enchanted.corebooster.utils;

import me.enchanted.corebooster.CoreBooster;

public class Config {
	
	double BASE,FACTOR,LPT;
	
	public Config() {
		BASE = CoreBooster.getInstance().getConfig().getDouble("xp-base");
		FACTOR = CoreBooster.getInstance().getConfig().getDouble("xp-factor");
		LPT = CoreBooster.getInstance().getConfig().getDouble("levels-per-tier");
	}
	
	public double getBase() {
		return BASE;
	}
	
	public double getFactor() {
		return FACTOR;
	}
	
	public double getLPT() {
		return LPT;
	}

}