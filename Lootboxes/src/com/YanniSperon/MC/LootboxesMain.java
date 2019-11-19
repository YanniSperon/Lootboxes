package com.YanniSperon.MC;

import com.YanniSperon.MC.Lootboxes.Lootbox;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class LootboxesMain extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("---------------------------------------------");
        getLogger().info("|  Lootbox Plugin by Yanni Speron enabled!  |");
        getLogger().info("---------------------------------------------");
        getServer().getPluginManager().registerEvents(new LootboxEventListener(), this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Global.LoadConfig(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("----------------------------------------------");
        getLogger().info("|  Lootbox Plugin by Yanni Speron disabled!  |");
        getLogger().info("----------------------------------------------");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("lootbox")) {
                if (args.length > 0) {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                            Global.SendInfo(sender, label);
                        } else if (args[0].equalsIgnoreCase("types") || args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("box") || args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("b")) {
                            Global.SendLootboxTypes(sender);
                        } else if (args[0].equalsIgnoreCase("ci") || args[0].equalsIgnoreCase("customitem") || args[0].equalsIgnoreCase("customitems") || args[0].equalsIgnoreCase("giveci") || args[0].equalsIgnoreCase("cigive")) {
                            Global.SendCustomItemTypes(sender);
                        } else {
                            Global.SendUnknown(sender, label);
                        }
                    } else if (args.length == 3) {
                        if (args[0].equalsIgnoreCase("box") || args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("b")) {
                            if (Global.Lootboxes.size() > 0) {
                                Lootbox box = null;
                                for (String key : Global.Lootboxes.keySet()) {
                                    if (key.equalsIgnoreCase(args[1])) {
                                        box = Global.Lootboxes.get(key);
                                    }
                                }
                                if (box == null) {
                                    Global.SendValidLootboxTypes(sender);
                                } else {
                                    try {
                                        int amount = Integer.parseInt(args[2]);
                                        Global.GiveLootbox(box, player, amount);
                                    } catch (Exception e) {
                                        Global.SendInvalidNumber(sender, args[2]);
                                    }
                                }
                            } else {
                                Global.SendNoValidLootboxTypes(sender);
                            }
                        } else if (args[0].equalsIgnoreCase("ci") || args[0].equalsIgnoreCase("customitem") || args[0].equalsIgnoreCase("customitems") || args[0].equalsIgnoreCase("giveci") || args[0].equalsIgnoreCase("cigive")) {
                            if (Global.CustomItems.size() > 0) {
                                ItemStack item = null;
                                for (String key : Global.CustomItems.keySet()) {
                                    if (key.equalsIgnoreCase(args[1])) {
                                        item = Global.CustomItems.get(key);
                                    }
                                }
                                if (item == null) {
                                    Global.SendValidCustomItemTypes(sender);
                                } else {
                                    try {
                                        int amount = Integer.parseInt(args[2]);
                                        ItemStack addableItem = new ItemStack(item);
                                        addableItem.setAmount(amount);
                                        Global.AddItem(player, addableItem);
                                    } catch (Exception e) {
                                        Global.SendInvalidNumber(sender, args[2]);
                                    }
                                }
                            } else {
                                Global.SendNoValidCustomItems(sender);
                            }
                        } else {
                            Global.SendUnknown(sender, label);
                        }
                    } else if (args.length == 4) {
                        if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("b") || args[0].equalsIgnoreCase("box")) {
                            if (Global.Lootboxes.size() > 0) {
                                Player p = Bukkit.getPlayer(args[1]);
                                if (p != null) {
                                    Lootbox box = null;
                                    for (String key : Global.Lootboxes.keySet()) {
                                        if (key.equalsIgnoreCase(args[2])) {
                                            box = Global.Lootboxes.get(key);
                                        }
                                    }
                                    if (box == null) {
                                        Global.SendValidLootboxTypes(sender);
                                    } else {
                                        try {
                                            int amount = Integer.parseInt(args[3]);
                                            Global.GiveLootbox(box, p, amount);
                                        } catch (Exception e) {
                                            Global.SendInvalidNumber(sender, args[3]);
                                        }
                                    }
                                } else {
                                    Global.SendInvalidPlayer(sender, args[1]);
                                }
                            } else {
                                Global.SendNoValidLootboxTypes(sender);
                            }
                        } else if (args[0].equalsIgnoreCase("ci") || args[0].equalsIgnoreCase("customitem") || args[0].equalsIgnoreCase("customitems") || args[0].equalsIgnoreCase("giveci") || args[0].equalsIgnoreCase("cigive")) {
                            if (Global.CustomItems.size() > 0) {
                                Player p = Bukkit.getPlayer(args[1]);
                                if (p != null) {
                                    ItemStack item = null;
                                    for (String key : Global.CustomItems.keySet()) {
                                        if (key.equalsIgnoreCase(args[2])) {
                                            item = Global.CustomItems.get(key);
                                        }
                                    }

                                    if (item == null) {
                                        Global.SendValidCustomItemTypes(sender);
                                    } else {
                                        try {
                                            int amount = Integer.parseInt(args[3]);
                                            ItemStack addableItem = new ItemStack(item);
                                            addableItem.setAmount(amount);
                                            Global.AddItem(player, addableItem);
                                        } catch (Exception e) {
                                            Global.SendInvalidNumber(sender, args[3]);
                                        }
                                    }
                                } else {
                                    Global.SendInvalidPlayer(sender, args[1]);
                                }
                            } else {
                                Global.SendNoValidCustomItems(sender);
                            }
                        } else {
                            Global.SendUnknown(sender, label);
                        }
                    } else {
                        Global.SendUnknown(sender, label);
                    }
                } else {
                    Global.SendInfo(sender, label);
                }
            }
        } else {
            sender.sendMessage("Currently, lootbox commands may currently only be executed by a player, sorry!");
        }

        return false;
    }
}