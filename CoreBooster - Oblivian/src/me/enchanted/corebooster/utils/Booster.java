package me.enchanted.corebooster.utils;

import org.bukkit.entity.Player;

public class Booster {
	
	private Player p;
	private String type;
	private double tier,xp,level;
	
	public Booster(Player p, String type, double tier, double level, double xp) {
		this.p = p;
		this.type = type;
		this.tier = tier;
		this.level = level;
		this.xp = xp;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public String getType() {
		return type;
	}
	
	public double getTier() {
		return tier;
	}
	
	public double getXP() {
		return xp;
	}
	
	public double getLevel() {
		return level;
	}
	
	public void setTier(double toSet) {
		tier = toSet;
	}
	
	public void addLevel(double toAdd) {
		level += toAdd;
	}
	
	public void addXP(double toAdd) {
		xp += toAdd;
	}
	
	public void setLevel(double toSet) {
		level = toSet;
	}
	
	public void setXP(double toSet) {
		xp = toSet;
	}

}
