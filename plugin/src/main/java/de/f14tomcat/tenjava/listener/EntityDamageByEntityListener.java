package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import de.f14tomcat.tenjava.util.mineball.MineMon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static de.f14tomcat.tenjava.util.Message.msg;

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
                        if ( MineBallManager.getInstance().getTrainers().get( shooter.getName() ).size() >= MineBallManager.HOLDER_MAX )
                        {
                            shooter.sendMessage( msg( "maxHolder" ) );
                        } else
                        {
                            MineMon mineMon = new MineMon( event.getEntity().getType() );
                            MineBallManager.getInstance().getTrainers().get( shooter.getName() ).add( mineMon );
                            shooter.sendMessage( msg( "catched", mineMon.getName() ) );
                            shooter.sendMessage( msg( "holderStatus", MineBallManager.getInstance().getTrainers().get( shooter.getName() ).size(), MineBallManager.HOLDER_MAX ) );
                            event.getEntity().remove();
                        }
                    }
                }
            }
        }
    }
}
