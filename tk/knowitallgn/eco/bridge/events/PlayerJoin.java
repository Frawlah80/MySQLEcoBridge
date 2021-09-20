package tk.knowitallgn.eco.bridge.events;

import tk.knowitallgn.eco.bridge.Eco;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class PlayerJoin implements Listener {
  private Eco eco;
  
  public PlayerJoin(Eco eco) {
    this.eco = eco;
  }
  
  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {
    Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)this.eco, new Runnable() {
          public void run() {
            if (event.getPlayer() != null && 
              event.getPlayer().isOnline()) {
              Player p = event.getPlayer();
              PlayerJoin.this.eco.getEcoDataHandler().onJoinFunction(p);
              PlayerJoin.this.syncCompleteTask(p);
            } 
          }
        },  5L);
  }
  
  private void syncCompleteTask(final Player p) {
    if (p != null && 
      p.isOnline()) {
      final long startTime = System.currentTimeMillis();
      BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this.eco, new Runnable() {
            public void run() {
              if (p.isOnline())
                if (PlayerJoin.this.eco.getEcoDataHandler().isSyncComplete(p)) {
                  if (PlayerJoin.this.eco.syncCompleteTasks.containsKey(p)) {
                    int taskID = ((Integer)PlayerJoin.this.eco.syncCompleteTasks.get(p)).intValue();
                    PlayerJoin.this.eco.syncCompleteTasks.remove(p);
                    Bukkit.getScheduler().cancelTask(taskID);
                  } 
                } else if (System.currentTimeMillis() - startTime >= 10000L && 
                  PlayerJoin.this.eco.syncCompleteTasks.containsKey(p)) {
                  int taskID = ((Integer)PlayerJoin.this.eco.syncCompleteTasks.get(p)).intValue();
                  PlayerJoin.this.eco.syncCompleteTasks.remove(p);
                  Bukkit.getScheduler().cancelTask(taskID);
                }  
            }
          },  5L, 20L);
      this.eco.syncCompleteTasks.put(p, Integer.valueOf(task.getTaskId()));
    } 
  }
}
