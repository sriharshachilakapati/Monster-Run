package com.shc.ld33.game.states;

import com.shc.ld33.game.entities.Monster;
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
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    private float bgX;
    
    private OrthoCam        camera;
    private Scene2D         scene;
    private SceneCollider2D collider;

    public PlayState()
    {
        camera = new OrthoCam(Display.getWidth(), Display.getHeight());
        
        scene = new Scene2D();
        scene.addChild(new Monster(new Vector2(200, 300)));
        
        collider = new SceneCollider2D(new DynamicTree2D());
    }

    @Override
    public void update(float delta)
    {
        if (Keyboard.isPressed(Keyboard.KEY_ESCAPE))
            Game.end();
        
        scene.update(delta);
//        collider.checkCollisions();
        
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
    }
    
    @Override
    public void onLeave()
    {
        scene.destroy();
    }
}
