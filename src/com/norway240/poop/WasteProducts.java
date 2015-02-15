package com.norway240.poop;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

public class WasteProducts {
	
	private ItemStack isPoop;
	private ItemStack isPee;
	private ItemStack isDiarrhea;
	
	public WasteProducts(){
		isPoop = new ItemStack(Material.INK_SACK,1);
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
		
		isPee = new ItemStack(Material.INK_SACK,1);
		Dye dyePee = new Dye();
		dyePee.setColor(DyeColor.YELLOW);
		isPee.setDurability(dyePee.getData());
		ItemMeta imPee = isPee.getItemMeta();
		imPee.setDisplayName("Pee");
		ArrayList<String> lorePee = new ArrayList<String>();
		lorePee.add("Ewwwww...");
		lorePee.add("That's nasty!");
		imPee.setLore(lorePee);
		isPee.setItemMeta(imPee);
		
		isDiarrhea = new ItemStack(Material.INK_SACK,1);
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
	}
	
	public ItemStack Poop(){
		return isPoop;
	}
	
	public ItemStack Pee(){
		return isPee;
	}
	
	public ItemStack Diarrhea(){
		return isDiarrhea;
	}

}
