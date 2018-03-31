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

import com.connorlinfoot.titleapi.TitleAPI;

import net.pentazy.playerstore.Main;
import net.pentazy.playerstore.MySQL;
import net.pentazy.utils.PlayerStoreUtils;

public class SetFirstNameEvent implements Listener {
	
	@EventHandler 
	
	public void setName(AsyncPlayerChatEvent e){
		
		PlayerStoreUtils playerStore = new PlayerStoreUtils();

		Player p = e.getPlayer();
		
		File f = new File(Main.getPlugin().getDataFolder(), "player-shop-set.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		FileConfiguration config = Main.getPlugin().getConfig();
		
		if(playerStore.containsfistSetName(p)){
			
			try {
				ResultSet rs = MySQL.prepareStatement("SELECT * FROM `StoreLocation` WHERE Player = '" + p.getName() + "'").executeQuery();
				rs.next();

				MySQL.execute("UPDATE `StoreLocation` SET `Store_Name` = '"
						+ e.getMessage().replace("&", "§") + "' WHERE Player = '" + p.getName() +"' ");

				
				playerStore.removefistSetName(p);
				
				p.sendMessage(config.getString("MENSAGENS.STORE-SAVED").replace("&", "§"));
				
				e.setCancelled(true);
												
				
				
				for(Player pn : Bukkit.getOnlinePlayers()){
					
					TitleAPI.sendTitle(pn, 35, 30, 30, "§a[STORE]", e.getMessage().replace("&", "§") + " §aWas raised");
					
					
				}
				
			}catch (SQLException el) {
				p.sendMessage("ERROR." + el);
			}
		
	
	
			
		
		
		
		
	}
	
	
	
	
	}

}
