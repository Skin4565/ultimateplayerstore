package net.pentazy.commands;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.pentazy.playerstore.ActionBar;
import net.pentazy.playerstore.Main;
import net.pentazy.playerstore.MySQL;
import net.pentazy.utils.PlayerStoreUtils;

public class Store implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player)) {

			Player p = ((Player) sender).getPlayer();

			File f = new File(Main.getPlugin().getDataFolder(), "player-shop-set.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

			PlayerStoreUtils playerStore = new PlayerStoreUtils();

			FileConfiguration config = Main.getPlugin().getConfig();

			if (cmd.getName().equalsIgnoreCase("store")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("This command can only be run by a player.");
					return true;
				}

				if (args.length == 0) {

					p.sendMessage("§c/store [player]");

					return true;

				}

				if (args.length == 1) {

					if (args[0].equalsIgnoreCase("create")) {

						if (p.hasPermission("playerstore.store.create")) {

							if (p.hasPermission("loja.created")) {

								p.sendMessage(config.getString("MENSAGENS.STORE-ALREADY-CREATED").replace("&", "§"));

							} else {

								MySQL.executarUpdate(
										"INSERT INTO `StoreLocation`(`Player`, `X`, `Y`, `Z`, `WORLD_NAME`) VALUES ('"
												+ p.getName() + "', ' " + p.getLocation().getX() + "', '"
												+ p.getLocation().getY() + "', '" + p.getLocation().getZ() + "', '"
												+ p.getWorld().getName() + "')");

								p.sendMessage(config.getString("MENSAGENS.STORE-LOCATION-WARN").replace("&", "§"));
								p.sendMessage(" ");
								p.sendMessage("§e§lX:§b " + p.getLocation().getBlockX());
								p.sendMessage("§e§lY:§b " + p.getLocation().getBlockY());
								p.sendMessage("§e§lZ:§b " + p.getLocation().getBlockZ());

								Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
										"pex user " + p.getName() + " add loja.created");

								p.sendMessage(" ");

								p.sendMessage(config.getString("MENSAGENS.STORE-SET-FIRST-NAME").replace("&", "§"));

								playerStore.fistSetName(p);

							}

							return true;
						} else {
							p.sendMessage(config.getString("MENSAGENS.STORE-NO-PERM").replace("&", "§"));
							return true;
						}
					}

					if (args[0].equalsIgnoreCase("config")) {

						if (p.hasPermission("playerstore.store.create")) {

							if (p.hasPermission("loja.created")) {

								try {
									ResultSet rs = MySQL.prepareStatement(
											"SELECT * FROM `StoreLocation` WHERE Player = '" + p.getName() + "'")
											.executeQuery();
									rs.next();

									Inventory loja = Bukkit.getServer().createInventory(p, 54, "Config");

									// SIGN INFO

									if (rs.getString("Store_Name") == null) {
										ItemStack placa = new ItemStack(Material.SIGN);
										ItemMeta metaplaca = placa.getItemMeta();
										ArrayList<String> placalore = new ArrayList<String>();

										metaplaca.setLore(placalore);
										metaplaca.setDisplayName("§lUnnamed store!");
										placa.setItemMeta(metaplaca);
										loja.setItem(4, placa);
									} else {
										ItemStack placa = new ItemStack(Material.SIGN);
										ItemMeta metaplaca = placa.getItemMeta();
										ArrayList<String> placalore = new ArrayList<String>();

										placalore.add(" ");

										metaplaca.setLore(placalore);
										metaplaca.setDisplayName(rs.getString("Store_Name"));
										placa.setItemMeta(metaplaca);
										loja.setItem(4, placa);
									}

									ItemStack nome = new ItemStack(Material.BOOK_AND_QUILL);
									ItemMeta metanome = nome.getItemMeta();
									ArrayList<String> nomelore = new ArrayList<String>();

									nomelore.add(" ");
									nomelore.add("§fUpdate your store name.");

									metanome.setLore(nomelore);
									metanome.setDisplayName("§aUpdate Name");
									nome.setItemMeta(metanome);
									loja.setItem(20, nome);

									ItemStack broadcast = new ItemStack(Material.DIAMOND);

									ItemMeta metabroadcast = broadcast.getItemMeta();
									ArrayList<String> broadcastlore = new ArrayList<String>();

									broadcastlore.add(" ");
									broadcastlore.add("§fSend an broadcast from your store");
									broadcastlore.add(" ");
									broadcastlore.add("§ffor all online players!");
									broadcastlore.add(" ");
									broadcastlore.add("§ePrice: " + config.getInt("EXTRAS.BROADCAST.PRICE"));

									metabroadcast.setLore(broadcastlore);
									metabroadcast.setDisplayName("§aBroadcast");
									broadcast.setItemMeta(metabroadcast);
									loja.setItem(21, broadcast);

									ItemStack mapa = new ItemStack(Material.EMPTY_MAP);

									ItemMeta metamapa = mapa.getItemMeta();
									ArrayList<String> mapalore = new ArrayList<String>();

									mapalore.add(" ");
									mapalore.add("§fAdd a message");
									mapalore.add("§fWelcome when someone");
									mapalore.add("§fin your store!");

									metamapa.setLore(mapalore);
									metamapa.setDisplayName("§aWelcome");
									mapa.setItemMeta(metamapa);
									loja.setItem(22, mapa);

									p.openInventory(loja);

								} catch (SQLException e) {
									p.sendMessage("ERROR.");
								}

							} else {
								p.sendMessage(config.getString("MENSAGENS.NO-STORE").replace("&", "§"));
							}

						} else {
							p.sendMessage("§cYou dont have permission.");
						}

						return true;
					}

					if (args[0].equalsIgnoreCase("uploc")) {

						if (p.hasPermission("playerstore.store.create")) {

							if (p.hasPermission("loja.created")) {

								try {
									ResultSet rs = MySQL.prepareStatement(
											"SELECT * FROM `StoreLocation` WHERE Player = '" + p.getName() + "'")
											.executeQuery();
									rs.next();

									MySQL.executarUpdate("UPDATE `StoreLocation` SET X = '"
											+ p.getLocation().getBlockX() + "', Y = '" + p.getLocation().getBlockY()
											+ "', Z = '" + p.getLocation().getBlockZ() + "', WORLD_NAME = '"
											+ p.getWorld().getName() + "' ");

									p.sendMessage(
											config.getString("MENSAGENS.STORE-UPDATE-LOCATION").replace("&", "§"));
									p.sendMessage("§e§lX:§b " + p.getLocation().getBlockX());
									p.sendMessage("§e§lY:§b " + p.getLocation().getBlockY());
									p.sendMessage("§e§lZ:§b " + p.getLocation().getBlockZ());

								} catch (SQLException e1) {
									Bukkit.getConsoleSender().sendMessage("§cErro ao atualizar DataBase! " + e1);
								}

							} else {

								p.sendMessage(config.getString("MENSAGENS.NO-STORE").replace("&", "§"));

							}

						} else {
							p.sendMessage("§cYou dont have permission.");

						}
						return true;

					}

					if (args[0].equalsIgnoreCase("avalite")) {

						if (args.length < 2) {

							p.sendMessage("§cUse: /store avalite [player]");

							return true;
						} else {

							try {
								ResultSet rs = MySQL
										.prepareStatement("SELECT * FROM `StoreLocation` ORDER BY Player ASC LIMIT 10")
										.executeQuery();

								Inventory loja_top = Bukkit.getServer().createInventory(p, 54, "Store Top");
								int i = -1;
								while (rs.next()) {
									i++;
									ItemStack is = new ItemStack(Material.IRON_SWORD, 1);
									ItemMeta im = is.getItemMeta();
									im.setDisplayName(rs.getString("Player"));
									is.setItemMeta(im);
									loja_top.setItem(i, is);
								}

								p.openInventory(loja_top);

							} catch (SQLException e) {
								e.printStackTrace();
							}

						}

						return true;
					}

					if (args[0].equalsIgnoreCase("delete")) {

						if (p.hasPermission("playerstore.store.create")) {

							if (p.hasPermission("loja.created")) {

								playerStore.addPlayerToDeleteStore(p);

								p.sendMessage("§a§lEnter §a'Confirm'§a§l to delete your store!");

								new BukkitRunnable() {

									int tempo = 30;

									public void run() {
										--tempo;
										if (tempo > 0) {

											if (playerStore.containsPlayerToDeleteStore(p)) {
												ActionBar.sendActionBarMessage(p,
														"§aYou have §e" + tempo + "s §ato confirm");
											} else {
												cancel();
											}

										}

										else if (tempo == 0) {
											cancel();

											if (playerStore.containsPlayerToDeleteStore(p)) {
												p.sendMessage("§c§lStore not deleted !!");

												playerStore.removePlayerToDeleteStore(p);
											}
										}
									}
								}.runTaskTimer(Main.getPlugin(), 0L, 20L);

							} else {

								p.sendMessage(config.getString("MENSAGENS.NO-STORE").replace("&", "§"));

							}
						} else {
							p.sendMessage("§cYou dont have permission");

						}

						return true;

					}

				}

				if (args.length > 0) {

					if (args.length < 1) {
						p.sendMessage("§c/store [player]");
					} else {
						if (Bukkit.getPlayerExact(args[0]) != null) {
							Player target = Bukkit.getPlayerExact(args[0]);

							String args1 = args[0];

							if (args1.equalsIgnoreCase(p.getName())) {

								p.sendMessage("§cVocê não pode visitar sua propria loja...");

								return true;
							}

							if (target.hasPermission("loja.created")) {

								try {
									ResultSet rs = MySQL.prepareStatement(
											"SELECT * FROM `StoreLocation` WHERE Player = '" + target.getName() + "'")
											.executeQuery();
									rs.next();

									double x = rs.getInt("X");
									double y = rs.getInt("Y");
									double z = rs.getInt("Z");

									p.teleport(new Location(Bukkit.getWorld(rs.getString("WORLD_NAME")), x, y, z));

									if (rs.getString("Welcome") == null) {

										p.sendMessage("§aYou are entering the store of " + rs.getString("Player"));

									} else {
										p.sendMessage(rs.getString("Welcome"));

									}

									target.sendMessage(config.getString("MENSAGENS.PLAYER-JOIN-STORE")
											.replace("{player}", p.getName()).replaceAll("&", "§"));

								} catch (SQLException e1) {
									p.sendMessage(config.getString("MENSAGENS.PLAYER-WITHOUT-SHOP").replace("&", "§"));
								}
							} else {
								p.sendMessage(config.getString("MENSAGENS.PLAYER-WITHOUT-SHOP").replace("&", "§"));
							}

						} else {
							p.sendMessage(config.getString("MENSAGENS.PLAYER-OFFLINE").replace("&", "§"));

						}

					}

				}

			}
		}
		return false;
	}

}
