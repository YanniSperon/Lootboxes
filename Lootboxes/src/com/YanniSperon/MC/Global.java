package com.YanniSperon.MC;

import com.YanniSperon.MC.Helper.PossibleItem;
import com.YanniSperon.MC.Lootboxes.Lootbox;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Global {
    public static Map<String, Lootbox> Lootboxes = new HashMap<>();

    public static Map<String, ItemStack> CustomItems = new HashMap<>();

    public static String LootboxIndicator = "&c&b&c&b&c&b&c&b&r&r&r";

    public static void AddItems(Player player, ArrayList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            AddItem(player, items.get(i));
        }
    }

    public static boolean AddItem(Player player, ItemStack item) {
        boolean droppedOnGround = false;
        for (int i = 0; i < item.getAmount(); i += item.getMaxStackSize()) {
            ItemStack newItem = new ItemStack(item);
            if (newItem.getAmount() >= item.getMaxStackSize() + i) {
                newItem.setAmount(item.getMaxStackSize());
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(newItem);
                } else {
                    player.getWorld().dropItem(player.getLocation(), newItem);
                    droppedOnGround = true;
                }
            } else {
                newItem.setAmount(item.getAmount() - i);
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(newItem);
                } else {
                    player.getWorld().dropItem(player.getLocation(), newItem);
                    droppedOnGround = true;
                }
            }
        }
        if (droppedOnGround) {
            return false;
        }
        return true;
    }

    public static void SendInfo(CommandSender sender, String label) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eLootbox plugin");
        sender.sendMessage("§eby Yanni Speron");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eCommands:");
        sender.sendMessage("");
        SendCommands(sender, label);
    }

    public static void SendCommands(CommandSender sender, String label) {
        sender.sendMessage("§6/" + label + " help");
        sender.sendMessage("§6/" + label + " types");
        sender.sendMessage("§6/" + label + " box (type) (amount)");
        sender.sendMessage("§6/" + label + " box (player) (type) (amount)");
        sender.sendMessage("§6/" + label + " customitem (type) (amount)");
        sender.sendMessage("§6/" + label + " customitem (player) (type) (amount)");
    }

    public static void SendUnknown(CommandSender sender, String label) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eUnknown command");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eValid Commands:");
        sender.sendMessage("");
        SendCommands(sender, label);
    }

    public static void SendLootboxTypes(CommandSender sender) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eLootbox Types");
        sender.sendMessage("§c------------------------------");
        for (String key : Global.Lootboxes.keySet()) {
            sender.sendMessage(key);
        }
    }

    public static void SendCustomItemTypes(CommandSender sender) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eLootbox Types");
        sender.sendMessage("§c------------------------------");
        for (String key : Global.CustomItems.keySet()) {
            sender.sendMessage(key);
        }
    }

    public static void SendValidLootboxTypes(CommandSender sender) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eInvalid Lootbox Type");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eValid Types:");
        sender.sendMessage("");
        for (String key : Global.Lootboxes.keySet()) {
            sender.sendMessage(key);
        }
    }

    public static void SendValidCustomItemTypes(CommandSender sender) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eInvalid Custom Item");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eValid Items:");
        sender.sendMessage("");
        for (String key : Global.CustomItems.keySet()) {
            sender.sendMessage(key);
        }
    }

    public static void SendNoValidLootboxTypes(CommandSender sender) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eThere are no valid lootbox types");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eAdd some in the config.yml:");
    }

    public static void SendNoValidCustomItems(CommandSender sender) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eThere are no valid custom items");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eAdd some in the config.yml:");
    }

    public static void SendInvalidNumber(CommandSender sender, String number) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eInvalid value: \"" + number + "\"");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§ePlease enter a valid integer");
    }

    public static void SendInvalidPlayer(CommandSender sender, String player) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eInvalid Player: \"" + player + "\"");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§ePlease enter a valid username");
    }

    public static void SendInvalidLootbox(CommandSender sender, String name) {
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§eInvalid Lootbox: \"" + name + "\"");
        sender.sendMessage("§c------------------------------");
        sender.sendMessage("§ePlease contact your server administrator");
    }

    public static ItemStack GenerateLootbox(Lootbox type, int amount) {
        ItemStack returnItem = type.GetItem();
        returnItem.setAmount(amount);
        return returnItem;
    }

    public static void GiveLootbox(Lootbox type, Player player, int amount) {
        ItemStack lootbox = Global.GenerateLootbox(type, amount);
        Global.AddItem(player, lootbox);
    }

    public static int GenerateRandomIntInclusive(int min, int max) {
        Random r = new Random();
        int result = r.nextInt((max + 1) - min) + min;
        return result;
    }

    public static int GenerateRandomIntExclusive(int min, int max) {
        Random r = new Random();
        int result = r.nextInt((max - 1) - min) + min + 1;
        return result;
    }

    public static double GenerateRandomDouble(double min, double max) {
        Random r = new Random();
        double result = min + (max - min) * r.nextDouble();
        return result;
    }

    public static ArrayList<PossibleItem> MakeDeepCopyOfArrayListOfPossibleItem(ArrayList<PossibleItem> listToCopy) {
        ArrayList<PossibleItem> copiedArray = new ArrayList<PossibleItem>();
        for (int i = 0; i < listToCopy.size(); i++) {
            copiedArray.add(new PossibleItem(listToCopy.get(i)));
        }
        return copiedArray;
    }

    public static String Colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static ArrayList<String> Colorize(ArrayList<String> input) {
        ArrayList<String> newValue = new ArrayList<String>();
        for (int i = 0; i < input.size(); i++) {
            newValue.add(Global.Colorize(input.get(i)));
        }
        return newValue;
    }

    public static List<String> Colorize(List<String> input) {
        ArrayList<String> newValue = new ArrayList<String>();
        for (String element : input) {
            newValue.add(Global.Colorize(element));
        }
        return newValue;
    }

    public static String Decolorize(String input) {
        return input.replaceAll("§", "&");
    }

    public static ArrayList<String> Decolorize(ArrayList<String> input) {
        ArrayList<String> newValue = new ArrayList<String>();
        for (int i = 0; i < input.size(); i++) {
            newValue.add(input.get(i).replaceAll("§", "&"));
        }
        return newValue;
    }

    public static List<String> Decolorize(List<String> input) {
        ArrayList<String> newValue = new ArrayList<String>();
        for (String element : input) {
            newValue.add(element.replaceAll("§", "&"));
        }
        return newValue;
    }

    public static String StripColor(String input) {
        return ChatColor.stripColor(input);
    }

    public static void LoadConfig(LootboxesMain lootboxesMain) {
        Global.LootboxIndicator = Global.Colorize(lootboxesMain.getConfig().getString("lootboxIndicator"));
        String path = "customItems";
        for (String key : lootboxesMain.getConfig().getConfigurationSection(path).getKeys(false)) {
            try {
                String name = "defaultCustomItemName";
                String type = "DIAMOND_SWORD";
                int damage = 0;
                Material material = Material.DIAMOND_SWORD;
                List<String> lore = new ArrayList<String>();
                List<String> enchantments = new ArrayList<String>();

                try {
                    lore = lootboxesMain.getConfig().getStringList(path + "." + key + ".lore");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("customItem." + key + ".lore not specified, defaulting to nothing!");
                }

                try {
                    enchantments = lootboxesMain.getConfig().getStringList(path + "." + key + ".enchantments");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("customItem." + key + ".enchantments not specified, defaulting to nothing!");
                }

                try {
                    name = lootboxesMain.getConfig().getString(path + "." + key + ".displayName");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("customItem." + key + ".name not specified, defaulting to \"defaultCustomItemName\"!");
                }

                try {
                    type = lootboxesMain.getConfig().getString(path + "." + key + ".type");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("customItem." + key + ".type not specified, defaulting to \"DIAMOND_SWORD\"!");
                }

                try {
                    damage = lootboxesMain.getConfig().getInt(path + "." + key + ".damage");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("customItem." + key + ".damage not specified, defaulting to 0!");
                }

                try {
                    material = Material.getMaterial(type.toUpperCase());
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("customItem." + key + ".type specified, but is invalid, defaulting to \"DIAMOND_SWORD\"!");
                }

                ItemStack item = new ItemStack(material, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Global.Colorize(name));
                meta.setLore(Global.Colorize(lore));

                for (int k = 0; k < enchantments.size(); k++) {
                    try {
                        String[] enchantmentAndLevel = enchantments.get(k).split(" ", 2);

                        String enchant = enchantmentAndLevel[0].trim();
                        String lvl = enchantmentAndLevel[1].trim();

                        try {
                            meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchant.toLowerCase())), Integer.parseInt(lvl), true);
                        } catch (Exception e) {
                            lootboxesMain.getLogger().info("Enchantment: " + enchant + " " + lvl + " is invalid!");
                        }
                    } catch (Exception e) {
                        lootboxesMain.getLogger().info("Error extracting enchantment from config.yml, make sure to seperate the enchantment and the level with a space!");
                    }
                }
                if (damage > 0) {
                    if (item instanceof Damageable) {
                        Damageable dmgable = (Damageable) meta;
                        dmgable.setDamage(damage);
                        item.setItemMeta((ItemMeta) dmgable);
                    } else {
                        lootboxesMain.getLogger().info("Cannot change the condition of the material \"" + material + "\"!");
                    }
                } else {
                    item.setItemMeta(meta);
                }
                Global.CustomItems.put(key, item);
            } catch (Exception e) {
                lootboxesMain.getLogger().info("Error extracting item from config.yml!");
            }
        }


        String path2 = "lootboxes";
        for (String key : lootboxesMain.getConfig().getConfigurationSection(path2).getKeys(false)) {
            try {
                Particle particle = Particle.CLOUD;
                Effect soundEffect = Effect.DRAGON_BREATH;
                Material material = Material.BARRIER;
                ItemStack inGameItem = null;
                boolean repeats = false;
                int outcomesToGenerateMin = 1;
                int outcomesToGenerateMax = 1;
                String lootboxName = "defaultName";
                String particleTypeOnOpening = "CLOUD";
                String soundEffectToPlayOnOpening = "SMOKE";
                String openingMessage = "Default opening message";
                String lootboxItem = "CHEST";
                List<String> possibleOutcomes = new ArrayList<String>();
                List<Integer> minAmtPerOutcome = new ArrayList<Integer>();
                List<Integer> maxAmtPerOutcome = new ArrayList<Integer>();
                List<Double> chancePerOutcome = new ArrayList<Double>();
                List<String> lootboxLore = new ArrayList<String>();

                try {
                    possibleOutcomes = lootboxesMain.getConfig().getStringList(path2 + "." + key + ".possibleOutcomes");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".possibleOutcomes not specified, defaulting to nothing, lootbox will be useless!");
                }

                try {
                    minAmtPerOutcome = lootboxesMain.getConfig().getIntegerList(path2 + "." + key + ".minAmtPerOutcome");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".minAmtPerOutcome not specified, defaulting to nothing, lootbox will be useless!");
                }

                try {
                    maxAmtPerOutcome = lootboxesMain.getConfig().getIntegerList(path2 + "." + key + ".maxAmtPerOutcome");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".maxAmtPerOutcome not specified, defaulting to nothing, lootbox will be useless!");
                }

                try {
                    chancePerOutcome = lootboxesMain.getConfig().getDoubleList(path2 + "." + key + ".chancePerOutcome");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".chancePerOutcome not specified, defaulting to nothing, lootbox will be useless!");
                }

                try {
                    lootboxLore = lootboxesMain.getConfig().getStringList(path2 + "." + key + ".lootboxLore");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".lootboxLore not specified, defaulting to nothing!");
                }

                try {
                    repeats = lootboxesMain.getConfig().getBoolean(path2 + "." + key + ".repeats");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".repeats not specified, defaulting to false!");
                }

                try {
                    outcomesToGenerateMin = lootboxesMain.getConfig().getInt(path2 + "." + key + ".outcomesToGenerateMin");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".outcomesToGenerateMin not specified, defaulting to 1!");
                }

                try {
                    outcomesToGenerateMax = lootboxesMain.getConfig().getInt(path2 + "." + key + ".outcomesToGenerateMax");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".outcomesToGenerateMax not specified, defaulting to 1!");
                }

                try {
                    lootboxName = lootboxesMain.getConfig().getString(path2 + "." + key + ".lootboxName");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".lootboxName not specified, defaulting to \"defaultName\"!");
                }

                try {
                    particleTypeOnOpening = lootboxesMain.getConfig().getString(path2 + "." + key + ".particleTypeOnOpening");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".particleTypeOnOpening not specified, defaulting to \"CLOUD\"!");
                }

                try {
                    soundEffectToPlayOnOpening = lootboxesMain.getConfig().getString(path2 + "." + key + ".soundEffectToPlayOnOpening");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".soundEffectToPlayOnOpening not specified, defaulting to \"SMOKE\"!");
                }

                try {
                    openingMessage = Global.Colorize(lootboxesMain.getConfig().getString(path2 + "." + key + ".openingMessage"));
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".openingMessage not specified, defaulting to \"Default opening message\"!");
                }

                try {
                    lootboxItem = lootboxesMain.getConfig().getString(path2 + "." + key + ".lootboxItem");
                } catch (Exception e) {
                    lootboxesMain.getLogger().info("lootbox." + key + ".lootboxItem not specified, defaulting to \"CHEST\"!");
                }


                if (possibleOutcomes.size() == minAmtPerOutcome.size() && possibleOutcomes.size() == maxAmtPerOutcome.size() && possibleOutcomes.size() == chancePerOutcome.size()) {
                    try {
                        particle = Particle.valueOf(particleTypeOnOpening.toUpperCase());
                        soundEffect = Effect.valueOf(soundEffectToPlayOnOpening.toUpperCase());
                        material = Material.getMaterial(lootboxItem.toUpperCase());
                        inGameItem = new ItemStack(material, 1);
                        ItemMeta meta = inGameItem.getItemMeta();
                        meta.setDisplayName(Global.Colorize(lootboxName));
                        meta.setLore(Global.Colorize(lootboxLore));
                        inGameItem.setItemMeta(meta);
                    } catch (Exception e) {
                        lootboxesMain.getLogger().info("Error in conversions");
                    }

                    ArrayList<PossibleItem> possibleItemsToBeAdded = new ArrayList<PossibleItem>();

                    for (int i = 0; i < possibleOutcomes.size(); i++) {
                        if (possibleOutcomes.get(i).length() >= 3) {
                            if (possibleOutcomes.get(i).charAt(0) == '{' && possibleOutcomes.get(i).charAt(possibleOutcomes.get(i).length() - 1) == '}') {
                                try {
                                    possibleItemsToBeAdded.add(new PossibleItem(new ItemStack(Global.CustomItems.get(possibleOutcomes.get(i).substring(1, possibleOutcomes.get(i).length() - 1))), chancePerOutcome.get(i), minAmtPerOutcome.get(i), maxAmtPerOutcome.get(i)));
                                } catch (Exception e) {
                                    lootboxesMain.getLogger().info("Error, possible outcome at index: " + i + " (starting at 0) - \"" + possibleOutcomes.get(i) + "\"");
                                    lootboxesMain.getLogger().info("is not a valid customItem, make sure you use the same name as the identifier of the item.");
                                    lootboxesMain.getLogger().info("This is not the displayName of the item, but rather the title of the section that contains its");
                                    lootboxesMain.getLogger().info("subsections such as \"displayName:\", \"lore:\", etc.");
                                }
                            } else if (possibleOutcomes.get(i).charAt(0) == '(' && possibleOutcomes.get(i).charAt(possibleOutcomes.get(i).length() - 1) == ')') {
                                try {
                                    possibleItemsToBeAdded.add(new PossibleItem(possibleOutcomes.get(i).substring(1, possibleOutcomes.get(i).length() - 1), chancePerOutcome.get(i), minAmtPerOutcome.get(i), maxAmtPerOutcome.get(i)));
                                } catch (Exception e) {
                                    lootboxesMain.getLogger().info("Error, possible outcome at index: " + i + " (starting at 0) - \"" + possibleOutcomes.get(i) + "\"");
                                    lootboxesMain.getLogger().info("is not a valid command.");
                                }
                            } else {
                                try {
                                    possibleItemsToBeAdded.add(new PossibleItem(new ItemStack(Material.getMaterial(possibleOutcomes.get(i).toUpperCase()), 1), chancePerOutcome.get(i), minAmtPerOutcome.get(i), maxAmtPerOutcome.get(i)));
                                } catch (Exception e) {
                                    lootboxesMain.getLogger().info("Error, possible outcome at index: " + i + " (starting at 0) - \"" + possibleOutcomes.get(i) + "\"");
                                    lootboxesMain.getLogger().info("is not a valid material, if you would like this outcome to be a command or a custom item, wrap it in");
                                    lootboxesMain.getLogger().info("parentheses () for a command, and curly brackets {} for a custom items.");
                                }
                            }
                        } else {
                            possibleItemsToBeAdded.add(new PossibleItem(new ItemStack(Material.getMaterial(possibleOutcomes.get(i).toUpperCase()), 1), chancePerOutcome.get(i), minAmtPerOutcome.get(i), maxAmtPerOutcome.get(i)));
                        }
                    }

                    Lootbox lb = new Lootbox(inGameItem, possibleItemsToBeAdded, repeats, outcomesToGenerateMin, outcomesToGenerateMax, particle, soundEffect, openingMessage);

                    Global.Lootboxes.put(key, lb);
                } else {
                    lootboxesMain.getLogger().info("Invalid lootbox - possibleOutcomes, minAmtPerOutcome, maxAmtPerOutcome, and chancePerOutcome must all have equal sizes");
                }
            } catch (Exception e) {
                lootboxesMain.getLogger().info("Error extracting lootbox from config.yml");
            }
        }
    }
}
