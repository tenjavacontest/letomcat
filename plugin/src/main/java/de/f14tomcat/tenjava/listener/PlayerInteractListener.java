package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.MineBallManager;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if ( event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK )
        {
            return;
        }
        if ( MineBallManager.getInstance().isCatching( event.getPlayer() ) )
        {
            if ( event.getPlayer().getItemInHand() != null && MineBallManager.isMineBall( event.getPlayer().getItemInHand() ) )
            {
                //Launch MineBall
                Snowball snowball = event.getPlayer().launchProjectile( Snowball.class );
                snowball.setShooter( event.getPlayer() );
                MineBallManager.getInstance().getMineballs().put( snowball.getEntityId(), event.getPlayer().getName() );
                event.setCancelled( true );
            }
        }
    }
}
