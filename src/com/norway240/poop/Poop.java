package com.norway240.poop; 

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
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
		ItemStack is = new ItemStack(Material.WOOL,1);
		Wool wool = new Wool(DyeColor.BROWN);
		is.setDurability(wool.getData());		
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Poop");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Dispose of this");
		lore.add("which you will");
		im.setLore(lore);
		is.setItemMeta(im);
		event.getPlayer().getInventory().addItem(new ItemStack(is));
		event.getPlayer().sendMessage("You will now need to poop");
	}
	
}
