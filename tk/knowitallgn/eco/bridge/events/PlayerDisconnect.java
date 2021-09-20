package tk.knowitallgn.eco.bridge.events;

import tk.knowitallgn.eco.bridge.Eco;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDisconnect implements Listener {
  private Eco eco;
  
  public PlayerDisconnect(Eco eco) {
    this.eco = eco;
  }
  
  @EventHandler
  public void onDisconnect(final PlayerQuitEvent event) {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.eco, new Runnable() {
          public void run() {
            if (event.getPlayer() != null) {
              Player p = event.getPlayer();
              PlayerDisconnect.this.cleanup(p);
              PlayerDisconnect.this.eco.getEcoDataHandler().onDataSaveFunction(p, Boolean.valueOf(true), "true", Boolean.valueOf(false));
            } 
          }
        });
  }
  
  private void cleanup(Player p) {
    if (this.eco.syncCompleteTasks.containsKey(p)) {
      Bukkit.getScheduler().cancelTask(((Integer)this.eco.syncCompleteTasks.get(p)).intValue());
      this.eco.syncCompleteTasks.remove(p);
    } 
  }
}
