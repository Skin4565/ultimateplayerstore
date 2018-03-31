package net.pentazy.playerstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.pentazy.api.PentazyAuthAPI;
import net.pentazy.commands.Store;
import net.pentazy.handlers.InventoryEvent;
import net.pentazy.handlers.chat.BroadcastStoreEvent;
import net.pentazy.handlers.chat.DeleteStoreEvent;
import net.pentazy.handlers.chat.SetFirstNameEvent;
import net.pentazy.handlers.chat.SetWelcomeEvent;
import net.pentazy.handlers.chat.UpdateNameStoreEvent;

public class Main extends JavaPlugin {

	public static JavaPlugin plugin;

	public void onEnable() {

			
			saveDefaultConfig();

			registerEvents();
			registerCommands();

			MySQL.abrirConexao();
			MySQL.execute(
					"CREATE TABLE IF NOT EXISTS `StoreLocation` (Player varchar(17), X int, Y int, Z int, WORLD_NAME varchar(255), Welcome varchar(255), Store_Name varchar(255))");

			setupEconomy();
	



	}

	public void registerEvents() {
		Bukkit.getServer().getPluginManager().registerEvents(new BroadcastStoreEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DeleteStoreEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SetFirstNameEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SetWelcomeEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new UpdateNameStoreEvent(), this);

		Bukkit.getServer().getPluginManager().registerEvents(new InventoryEvent(), this);

	}

	public void registerCommands() {
		getCommand("store").setExecutor(new Store());

	}

	public static Main getPlugin() {

		return (Main) getPlugin(Main.class);

	}

	public static Economy economy = null;

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = (Economy) rsp.getProvider();
		return economy != null;
	}

}
