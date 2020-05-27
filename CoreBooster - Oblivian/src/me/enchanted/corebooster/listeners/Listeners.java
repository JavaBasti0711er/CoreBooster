package me.enchanted.corebooster.listeners;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.enchanted.corebooster.CoreBooster;
import me.enchanted.corebooster.gui.BoosterGUI;
import me.enchanted.corebooster.gui.UpgradeGUI;
import me.enchanted.corebooster.utils.Booster;
import me.enchanted.corebooster.utils.BoosterManager;
import me.enchanted.corebooster.utils.Items;
import me.enchanted.corebooster.utils.PlayerData;

public class Listeners implements Listener {

	CoreBooster plugin = CoreBooster.getInstance();
	BoosterManager manager = plugin.getManager();
	Items items = new Items();

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		PlayerData pd = new PlayerData(uuid);

		if (!pd.exists()) {
			try {
				File dir = new File(CoreBooster.getInstance().getDataFolder() + File.separator + "PlayerData");
				File file = new File(CoreBooster.getInstance().getDataFolder() + File.separator + "PlayerData",
						uuid + ".yml");
				dir.mkdirs();
				file.createNewFile();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		}

		if (pd.hasActiveBooster()) {
			manager.setBooster(p, pd.getType(), pd.getTier(), pd.getLevel(), pd.getXP());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		PlayerData pd = new PlayerData(uuid);

		if (manager.hasActiveBooster(p)) {
			Booster b = manager.getBooster(p);
			pd.setBooster(b.getType(), b.getTier(), b.getLevel(), b.getXP());
		} else {
			manager.removeBooster(p);
			pd.removeBooster();
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	  public void onClick(InventoryClickEvent e) {
	    Player p = (Player)e.getWhoClicked();
	    if (e.getView().getTitle().equalsIgnoreCase("§7        §6§l* §e§lUpgrade Booster §6§l*")) {
	      Inventory inv = e.getInventory();
	      if (e.getRawSlot() == 16 && inv.getItem(16) != null && (
	        e.getCursor() == null || e.getCursor().getType() == Material.AIR))
	        return; 
	      if (e.getRawSlot() >= 0 && e.getRawSlot() < 27 && e.getRawSlot() != 10 && e.getRawSlot() != 11 && 
	        e.getRawSlot() != 12) {
	        e.setCancelled(true);
	      } else if ((e.getRawSlot() == 10 || e.getRawSlot() == 11 || e.getRawSlot() == 12) && 
	        e.getAction().equals(InventoryAction.PLACE_ONE)) {
	        e.setCancelled(true);
	      } 
			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
						if (e.getCurrentItem().getItemMeta().getDisplayName()
								.equalsIgnoreCase("§6§l* §aCLICK HERE TO CONFIRM")) {
							if (inv.getItem(10) == null || inv.getItem(11) == null || inv.getItem(12) == null) {
								p.sendMessage("§6§lBooster » §eYou have not filled all of the slots.");
								return;
							}
							if (isMoneyBooster(inv.getItem(10)) && isMoneyBooster(inv.getItem(11))
									&& isMoneyBooster(inv.getItem(12))) {
								int tier = getBoosterTier(inv.getItem(10));
								if (inv.getItem(10).getAmount() == 1 && inv.getItem(11).getAmount() == 1
										&& inv.getItem(12).getAmount() == 1) {
									if (tier == getBoosterTier(inv.getItem(11))
											&& tier == getBoosterTier(inv.getItem(12))) {
										inv.setItem(10, new ItemStack(Material.AIR));
										inv.setItem(11, new ItemStack(Material.AIR));
										inv.setItem(12, new ItemStack(Material.AIR));
										inv.setItem(16, items.getBooster("money", tier + 1, 1, 0));
									} else {
										p.sendMessage("§6§lBooster » §eYour boosters aren't the same tier.");
										return;
									}
									return;
								} else {
									p.sendMessage("§6§lBooster » §eUnstack your items.");
									return;
								}
							}

							if (isTokenBooster(inv.getItem(10)) && isTokenBooster(inv.getItem(11))
									&& isTokenBooster(inv.getItem(12))) {
								int tier = getBoosterTier(inv.getItem(10));
								if (inv.getItem(10).getAmount() == 1 && inv.getItem(11).getAmount() == 1
										&& inv.getItem(12).getAmount() == 1) {
									if (tier == getBoosterTier(inv.getItem(11))
											&& tier == getBoosterTier(inv.getItem(12))) {
										inv.setItem(10, new ItemStack(Material.AIR));
										inv.setItem(11, new ItemStack(Material.AIR));
										inv.setItem(12, new ItemStack(Material.AIR));
										inv.setItem(16, items.getBooster("token", tier + 1, 1, 0));
									} else {
										p.sendMessage("§6§lBooster » §eYour boosters aren't the same tier.");
										return;
									}
									return;
								} else {
									p.sendMessage("§6§lBooster » §eUnstack your items.");
									return;
								}
							}

							p.sendMessage("§6§lBooster » §eYour inputs are not of the same type.");
						}
					}
				}
			}
		}

		if (e.getView().getTitle().equalsIgnoreCase("§7              §c§lBooster")
				|| e.getView().getTitle().equalsIgnoreCase("§7              §a§lBooster")) {
			if (e.getCurrentItem() != null) {
				if (e.getRawSlot() >= 0 && e.getRawSlot() < 27 && e.getRawSlot() != 13) {
					e.setCancelled(true);
					if (e.getCurrentItem().getType() == Material.STONE_BUTTON && e.getCurrentItem().getItemMeta()
							.getDisplayName().equalsIgnoreCase("§6§l* §eUpgrade your §6Booster")) {
						UpgradeGUI upGUI = new UpgradeGUI(p);
						upGUI.open();
					}
				} else if (e.getRawSlot() == 13) {
					if (e.getAction().equals(InventoryAction.PICKUP_ALL)
							|| e.getAction().equals(InventoryAction.PLACE_ALL)) {
						if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
							e.setCancelled(true);
							manager.removeBooster(p);
							ItemStack cu = e.getCurrentItem();
							BoosterGUI gui = new BoosterGUI(p);
							gui.open();
							e.setCursor(cu);
							manager.removeBooster(p);
						} else if (e.getAction().equals(InventoryAction.PLACE_ALL)) {
							e.setCancelled(true);
							ItemStack is = e.getCursor();
							if (ChatColor.stripColor(is.getItemMeta().getDisplayName().split(" ")[2]).toLowerCase()
									.equalsIgnoreCase("money")
									|| ChatColor.stripColor(is.getItemMeta().getDisplayName().split(" ")[2])
											.toLowerCase().equalsIgnoreCase("token")) {
								if (e.getCursor().getAmount() > 1) {
									p.sendMessage("§cYou can only put one booster in at a time.");
									return;
								}

								e.getInventory().setItem(13, e.getCursor());
								e.setCursor(new ItemStack(Material.AIR));

								String type = "";
								double tier = 0;
								double level = 0;
								String exp = "";
								type = ChatColor.stripColor(is.getItemMeta().getDisplayName().split(" ")[2])
										.toLowerCase();
								tier = Double
										.valueOf(ChatColor.stripColor(is.getItemMeta().getDisplayName().split(" ")[1]));
								level = Double.valueOf(ChatColor.stripColor(
										is.getItemMeta().getLore().get(1).replaceAll("[/][\\d]+", "").split(" ")[4]));

								double cost = plugin.getC().getBase() + (long) Math
										.pow(plugin.getC().getBase() * (level - 1), plugin.getC().getFactor());

								exp = ChatColor.stripColor(is.getItemMeta().getLore().get(2).split(" ")[6]
										.replace("[", "").replace("]", "").replace(",", ""));
								exp = exp.replaceAll("[/][\\d]+", "");

								double xp = Double.valueOf(exp);

								manager.setBooster(p, type, tier, level, xp);

								BoosterGUI gui = new BoosterGUI(p);
								gui.open();
							} else {
								p.sendMessage("§cThat is not a booster!");
							}
						}
					} else {
						p.sendMessage("§cPlease put your booster in the slot.");
						e.setCancelled(true);
					}
				}
			}
		}
	}

	public String getType(ItemStack item) {
		return ChatColor.stripColor(item.getItemMeta().getDisplayName().split(" ")[2]).toLowerCase();
	}

	public boolean isMoneyBooster(ItemStack i) {
		if (i != null && i.getType() == Material.INK_SACK && i.hasItemMeta()) {
			ItemMeta m = i.getItemMeta();
			if (m.hasDisplayName() && m.hasLore() && ChatColor.stripColor(m.getDisplayName()).startsWith("Tier ")
					&& ChatColor.stripColor(m.getDisplayName()).endsWith(" Money Booster")) {
				List<String> l = m.getLore();
				if (l.size() == 4 && l.get(0).startsWith("  §7* §2§lType: ")
						&& l.get(1).startsWith("  §7* §2§lLevel: ")) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isTokenBooster(ItemStack i) {
		if (i != null && i.getType() == Material.INK_SACK && i.hasItemMeta()) {
			ItemMeta m = i.getItemMeta();
			if (m.hasDisplayName() && m.hasLore() && ChatColor.stripColor(m.getDisplayName()).startsWith("Tier ")
					&& ChatColor.stripColor(m.getDisplayName()).endsWith(" Token Booster")) {
				List<String> l = m.getLore();
				if (l.size() == 4 && l.get(0).startsWith("  §7* §6§lType: ")
						&& l.get(1).startsWith("  §7* §6§lLevel: ")) {
					return true;
				}
			}
		}
		return false;
	}

	public Integer getBoosterTier(ItemStack i) {
		return Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getDisplayName().split(" ")[1]));
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (e.getView().getTitle().equalsIgnoreCase("§7        §6§l* §e§lUpgrade Booster §6§l*")) {
			p.getInventory().addItem(e.getView().getItem(10));
			p.getInventory().addItem(e.getView().getItem(11));
			p.getInventory().addItem(e.getView().getItem(12));
		}
		if (e.getView().getTitle().equalsIgnoreCase("§7               §c§lBooster")
				|| e.getView().getTitle().equalsIgnoreCase("§7               §a§lBooster")) {

			ItemStack is = e.getInventory().getItem(13);

			if (is != null) {
				String type = "";
				double tier = 0;
				double level = 0;
				String exp = "";
				type = ChatColor.stripColor(is.getItemMeta().getDisplayName().split(" ")[2]).toLowerCase();
				tier = Double.valueOf(ChatColor.stripColor(is.getItemMeta().getDisplayName().split(" ")[1]));
				level = Double.valueOf(ChatColor
						.stripColor(is.getItemMeta().getLore().get(1).replaceAll("[/][\\d]+", "").split(" ")[4]));

				double cost = plugin.getC().getBase()
						+ (long) Math.pow(plugin.getC().getBase() * (level - 1), plugin.getC().getFactor());

				exp = ChatColor.stripColor(is.getItemMeta().getLore().get(2).split(" ")[6].replace("[", "")
						.replace("]", "").replace(",", ""));
				exp = exp.replaceAll("[/][\\d]+", "");

				double xp = Double.valueOf(exp);

				manager.setBooster(p, type, tier, level, xp);
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (manager.hasActiveBooster(p)) {
			Booster b = manager.getBooster(p);
			double cost = plugin.getC().getBase()
					+ (long) Math.pow(plugin.getC().getBase() * (b.getLevel() - 1), plugin.getC().getFactor());
			if (b.getXP() >= cost) {
				if (b.getLevel() + 1 <= plugin.getC().getLPT() * b.getTier()) {
					b.setXP(0);
					b.addLevel(1);
				} else if (b.getLevel() + 1 > plugin.getC().getLPT() * b.getTier()) {
					b.setXP(cost - 1);
				}
			} else {
				b.addXP(1.00);
			}
		}
	}
}
