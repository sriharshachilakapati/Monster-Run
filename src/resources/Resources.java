package resources;

import com.shc.silenceengine.audio.Sound;
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
        public static Texture THORNS;
        public static Texture FLOOR;
        public static Texture GRASS;
        public static Texture CLOUD;
        public static Texture LOGO;
    }
    
    public static class Sprites
    {
        public static Sprite MONSTER;
        public static Sprite THORNS;
        public static Sprite FLOOR;
        public static Sprite GRASS;
        public static Sprite CLOUD;
    }
    
    public static class Sounds
    {
        public static Sound MUSIC;
        public static Sound JUMP;
        public static Sound GAME_OVER;
    }
    
    public static void init()
    {
        loader = new ResourceLoader();
        
        int monsterTexID = loader.loadResource(Texture.class, "resources/monster.png");
        int backgroundID = loader.loadResource(Texture.class, "resources/background.png"); 
        int thornsTexID = loader.loadResource(Texture.class, "resources/thorns.png");
        int floorTexID = loader.loadResource(Texture.class, "resources/dirt_floor.png");
        int grassTexID = loader.loadResource(Texture.class, "resources/grassLayer.png");
        int cloudTexID = loader.loadResource(Texture.class, "resources/cloud.png");
        int logoTexID = loader.loadResource(Texture.class, "resources/gameLogo.png");
        
        int musicSndID = loader.loadResource(Sound.class, "resources/music.wav");
        int jumpSndID = loader.loadResource(Sound.class, "resources/jump.wav");
        int gameOverSndID = loader.loadResource(Sound.class, "resources/gameOver.wav");
        
        loader.startLoading();
        
        Textures.MONSTER = loader.getResource(monsterTexID);
        Textures.BACKGROUND = loader.getResource(backgroundID);
        Textures.THORNS = loader.getResource(thornsTexID);
        Textures.FLOOR = loader.getResource(floorTexID);
        Textures.GRASS = loader.getResource(grassTexID);
        Textures.CLOUD = loader.getResource(cloudTexID);
        Textures.LOGO = loader.getResource(logoTexID);
        
        Sounds.MUSIC = loader.getResource(musicSndID);
        Sounds.JUMP = loader.getResource(jumpSndID);
        Sounds.GAME_OVER = loader.getResource(gameOverSndID);
        
        Sprites.MONSTER = new Sprite(Textures.MONSTER);
        Sprites.THORNS = new Sprite(Textures.THORNS);
        Sprites.FLOOR = new Sprite(Textures.FLOOR);
        Sprites.GRASS = new Sprite(Textures.GRASS);
        Sprites.CLOUD = new Sprite(Textures.CLOUD);
        
        Sounds.MUSIC.setLooping(true);
        Sounds.MUSIC.play();
    }
    
    public static void dispose()
    {
        loader.dispose();
    }
}
