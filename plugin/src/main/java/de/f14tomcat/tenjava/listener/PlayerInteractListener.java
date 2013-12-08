package de.f14tomcat.tenjava.listener;

import de.f14tomcat.tenjava.Main;
import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import de.f14tomcat.tenjava.util.mineball.MineMon;
import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.f14tomcat.tenjava.util.Message.msg;

public class PlayerInteractListener implements Listener
{
    //Handler for MineBall
    @EventHandler
    public void onPlayerInteractBall(PlayerInteractEvent event)
    {
        if ( event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK )
        {
            return;
        }
        if ( MineBallManager.getInstance().isCatching( event.getPlayer() ) )
        {
            if ( event.getItem() != null && MineBallManager.isMineBall( event.getItem() ) )
            {
                event.setCancelled( true );
                //Launch MineBall
                Snowball snowball = event.getPlayer().launchProjectile( Snowball.class );
                snowball.setShooter( event.getPlayer() );
                MineBallManager.getInstance().getMineballs().put( snowball.getEntityId(), event.getPlayer().getName() );
                ItemStack stack = event.getItem();
                stack.setAmount( stack.getAmount() - 1 );
                event.getPlayer().setItemInHand( stack );
                event.getPlayer().updateInventory();
            }
        } else
        {
            if ( event.getItem() != null && MineBallManager.isMineBall( event.getItem() ) )
            {
                //Dont launch MineBall
                event.setCancelled( true );
                event.getPlayer().sendMessage( msg( "notCatching" ) );
                event.getPlayer().updateInventory();
            }
        }
    }

    //Handler for MineBallHolder
    @EventHandler
    public void onPlayerInteractHolder(PlayerInteractEvent event)
    {
        if ( event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK )
        {
            return;
        }
        if ( MineBallManager.getInstance().isCatching( event.getPlayer() ) )
        {
            if ( event.getItem() != null && MineBallManager.isMineBallHolder( event.getItem() ) )
            {
                //Open holder view
                event.setCancelled( true );
                Inventory holderView = Main.getInstance().getServer().createInventory( null, 9, MineBallManager.HOLDER_NAME );
                int counter = 0;
                for ( MineMon mineMon : MineBallManager.getInstance().getTrainers().get( event.getPlayer().getName() ) )
                {
                    holderView.setItem( counter, mineMon.getItemStack() );
                    counter++;
                }
                ItemStack fillUp = new ItemStack( Material.PISTON_EXTENSION, 1 );
                ItemMeta meta = fillUp.getItemMeta();
                meta.setDisplayName( " " );
                fillUp.setItemMeta( meta );
                for ( int i = 0; i < holderView.getSize(); i++ )
                {
                    if ( holderView.getItem( i ) == null || holderView.getItem( i ).getType() == Material.AIR )
                    {
                        holderView.setItem( i, fillUp );
                    }
                }
                event.getPlayer().openInventory( holderView );
            }
        } else
        {
            if ( event.getItem() != null && MineBallManager.isMineBallHolder( event.getItem() ) )
            {
                //Dont open holder view
                event.setCancelled( true );
                event.getPlayer().sendMessage( msg( "notCatching" ) );
                event.getPlayer().updateInventory();
            }
        }
    }
}
