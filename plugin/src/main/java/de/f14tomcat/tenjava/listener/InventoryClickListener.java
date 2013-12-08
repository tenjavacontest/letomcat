package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import de.f14tomcat.tenjava.util.mineball.MineMon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static de.f14tomcat.tenjava.util.Message.msg;

public class InventoryClickListener implements Listener
{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if ( event.getWhoClicked() instanceof Player )
        {
            if ( event.getInventory().getName().equals( MineBallManager.HOLDER_NAME ) )
            {
                event.setCancelled( true );
                final Player player = (Player) event.getWhoClicked();
                if ( event.getSlot() + 1 <= MineBallManager.getInstance().getTrainers().get( player.getName() ).size() )
                {
                    //MineMon clicked
                    MineMon mineMon = MineBallManager.getInstance().getTrainers().get( player.getName() ).get( event.getSlot() );
                    player.sendMessage( msg("selectedMineMon", mineMon.getName()) );
                    player.getInventory().remove( Material.MONSTER_EGG );
                    player.updateInventory();
                    player.getInventory().addItem( mineMon.getItemStack() );
                    MineBallManager.getInstance().getTrainers().get( player.getName() ).remove( mineMon );
                }
                event.getWhoClicked().closeInventory();
            }
        }
    }
}
