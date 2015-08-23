package com.shc.ld33.game.states;

import com.shc.ld33.game.entities.Cloud;
import com.shc.ld33.game.entities.Floor;
import com.shc.ld33.game.entities.Grass;
import com.shc.ld33.game.entities.Monster;
import com.shc.ld33.game.entities.Thorns;
import com.shc.silenceengine.collision.broadphase.DynamicTree2D;
import com.shc.silenceengine.collision.colliders.SceneCollider2D;
import com.shc.silenceengine.core.Display;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Batcher;
import com.shc.silenceengine.graphics.Graphics2D;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Scene2D;
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.MathUtils;
import com.shc.silenceengine.utils.TimeUtils;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    public static int SCORE;
    public static int BEST_SCORE;
    
    private float bgX;

    private OrthoCam        camera;
    private Scene2D         scene;
    private SceneCollider2D collider;

    private GameTimer entitySpawnTimer;
    
    private int platformsCreated;

    public PlayState()
    {
        SCORE = 0;
        platformsCreated = 0;
        
        camera = new OrthoCam(Display.getWidth(), Display.getHeight());

        scene = new Scene2D();
        scene.addChild(new Monster(new Vector2(200, 300)));

        spawnEntities(0, false);
        spawnEntities(800, false);

        collider = new SceneCollider2D(new DynamicTree2D());
        collider.setScene(scene);

        collider.register(Monster.class, Floor.class);
        collider.register(Monster.class, Grass.class);
        collider.register(Monster.class, Thorns.class);

        entitySpawnTimer = new GameTimer(3.5, TimeUtils.Unit.SECONDS);
        entitySpawnTimer.setCallback(() ->
        {
            if (Game.getGameState() == this)
            {
                spawnEntities(800, true);
                entitySpawnTimer.start();
            }
        });
        entitySpawnTimer.start();
    }

    private void spawnEntities(int startX, boolean placeThorns)
    {
        platformsCreated++;
        
        int height = MathUtils.random_range(400, 580);
        int width = MathUtils.random_range(300, 700);
        
        scene.addChild(new Cloud(new Vector2(startX, height - 400)));
        
        for (int i = startX, tileCounter = 0; i < startX + width; i += Resources.Textures.FLOOR.getWidth(), tileCounter++)
        {
            if (placeThorns && tileCounter > 1 && MathUtils.chance(10))
                scene.addChild(new Thorns(new Vector2(i, height - Resources.Textures.THORNS.getHeight())));
            
            scene.addChild(new Grass(new Vector2(i, height - 20)));
            
            for (int j = height; j < 600; j += Resources.Textures.FLOOR.getHeight())
                scene.addChild(new Floor(new Vector2(i, j), platformsCreated));
        }
    }

    @Override
    public void update(float delta)
    {
        if (Keyboard.isPressed(Keyboard.KEY_ESCAPE))
            Game.setGameState(new IntroState());

        BEST_SCORE = Math.max(BEST_SCORE, SCORE);
        
        scene.update(delta);
        collider.checkCollisions();
        
        bgX -= 4;
        
        if (bgX < -Display.getWidth())
            bgX = 0;
    }

    @Override
    public void render(float delta, Batcher batcher)
    {
        Graphics2D g2d = SilenceEngine.graphics.getGraphics2D();        
        
        g2d.drawTexture(Resources.Textures.BACKGROUND, bgX, 0, Display.getWidth(), Display.getHeight());
        if (bgX < 0)
            g2d.drawTexture(Resources.Textures.BACKGROUND, bgX + Display.getWidth(), 0, Display.getWidth(), Display.getHeight());

        camera.apply();
        scene.render(delta);
        
        g2d.drawString("SCORE: " + SCORE + "\nBEST: " + BEST_SCORE, 10, 10);
    }
    
    @Override
    public void onLeave()
    {
        scene.destroy();
    }
}
