package net.pentazy.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.pentazy.playerstore.Main;
import net.pentazy.utils.PlayerStoreUtils;

public class InventoryEvent implements Listener {

	@EventHandler
	private void inventoryClick(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();
		PlayerStoreUtils playerStore = new PlayerStoreUtils();
		
		
		FileConfiguration config = Main.getPlugin().getConfig();

		if (e.getInventory().getTitle().equalsIgnoreCase("Config")) {
			e.setCancelled(true);
			if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) {
				return;
			}

			if (e.getSlot() == 20
					&& (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aUpdate Name"))) {

				p.sendMessage("§a§lEnter the new name of your store:");


				playerStore.addPlayerToUpdateStoreName(p);
				
				p.closeInventory();
				e.setCancelled(true);

			}

			if (e.getSlot() == 21
					&& (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aBroadcast"))) {


				
				if (Main.economy.getBalance(p) < config.getInt("EXTRAS.BROADCAST.PRICE")) {


						
						p.sendMessage(config.getString("MENSAGENS.NO-MONEY").replace("&", "§"));
	
				

				} else {

					p.sendMessage(config.getString("MENSAGENS.BROADCAST-WARN").replace("&", "§"));

					
					playerStore.addPlayerToSendBroadcast(p);
					
					Main.getPlugin().economy.withdrawPlayer(p, config.getInt("EXTRAS.BROADCAST.PRICE"));
					
					

				}

				p.closeInventory();
				e.setCancelled(true);

			}

			if (e.getSlot() == 22
					&& (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aWelcome"))) {

				p.sendMessage(config.getString("MENSAGENS.WELCOME-WARN").replace("&", "§"));

				
				playerStore.addPlayerToSetWelcome(p);

				
				p.closeInventory();
				e.setCancelled(true);

			}

		}
	}

}
