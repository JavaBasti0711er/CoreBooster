package me.enchanted.corebooster.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BoosterManager {

	private static HashMap<Player, Booster> boosters = new HashMap<Player, Booster>();
	
	public void setBooster(Player p, String type, double tier, double level, double xp) {
		Booster booster = new Booster(p, type, tier, level, xp);
		boosters.put(p, booster);
	}
	
	public void removeBooster(Player p) {
		boosters.remove(p);
	}
	
	public Booster getBooster(Player p) {
		return boosters.get(p);
	}
	
	public boolean hasActiveBooster(Player p) {
		return boosters.containsKey(p);
	}
	
	public void saveBoosters() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(hasActiveBooster(p)) {
				PlayerData pd = new PlayerData(p.getUniqueId());
				Booster b = getBooster(p);
				
				pd.setBooster(b.getType(), b.getTier(), b.getLevel(), b.getXP());
			} else {
				removeBooster(p);
			}
		}
	}
}