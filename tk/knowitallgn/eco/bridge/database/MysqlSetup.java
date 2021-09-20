package tk.knowitallgn.eco.bridge.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import tk.knowitallgn.eco.bridge.Eco;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MysqlSetup {
  private Connection conn = null;
  
  private Eco eco;
  
  public MysqlSetup(Eco eco) {
    this.eco = eco;
    connectToDatabase();
    setupDatabase();
    updateTables();
    databaseMaintenanceTask();
  }
  
  public void connectToDatabase() {
    Eco.log.info("Connecting to the database...");
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Properties properties = new Properties();
      properties.setProperty("user", this.eco.getConfigHandler().getString("database.mysql.user"));
      properties.setProperty("password", this.eco.getConfigHandler().getString("database.mysql.password"));
      properties.setProperty("autoReconnect", "true");
      properties.setProperty("verifyServerCertificate", "false");
      properties.setProperty("useSSL", this.eco.getConfigHandler().getString("database.mysql.sslEnabled"));
      properties.setProperty("requireSSL", this.eco.getConfigHandler().getString("database.mysql.sslEnabled"));
      this.conn = DriverManager.getConnection("jdbc:mysql://" + this.eco.getConfigHandler().getString("database.mysql.host") + ":" + this.eco.getConfigHandler().getString("database.mysql.port") + "/" + this.eco.getConfigHandler().getString("database.mysql.databaseName"), properties);
    } catch (ClassNotFoundException e) {
      Eco.log.severe("Could not locate drivers for mysql! Error: " + e.getMessage());
      return;
    } catch (SQLException e) {
      Eco.log.severe("Could not connect to mysql database! Error: " + e.getMessage());
      return;
    } 
    Eco.log.info("Database connection successful!");
  }
  
  public void setupDatabase() {
    if (this.conn == null)
      return; 
    PreparedStatement query = null;
    try {
      String data = "CREATE TABLE IF NOT EXISTS `" + this.eco.getConfigHandler().getString("database.mysql.dataTableName") + "` (id int(10) AUTO_INCREMENT, player_uuid varchar(50) NOT NULL UNIQUE, player_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL, money double(30,2) NOT NULL, sync_complete varchar(5) NOT NULL, last_seen varchar(30) NOT NULL, PRIMARY KEY(id));";
      query = this.conn.prepareStatement(data);
      query.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      Eco.log.severe("Error creating tables! Error: " + e.getMessage());
    } finally {
      try {
        if (query != null)
          query.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public Connection getConnection() {
    checkConnection();
    return this.conn;
  }
  
  public void checkConnection() {
    try {
      if (this.conn == null) {
        Eco.log.warning("Connection failed. Reconnecting...");
        reConnect();
      } 
      if (!this.conn.isValid(3)) {
        Eco.log.warning("Connection is idle or terminated. Reconnecting...");
        reConnect();
      } 
      if (this.conn.isClosed()) {
        Eco.log.warning("Connection is closed. Reconnecting...");
        reConnect();
      } 
    } catch (Exception e) {
      Eco.log.severe("Could not reconnect to Database! Error: " + e.getMessage());
    } 
  }
  
  public boolean reConnect() {
    try {
      long start = 0L;
      long end = 0L;
      start = System.currentTimeMillis();
      Eco.log.info("Attempting to establish a connection to the MySQL server!");
      Class.forName("com.mysql.jdbc.Driver");
      Properties properties = new Properties();
      properties.setProperty("user", this.eco.getConfigHandler().getString("database.mysql.user"));
      properties.setProperty("password", this.eco.getConfigHandler().getString("database.mysql.password"));
      properties.setProperty("autoReconnect", "true");
      properties.setProperty("verifyServerCertificate", "false");
      properties.setProperty("useSSL", this.eco.getConfigHandler().getString("database.mysql.sslEnabled"));
      properties.setProperty("requireSSL", this.eco.getConfigHandler().getString("database.mysql.sslEnabled"));
      this.conn = DriverManager.getConnection("jdbc:mysql://" + this.eco.getConfigHandler().getString("database.mysql.host") + ":" + this.eco.getConfigHandler().getString("database.mysql.port") + "/" + this.eco.getConfigHandler().getString("database.mysql.databaseName"), properties);
      end = System.currentTimeMillis();
      Eco.log.info("Connection to MySQL server established in " + (end - start) + " ms!");
      return true;
    } catch (Exception e) {
      Eco.log.severe("Error re-connecting to the database! Error: " + e.getMessage());
      return false;
    } 
  }
  
  public void closeConnection() {
    try {
      Eco.log.info("Closing database connection...");
      this.conn.close();
      this.conn = null;
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  private void updateTables() {
    if (this.conn != null) {
      DatabaseMetaData md = null;
      ResultSet rs1 = null;
      PreparedStatement query1 = null;
      try {
        md = this.conn.getMetaData();
        rs1 = md.getColumns(null, null, this.eco.getConfigHandler().getString("database.mysql.dataTableName"), "sync_complete");
        if (!rs1.next()) {
          String data = "ALTER TABLE `" + this.eco.getConfigHandler().getString("database.mysql.dataTableName") + "` ADD sync_complete varchar(5) NOT NULL DEFAULT 'true';";
          query1 = this.conn.prepareStatement(data);
          query1.execute();
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (query1 != null)
            query1.close(); 
          if (rs1 != null)
            rs1.close(); 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  private void databaseMaintenanceTask() {
    if (this.eco.getConfigHandler().getBoolean("database.removeOldAccounts.enabled").booleanValue())
      Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.eco, new Runnable() {
            public void run() {
              if (MysqlSetup.this.conn != null) {
                long inactivityDays = Long.parseLong(MysqlSetup.this.eco.getConfigHandler().getString("database.removeOldAccounts.inactivity"));
                long inactivityMils = inactivityDays * 24L * 60L * 60L * 1000L;
                long curentTime = System.currentTimeMillis();
                long inactiveTime = curentTime - inactivityMils;
                Eco.log.info("Database maintenance task started...");
                PreparedStatement preparedStatement = null;
                try {
                  String sql = "DELETE FROM `" + MysqlSetup.this.eco.getConfigHandler().getString("database.mysql.dataTableName") + "` WHERE `last_seen` < ?";
                  preparedStatement = MysqlSetup.this.conn.prepareStatement(sql);
                  preparedStatement.setString(1, String.valueOf(inactiveTime));
                  preparedStatement.execute();
                } catch (Exception e) {
                  e.printStackTrace();
                } finally {
                  try {
                    if (preparedStatement != null)
                      preparedStatement.close(); 
                  } catch (Exception e) {
                    e.printStackTrace();
                  } 
                } 
                Eco.log.info("Database maintenance complete!");
              } 
            }
          }); 
  }
}
