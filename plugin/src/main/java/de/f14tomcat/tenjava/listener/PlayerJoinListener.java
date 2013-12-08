package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import de.f14tomcat.tenjava.util.storage.StorageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static de.f14tomcat.tenjava.util.Message.msg;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if ( !event.getPlayer().getInventory().contains( MineBallManager.getMineBallHolder() ) )
        {
            event.getPlayer().getInventory().addItem( MineBallManager.getMineBallHolder() );
            event.getPlayer().sendMessage( msg( "addHolder" ) );
        }
        if ( StorageManager.getInstance().doesDataExist( event.getPlayer() ) )
        {
            MineBallManager.getInstance().getTrainers().put( event.getPlayer().getName(), StorageManager.getInstance().load( event.getPlayer() ) );
        }
    }
}
