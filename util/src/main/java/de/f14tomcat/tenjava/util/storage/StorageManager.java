package de.f14tomcat.tenjava.util.storage;

import com.google.common.collect.Lists;
import de.f14tomcat.tenjava.util.mineball.MineBallManager;
import de.f14tomcat.tenjava.util.mineball.MineMon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;
import java.util.Locale;

public class StorageManager
{
    private static StorageManager instance;
    private final File folder;

    public StorageManager(File parentFolder)
    {
        StorageManager.instance = this;
        folder = new File( parentFolder, "data" );
    }

    public static StorageManager getInstance()
    {
        return instance;
    }

    public void init()
    {
        if ( !folder.exists() )
        {
            folder.mkdirs();
        }
    }

    public boolean doesDataExist(Player player)
    {
        return new File( folder, player.getName() + ".dat" ).exists();
    }

    public void save(Player player, List<MineMon> mineMons)
    {
        File playerFile = new File( folder, player.getName() + ".dat" );
        if ( playerFile.exists() )
        {
            playerFile.delete();
        }
        StringBuilder builder = new StringBuilder();
        for ( MineMon mineMon : mineMons )
        {
            if ( !builder.toString().isEmpty() )
            {
                builder.append( "\n" );
            }
            builder.append( mineMon.toString() );
        }
        try
        {
            BufferedWriter bw = new BufferedWriter( new FileWriter( playerFile, false ) );
            bw.write( builder.toString() );
            bw.flush();
            bw.close();
        } catch ( IOException ex )
        {
            ex.printStackTrace();
        }
    }

    public void delete(Player player)
    {
        File playerFile = new File( folder, player.getName() + ".dat" );
        playerFile.delete();
    }

    public List<MineMon> load(Player player)
    {
        File playerFile = new File( folder, player.getName() + ".dat" );
        List<MineMon> mineMons = Lists.newArrayListWithCapacity( MineBallManager.HOLDER_MAX );
        try
        {
            BufferedReader br = new BufferedReader( new FileReader( playerFile ) );
            String line;
            while ( (line = br.readLine()) != null ) {
                mineMons.add( new MineMon( EntityType.valueOf( line.toUpperCase( Locale.ENGLISH ) ) ) );
            }
            br.close();
        } catch ( IOException ex )
        {
            ex.printStackTrace();
        }
        return mineMons;
    }
}
