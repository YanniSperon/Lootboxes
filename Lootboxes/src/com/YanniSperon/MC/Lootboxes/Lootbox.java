package com.YanniSperon.MC.Lootboxes;

import com.YanniSperon.MC.Global;
import com.YanniSperon.MC.Helper.LootboxOutput;
import com.YanniSperon.MC.Helper.PossibleItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Lootbox {

    private Particle particleEffect;
    private Effect audioEffect;
    private String openingMessage;
    private ArrayList<PossibleItem> items;
    private int min;
    private int max;
    private ItemStack physicalItem;
    private boolean repeats;


    ////////////////////////////////////////////////////////////////////////////////////////


    public Lootbox() {
        items = new ArrayList<PossibleItem>();
        particleEffect = Particle.CLOUD;
        audioEffect = Effect.DRAGON_BREATH;
        min = 0;
        max = 0;
        openingMessage = "%name% just opened a %lootbox%, get yours at website.com";
        physicalItem = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = physicalItem.getItemMeta();
        meta.setDisplayName(Global.LootboxIndicator + "Lootbox");
        physicalItem.setItemMeta(meta);
        repeats = false;
    }

    public Lootbox(ItemStack inGameItem, ArrayList<PossibleItem> listOfPossibleItems, boolean shouldRepeatItems, int minAmtToGenerate, int maxAmtToGenerate, Particle openParticleEffect, Effect openNoise, String openMessage) {
        items = listOfPossibleItems;
        particleEffect = openParticleEffect;
        audioEffect = openNoise;
        openingMessage = openMessage;
        min = minAmtToGenerate;
        max = maxAmtToGenerate;
        physicalItem = inGameItem;
        ItemMeta meta = physicalItem.getItemMeta();
        meta.setDisplayName(Global.LootboxIndicator + meta.getDisplayName());
        physicalItem.setItemMeta(meta);
        repeats = shouldRepeatItems;
    }


    ////////////////////////////////////////////////////////////////////////////////////////


    public void Open(Player player) {
        SpawnParticles(player, particleEffect);
        PlayNoise(player, audioEffect);
        BroadcastOpenMessage(player, openingMessage);
        GenerateAndAddItems(player);
    }

    public void SpawnParticles(Player player, Particle type) {
        Location loc = player.getLocation();
        double theta = 0;
        for (int i = 0; i < 32; i++) {
            theta += Math.PI / 16;
            Location firstLocation = loc.clone().add(Math.cos(theta), Math.sin(theta) + 1, Math.sin(theta));
            Location secondLocation = loc.clone().add(Math.cos(theta + Math.PI), Math.sin(theta) + 1, Math.sin(theta + Math.PI));
            player.spawnParticle(type, firstLocation, 0, 0, 0, 0, 0);
            player.spawnParticle(type, secondLocation, 0, 0, 0, 0, 0);
        }
    }

    public void PlayNoise(Player player, Effect noise) {
        player.getWorld().playEffect(player.getLocation(), noise, 1);
    }

    public void BroadcastOpenMessage(Player player, String msg) {
        String broadcastedString = msg.replaceAll("%name%", player.getDisplayName()).replaceAll("%lootbox%", physicalItem.getItemMeta().getDisplayName());
        Bukkit.broadcastMessage(broadcastedString);
    }

    public void GenerateAndAddItems(Player player) {
        //LootboxOutput lbOutput = GenerateItems(items, min, max, repeats);
        LootboxOutput lbOutput = GenerateItems(player, items, min, max, repeats);
        ArrayList<ItemStack> generatedItems = lbOutput.GetItems();
        ArrayList<String> generatedCommands = lbOutput.GetCommands();
        boolean didDropOnFloor = false;
        for (int i = 0; i < generatedItems.size(); i++) {
            if (!Global.AddItem(player, generatedItems.get(i))) {
                didDropOnFloor = true;
            }
        }
        if (didDropOnFloor) {
            player.sendMessage("Inventory full, dropping item(s) on the ground");
        }
        for (int i = 0; i < generatedCommands.size(); i++) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), generatedCommands.get(i).replaceAll("%name%", player.getName()));
        }
    }

    public LootboxOutput GenerateItems(Player console, ArrayList<PossibleItem> listOfPossibleItems, int minAmountOfItemsToGenerate, int maxAmountOfItemsToGenerate, boolean shouldRepeatValues) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        ArrayList<String> commands = new ArrayList<String>();
        ArrayList<PossibleItem> copiedModifiableArray = Global.MakeDeepCopyOfArrayListOfPossibleItem(listOfPossibleItems);

        int amountOfItemsToGenerate = Global.GenerateRandomIntInclusive(minAmountOfItemsToGenerate, maxAmountOfItemsToGenerate);
        for (int i = 0; i < amountOfItemsToGenerate; i++) {

            double maxChance = 0.0;
            for (int k = 0; k < copiedModifiableArray.size(); k++) {
                maxChance += copiedModifiableArray.get(k).chance;
            }

            double randVal = Global.GenerateRandomDouble(0.0, maxChance);

            double currentVal = 0.0;

            for (int k = 0; k < copiedModifiableArray.size(); k++) {
                double nextVal = currentVal + copiedModifiableArray.get(k).chance;
                if (randVal >= currentVal && randVal < nextVal) {
                    if (copiedModifiableArray.get(k).item != null) {
                        ItemStack item = copiedModifiableArray.get(k).item;
                        int amountOfItem = Global.GenerateRandomIntInclusive(copiedModifiableArray.get(k).minAmount, copiedModifiableArray.get(k).maxAmount);
                        item.setAmount(amountOfItem);
                        items.add(item);
                    } else {
                        int amountOfItem = Global.GenerateRandomIntInclusive(copiedModifiableArray.get(k).minAmount, copiedModifiableArray.get(k).maxAmount);
                        String str = copiedModifiableArray.get(k).command;
                        if (str.contains("%amount%")) {
                            str.replaceAll("%amount%", "" + amountOfItem);
                            commands.add(str);
                        } else {
                            for (int l = 0; l < amountOfItem; l++) {
                                commands.add(str);
                            }
                        }
                    }
                    if (!shouldRepeatValues) {
                        copiedModifiableArray.remove(k);
                    }
                    break;
                }

                currentVal = nextVal;
            }
        }

        return new LootboxOutput(items, commands);
    }


    ////////////////////////////////////////////////////////////////////////////////////////


    public Particle GetParticle() {
        return particleEffect;
    }

    public Effect GetSoundEffect() {
        return audioEffect;
    }

    public String GetName() {
        return physicalItem.getItemMeta().getDisplayName();
    }

    public List<String> GetLore() {
        return physicalItem.getItemMeta().getLore();
    }

    public ItemMeta GetItemMeta() {
        return physicalItem.getItemMeta();
    }

    public ItemStack GetItem() {
        return new ItemStack(physicalItem);
    }

    public String GetOpeningMessage() {
        return openingMessage;
    }

    public int GetMin() {
        return min;
    }

    public int GetMax() {
        return max;
    }

    public boolean GetRepeats() {
        return repeats;
    }
}