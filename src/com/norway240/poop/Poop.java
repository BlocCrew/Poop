package com.norway240.poop; 

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Poop extends JavaPlugin implements Listener {
	
	static final Logger log = Bukkit.getLogger();
	private SQLStuffs sqlite;
	private WasteProducts waste = new WasteProducts();
	  
	public void onDisable() {
		log.info("Poop has been disabled! (a plugin by norway240)");
	}
	
	public void onEnable() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Poop");
		String dbpath = plugin.getDataFolder().getAbsolutePath();
		File file = new File(dbpath);
		String dbfn = "poopdb.db";
		
		if (!file.exists()) {
			if(file.mkdir()) {
				System.out.println("Poop Directory is created!");
			}else{
				System.out.println("Failed to create Poop directory!");
			}
		}
		
		String db = dbpath + "/" + dbfn;
		sqlite = new SQLStuffs(db);
		sqlite.execute("CREATE TABLE IF NOT EXISTS poopdata (name TEXT, eaten INTEGER);");
		
		log.info("Poop has been enabled! (a plugin by norway240)");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		
		sqlite.execute("UPDATE poopdata SET eaten = eaten + 1 WHERE name = '"+name+"';");
		int e = sqlite.getEaten(name);
		
		if(e>=10){
			Location loc = player.getLocation();
			World world = loc.getWorld();
			
			for(int i=0; i<e*2; i++){
				world.dropItemNaturally(loc, waste.Poop());
			}
			//world.createExplosion(loc, 0);
			sqlite.execute("UPDATE poopdata SET eaten = 0 WHERE name = '"+name+"';");
			player.sendMessage("You have now relieved yourself.");
		}else{
			if(e>=7){
				player.sendMessage("You've eaten a lot, you might want to go to the bathroom soon.");
			}else{
				player.sendMessage("You've eaten "+e+" things since your last poop");
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		String name = event.getPlayer().getName();
		sqlite.execute("INSERT INTO poopdata(name, eaten) VALUES('"+name+"', '0');");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("poop")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				String name = player.getName();
				Location loc = player.getLocation();
				World world = loc.getWorld();
				int e = sqlite.getEaten(name);
				
				if(e>=1){
					for(int i=0; i<e*2; i++){
						world.dropItem(loc, waste.Poop());
					}
					sqlite.execute("UPDATE poopdata SET eaten = 0 WHERE name = '"+name+"';");
					sender.sendMessage("You have now relieved yourself.");
				}else{
					sender.sendMessage("You can't poop right now");
				}
			}else{
				sender.sendMessage("You can't poop right now");
			}
			return true;
		}
		return false;
	}
	
}
