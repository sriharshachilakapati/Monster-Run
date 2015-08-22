package resources;

import com.shc.silenceengine.core.ResourceLoader;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.graphics.opengl.Texture;

/**
 * @author Sri Harsha Chilakapati
 */
public final class Resources
{
    private static ResourceLoader loader;
    
    public static class Textures
    {
        public static Texture MONSTER;
        public static Texture BACKGROUND;
    }
    
    public static class Sprites
    {
        public static Sprite MONSTER;
    }
    
    public static void init()
    {
        loader = new ResourceLoader();
        
        int monsterTexID = loader.loadResource(Texture.class, "resources/monster.png");
        int backgroundID = loader.loadResource(Texture.class, "resources/background.png"); 
        
        loader.startLoading();
        
        Textures.MONSTER = loader.getResource(monsterTexID);
        Textures.BACKGROUND = loader.getResource(backgroundID);
        
        Sprites.MONSTER = new Sprite(Textures.MONSTER);
    }
    
    public static void dispose()
    {
        loader.dispose();
    }
}
