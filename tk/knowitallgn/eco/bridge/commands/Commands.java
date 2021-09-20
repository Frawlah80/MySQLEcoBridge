package tk.knowitallgn.eco.bridge.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Commands extends JavaPlugin implements Listener {
 
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	if (!sender.hasPermission("knowitallmeb.admin")) {
    		sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to do this!");
    		return true;
    	}
        /*
        /motd: Display help information.
        /motd set <message>: Set the MOTD to <message>.
        /motd reload: Reload the config file.
         */
        
        if (cmd.getName().equalsIgnoreCase("kmeb")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/kmeb reload: Reload the config file.");
                Bukkit.getServer().getPluginManager().disablePlugin(this);
                Bukkit.getServer().getPluginManager().enablePlugin(this);
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Reloaded config.");
            }
            
            else {
                sender.sendMessage(ChatColor.RED + "Invalid operation.");
            }
        }
        
        return true;
    }
} 
