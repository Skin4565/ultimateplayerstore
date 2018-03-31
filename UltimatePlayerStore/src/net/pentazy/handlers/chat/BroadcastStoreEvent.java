package net.pentazy.handlers.chat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.pentazy.playerstore.Main;
import net.pentazy.utils.PlayerStoreUtils;

public class BroadcastStoreEvent implements Listener {

	@EventHandler

	public void broadcastEvent(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		PlayerStoreUtils playerStore = new PlayerStoreUtils();

		FileConfiguration config = Main.getPlugin().getConfig();

		if (playerStore.containsPlayerToSendBroadcast(p)) {

			Bukkit.broadcastMessage(config.getString("EXTRAS.BROADCAST.FORMAT").replace("{player}", p.getName())
					.replace("{prefix}", config.getString("EXTRAS.BROADCAST.PREFIX")).replace("{msg}", e.getMessage())
					.replace("&", "§"));

			playerStore.removePlayerToSendBroadcast(p);

			e.setCancelled(true);

			return;

		}

	}

}
