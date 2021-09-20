package tk.knowitallgn.eco.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import tk.knowitallgn.eco.bridge.database.EcoMysqlHandler;
import tk.knowitallgn.eco.bridge.database.MysqlSetup;
import tk.knowitallgn.eco.bridge.events.PlayerDisconnect;
import tk.knowitallgn.eco.bridge.events.PlayerJoin;
import tk.knowitallgn.eco.bridge.events.handlers.EcoDataHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Eco extends JavaPlugin {
  public static Logger log;
  
  public static Economy vault = null;
  
  public static String pluginName = "KnowItAllMEB";
  
  public Map<Player, Integer> syncCompleteTasks = new HashMap<>();
  
  private static ConfigHandler configHandler;
  
  private static MysqlSetup mysqlSetup;
  
  private static EcoMysqlHandler ecoMysqlHandler;
  
  private static EcoDataHandler edH;
  
  private static BackgroundTask bt;
  
  public void onEnable() {
    log = getLogger();
    if (!setupEconomy()) {
      log.severe("Warning! - Vault installed? If yes Economy system installed?");
      getServer().getPluginManager().disablePlugin((Plugin)this);
      return;
    } 
    configHandler = new ConfigHandler(this);
    mysqlSetup = new MysqlSetup(this);
    ecoMysqlHandler = new EcoMysqlHandler(this);
    edH = new EcoDataHandler(this);
    bt = new BackgroundTask(this);
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents((Listener)new PlayerJoin(this), (Plugin)this);
    pm.registerEvents((Listener)new PlayerDisconnect(this), (Plugin)this);
    log.info(String.valueOf(pluginName) + " loaded successfully! Thanks for using this plugin!");
  }
  
  public void onDisable() {
    Bukkit.getScheduler().cancelTasks((Plugin)this);
    HandlerList.unregisterAll((Plugin)this);
    if (mysqlSetup.getConnection() != null) {
      edH.onShutDownDataSave();
      mysqlSetup.closeConnection();
    } 
    log.info(String.valueOf(pluginName) + " is disabled! GoodBye!");
  }
  
  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null)
      return false; 
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null)
      return false; 
    vault = (Economy)rsp.getProvider();
    log.info("Using economy system: " + ((Economy)rsp.getProvider()).getName());
    return (vault != null);
  }
  
  public ConfigHandler getConfigHandler() {
    return configHandler;
  }
  
  public MysqlSetup getMysqlSetup() {
    return mysqlSetup;
  }
  
  public EcoMysqlHandler getEcoMysqlHandler() {
    return ecoMysqlHandler;
  }
  
  public EcoDataHandler getEcoDataHandler() {
    return edH;
  }
  
  public BackgroundTask getBackgroundTask() {
    return bt;
  }
}
