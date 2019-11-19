package com.YanniSperon.MC.Helper;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class LootboxOutput {
    private ArrayList<ItemStack> items;
    private ArrayList<String> commands;

    public LootboxOutput() {
        items = new ArrayList<ItemStack>();
        commands = new ArrayList<String>();
    }

    public LootboxOutput(ArrayList<ItemStack> itemsArrayList, ArrayList<String> commandsArrayList) {
        items = itemsArrayList;
        commands = commandsArrayList;
    }

    public void SetItems(ArrayList<ItemStack> newItems) {
        items = newItems;
    }

    public void SetCommands(ArrayList<String> newCommands) {
        commands = newCommands;
    }

    public ArrayList<ItemStack> GetItems() {
        return items;
    }

    public ArrayList<String> GetCommands() {
        return commands;
    }
}