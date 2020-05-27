package me.enchanted.corebooster.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Strings;

import me.enchanted.corebooster.CoreBooster;
import me.enchanted.corebooster.utils.Booster;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class BoosterGUI {

	Player p;
	CoreBooster plugin = CoreBooster.getInstance();

	public BoosterGUI(Player p) {
		this.p = p;
	}

	public void open() {
		Inventory inv = null;
		if (plugin.getManager().hasActiveBooster(p)) {
			inv = Bukkit.createInventory(null, 27, "§7              §a§lBooster");
		} else {
			inv = Bukkit.createInventory(null, 27, "§7              §c§lBooster");
		}

		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName("§7");
			is.setItemMeta(im);

			inv.setItem(i, is);
		}
		inv.setItem(13, new ItemStack(Material.AIR));
		ItemStack upgradeBooster = new ItemStack(Material.STONE_BUTTON);
		ItemMeta upgradeMeta = upgradeBooster.getItemMeta();
		upgradeMeta.setDisplayName("§6§l* §eUpgrade your §6Booster");
		ArrayList<String> lore2 = new ArrayList<>();
		lore2.add("§6§l* §7You need 3 same type & tier Booster");
		lore2.add("§6§l* §7to upgrade it to a higher tier");
		lore2.add("§d   ");
		lore2.add("§6§l* §eExample:");
		lore2.add("§6§l* §63x Tier 3 §7upgrades to a §6Tier 4");
		lore2.add("§e    ");
		lore2.add("§6§l* WARNING");
		lore2.add("§6§l* §eXP & LEVEL get reset");
		upgradeMeta.setLore(lore2);
		upgradeBooster.setItemMeta(upgradeMeta);
		inv.setItem(26, addGlow(upgradeBooster));

		inv.setItem(13, new ItemStack(Material.AIR));
		

		if (plugin.getManager().hasActiveBooster(p)) {

			for (int i = 0; i < inv.getSize(); i++) {
				ItemStack is = null;
				if (plugin.getManager().getBooster(p).getType().equalsIgnoreCase("money")) {
					is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
				} else if (plugin.getManager().getBooster(p).getType().equalsIgnoreCase("token")) {
					is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1);
				} else {
					is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 8);
				}
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("§7");
				is.setItemMeta(im);

				inv.setItem(i, is);
				inv.setItem(26, addGlow(upgradeBooster));
			}

			inv.setItem(13, new ItemStack(Material.AIR));
			Booster b = plugin.getManager().getBooster(p);
			ArrayList<String> lore = new ArrayList<String>();
			DecimalFormat f = new DecimalFormat("#");
			DecimalFormat df = new DecimalFormat("#");
			df.setGroupingUsed(true);
			df.setGroupingSize(3);
			double cost = plugin.getC().getBase()
					+ (long) Math.pow(plugin.getC().getBase() * (b.getLevel() - 1), plugin.getC().getFactor());

			cost = plugin.getC().getBase()
					+ (long) Math.pow(plugin.getC().getBase() * (b.getLevel() - 1), plugin.getC().getFactor());

			double old = b.getLevel() == 1 ? 0
					: plugin.getC().getBase()
							+ (long) Math.pow(plugin.getC().getBase() * (b.getLevel() - 2), plugin.getC().getFactor());

			String percent = f.format((b.getXP() / (cost)) * 100.0);

			System.out.print(b.getXP() + ":" + cost);
			if (b.getXP() >= cost) {
				b.setXP(cost - 1);
			}

			ItemStack booster = new ItemStack(Material.AIR);
			ItemMeta im = booster.getItemMeta();
			double returned = (ThreadLocalRandom.current().nextDouble((5 * (int) b.getLevel()) - 5,
					5 * (int) b.getLevel()));
			if (b.getType().equalsIgnoreCase("money")) {
				booster = new ItemStack(Material.INK_SACK, 1, (byte) 10);
				im = booster.getItemMeta();
				im.setDisplayName("§a§lTier§2§l" + f.format(b.getTier()) + "§a§l Money Booster");
				lore.add(" §7*§2§lType:§a" + StringUtils.capitalize(b.getType().toLowerCase()));
				lore.add(" §7*§2§lLevel:§a" + df.format(b.getLevel()) + "§2/"
						+ df.format(plugin.getC().getLPT() * b.getTier()));
				lore.add(" §7*§2§lXP:§8[" + getProgressBar(b.getXP(), old, cost, 40, '|', "§a", "§7") + "§8]" + "§a§l"
						+ percent + "%" + "§8[§a" + df.format(b.getXP()) + "§2/" + df.format(cost) + "§8]");
				lore.add(" §7*§2§lBoost:§a" + ((5 * (int) b.getLevel()) - 5) + "%-" + (5 * (int) b.getLevel()) + "%");
			} else if (b.getType().equalsIgnoreCase("token")) {
				booster = new ItemStack(Material.INK_SACK, 1, (byte) 14);
				im = booster.getItemMeta();
				im.setDisplayName("§e§lTier§6§l" + f.format(b.getTier()) + "§e§l Token Booster");
				lore.add(" §7*§6§lType:§e" + StringUtils.capitalize(b.getType().toLowerCase()));
				lore.add(" §7*§6§lLevel:§e" + f.format(b.getLevel()) + "§6/"
						+ df.format(plugin.getC().getLPT() * b.getTier()));
				lore.add(" §7*§6§lXP:§8[" + getProgressBar(b.getXP(), old, cost, 40, '|', "§e", "§7") + "§8]" + "§e§l"
						+ percent + "%" + "§8[§e" + df.format(b.getXP()) + "§6/" + df.format(cost) + "§8]");
				lore.add(" §7*§6§lBoost:§e" + ((5 * (int) b.getLevel()) - 5) + "%-" + (5 * (int) b.getLevel()) + "%");
			}
			im.setLore(lore);

			booster.setItemMeta(im);

			inv.setItem((inv.getSize() - 1) / 2, booster);
		}

		p.openInventory(inv);
	}

	public String getProgressBar(double d, double e, double cost, int totalBars, char symbol, String completedColor,
			String notCompletedColor) {
		float percent = (float) ((float) d / cost);
		System.out.print(d);
		System.out.print(cost);
		System.out.print(percent);
		int progressBars = (int) (totalBars * percent);

		return Strings.repeat("" + completedColor + symbol, progressBars)
				+ Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
	}

	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null)
			tag = nmsStack.getTag();
		NBTTagList enchantment = new NBTTagList();
		tag.set("ench", (NBTBase) enchantment);
		nmsStack.setTag(tag);
		return (ItemStack) CraftItemStack.asCraftMirror(nmsStack);
	}
}
