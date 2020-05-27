package me.enchanted.corebooster;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.enchanted.corebooster.commands.BoosterCommand;
import me.enchanted.corebooster.commands.RedeemBoosterCommand;
import me.enchanted.corebooster.listeners.Listeners;
import me.enchanted.corebooster.utils.BoosterManager;
import me.enchanted.corebooster.utils.Config;
import me.enchanted.corebooster.utils.Increaser;
import me.enchanted.corebooster.utils.PlayerData;

public class CoreBooster extends JavaPlugin implements Listener {
  private static CoreBooster instance;
  
  private BoosterManager manager;
  
  private Increaser increaser;
  
  private Config config;
  
  public void onEnable() {
    instance = this;
    this.manager = new BoosterManager();
    this.increaser = new Increaser();
    this.config = new Config();
    saveInterval();
    saveDefaultConfig();
    setupRedeem();
    getCommand("redeembooster").setExecutor((CommandExecutor)new RedeemBoosterCommand());
    getCommand("booster").setExecutor((CommandExecutor)new BoosterCommand());
    getServer().getPluginManager().registerEvents((Listener)new Listeners(), (Plugin)this);
  }
  
  public void onDisable() {
    this.manager.saveBoosters();
  }
  
  public static CoreBooster getInstance() {
    return instance;
  }
  
  public BoosterManager getManager() {
    return this.manager;
  }
  
  public Increaser getIncreaser() {
    return this.increaser;
  }
  
  public Config getC() {
    return this.config;
  }
  
  public void saveInterval() {
    (new BukkitRunnable() {
        public void run() {
          CoreBooster.this.manager.saveBoosters();
        }
      }).runTaskTimer((Plugin)this, 6000L, 6000L);
  }
  
  public void setupRedeem() {
    (new BukkitRunnable() {
        public void run() {
          for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = new PlayerData(p.getUniqueId());
            try {
              if (data.getRedeemable() != null && data.getRedeemable().size() != 0)
                p.sendMessage(
                		"§eMake sure to do get your boosters you bought from the store."); 
            } catch (Exception exception) {}
          } 
        }
      }).runTaskTimer((Plugin)this, 1200L, 1200L);
  }
}
