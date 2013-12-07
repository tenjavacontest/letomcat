package de.f14tomcat.tenjava.util;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Message
{
    private static Message instance;
    private final ResourceBundle bundle;
    private final Map<String, MessageFormat> messageFormatCache = Maps.newHashMap();
    public static final String PREFIX = "" + ChatColor.GRAY + '[' + ChatColor.AQUA + "MB" + ChatColor.GRAY + ']';

    public Message(final Locale locale)
    {
        instance = this;
        this.bundle = ResourceBundle.getBundle( "messages", locale );
    }

    public static String msg(String string, Object... objects)
    {
        return objects.length < 1 ? instance.bundle.getString( string ) : instance.format( string, objects );
    }

    public static String msgWithPrefix(String string, Object... objects)
    {
        return objects.length < 1 ? Message.PREFIX + ' ' + instance.bundle.getString( string ) : Message.PREFIX + ' ' + instance.format( string, objects );
    }

    private String format(String string, Object... objects)
    {
        String format = bundle.getString( string );
        MessageFormat messageFormat = messageFormatCache.get( format );
        if ( messageFormat == null )
        {
            messageFormat = new MessageFormat( format );
            messageFormatCache.put( format, messageFormat );
        }
        return messageFormat.format( objects );
    }
}
