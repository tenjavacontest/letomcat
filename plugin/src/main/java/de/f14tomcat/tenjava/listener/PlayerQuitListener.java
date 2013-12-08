package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import de.f14tomcat.tenjava.util.storage.StorageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if ( MineBallManager.getInstance().getTrainers().get( event.getPlayer().getName() ).size() > 0 )
        {
            StorageManager.getInstance().save( event.getPlayer(), MineBallManager.getInstance().getTrainers().get( event.getPlayer().getName() ) );
        } else
        {
            if ( StorageManager.getInstance().doesDataExist( event.getPlayer() ) )
            {
                StorageManager.getInstance().delete( event.getPlayer() );
            }
        }
        if ( MineBallManager.getInstance().isCatching( event.getPlayer() ) )
        {
            MineBallManager.getInstance().getTrainers().remove( event.getPlayer().getName() );
        }
    }
}
