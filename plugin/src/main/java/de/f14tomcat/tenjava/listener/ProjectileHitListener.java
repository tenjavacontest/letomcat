package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.MineBallManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener
{
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        if ( event.getEntity() instanceof Snowball )
        {
            Snowball snowball = (Snowball) event.getEntity();
            if ( snowball.getShooter() instanceof Player && MineBallManager.getInstance().isCatching( (Player) snowball.getShooter() ) )
            {
                if ( MineBallManager.getInstance().getMineballs().containsKey( snowball.getEntityId() ) )
                {
                    MineBallManager.getInstance().getMineballs().remove( snowball.getEntityId() );
                }
            }
        }
    }
}
