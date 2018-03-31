package net.pentazy.handlers.chat;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.pentazy.playerstore.Main;
import net.pentazy.playerstore.MySQL;
import net.pentazy.utils.PlayerStoreUtils;

public class SetWelcomeEvent implements Listener {

	@EventHandler

	public void setWelcome(AsyncPlayerChatEvent e) {

		File f = new File(Main.getPlugin().getDataFolder(), "player-shop-set.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

		Player p = e.getPlayer();
		
		FileConfiguration config = Main.getPlugin().getConfig();

		PlayerStoreUtils playerStore = new PlayerStoreUtils();

		if(playerStore.containsPlayerToSetWelcome(p)) {

			try {
				ResultSet rs = MySQL.prepareStatement("SELECT * FROM `StoreLocation` WHERE Player = '" + p.getName() + "'").executeQuery();
				rs.next();

				MySQL.execute("UPDATE `StoreLocation` SET `Welcome` = '"
						+ e.getMessage().replace("&", "§") + "' WHERE Player = '" + p.getName() +"' ");

				p.sendMessage(config.getString("MENSAGENS.WELCOME-SUCESS-UPDATE").replace("&", "§"));

				playerStore.removePlayerToSetWelcome(p);
				e.setCancelled(true);
				
			} catch (SQLException el) {

				p.sendMessage("Error.");
				e.setCancelled(true);

			}	
			

	
			return;

		}

	}

}
