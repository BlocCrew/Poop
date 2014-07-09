package com.norway240.poop; 

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poop extends JavaPlugin implements Listener {
	public void onEnable() {
		getLogger().info("Poop has been enabled! (a plugin by norway240)");
		getServer().getPluginManager().registerEvents(this, this);
	}
  
	public void onDisable() {
		getLogger().info("Poop has been disabled! (a plugin by norway240)");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEat(PlayerItemConsumeEvent event){
		ItemStack isPoop = new ItemStack(Material.INK_SACK,1);
		Dye dyePoop = new Dye();
		dyePoop.setColor(DyeColor.BROWN);
		isPoop.setDurability(dyePoop.getData());		
		ItemMeta imPoop = isPoop.getItemMeta();
		imPoop.setDisplayName("Poop");
		ArrayList<String> lorePoop = new ArrayList<String>();
		lorePoop.add("Dispose of this");
		lorePoop.add("which you will");
		imPoop.setLore(lorePoop);
		isPoop.setItemMeta(imPoop);
		
		ItemStack isPee = new ItemStack(Material.INK_SACK,1);
		Dye dyePee = new Dye();
		dyePee.setColor(DyeColor.YELLOW);
		isPee.setDurability(dyePee.getData());
		ItemMeta imPee = isPoop.getItemMeta();
		imPee.setDisplayName("Pee");
		ArrayList<String> lorePee = new ArrayList<String>();
		lorePee.add("Ewwwww...");
		lorePee.add("That's nasty!");
		imPee.setLore(lorePee);
		isPee.setItemMeta(imPee);
		
		ItemStack isDiarrhea = new ItemStack(Material.INK_SACK,1);
		Dye dyeDiarrhea = new Dye();
		dyeDiarrhea.setColor(DyeColor.GREEN);
		isDiarrhea.setDurability(dyeDiarrhea.getData());
		ItemMeta imDiarrhea = isDiarrhea.getItemMeta();
		imDiarrhea.setDisplayName("Diarrhea");
		ArrayList<String> loreDiarrhea = new ArrayList<String>();
		loreDiarrhea.add("Get to the");
		loreDiarrhea.add("BATHROOM!");
		imDiarrhea.setLore(loreDiarrhea);
		isDiarrhea.setItemMeta(imDiarrhea);
		
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		int itemID = item.getTypeId();
		
		if ((itemID == 373)||(itemID == 335)){
			player.getInventory().addItem(new ItemStack(isPee));
			player.sendMessage("You will now need to pee");
		}else if(itemID == 367){
			PotionEffect pe = new PotionEffect(PotionEffectType.CONFUSION, 600, 1);
			player.addPotionEffect(pe);
			player.getInventory().addItem(new ItemStack(isDiarrhea));
			player.sendMessage("I think you need to go to the bathroom");
		}else{
			player.getInventory().addItem(new ItemStack(isPoop));
			player.sendMessage("You will now need to poop");
		}
		
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		World world = loc.getWorld();
		Item item = event.getItemDrop();
		ItemStack is = item.getItemStack();
		String name = is.getItemMeta().getDisplayName().toString();
		if (name.equalsIgnoreCase("Diarrhea")){
			world.createExplosion(loc, 0);
			player.sendMessage("You might want to talk to a doctor...");
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("poop")){
			Plugin poopplugin = Bukkit.getServer().getPluginManager().getPlugin("Poop");
			String v = poopplugin.getDescription().getVersion();
			
			if(args.length == 0){
				String w = poopplugin.getDescription().getWebsite();
				List<String> a = poopplugin.getDescription().getAuthors();
				String d = poopplugin.getDescription().getDescription();
				
				sender.sendMessage("Poop "+ChatColor.BLUE+"v"+v);
				sender.sendMessage(d);
				sender.sendMessage("Website: "+ChatColor.BLUE+w);
				sender.sendMessage("Author: "+ChatColor.BLUE+a);
			}else if(args.length == 1){
				if(args[0].equalsIgnoreCase("version")){
					sender.sendMessage("Poop "+ChatColor.BLUE+"v"+v);
				}else if(args[0].equalsIgnoreCase("update")){
					sender.sendMessage("Poop "+ChatColor.BLUE+"v"+v);
					sender.sendMessage("Unable to check for update at this time...");
				}
			}
			return true;
		}
		return false;
	}
	
}
