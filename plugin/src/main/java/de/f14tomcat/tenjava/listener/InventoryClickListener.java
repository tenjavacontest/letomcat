package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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
                    player.sendMessage( MineBallManager.getInstance().getTrainers().get( player.getName() ).get( event.getSlot() ).getName() );
                }
                event.getWhoClicked().closeInventory();
            }
        }
    }
}
