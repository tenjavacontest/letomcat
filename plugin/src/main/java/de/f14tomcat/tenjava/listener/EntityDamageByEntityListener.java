package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.MineBallManager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.SpawnEgg;

public class EntityDamageByEntityListener implements Listener
{
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if ( event.getEntity() instanceof Player || !(event.getEntity() instanceof LivingEntity) )
        {
            return;
        }
        if ( event.getDamager() instanceof Snowball )
        {
            Snowball snowball = (Snowball) event.getDamager();
            if ( snowball.getShooter() instanceof Player )
            {
                Player shooter = (Player) snowball.getShooter();
                if ( MineBallManager.getInstance().isCatching( shooter ) )
                {
                    if ( MineBallManager.getInstance().getMineballs().containsKey( snowball.getEntityId() ) )
                    {
                        SpawnEgg spawnEgg = new SpawnEgg( event.getEntity().getType() );
                        ItemStack egg = new ItemStack( spawnEgg.toItemStack( 1 ) );
                        ItemMeta meta = egg.getItemMeta();
                        meta.setDisplayName( "\u00A7a" + WordUtils.capitalizeFully( event.getEntity().getType().toString() ) );
                        egg.setItemMeta( meta );
                        shooter.getInventory().addItem( egg );
                        shooter.updateInventory();
                    }
                }
            }
        }
    }
}
