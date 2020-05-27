 package me.enchanted.corebooster.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.enchanted.corebooster.CoreBooster;
import me.enchanted.corebooster.utils.Items;
import me.enchanted.corebooster.utils.PlayerData;


public class RedeemBoosterCommand implements CommandExecutor {

	Items items = new Items();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			UUID u = p.getUniqueId();
			PlayerData data = new PlayerData(u);
			if (p.getInventory().firstEmpty() == -1) {
				p.sendMessage("§cYour inventory is full so you cannot use /redeembooster");
				return true;
			}
			int current = 0;
			for (String min : data.getRedeemable()) {
				if (p.getInventory().firstEmpty() == -1) {
					break;
				}
				String type = data.getConfig().getString("redeemable." + min + ".Type");
				double tier = data.getConfig().getInt("redeemable." + min + ".Tier");
				double level = data.getConfig().getInt("redeemable." + min + ".Level");
				p.getInventory().addItem(items.getBooster(type, tier, level, 0));
				data.removeRedeemable(type, tier, level);
				current++;
			}
			p.sendMessage("§eYou were able to redeem " + current + " of your boosters successfully!");
		}
		return true;
	}
}