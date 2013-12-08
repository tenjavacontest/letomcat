package de.f14tomcat.tenjava.commands;

import de.f14tomcat.tenjava.Main;
import de.f14tomcat.tenjava.util.NumberUtil;
import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import de.f14tomcat.tenjava.util.mineball.MineMon;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
            sender.sendMessage( msg( "noConsole" ) );
            return true;
        }
        if ( args.length < 1 || args[ 0 ].toLowerCase( Locale.ENGLISH ).equals( "help" ) )
        {
            sender.sendMessage( msg( "commandInfo", label + " <enable|disable>", msg( "switchDescription" ) ) );
            sender.sendMessage( msg( "commandInfo", label + " get <amount>", msg( "getBallsDescription" ) ) );
            sender.sendMessage( msg( "commandInfo", label + " status", msg( "statusDescription" ) ) );
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
                    MineBallManager.getInstance().getTrainers().put( sender.getName(), new ArrayList<MineMon>( 6 ) );
                    sender.sendMessage( msg( "statusCatch", ChatColor.GREEN + "enabled" ) );
                    Main.getInstance().getServer().broadcastMessage( msgWithPrefix( "broadcastEnable", sender.getName() ) );
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
                    Main.getInstance().getServer().broadcastMessage( msgWithPrefix( "broadcastDisable", sender.getName() ) );
                }
            } else if ( args[ 0 ].toLowerCase( Locale.ENGLISH ).equals( "status" ) )
            {
                sender.sendMessage( msg( "statusCatch", (MineBallManager.getInstance().isCatching( (Player) sender )) ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled" ) );
                if ( MineBallManager.getInstance().getTrainers().containsKey( sender.getName() ) )
                {
                    sender.sendMessage( msg( "holderStatus", MineBallManager.getInstance().getTrainers().get( sender.getName() ).size(), MineBallManager.HOLDER_MAX ) );
                }
            }
        }
        if ( args.length >= 2 )
        {
            if ( args[ 0 ].toLowerCase( Locale.ENGLISH ).equals( "get" ) )
            {
                if ( !NumberUtil.isInt( args[ 1 ] ) || NumberUtil.getInt( args[ 1 ] ) <= 0 )
                {
                    sender.sendMessage( msg( "noInt", args[ 1 ] ) );
                } else
                {
                    ((Player) sender).getInventory().addItem( MineBallManager.getMineBall( NumberUtil.getInt( args[ 1 ] ) ) );
                    sender.sendMessage( msg( "addBalls", NumberUtil.getInt( args[ 1 ] ) ) );
                }
            }
        }
        return true;
    }
}
