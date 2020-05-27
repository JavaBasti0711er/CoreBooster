package me.enchanted.corebooster.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Strings;

import me.enchanted.corebooster.CoreBooster;

public class Items {

	CoreBooster plugin = CoreBooster.getInstance();
	
	public ItemStack getBooster(String type, double tier, double level, double xp) {
		ArrayList<String> lore = new ArrayList<String>();
		DecimalFormat f = new DecimalFormat("#");
		DecimalFormat df = new DecimalFormat("#");
		df.setGroupingUsed(true);
		df.setGroupingSize(3);
		double cost = plugin.getC().getBase()
				+ (long) Math.pow(plugin.getC().getBase() * (level-1),
						plugin.getC().getFactor());
		
		double old = level == 1 ? 0 : plugin.getC().getBase()
				+ (long) Math.pow(plugin.getC().getBase() * (level-2),
						plugin.getC().getFactor());
		
		String percent = f.format((xp / (cost-old)) * 100.0);
		
		ItemStack booster = new ItemStack(Material.AIR);
		ItemMeta im = booster.getItemMeta();
		if(type.equalsIgnoreCase("money")) {
			booster = new ItemStack(Material.INK_SACK, 1, (byte) 10);
			im = booster.getItemMeta();
			im.setDisplayName("§a§lTier §2§l" + f.format(tier) + "§a§l Money Booster");
			lore.add("  §7* §2§lType: §a" + StringUtils.capitalize(type.toLowerCase()));
			lore.add("  §7* §2§lLevel: §a" + df.format(level) + "§2/" + df.format(plugin.getC().getLPT() * tier) + "");
			lore.add("  §7* §2§lXP: §8[" + getProgressBar(xp, old,
					cost, 40, '|', "§a", "§7") + "§8]" + " §a§l" + percent + "%" + " §8[§a" + df.format(xp)+"§2/" + df.format(cost) + "§8]");
			lore.add("  §7* §2§lBoost: §a" + ((5 * (int)level) - 5) + "%-" + (5 * (int)level) + "%");
		} else if(type.equalsIgnoreCase("token")) {
			booster = new ItemStack(Material.INK_SACK, 1, (byte) 14);
			im = booster.getItemMeta();
			im.setDisplayName("§e§lTier §6§l" + f.format(tier) + "§e§l Token Booster");
			lore.add("  §7* §6§lType: §e" + StringUtils.capitalize(type.toLowerCase()));
			lore.add("  §7* §6§lLevel: §e" + f.format(level) + "§e/" + df.format(plugin.getC().getLPT() * tier) + "");
			lore.add("  §7* §6§lXP: §8[" + getProgressBar(xp, old,
					cost, 40, '|', "§a", "§7") + "§8]" + " §e§l" + percent + "%" + " §8[§e" + df.format(xp)+"§6/" + df.format(cost) + "§8]");
			lore.add("  §7* §6§lBoost: §e" + ((5 * (int)level) - 5) + "%-" + (5 * (int)level) + "%");
		}
		im.setLore(lore);
		booster.setItemMeta(im);
		
		return booster;
	}

	public String getProgressBar(double d, double e, double cost, int totalBars, char symbol, String completedColor,
			String notCompletedColor) {
		float percent = (float) ((float) d / (cost - e));
		int progressBars = (int) (totalBars * percent);

		return Strings.repeat("" + completedColor + symbol, progressBars)
				+ Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
	}
}
