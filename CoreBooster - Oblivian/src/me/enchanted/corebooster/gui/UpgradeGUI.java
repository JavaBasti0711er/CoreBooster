package me.enchanted.corebooster.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.enchanted.corebooster.CoreBooster;

public class UpgradeGUI {
	Player p;
	CoreBooster plugin = CoreBooster.getInstance();

	public UpgradeGUI(Player p) {
		this.p = p;
	}

	public void open() {
		Inventory inv = null;

		inv = Bukkit.createInventory(null, 27, "§7        §6§l* §e§lUpgrade Booster §6§l*");
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName("§7");
			is.setItemMeta(im);
			inv.setItem(i, is);
		}
		inv.setItem(10, new ItemStack(Material.AIR));
		inv.setItem(11, new ItemStack(Material.AIR));
		inv.setItem(12, new ItemStack(Material.AIR));
		inv.setItem(16, new ItemStack(Material.AIR));

		ItemStack upgradeBoosterBarrier = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta upgradeBarrierMeta = upgradeBoosterBarrier.getItemMeta();
		upgradeBarrierMeta.setDisplayName("§6§l* §aCLICK HERE TO CONFIRM");
		ArrayList<String> lore2 = new ArrayList<String>();
		lore2.add("§6§l* §7Put 3 same type & tier booster");
		lore2.add("§6§l* §7in the 3 empty slots");
		lore2.add("§6§l* §7to upgrade it");
		lore2.add("§e    ");
		lore2.add("§6§l* WARNING");
		lore2.add("§6§l* §eXP & LEVEL get reset");
		upgradeBarrierMeta.setLore(lore2);
		upgradeBoosterBarrier.setItemMeta(upgradeBarrierMeta);

		inv.setItem(14, upgradeBoosterBarrier);
		p.openInventory(inv);
	}
}
