package net.pentazy.playerstore;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class MySQL
{
  public static Connection conexao;
  
  public static synchronized void abrirConexao()
  {
    try
    {
      String HOST = Main.getPlugin().getConfig().getString("MYSQL.HOST");
      int PORT = Main.getPlugin().getConfig().getInt("MYSQL.PORT");
      String USER = Main.getPlugin().getConfig().getString("MYSQL.USER");
      String PASSWORD = Main.getPlugin().getConfig().getString("MYSQL.PASSWORD");
      String tabela = "UltimatePlayerStore";
      conexao = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + tabela, USER, PASSWORD);
    }
    catch (SQLException ex)
    {
      Bukkit.getConsoleSender().sendMessage("§cError opening MySQL connection! " + ex);
    }
  }
  
  public static void executarUpdate(String update)
  {
    try
    {
      conexao.createStatement().executeUpdate(update);
    }
    catch (SQLException ex)
    {
      Bukkit.getConsoleSender().sendMessage("§cError while giving execute update! " + ex);
    }
  }
  
  public static void execute(String update)
  {
    try
    {
      conexao.createStatement().execute(update);
    }
    catch (SQLException ex)
    {
      Bukkit.getConsoleSender().sendMessage("§cErro ao dar execute! " + ex);
    }
  }
  
  public static boolean containsPlayer(Player p)
  {
    try
    {
      return conexao.createStatement().executeQuery("SELECT * FROM `StoreLocation` WHERE Player = '" + p.getName() + "'").next();
    }
    catch (SQLException ex)
    {
      Bukkit.getConsoleSender().sendMessage("§cErro ao conter jogador MySQL! " + ex);
    }
    return false;
  }
  
  public static PreparedStatement prepareStatement(String statement)
  {
    try
    {
      return conexao.prepareStatement(statement);
    }
    catch (SQLException ex)
    {
      Bukkit.getConsoleSender().sendMessage("§cErro ao criar um statement! " + ex);
    }
    return null;
  }
}

