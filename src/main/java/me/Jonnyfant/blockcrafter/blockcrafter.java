package me.Jonnyfant.blockcrafter;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getRecipesFor;


public class blockcrafter extends JavaPlugin {
    String pathItems = "Items to make to Blocks";
    public void onEnable() {
        loadConfig();
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only!");
            return true;
        }
/*
        ArrayList<Material> enabledItems= (ArrayList<Material>) getConfig().getList(pathItems);
 */
        ArrayList<Material> enabledItems=new ArrayList<>();
        enabledItems.add(Material.IRON_NUGGET);
        enabledItems.add(Material.IRON_INGOT);
        for (int i=0;i<enabledItems.size();i++)
        {
            player.sendMessage("Looking for the following Material: " + enabledItems.get(i).name());
            Inventory inventory = player.getInventory();
            ItemStack[] items = inventory.getContents();
            ShapedRecipe temprecipe = null;
            List<Recipe> possibleRecipes = getRecipesFor(new ItemStack(enabledItems.get(i),9));
            for (int j=0; j<possibleRecipes.size();j++)
            {
                player.sendMessage("Following possible Recipe found: " + possibleRecipes.get(j).getResult().getType().name());
                ShapedRecipe temp = (ShapedRecipe) possibleRecipes.get(j);
                if(temp.getShape()[0].equals(temp.getShape()[1]) && temp.getShape()[1].equals(temp.getShape()[2]))
                {
                    temprecipe = temp;
                }
            }
            if(temprecipe!=null) {
                upcycle(enabledItems.get(i), temprecipe.getResult().getType(), items, player);
            }
        }
/*
        //Iron Nugget
        if(getConfig().getBoolean(pathIronNugget))
        {
            upcycle(Material.IRON_NUGGET, Material.IRON_INGOT,items, player);
        }
        //Iron Ingot
        if(getConfig().getBoolean(pathIronIngot))
        {
            upcycle(Material.IRON_INGOT, Material.IRON_BLOCK,items, player);
        }
        //Gold Nugget
        if(getConfig().getBoolean(pathGoldNugget))
        {
            upcycle(Material.GOLD_NUGGET, Material.GOLD_INGOT,items, player);
        }
        //GOLD Ingot
        if(getConfig().getBoolean(pathGoldIngot))
        {
            upcycle(Material.GOLD_INGOT, Material.GOLD_BLOCK,items, player);
        }
        //Diamond
        if(getConfig().getBoolean(pathDiamond))
        {
            upcycle(Material.DIAMOND, Material.DIAMOND_BLOCK,items, player);
        }
        //Emerald
        if(getConfig().getBoolean(pathEmerald))
        {
            upcycle(Material.EMERALD, Material.EMERALD_BLOCK,items, player);
        }
        //Coal
        if(getConfig().getBoolean(pathCoal))
        {
            upcycle(Material.COAL, Material.COAL_BLOCK,items, player);
        }
*/
        return true;
    }

    public void addItems(Player player, int amountSmall, int amountBig, Material materialSmall, Material materialBig)
    {
        Inventory inventory = player.getInventory();
        inventory.addItem(new ItemStack(materialSmall, amountSmall));
        while (amountBig>0)
        {
            if(amountBig>=64)
            {
                amountBig-=64;
                inventory.addItem(new ItemStack(materialBig, 64));
            }
            else
            {
                inventory.addItem(new ItemStack(materialBig,amountBig));
                amountBig=0;
            }
        }
    }

    public void upcycle (Material search, Material output, ItemStack[] items, Player player)
    {
        int amountSmall=0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                if (items[i].getType() == search) {
                    amountSmall += items[i].getAmount();
                    player.getInventory().remove(items[i]);
                }
            }
        }
        int amountBig = amountSmall/9;
        amountSmall -= amountBig*9;
        addItems(player,amountSmall, amountBig,search, output);
    }

    public void loadConfig() {
        //getConfig().addDefaults("#DEFAULTS:\n");
        /*
        ArrayList<Material> list = new ArrayList<>();
        list.add(Material.IRON_NUGGET);
        list.add(Material.IRON_INGOT);
        list.add(Material.GOLD_NUGGET);
        list.add(Material.GOLD_INGOT);
        list.add(Material.DIAMOND);
        list.add(Material.EMERALD);
        list.add(Material.COAL);
        list.add(Material.REDSTONE);

        getConfig().addDefault(pathItems, list);

        getConfig().options().copyDefaults(true);
        saveConfig();*/
    }
}
