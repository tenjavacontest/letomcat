package de.f14tomcat.tenjava.commands;

import de.f14tomcat.tenjava.Main;
import de.f14tomcat.tenjava.util.MineBallManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

import static de.f14tomcat.tenjava.util.Message.msg;
import static de.f14tomcat.tenjava.util.Message.msgWithPrefix;

public class MineBallExecutor implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if ( !(sender instanceof Player) )
        {
            sender.sendMessage( msg( "noconsole" ) );
            return true;
        }
        if ( args.length < 1 || args[ 0 ].toLowerCase( Locale.ENGLISH ).equals( "help" ) )
        {
            sender.sendMessage( msg( "cmdinfo", label + " <enable|disable>", msg( "switchDescription" ) ) );
            sender.sendMessage( msg( "cmdinfo", label + " status", msg( "statusDescription" ) ) );
            return true;
        }
        if ( args.length >= 1 )
        {
            if ( args[ 0 ].toLowerCase( Locale.ENGLISH ).equals( "enable" ) )
            {
                if ( MineBallManager.getInstance().isCatching( (Player) sender ) )
                {
                    sender.sendMessage( msg( "alreadyCatching" ) );
                } else
                {
                    MineBallManager.getInstance().getTrainers().add( sender.getName() );
                    sender.sendMessage( msg( "statusCatch", ChatColor.GREEN + "enabled" ) );
                    ((Player) sender).getInventory().addItem( MineBallManager.getMineBall( 10 ) );
                    Main.getInstance().getServer().broadcastMessage( msgWithPrefix( "bcastEnable", sender.getName() ) );
                }
            } else if ( args[ 0 ].toLowerCase( Locale.ENGLISH ).equals( "disable" ) )
            {
                if ( !MineBallManager.getInstance().isCatching( (Player) sender ) )
                {
                    sender.sendMessage( msg( "notCatching" ) );
                } else
                {
                    MineBallManager.getInstance().getTrainers().remove( sender.getName() );
                    sender.sendMessage( msg( "statusCatch", ChatColor.RED + "disabled" ) );
                    Main.getInstance().getServer().broadcastMessage( msgWithPrefix( "bcastDisable", sender.getName() ) );
                }
            } else if ( args[ 0 ].toLowerCase( Locale.ENGLISH ).equals( "status" ) )
            {
                sender.sendMessage( msg( "statusCatch", (MineBallManager.getInstance().isCatching( (Player) sender )) ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled" ) );
            }
            return true;
        }
        return true;
    }
}
