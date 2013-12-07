package de.f14tomcat.tenjava.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class MineBallManager
{
    private static MineBallManager instance;
    private final List<String> trainers = Lists.newArrayList();
    private final Map<Integer, String> mineballs = Maps.newHashMap();

    public MineBallManager()
    {
        MineBallManager.instance = this;
    }

    public static MineBallManager getInstance()
    {
        return instance;
    }

    public List<String> getTrainers()
    {
        return trainers;
    }

    public boolean isCatching(Player player)
    {
        return trainers.contains( player.getName() );
    }

    public Map<Integer, String> getMineballs()
    {
        return mineballs;
    }

    public static boolean isMineBall(ItemStack itemStack)
    {
        if ( itemStack.getType() == Material.SNOW_BALL )
        {
            if ( itemStack.getItemMeta() != null )
            {
                ItemMeta meta = itemStack.getItemMeta();
                return meta.getDisplayName().equals( "" + ChatColor.RED + "Mine" + ChatColor.WHITE + "Ball" );
            }
        }
        return false;
    }

    public static ItemStack getMineBall(int amount)
    {
        ItemStack mineBall = new ItemStack( Material.SNOW_BALL, amount );
        List<String> lore = Lists.newArrayListWithCapacity( 4 );
        lore.add( ChatColor.AQUA + "Throw it at an entity!" );
        lore.add( ChatColor.AQUA + "Maybe you catch it!" );
        ItemMeta meta = mineBall.getItemMeta();
        meta.setDisplayName( "" + ChatColor.RED + "Mine" + ChatColor.WHITE + "Ball" );
        meta.setLore( lore );
        mineBall.setItemMeta( meta );
        return mineBall;
    }
}
