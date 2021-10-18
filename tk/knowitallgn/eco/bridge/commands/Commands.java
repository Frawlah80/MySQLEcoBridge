package tk.knowitallgn.eco.bridge.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Commands extends JavaPlugin implements Listener {
     
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        
        if (cmd.getName().equalsIgnoreCase("kmeb")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/kmeb help: Reload the config file.");
                sender.sendMessage(ChatColor.GOLD + "/kmeb authors: Not yet implemented!");
                sender.sendMessage(ChatColor.GOLD + "/kmeb version: Not yet implemented!");
                sender.sendMessage(ChatColor.GOLD + "/kmeb support: Not yet implemented!");
                sender.sendMessage(ChatColor.GOLD + "/kmeb spigot: Not yet implemented!");
                sender.sendMessage(ChatColor.GOLD + "/kmeb discord: Not yet implemented!");
                return true;
            }
            
            
            if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(ChatColor.GREEN + "Reloaded config.");
                Bukkit.getServer().getPluginManager().disablePlugin(this);
                Bukkit.getServer().getPluginManager().enablePlugin(this);
            }
            
            else {
                sender.sendMessage(ChatColor.RED + "Invalid operation.");
            }
        }
        
        return true;
    }
}
   
       
