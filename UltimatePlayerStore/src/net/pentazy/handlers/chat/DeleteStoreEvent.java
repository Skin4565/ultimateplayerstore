package net.pentazy.handlers.chat;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.pentazy.playerstore.Main;
import net.pentazy.playerstore.MySQL;
import net.pentazy.utils.PlayerStoreUtils;

public class DeleteStoreEvent implements Listener {
	
	
	@EventHandler
	
	public void deleteConfirm(AsyncPlayerChatEvent e){
		
		PlayerStoreUtils playerStore = new PlayerStoreUtils();

		Player p = e.getPlayer();
		FileConfiguration config = Main.getPlugin().getConfig();
		
		if(playerStore.containsPlayerToDeleteStore(p)){
			if(e.getMessage().equalsIgnoreCase("confirm")){

				
				try {
					ResultSet rs = MySQL
							.prepareStatement(
									"SELECT * FROM `StoreLocation` WHERE Player  = '" + p.getName() + "'")
							.executeQuery();
					rs.next();

					p.sendMessage(config.getString("MENSAGENS.STORE-DELETED").replace("&", "§"));
					
					MySQL.execute("DELETE FROM `StoreLocation` WHERE Player = '" + p.getName() + "'");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " remove loja.delete");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " remove loja.created");

					e.setCancelled(true);

					playerStore.removePlayerToDeleteStore(p);

					
				} catch (SQLException e1) {
					e.setCancelled(true);
					p.sendMessage("§cError deleting the store");
					Bukkit.getConsoleSender().sendMessage("§cErro ao atualizar DataBase! " + e1);
				}
				
					

					e.setCancelled(true);
						
				
			}else{
				playerStore.removePlayerToDeleteStore(p);
				p.sendMessage("§cError deleting the store!");
				e.setCancelled(true);

			}
		}
		
		
	}
		
		
		
		
		
		
	}
	
	
	


