package de.f14tomcat.tenjava;

import com.google.common.reflect.ClassPath;
import de.f14tomcat.tenjava.commands.MineBallExecutor;
import de.f14tomcat.tenjava.util.Message;
import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;

public class Main extends JavaPlugin
{
    private static Main instance;

    public static Main getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        Main.instance = this;
        new Message( Locale.ENGLISH );
        new MineBallManager();
        getCommand( "mineball" ).setExecutor( new MineBallExecutor() );
        registerListeners( "de.f14tomcat.tenjava.listener" );
    }

    @Override
    public void onDisable()
    {
        Main.instance = null;
    }

    public void registerListeners(String path)
    {
        try
        {
            for ( ClassPath.ClassInfo classInfo : ClassPath.from( getClassLoader() ).getTopLevelClasses( path ) )
            {
                Object clazz = Class.forName( classInfo.getName() ).newInstance();
                if ( clazz instanceof Listener )
                {
                    getServer().getPluginManager().registerEvents( (Listener) clazz, this );
                    getLogger().info( "Registered " + clazz.getClass().getSimpleName() );
                }
            }
        } catch ( IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex )
        {
            getLogger().log( Level.WARNING, "Error while registering listeners", ex );
        }
    }
}
