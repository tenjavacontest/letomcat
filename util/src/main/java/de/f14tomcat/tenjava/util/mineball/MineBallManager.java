package de.f14tomcat.tenjava.util.mineball;

import com.google.common.base.Preconditions;
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
    public static final int HOLDER_MAX = 6;
    public static final String HOLDER_NAME = "" + ChatColor.RED + "Mine" + ChatColor.WHITE + "Ball" + ChatColor.GOLD + "Holder";
    private final Map<String, List<MineMon>> trainers = Maps.newHashMap();
    private final Map<Integer, String> mineballs = Maps.newHashMap();

    public MineBallManager()
    {
        MineBallManager.instance = this;
    }

    public static MineBallManager getInstance()
    {
        return instance;
    }

    public boolean isCatching(Player player)
    {
        return trainers.containsKey( player.getName() );
    }

    public Map<Integer, String> getMineballs()
    {
        return mineballs;
    }

    public static boolean isMineBall(ItemStack itemStack)
    {
        Preconditions.checkNotNull( itemStack, "ItemStack cannot be null" );
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

    public static boolean isMineBallHolder(ItemStack itemStack)
    {
        Preconditions.checkNotNull( itemStack, "ItemStack cannot be null" );
        if ( itemStack.getType() == Material.FLOWER_POT_ITEM )
        {
            if ( itemStack.getItemMeta() != null )
            {
                ItemMeta meta = itemStack.getItemMeta();
                return meta.getDisplayName().equals( "" + ChatColor.RED + "Mine" + ChatColor.WHITE + "Ball" + ChatColor.GOLD + "Holder" );
            }
        }
        return false;
    }

    public static ItemStack getMineBallHolder()
    {
        ItemStack mineBallHolder = new ItemStack( Material.FLOWER_POT_ITEM, 1 );
        List<String> lore = Lists.newArrayListWithCapacity( 4 );
        lore.add( ChatColor.AQUA + "Right click to open!" );
        lore.add( ChatColor.AQUA + "Your MineMons will be stored here!" );
        ItemMeta meta = mineBallHolder.getItemMeta();
        meta.setDisplayName( "" + ChatColor.RED + "Mine" + ChatColor.WHITE + "Ball" + ChatColor.GOLD + "Holder" );
        mineBallHolder.setItemMeta( meta );
        return mineBallHolder;
    }

    public Map<String, List<MineMon>> getTrainers()
    {
        return trainers;
    }
}
