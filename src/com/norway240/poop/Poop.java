package com.norway240.poop; 

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;

public class Poop extends JavaPlugin implements Listener {
	public void onEnable() {
		getLogger().info("Poop has been enabled!");
		getServer().getPluginManager().registerEvents(this, this);
	}
  
	public void onDisable() {
		getLogger().info("Poop has been disabled!");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEat(PlayerItemConsumeEvent event){
		ItemStack isPoop = new ItemStack(Material.WOOL,1);
		Wool woolPoop = new Wool(DyeColor.BROWN);
		isPoop.setDurability(woolPoop.getData());		
		ItemMeta imPoop = isPoop.getItemMeta();
		imPoop.setDisplayName("Poop");
		ArrayList<String> lorePoop = new ArrayList<String>();
		lorePoop.add("Dispose of this");
		lorePoop.add("which you will");
		imPoop.setLore(lorePoop);
		isPoop.setItemMeta(imPoop);
		
		ItemStack isPee = new ItemStack(Material.WOOL,1);
		Wool woolPee = new Wool(DyeColor.YELLOW);
		isPee.setDurability(woolPee.getData());
		ItemMeta imPee = isPoop.getItemMeta();
		imPee.setDisplayName("Pee");
		ArrayList<String> lorePee = new ArrayList<String>();
		lorePee.add("Ewwwww...");
		lorePee.add("That's nasty!");
		imPee.setLore(lorePee);
		isPee.setItemMeta(imPee);
		
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		int itemID = item.getTypeId();
		
		if (itemID == 373){
			player.getInventory().addItem(new ItemStack(isPee));
			player.sendMessage("You will now need to pee");
			
		}else{
			player.getInventory().addItem(new ItemStack(isPoop));
			player.sendMessage("You will now need to poop");
		}
		
	}
	
}
