package me.enchanted.corebooster.utils;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import me.enchanted.corebooster.CoreBooster;

public class PlayerData {

	private File file;
	public FileConfiguration config;

	UUID uuid;

	public PlayerData(UUID uuid) {
		this.uuid = uuid;
		file = new File(CoreBooster.getInstance().getDataFolder() + "/PlayerData", uuid + ".yml");
		config = YamlConfiguration.loadConfiguration(file);
	}

	public boolean exists() {
		return file.exists();
	}

	public Configuration getConfig() {
		return config;
	}

	public void setBooster(String type, double tier, double level, double xp) {
		try {
			config.set("booster.type", type);
			config.set("booster.tier", tier);
			config.set("booster.level", level);
			config.set("booster.xp", xp);
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeBooster() {
		try {
			config.set("booster", null);
			config.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasActiveBooster() {
		return getConfig().contains("booster");
	}

	public String getType() {
		return getConfig().getString("booster.type");
	}

	public double getTier() {
		return getConfig().getDouble("booster.tier");
	}

	public double getLevel() {
		return getConfig().getDouble("booster.level");
	}

	public double getXP() {
		return getConfig().getDouble("booster.xp");
	}

	public Set<String> getRedeemable() {
		return getConfig().getConfigurationSection("redeemable").getKeys(false);
	}

	public void addRedeemable(String type, double tier, double level) {
		UUID u = UUID.randomUUID();
		try {
			getConfig().set("redeemable." + u + ".Type", type);
			getConfig().set("redeemable." + u + ".Tier", tier);
			getConfig().set("redeemable." + u + ".Level", level);
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeRedeemable(String type, double tier, double level) {
		try {
			for (String s : getConfig().getConfigurationSection("redeemable").getKeys(false)) {
				if (getConfig().getString("redeemable." + s + ".Type").equalsIgnoreCase(type)
						&& getConfig().getDouble("redeemable." + s + ".Tier") == tier
						&& getConfig().getDouble("redeemable." + s + ".Level") == level) {
					getConfig().set("redeemable." + s, null);
					config.save(file);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
