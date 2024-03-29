package tk.knowitallgn.eco.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import tk.knowitallgn.eco.bridge.commands.Commands;
import tk.knowitallgn.eco.bridge.database.EcoMysqlHandler;
import tk.knowitallgn.eco.bridge.database.MysqlSetup;
import tk.knowitallgn.eco.bridge.events.PlayerDisconnect;
import tk.knowitallgn.eco.bridge.events.PlayerJoin;
import tk.knowitallgn.eco.bridge.events.handlers.EcoDataHandler;

public class Eco extends JavaPlugin {

	public static Logger log;
	public static Economy vault = null;
	public static String pluginName = "KnowItAllMEB";
	public static String authorsName = "KnowItAllNet, brunyman";
	public static String contributorsName = "Your mom";
	public static String versionProperty = "STABLE & Tested BUILD";
	public static String discord = "";
	public Map<Player, Integer> syncCompleteTasks = new HashMap<Player, Integer>();

	private static ConfigHandler configHandler;
	private static MysqlSetup mysqlSetup;
	private static EcoMysqlHandler ecoMysqlHandler;
	private static EcoDataHandler edH;
	private static BackgroundTask bt;

	@Override
	public void onEnable() {
		log = getLogger();
		getCommand("kmeb").setExecutor(new Commands());
		// Setup Vault for economy
		if (setupEconomy() == false) {
			log.severe("Warning! - Vault installed? If yes Economy system installed?");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		// Load Configuration
		configHandler = new ConfigHandler(this);
		// Setup MySQL
		mysqlSetup = new MysqlSetup(this);
		ecoMysqlHandler = new EcoMysqlHandler(this);
		edH = new EcoDataHandler(this);
		bt = new BackgroundTask(this);
		// Register Listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerDisconnect(this), this);
		log.info("Thanks for using this plugin!");
		log.info("By " + authorsName);
		log.info("Thanks to the contributors: " + contributorsName);
		log.info(versionProperty);
		log.info("Need support? Join our discord! " + discord);
		log.info(pluginName + " successfully enabled!");
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		if (mysqlSetup.getConnection() != null) {
			edH.onShutDownDataSave();
			mysqlSetup.closeConnection();
		}
		log.info(pluginName + " successfully disabled!");
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		log.info("Goodbye!");
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		vault = rsp.getProvider();
		log.info("Using economy system: " + rsp.getProvider().getName());
		return vault != null;
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
