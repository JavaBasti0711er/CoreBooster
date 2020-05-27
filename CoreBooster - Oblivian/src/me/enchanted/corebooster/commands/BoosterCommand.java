package me.enchanted.corebooster.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.enchanted.corebooster.CoreBooster;
import me.enchanted.corebooster.gui.BoosterGUI;
import me.enchanted.corebooster.utils.BoosterManager;
import me.enchanted.corebooster.utils.Items;
import me.enchanted.corebooster.utils.PlayerData;

public class BoosterCommand implements CommandExecutor {

	CoreBooster plugin = CoreBooster.getInstance();
	BoosterManager manager = plugin.getManager();
	Items items = new Items();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				BoosterGUI gui = new BoosterGUI(p);
				gui.open();
			}
		}

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("give")) {
				if (!sender.hasPermission("booster.give")) {
					sender.sendMessage("§4You do not have permission for that!");
					return true;
				}
				if (args.length != 5) {
					sender.sendMessage("§cUsage: /booster give [Player] [Type] [Tier] [Level]");
					return true;
				} else {
					try {
						double tier = Double.valueOf(args[3]);
						double level = Double.valueOf(args[4]);
						if (Bukkit.getPlayer(args[1]) == null) {
							PlayerData data = new PlayerData(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
							data.addRedeemable(args[2].toLowerCase(), tier, level);
							sender.sendMessage("§cThat player isn't online.");
							return true;
						}

						if (args[2].equalsIgnoreCase("money") || args[2].equalsIgnoreCase("token")) {
							Player target = Bukkit.getPlayer(args[1]);
							
							if(target.getInventory().firstEmpty() == -1) {
								PlayerData data = new PlayerData(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
								data.addRedeemable(args[2].toLowerCase(), tier, level);
								return true;
							}
							
							target.getInventory().addItem(items.getBooster(args[2].toLowerCase(), tier, level, 0));
							sender.sendMessage("§eYou have given §6" + target.getName() + " §ea §6"
									+ StringUtils.capitalize(args[2].toLowerCase()) + " §eBooster.");
						} else {
							sender.sendMessage("§cInvalid Booster type. \nBoosters: §aMoney§c, §eToken");
						}

					} catch (NumberFormatException ex) {
						sender.sendMessage("§cPlease enter a valid number.");
					}
				}
			}
		}
		return true;
	}

}
