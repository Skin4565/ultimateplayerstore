package net.pentazy.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class PlayerStoreUtils {
	
	public static ArrayList<Player> delete = new ArrayList<>();
	public static ArrayList<Player> create = new ArrayList<>();
	public static ArrayList<Player> update = new ArrayList<>();
	public static ArrayList<Player> name  = new ArrayList<>();
	public static ArrayList<Player> update_name  = new ArrayList<>();
	public static ArrayList<Player> broadcast  = new ArrayList<>();
	public static ArrayList<Player> welcome  = new ArrayList<>();

	
	
	public void addPlayerToUpdateStoreName(Player p){
		
		update_name.add(p);
		
		
	}	
	
	public void removePlayerToUpdateStoreName(Player p){
		
		update_name.remove(p);
		
		
	}
	
	public boolean containsPlayerToUpdateStoreName(Player p){
		
		if(update_name.contains(p)){
			return true;
		}
		return false;
		
	}	
		
	
	
	public void addPlayerToSetWelcome(Player p){
		
		welcome.add(p);
		
		
	}	
	
	public void removePlayerToSetWelcome(Player p){
		
		welcome.remove(p);
		
		
	}
	
	public boolean containsPlayerToSetWelcome(Player p){
		
		if(welcome.contains(p)){
			return true;
		}
		return false;
		
	}	
	
	
	
	public void addPlayerToSendBroadcast(Player p){
		
		broadcast.add(p);
		
		
	}	
	
	public void removePlayerToSendBroadcast(Player p){
		
		broadcast.remove(p);
		
		
	}
	
	public boolean containsPlayerToSendBroadcast(Player p){
		
		if(broadcast.contains(p)){
			return true;
		}
		return false;
		
	}		
	
	
	public void addPlayerToDeleteStore(Player p){
		
		delete.add(p);
		
		
	}
	
	public void removePlayerToDeleteStore(Player p){
		
		delete.remove(p);
		
	}
	
	public boolean containsPlayerToDeleteStore(Player p){
		
		if(delete.contains(p)){
			return true;
		}
		return false;
		
		
	}
	
	public void addPlayerToCreateStore(Player p){
		
		create.add(p);
		
	}
	
	public void removePlayerToCreateStore(Player p){
		
		create.remove(p);
		
	}
	
	
	public void addPlayerToUpdateStore(Player p){
		
		update.add(p);
		
		
	
	}
	
	public void removePlayerToUpdateStore(Player p){
		
		update.remove(p);
		
		
	
	}
	
	public void fistSetName(Player p){
		
		name.add(p);
		
	}
	
	public void removefistSetName(Player p){
		
		name.remove(p);
	}
	
	public boolean containsfistSetName(Player p){
		
		if(name.contains(p)){
			return true;
		}
		return false;

	}	
	
	
	
	

}
