package tk.knowitallgn.eco.bridge.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import tk.knowitallgn.eco.bridge.Eco;

public class Commands implements CommandExecutor {
	
	Plugin plugin = Eco.getPlugin(Eco.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (cmd.getName().equalsIgnoreCase("kmeb")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GOLD + "/kmeb help: Shows a list of commands.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb reload: Reloads the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb authors: Shows the authors of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb version: Shows the version of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb support: Shows the support page of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb spigot: Shows the spigot link of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb discord: Shows the discord link of the author.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb contributors: Shows the contributors of the plugin.");
				return true;
			}

			else if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(ChatColor.GOLD + "/kmeb help: Shows a list of commands.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb reload: Reloads the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb authors: Shows the authors of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb version: Shows the version of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb support: Shows the support page of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb spigot: Shows the spigot link of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb discord: Shows the discord link of the author.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb contributors: Shows the contributors of the plugin.");
				return true;
			}
			
            else if (args[0].equalsIgnoreCase("authors")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "KnowItAllNet, brunyman");              
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("author")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "KnowItAllNet, brunyman");              
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("version")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Stable - 0.1.0");                 
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("ver")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Stable - 0.1.0");                 
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("support")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "https://dsc.gg/knowitallnet");              
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("spigot")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "https://www.spigotmc.org/resources/knowitallmeb.96522/");              
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("discord")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "https://dsc.gg/knowitallnet");               
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("contributors")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Cawiy, SOULGAMERYT, JACK, IronBat666, Frolic_Gamers, Scorponist ZlataOvce");               
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Reloading the plugin...");
                Bukkit.getServer().getPluginManager().disablePlugin(plugin);
                Bukkit.getServer().getPluginManager().enablePlugin(plugin);
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Successfully reloaded the plugin!");   
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("rel")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Reloading the plugin...");
                Bukkit.getServer().getPluginManager().disablePlugin(plugin);
                Bukkit.getServer().getPluginManager().enablePlugin(plugin);
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Successfully reloaded the plugin!");    
                return true;
            }

			else {
				sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.RED + "Unknown sub-command");
				sender.sendMessage(ChatColor.GOLD + "-------------------------------------------------------");
				sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Available commands are:");
				sender.sendMessage(ChatColor.GOLD + "-------------------------------------------------------");
				sender.sendMessage(ChatColor.GOLD + "/kmeb help: Shows a list of commands.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb reload: Reloads the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb authors: Shows the authors of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb version: Shows the version of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb support: Shows the support page of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb spigot: Shows the spigot link of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb discord: Shows the discord link of the author.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb contributors: Shows the contributors of the plugin.");
				
			}

		}

		return true;
	}
}

			}
			
            else if (args[0].equalsIgnoreCase("authors")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "KnowItAllNet, brunyman");              
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("author")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "KnowItAllNet, brunyman");              
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("version")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Stable build - 0.0.3");                 
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("ver")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Stable build - 0.0.3");                 
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("support")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "https://dsc.gg/knowitallnet");              
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("spigot")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "https://www.spigotmc.org/resources/knowitallmeb.96522/");              
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("discord")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "https://dsc.gg/knowitallnet");               
                return true;
            }
            
            else if (args[0].equalsIgnoreCase("contributors")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Cawiy, SOULGAMERYT, JACK, IronBat666, Frolic_Gamers, Scorponist ZlataOvce");               
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Reloading config. (Please wait for 0.0.4 version of this plugin, reload command doesn't yet work.)");             
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Reloaded config. (Please wait for 0.0.4 version of this plugin, reload command doesn't yet work.)");    
                return true;
            }
			
            else if (args[0].equalsIgnoreCase("rel")) {
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Reloading config. (Please wait for 0.0.4 version of this plugin, reload command doesn't yet work.)");             
                sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Reloaded config. (Please wait for 0.0.4 version of this plugin, reload command doesn't yet work.)");    
                return true;
            }

			else {
				sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.RED + "Unknown sub-command");
				sender.sendMessage(ChatColor.GOLD + "[KnowItAllMEB] " + ChatColor.GREEN + "Available commands are:");
				sender.sendMessage(ChatColor.GOLD + "/kmeb help: Shows a list of commands.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb reload: Reloads the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb authors: Shows the authors of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb version: Shows the version of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb support: Shows the support page of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb spigot: Shows the spigot link of the plugin.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb discord: Shows the discord link of the author.");
				sender.sendMessage(ChatColor.GOLD + "/kmeb contributors: Shows the contributors of the plugin.");
				
			}

		}

		return true;
	}
}
