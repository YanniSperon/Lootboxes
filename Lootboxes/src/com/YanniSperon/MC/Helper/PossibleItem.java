package com.YanniSperon.MC.Helper;

import org.bukkit.inventory.ItemStack;

public class PossibleItem {
    public ItemStack item;
    public String command;
    public double chance;
    public int minAmount;
    public int maxAmount;

    public PossibleItem(PossibleItem itemToCopy) {
        if (itemToCopy.item == null) {
            item = null;
        } else {
            item = new ItemStack(itemToCopy.item);
        }
        if (itemToCopy.command == null) {
            command = null;
        } else {
            command = new String(itemToCopy.command);
        }
        chance = new Double(itemToCopy.chance);
        minAmount = new Integer(itemToCopy.minAmount);
        maxAmount = new Integer(itemToCopy.maxAmount);
    }

    public PossibleItem(ItemStack itemStack, double chanceAtObtaining, int minAmt, int maxAmt) {
        item = itemStack;
        command = null;
        chance = chanceAtObtaining;
        minAmount = minAmt;
        maxAmount = maxAmt;
    }

    public PossibleItem(String commandToExecute, double chanceAtObtaining, int minAmt, int maxAmt) {
        item = null;
        command = commandToExecute;
        chance = chanceAtObtaining;
        minAmount = minAmt;
        maxAmount = maxAmt;
    }
}