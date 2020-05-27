package me.enchanted.corebooster.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.Player;

import me.enchanted.corebooster.CoreBooster;

public class Increaser {

	BoosterManager manager = CoreBooster.getInstance().getManager();

	public double getIncrease(Player p) {
		if (manager.hasActiveBooster(p)) {
			Booster b = manager.getBooster(p);
			double returned = (ThreadLocalRandom.current().nextDouble((5 * (int) b.getLevel()) - 5,
					5 * (int) b.getLevel())) / 100;
			
			return returned;
		}
		return 1;
	}
}
