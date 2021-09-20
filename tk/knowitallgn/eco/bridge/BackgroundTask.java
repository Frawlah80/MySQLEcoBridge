package tk.knowitallgn.eco.bridge;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BackgroundTask {
  private Eco m;
  
  private long lastSave = System.currentTimeMillis();
  
  public BackgroundTask(Eco m) {
    this.m = m;
    runTask();
  }
  
  private void runTask() {
    if (this.m.getConfigHandler().getBoolean("General.saveDataTask.enabled").booleanValue()) {
      Eco.log.info("Data save task is enabled.");
    } else {
      Eco.log.info("Data save task is disabled.");
    } 
    Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.m, new Runnable() {
          public void run() {
            BackgroundTask.this.m.getEcoDataHandler().updateBalanceMap();
            BackgroundTask.this.runSaveData();
          }
        });
  }
  
  private void runSaveData() {
    if (this.m.getConfigHandler().getBoolean("General.saveDataTask.enabled").booleanValue() && 
      !Bukkit.getOnlinePlayers().isEmpty() && 
      System.currentTimeMillis() - this.lastSave >= (this.m.getConfigHandler().getInteger("General.saveDataTask.interval").intValue() * 60 * 1000)) {
      List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
      this.lastSave = System.currentTimeMillis();
      if (!this.m.getConfigHandler().getBoolean("General.saveDataTask.hideLogMessages").booleanValue())
        Eco.log.info("Saving online players data..."); 
      for (Player p : onlinePlayers) {
        if (p.isOnline())
          this.m.getEcoDataHandler().onDataSaveFunction(p, Boolean.valueOf(false), "false", Boolean.valueOf(false)); 
      } 
      if (!this.m.getConfigHandler().getBoolean("General.saveDataTask.hideLogMessages").booleanValue())
        Eco.log.info("Data save complete for " + onlinePlayers.size() + " players."); 
      onlinePlayers.clear();
    } 
  }
}
