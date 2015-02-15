package com.norway240.poop; 

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poop extends JavaPlugin implements Listener {
	
	static final Logger log = Bukkit.getLogger();
	private SQLStuffs sqlite;
	private WasteProducts waste = new WasteProducts();
	  
	public void onDisable() {
		log.info("[Poop] Poop has been disabled! (a plugin by norway240)");
	}
	
	public void onEnable() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Poop");
		String dbpath = plugin.getDataFolder().getAbsolutePath();
		File file = new File(dbpath);
		String dbfn = "poopdb.db";
		
		if (!file.exists()) {
			if(file.mkdir()) {
				log.info("[Poop] Poop Directory is created!");
			}else{
				log.info("[Poop] Failed to create Poop directory!");
			}
		}
		
		String db = dbpath + "/" + dbfn;
		sqlite = new SQLStuffs(db);
		sqlite.execute("CREATE TABLE IF NOT EXISTS poopdata (name TEXT UNIQUE, poop INTEGER, pee INTEGER, diarrhea INTEGER);");
		
		log.info("[Poop] Poop has been enabled! (a plugin by norway240)");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		Material item = event.getItem().getType();
		
		
		String val = "poop";
		if(item == Material.ROTTEN_FLESH){
			val = "diarrhea";
		}else if(item == Material.POTION || item == Material.MILK_BUCKET){
			val = "pee";
		}
		sqlite.execute("UPDATE poopdata SET "+val+" = "+val+" + 1 WHERE name = '"+name+"';");

		int e[] = sqlite.getEaten(name);
		int te = e[0] + e[1] + e[2]; //total things eaten
		
		if(te>=64){
			dumpWaste(name);
			player.chat("/me Pooped their pants!");
			player.chat("Whoopsie dasie!");
		}
		if(te>=56){
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
		}
		if(te>=48){
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
		}
		if(te>=32){
			player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1));
		}
		player.sendMessage("You've eaten "+te+" things since your last poop");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		String name = event.getPlayer().getName();
		sqlite.execute("INSERT INTO poopdata(name, poop, pee, diarrhea) VALUES('"+name+"', '0', '0', '0');");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("poop")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				String name = player.getName();
				int e[] = sqlite.getEaten(name);
				int te = e[0] + e[1] + e[2]; //total things eaten
				
				if(te>=1){
					dumpWaste(name);
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
	
	private void dumpWaste(String name){
		Player player = Bukkit.getPlayer(name);
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		int e[] = sqlite.getEaten(name);

		for(int i=0; i<e[0]*2; i++){
			world.dropItem(loc, waste.Poop());
		}
		for(int i=0; i<e[1]*2; i++){
			world.dropItem(loc, waste.Pee());
		}
		for(int i=0; i<e[2]*2; i++){
			world.dropItem(loc, waste.Diarrhea());
		}
		if(e[2]>0){//if eaten r f > EXPLOSIONS!!!
			world.createExplosion(loc, 0);
		}
		sqlite.execute("UPDATE poopdata SET poop = 0 WHERE name = '"+name+"';");
		sqlite.execute("UPDATE poopdata SET pee = 0 WHERE name = '"+name+"';");
		sqlite.execute("UPDATE poopdata SET diarrhea = 0 WHERE name = '"+name+"';");
	}
	
}
