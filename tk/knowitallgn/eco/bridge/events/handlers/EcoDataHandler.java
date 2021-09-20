package tk.knowitallgn.eco.bridge.events.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import tk.knowitallgn.eco.bridge.Eco;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class EcoDataHandler {
  private Eco eco;
  
  private Map<Player, Double> backupMoney = new HashMap<>();
  
  private Map<Player, Double> balanceMap = new HashMap<>();
  
  private Map<Player, Integer> runningTasks = new HashMap<>();
  
  private Set<Player> playersInSync = new HashSet<>();
  
  public EcoDataHandler(Eco eco) {
    this.eco = eco;
  }
  
  public void onShutDownDataSave() {
    Eco.log.info("Saving online players data...");
    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
    for (Player p : onlinePlayers) {
      if (p.isOnline())
        onDataSaveFunction(p, Boolean.valueOf(true), "true", Boolean.valueOf(true)); 
    } 
    Eco.log.info("Data save complete for " + onlinePlayers.size() + " players.");
    //Did you know, this plugin was made from brunyman's plugin, for some reason he stopped updating MySQL Eco Bridge, this plugin gives 1.4-1.17 support, GG
  }
  
  public void updateBalanceMap() {
    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
    if (!onlinePlayers.isEmpty())
      for (Player p : onlinePlayers) {
        if (this.playersInSync.contains(p))
          this.balanceMap.put(p, Double.valueOf(Eco.vault.getBalance((OfflinePlayer)p))); 
      }  
  }
  
  public boolean isSyncComplete(Player p) {
    if (this.playersInSync.contains(p))
      return true; 
    return false;
  }
  
  private void dataCleanup(Player p, Boolean isDisabling) {
    if (!isDisabling.booleanValue()) {
      this.playersInSync.remove(p);
      this.backupMoney.remove(p);
      this.balanceMap.remove(p);
      if (this.runningTasks.containsKey(p)) {
        Bukkit.getScheduler().cancelTask(((Integer)this.runningTasks.get(p)).intValue());
        this.runningTasks.remove(p);
      } 
    } 
  }
  
  private void setPlayerData(Player p, String[] data, boolean cancelTask) {
    try {
      Double bal = Double.valueOf(Eco.vault.getBalance((OfflinePlayer)p));
      if (bal != null) {
        if (bal.doubleValue() != 0.0D)
          Eco.vault.withdrawPlayer((OfflinePlayer)p, bal.doubleValue()); 
        Double mysqlBal = Double.valueOf(Double.parseDouble(data[0]));
        Double localBal = Double.valueOf(Eco.vault.getBalance((OfflinePlayer)p));
        if (mysqlBal.doubleValue() >= localBal.doubleValue()) {
          Double finalBalance = Double.valueOf(mysqlBal.doubleValue() - localBal.doubleValue());
          Eco.vault.depositPlayer((OfflinePlayer)p, finalBalance.doubleValue());
        } else if (mysqlBal.doubleValue() < localBal.doubleValue()) {
          Double finalBalance = Double.valueOf(localBal.doubleValue() - mysqlBal.doubleValue());
          Eco.vault.withdrawPlayer((OfflinePlayer)p, finalBalance.doubleValue());
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      Double backupBalance = this.backupMoney.get(p);
      if (backupBalance.doubleValue() != 0.0D) {
        Double bal = Double.valueOf(Eco.vault.getBalance((OfflinePlayer)p));
        if (bal != null) {
          if (bal.doubleValue() != 0.0D)
            Eco.vault.withdrawPlayer((OfflinePlayer)p, bal.doubleValue()); 
          Double localBal = Double.valueOf(Eco.vault.getBalance((OfflinePlayer)p));
          if (backupBalance.doubleValue() >= localBal.doubleValue()) {
            Double finalBalance = Double.valueOf(backupBalance.doubleValue() - localBal.doubleValue());
            Eco.vault.depositPlayer((OfflinePlayer)p, finalBalance.doubleValue());
          } else if (backupBalance.doubleValue() < localBal.doubleValue()) {
            Double finalBalance = Double.valueOf(localBal.doubleValue() - backupBalance.doubleValue());
            Eco.vault.depositPlayer((OfflinePlayer)p, finalBalance.doubleValue());
          } 
        } 
      } 
    } 
    this.eco.getEcoMysqlHandler().setSyncStatus(p, "false");
    this.playersInSync.add(p);
    this.backupMoney.remove(p);
    if (cancelTask) {
      int taskID = ((Integer)this.runningTasks.get(p)).intValue();
      this.runningTasks.remove(p);
      Bukkit.getScheduler().cancelTask(taskID);
    } 
  }
  
  public void onDataSaveFunction(Player p, Boolean datacleanup, String syncStatus, Boolean isDisabling) {
    boolean isPlayerInSync = this.playersInSync.contains(p);
    if (!isDisabling.booleanValue()) {
      if (datacleanup.booleanValue())
        dataCleanup(p, isDisabling); 
      if (isPlayerInSync)
        this.eco.getEcoMysqlHandler().setData(p, Double.valueOf(Eco.vault.getBalance((OfflinePlayer)p)), syncStatus); 
    } else if (isPlayerInSync && 
      this.balanceMap.containsKey(p)) {
      this.eco.getEcoMysqlHandler().setData(p, this.balanceMap.get(p), syncStatus);
    } 
  }
  
  public void onJoinFunction(final Player p) {
    if (this.eco.getEcoMysqlHandler().hasAccount(p)) {
      final double balance = Eco.vault.getBalance((OfflinePlayer)p);
      this.backupMoney.put(p, Double.valueOf(balance));
      Bukkit.getScheduler().runTask((Plugin)this.eco, new Runnable() {
            public void run() {
              if (balance != 0.0D)
                Eco.vault.withdrawPlayer((OfflinePlayer)p, balance); 
              String[] data = EcoDataHandler.this.eco.getEcoMysqlHandler().getData(p);
              if (data[1].matches("true")) {
                EcoDataHandler.this.setPlayerData(p, data, false);
              } else {
                final long taskStart = System.currentTimeMillis();
                BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)EcoDataHandler.this.eco, new Runnable() {
                      public void run() {
                        if (p.isOnline()) {
                          final String[] data = (EcoDataHandler.null.access$0(EcoDataHandler.null.this)).eco.getEcoMysqlHandler().getData(p);
                          Bukkit.getScheduler().runTask((Plugin)(EcoDataHandler.null.access$0(EcoDataHandler.null.this)).eco, new Runnable() {
                                public void run() {
                                  if (data[1].matches("true")) {
                                    EcoDataHandler.null.access$0(EcoDataHandler.null.null.access$0(EcoDataHandler.null.null.this)).setPlayerData(p, data, true);
                                  } else if (System.currentTimeMillis() - Long.parseLong(data[2]) >= 15000L) {
                                    EcoDataHandler.null.access$0(EcoDataHandler.null.null.access$0(EcoDataHandler.null.null.this)).setPlayerData(p, data, true);
                                  } 
                                }
                              });
                        } 
                        if (System.currentTimeMillis() - taskStart >= 10000L) {
                          int taskID = ((Integer)(EcoDataHandler.null.access$0(EcoDataHandler.null.this)).runningTasks.get(p)).intValue();
                          (EcoDataHandler.null.access$0(EcoDataHandler.null.this)).runningTasks.remove(p);
                          Bukkit.getScheduler().cancelTask(taskID);
                        } 
                      }
                    });
                EcoDataHandler.this.runningTasks.put(p, Integer.valueOf(task.getTaskId()));
              } 
            }
          });
    } else {
      this.playersInSync.add(p);
      onDataSaveFunction(p, Boolean.valueOf(false), "false", Boolean.valueOf(false));
    } 
  }
}
