package me.approximations.spawners.util;


import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Utils {
//    public static ItemStack getHeadUrl(String url) {
//        url = "https://textures.minecraft.net/texture/"+url;
//        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
//        if (url != null && !url.isEmpty()) {
//            SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
//            GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
//            String format = String.format("{textures:{SKIN:{url:\"%s\"}}}", url);
//            String encodedData = Base64.getEncoder().encodeToString(format.getBytes());
//            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
//            Field profileField = null;
//
//            try {
//                profileField = skullMeta.getClass().getDeclaredField("profile");
//                profileField.setAccessible(true);
//                profileField.set(skullMeta, profile);
//            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException var8) {
//                var8.printStackTrace();
//            }
//
//            skull.setItemMeta(skullMeta);
//            return skull;
//        } else {
//            return skull;
//        }
//    }
//
//    public static ItemStack getHead(Player player) {
//        int lifePlayer = (int) player.getHealth();
//        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
//        SkullMeta skull = (SkullMeta) item.getItemMeta();
//        skull.setDisplayName(player.getName());
//        ArrayList<String> lore = new ArrayList<String>();
//        lore.add("Custom head");
//        skull.setLore(lore);
//        skull.setOwner(player.getName());
//        item.setItemMeta(skull);
//        return item;
//    }
//
//    public static ItemStack getHead(OfflinePlayer player) {
//        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
//        SkullMeta skull = (SkullMeta) item.getItemMeta();
//        skull.setDisplayName(player.getName());
//        ArrayList<String> lore = new ArrayList<String>();
//        lore.add("Custom head");
//        skull.setLore(lore);
//        skull.setOwner(player.getName());
//        item.setItemMeta(skull);
//        return item;
//    }

    public static List<Block> getBlocksBetweenPoints(Location l1, Location l2) {
        List<Block> blocks = new ArrayList();
        int topBlockX = Math.max(l1.getBlockX(), l2.getBlockX());
        int bottomBlockX = Math.min(l1.getBlockX(), l2.getBlockX());
        int topBlockY = Math.max(l1.getBlockY(), l2.getBlockY());
        int bottomBlockY = Math.min(l1.getBlockY(), l2.getBlockY());
        int topBlockZ = Math.max(l1.getBlockZ(), l2.getBlockZ());
        int bottomBlockZ = Math.min(l1.getBlockZ(), l2.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; ++x) {
            for(int y = bottomBlockY; y <= topBlockY; ++y) {
                for(int z = bottomBlockZ; z <= topBlockZ; ++z) {
                    Block block = l1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    public static double somarLista(List<Double> list) {
        double soma = 0;

        for (Double aDouble : list) {
            soma += aDouble;
        }
        return soma;
    }

//    public static HashMap<Location, Hologram> holograms = new HashMap<>();
//
//    public static void createHologram(Location location, String... name) {
//        HolographicDisplaysAPI hapi = Main.getHapi();
//        Hologram hologram = hapi.createHologram(location);
////        for (String s : name) {
////            hologram.getLines().appendText(s);
////        }
////        holograms.put(location, hologram);
//        hologram.getLines().appendText("wdawda");
//    }


    public static String getProgressBar(double current, double max, int totalBars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) (current / max);
        int progressBars = (int) (totalBars * percent);
        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars) + "??7 ??8(" + getPercent(percent) + ")";
    }

    public static String getPercent(double a) {
        NumberFormat df = NumberFormat.getPercentInstance();
        df.setMinimumFractionDigits(2);
        return "" + df.format(a);
    }

    public static double getPercentDouble(double valor, double total) {
        return (valor * 100) / total;
    }

    private static final DecimalFormat percent = new DecimalFormat("#.##");
    public static String getPercentString(double valor, double total) {
        return percent.format(((valor * 100) / total));
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void removeItemInHand(Player player, int quantia) {
        ItemStack item = player.getItemInHand();
        if (item.getAmount() == quantia) {
            player.setItemInHand(null);
        } else {
            item.setAmount(item.getAmount()-quantia);
            player.setItemInHand(item);
        }
    }

     static final Random random = new Random();
    public static int getRandomInt(int i1, int i2) {
        return random.nextInt((i2-i1) + 1) + i1;
    }

    public static void giveItem(Player player, ItemStack... is) {
        Map<Integer, ItemStack> map = player.getInventory().addItem(is);
        map.values().stream().filter(i -> !i.getType().equals(Material.AIR)).forEach(i -> player.getWorld().dropItemNaturally(player.getLocation(), i));
    }

    public static ItemStack getItemFromConfig(ConfigurationSection section, Map<String, String> placeholders) {
        String name = section.getString("Name");
        List<String> lore = section.getStringList("Lore");
        String[] item = section.getString("Item").split(":");

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            name = name.replace(entry.getKey(), entry.getValue());
            lore.replaceAll(line -> ColorUtil.colored(line.replace(entry.getKey(), entry.getValue())));
        }
        if(section.getBoolean("CustomHead")) {
            return new ItemBuilder(section.getString("Head_url")).setName(name).setLore(lore).wrap();
        }else {
            return new ItemBuilder(TypeUtil.getMaterialFromLegacy(item[0]), Integer.parseInt(item[1])).setName(name).setLore(lore).wrap();
        }
    }

    public static ItemStack getItemFromConfig(ConfigurationSection section, Map<String, String> placeholders, Material material, int data) {
        String name = section.getString("Name");
        List<String> lore = section.getStringList("Lore");

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            name = name.replace(entry.getKey(), entry.getValue());
            lore.replaceAll(line -> ColorUtil.colored(line.replace(entry.getKey(), entry.getValue())));
        }

        return new ItemBuilder(material, data).setName(name).setLore(lore).wrap();
    }

    public static ItemStack getItemFromConfig(ConfigurationSection section, Map<String, String> placeholders, String head_url) {
        String name = section.getString("Name");
        List<String> lore = section.getStringList("Lore");

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            name = name.replace(entry.getKey(), entry.getValue());
            lore.replaceAll(line -> ColorUtil.colored(line.replace(entry.getKey(), entry.getValue())));
        }

        return new ItemBuilder(head_url).setName(name).setLore(lore).wrap();
    }

    public static ItemStack getItemFromConfig(ConfigurationSection section) {
        if(section.getBoolean("CustomHead")) {
            return new ItemBuilder(section.getString("Head_url"))
                    .setName(section.getString("Name"))
                    .setLore(ColorUtil.colored(section.getStringList("Lore")))
                    .wrap();
        }else {
            String[] i = section.getString("Item").split(":");
            return new ItemBuilder(TypeUtil.getMaterialFromLegacy(i[0]), Integer.parseInt(i[1]))
                    .setName(section.getString("Name"))
                    .setLore(ColorUtil.colored(section.getStringList("Lore")))
                    .wrap();
        }
    }

    public static ItemStack getItemFromConfig(ConfigurationSection section, Material material, int data) {
        return new ItemBuilder(material, data)
                .setName(section.getString("Name"))
                .setLore(ColorUtil.colored(section.getStringList("Lore")))
                .wrap();
    }

    public static ItemStack getItemFromConfigSimple(String item) {
        String[] i = item.split(":");
        return new ItemStack(TypeUtil.getMaterialFromLegacy(i[0]), 1, Byte.parseByte(i[1]));
    }
}
