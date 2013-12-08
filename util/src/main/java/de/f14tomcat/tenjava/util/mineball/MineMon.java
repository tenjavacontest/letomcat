package de.f14tomcat.tenjava.util.mineball;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.SpawnEgg;

public class MineMon
{
    private final EntityType type;

    public MineMon(EntityType type)
    {
        this.type = type;
    }

    public String getName()
    {
        return WordUtils.capitalizeFully( type.toString() );
    }

    public ItemStack getItemStack()
    {
        ItemStack item = new SpawnEgg( type ).toItemStack( 1 );
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName( ChatColor.YELLOW + getName() );
        item.setItemMeta( meta );
        return item;
    }

    public String toString()
    {
        return type.toString();
    }
}
